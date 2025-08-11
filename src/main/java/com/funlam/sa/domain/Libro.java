package com.funlam.sa.domain;

import lombok.Getter;

@Getter
public class Libro {
    private final String titulo;
    private final String autor;
    private int stock;

    public Libro(String titulo, String autor, int stock) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    public boolean estaDisponible() {
        return stock > 0;
    }

    public void prestar() {
        if (stock <= 0) {
            throw new IllegalStateException("No hay stock disponible para prestar");
        }
        stock--;
    }

    public void devolver() {
        stock++;
    }
}
