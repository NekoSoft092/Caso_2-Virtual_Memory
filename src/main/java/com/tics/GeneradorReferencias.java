package com.tics;

import java.io.IOException;
import java.util.Objects;

public class GeneradorReferencias {
    @FunctionalInterface
    public interface DestinoReferencia {
        void escribir(Referencia referencia) throws IOException;
    }

    private final MapaMemoria mapaMemoria;

    public GeneradorReferencias(MapaMemoria mapaMemoria) {
        this.mapaMemoria = Objects.requireNonNull(mapaMemoria);
    }

    public static void cifrar(byte[][] m, byte[] v, int numPasadas) {
        int filas = m.length;
        int columnas = m[0].length;

        for (int pasada = 0; pasada < numPasadas; pasada++) {
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    m[i][j] = (byte) ((m[i][j] + v[j % v.length]) & 0xFF);
                }
            }

            for (int j = 0; j < columnas; j++) {
                for (int i = 0; i < filas; i++) {
                    m[i][j] = (byte) ((m[i][j] ^ v[i % v.length]) & 0xFF);
                }
            }
        }
    }

    public long cantidadReferencias(Parametros parametros) {
        Objects.requireNonNull(parametros);
        if (parametros.filas() <= 0 || parametros.columnas() <= 0
                || parametros.tamanoVectorClave() <= 0 || parametros.numeroPasadas() <= 0) {
            throw new IllegalArgumentException("Las dimensiones, el vector y el número de pasadas deben ser positivos.");
        }

        long elementosMatriz = (long) parametros.filas() * parametros.columnas();
        return Math.multiplyExact(Math.multiplyExact(elementosMatriz, 6L), parametros.numeroPasadas());
    }

    public void generar(Parametros parametros, DestinoReferencia destino) throws IOException {
        Objects.requireNonNull(parametros);
        Objects.requireNonNull(destino);
        cantidadReferencias(parametros);

        for (int pasada = 0; pasada < parametros.numeroPasadas(); pasada++) {
            recorridoPorFilas(parametros, destino);
            recorridoPorColumnas(parametros, destino);
        }
    }

    private void recorridoPorFilas(Parametros parametros, DestinoReferencia destino) throws IOException {
        for (int i = 0; i < parametros.filas(); i++) {
            for (int j = 0; j < parametros.columnas(); j++) {
                emitirElemento(i, j, j % parametros.tamanoVectorClave(), destino);
            }
        }
    }

    private void recorridoPorColumnas(Parametros parametros, DestinoReferencia destino) throws IOException {
        for (int j = 0; j < parametros.columnas(); j++) {
            for (int i = 0; i < parametros.filas(); i++) {
                emitirElemento(i, j, i % parametros.tamanoVectorClave(), destino);
            }
        }
    }

    private void emitirElemento(int fila, int columna, int indiceVector, DestinoReferencia destino)
            throws IOException {
        destino.escribir(mapaMemoria.referenciaMatriz(fila, columna));
        destino.escribir(mapaMemoria.referenciaVector(indiceVector));
        destino.escribir(mapaMemoria.referenciaMatriz(fila, columna));
    }
}
