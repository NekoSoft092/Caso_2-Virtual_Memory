package com.tics;

public record Parametros(
        int filas,
        int columnas,
        int tamanoVectorClave,
        int tamanoPagina,
        int numeroPasadas,
        String archivoSalida
) {
}
