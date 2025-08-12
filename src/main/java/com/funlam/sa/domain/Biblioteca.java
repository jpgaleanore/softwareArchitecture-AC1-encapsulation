package com.funlam.sa.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class Biblioteca {
    private final Map<String, Libro> coleccionLibros;
    private final Map<Integer, Usuario> miembros;

    public Biblioteca() {
        this.coleccionLibros = new HashMap<>();
        this.miembros = new HashMap<>();
    }

    public boolean agregarLibro(Libro libro) {
        if (libro == null || coleccionLibros.containsKey(libro.getTitulo())) {
            return false;
        }
        coleccionLibros.put(libro.getTitulo(), libro);
        log.info("Libro '{}' agregado a la colección.", libro.getTitulo());
        return true;
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || miembros.containsKey(usuario.getId())) {
            return false;
        }
        miembros.put(usuario.getId(), usuario);
        log.info("Usuario '{}' registrado.", usuario.getId());
        return true;
    }

    public boolean prestarLibro(String titulo, int usuarioId) {
        Libro libro = coleccionLibros.get(titulo);
        Usuario usuario = miembros.get(usuarioId);
        if (libro == null || usuario == null) {
            log.warn("Libro o usuario no encontrado.");
            return false;
        }
        if (usuario.isEsMoroso()) {
            log.warn("El usuario {} está moroso y no puede tomar libros.", usuario.getNombre());
            return false;
        }
        if (!libro.estaDisponible()) {
            log.warn("No hay stock disponible para el libro '{}'.", titulo);
            return false;
        }
        libro.prestar();
        usuario.prestarLibro(titulo);
        log.info("El Préstamo fue realizado a {} del libro '{}'.", usuario.getNombre(), titulo);
        return true;
    }

    public boolean devolverLibro(String titulo, int usuarioId) {
        Libro libro = coleccionLibros.get(titulo);
        Usuario usuario = miembros.get(usuarioId);
        if (libro == null || usuario == null) {
            return false;
        }
        if (usuario.devolverLibro(titulo)) {
            libro.devolver();
            log.info("El libro '{}' devuelto por usuario {}.", titulo, usuario.getNombre());
            return true;
        }
        return false;
    }

    public Optional<Integer> consultarStock(String titulo) {
        Libro libro = coleccionLibros.get(titulo);
        return libro != null ? Optional.of(libro.getStock()) : Optional.empty();
    }

    public Optional<Usuario> buscarUsuario(int id) {
        return Optional.ofNullable(miembros.get(id));
    }

    public Optional<Libro> buscarLibro(String titulo) {
        return Optional.ofNullable(coleccionLibros.get(titulo));
    }
}
