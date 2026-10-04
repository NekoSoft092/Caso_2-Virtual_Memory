package com.tics;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        Parametros parametros = new LectorParametros().leer();
        Path archivoSalida = EscritorTraza.rutaSalida(parametros.archivoSalida());

        System.out.println("\nParámetros seleccionados:");
        System.out.printf("Filas (NF): %d%n", parametros.filas());
        System.out.printf("Columnas (NC): %d%n", parametros.columnas());
        System.out.printf("Tamaño del vector clave (NV): %d%n", parametros.tamanoVectorClave());
        System.out.printf("Tamaño de página (TP): %d bytes%n", parametros.tamanoPagina());
        System.out.printf("Número de pasadas: %d%n", parametros.numeroPasadas());
        System.out.printf("Archivo de salida: %s%n", archivoSalida);

        MapaMemoria mapaMemoria = new MapaMemoria(
                parametros.filas(),
                parametros.columnas(),
                parametros.tamanoVectorClave(),
                parametros.tamanoPagina()
        );
        long cantidadReferencias = Math.multiplyExact(
                Math.multiplyExact((long) parametros.filas() * parametros.columnas(), 6L),
                parametros.numeroPasadas()
        );
        long numeroPaginas = mapaMemoria.numeroPaginas();

        try (EscritorTraza escritor = new EscritorTraza(parametros.archivoSalida())) {
            escritor.escribirCabecera(
                    parametros.tamanoPagina(),
                    parametros.filas(),
                    parametros.columnas(),
                    parametros.tamanoVectorClave(),
                    parametros.numeroPasadas(),
                    cantidadReferencias,
                    numeroPaginas
            );
            GeneradorReferencias generador = new GeneradorReferencias(mapaMemoria);
            generador.generar(parametros, escritor::escribirReferencia);
        }

        System.out.printf("Traza generada con %d referencias en %s.%n",
                cantidadReferencias, archivoSalida);
    }
}