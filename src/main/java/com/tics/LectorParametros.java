package com.tics;

import java.util.Scanner;

public class LectorParametros {
    private final Scanner scanner;
    private boolean entradaFinalizada;

    public LectorParametros() {
        this.scanner = new Scanner(System.in);
    }

    public Parametros leer() {
        System.out.println("Ingrese los parámetros (presione Enter para usar el valor de ejemplo):");

        int filas = leerEnteroPositivo("Filas (NF)", 300);
        int columnas = leerEnteroPositivo("Columnas (NC)", 20);
        int tamanoVectorClave = leerEnteroPositivo("Tamaño del vector clave (NV)", 142);
        int tamanoPagina = leerEnteroPositivo("Tamaño de página (TP, en bytes)", 256);
        int numeroPasadas = leerEnteroPositivo("Número de pasadas", 5);
        String archivoSalida = leerArchivoSalida();

        return new Parametros(
                filas,
                columnas,
                tamanoVectorClave,
                tamanoPagina,
                numeroPasadas,
                archivoSalida
        );
    }

    private int leerEnteroPositivo(String nombre, int valorPredeterminado) {
        while (true) {
            if (entradaFinalizada) {
                return valorPredeterminado;
            }

            System.out.printf("%s [%d]: ", nombre, valorPredeterminado);
            String entrada = leerLinea();

            if (entrada == null || entrada.isBlank()) {
                return valorPredeterminado;
            }

            try {
                int valor = Integer.parseInt(entrada.trim());
                if (valor > 0) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Se vuelve a solicitar el valor para permitir corregir la entrada.
            }

            System.out.println("Ingrese un número entero mayor que cero.");
        }
    }

    private String leerArchivoSalida() {
        String valorPredeterminado = "salida.txt";
        if (entradaFinalizada) {
            return valorPredeterminado;
        }

        while (true) {
            System.out.printf("Nombre del archivo de salida [%s]: ", valorPredeterminado);
            String linea = leerLinea();
            if (linea == null) {
                return valorPredeterminado;
            }
            String entrada = linea.trim();

            if (entrada.isEmpty()) {
                return valorPredeterminado;
            }
            return entrada;
        }
    }

    private String leerLinea() {
        if (!scanner.hasNextLine()) {
            entradaFinalizada = true;
            System.out.println("\nNo hay más entrada; se usarán los valores de ejemplo restantes.");
            return null;
        }
        return scanner.nextLine();
    }
}
