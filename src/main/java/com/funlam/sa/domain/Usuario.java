package com.funlam.sa.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Usuario {
    private final int id;
    private String nombre;
    private final List<String> librosPrestados;
    private boolean esMoroso;

    public Usuario(int id, String nombre) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
        this.esMoroso = false;
    }

    public List<String> getLibrosPrestados() {
        return Collections.unmodifiableList(librosPrestados);
    }

    public boolean prestarLibro(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        }
        if (librosPrestados.contains(titulo)) {
            return false;
        }
        librosPrestados.add(titulo);
        return true;
    }

    public boolean devolverLibro(String titulo) {
        return librosPrestados.remove(titulo);
    }
}
