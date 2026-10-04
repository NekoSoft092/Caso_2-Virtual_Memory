package com.tics;

public class MapaMemoria {
    private final int filas;
    private final int columnas;
    private final int tamanoVector;
    private final int tamanoPagina;

    public MapaMemoria(int filas, int columnas, int tamanoVector, int tamanoPagina) {
        if (filas <= 0 || columnas <= 0 || tamanoVector <= 0 || tamanoPagina <= 0) {
            throw new IllegalArgumentException("Las dimensiones, el vector y el tamaño de página deben ser positivos.");
        }

        this.filas = filas;
        this.columnas = columnas;
        this.tamanoVector = tamanoVector;
        this.tamanoPagina = tamanoPagina;
    }

    public Referencia referenciaMatriz(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            throw new IndexOutOfBoundsException("Coordenada de matriz fuera de rango.");
        }

        long direccion = (long) fila * columnas + columna;
        return crearReferencia("mat1-" + fila + "-" + columna, direccion);
    }

    public Referencia referenciaVector(int indice) {
        if (indice < 0 || indice >= tamanoVector) {
            throw new IndexOutOfBoundsException("Índice del vector fuera de rango.");
        }

        long baseVector = (long) filas * columnas;
        long direccion = baseVector + indice;
        return crearReferencia("v-0-" + indice, direccion);
    }

    public long numeroPaginas() {
        long bytesTotales = (long) filas * columnas + tamanoVector;
        return 1 + (bytesTotales - 1) / tamanoPagina;
    }

    private Referencia crearReferencia(String etiqueta, long direccion) {
        return new Referencia(etiqueta, direccion / tamanoPagina, direccion % tamanoPagina);
    }
}
