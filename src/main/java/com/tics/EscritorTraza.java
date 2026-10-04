package com.tics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class EscritorTraza implements AutoCloseable {
    private final BufferedWriter escritor;

    public EscritorTraza(String nombreArchivo) throws IOException {
        Path rutaSalida = rutaSalida(nombreArchivo);
        Files.createDirectories(rutaSalida.getParent());
        this.escritor = Files.newBufferedWriter(rutaSalida, StandardCharsets.UTF_8);
    }

    static Path rutaSalida(String nombreArchivo) {
        Objects.requireNonNull(nombreArchivo);
        Path nombre = Path.of(nombreArchivo).getFileName();
        if (nombre == null || nombre.toString().isBlank()) {
            throw new IllegalArgumentException("El nombre del archivo de salida no puede estar vacío.");
        }
        return Path.of("output").resolve(nombre);
    }

    public void escribirCabecera(
            int tamanoPagina,
            int filas,
            int columnas,
            int tamanoVector,
            int numeroPasadas,
            long cantidadReferencias,
            long numeroPaginas
    ) throws IOException {
        escribirLinea("TP=" + tamanoPagina);
        escribirLinea("NF=" + filas);
        escribirLinea("NC=" + columnas);
        escribirLinea("NV=" + tamanoVector);
        escribirLinea("numPasadas=" + numeroPasadas);
        escribirLinea("NR=" + cantidadReferencias);
        escribirLinea("NP=" + numeroPaginas);
    }

    public void escribirReferencia(Referencia referencia) throws IOException {
        Objects.requireNonNull(referencia);
        escribirLinea("[" + referencia.etiqueta() + "],"
                + referencia.pagina() + "," + referencia.desplazamiento());
    }

    private void escribirLinea(String linea) throws IOException {
        escritor.write(linea);
        escritor.newLine();
    }

    @Override
    public void close() throws IOException {
        escritor.close();
    }
}
