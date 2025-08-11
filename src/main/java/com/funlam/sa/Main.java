package com.funlam.sa;

import com.funlam.sa.domain.Biblioteca;
import com.funlam.sa.domain.Libro;
import com.funlam.sa.domain.Usuario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Biblioteca miBiblioteca = new Biblioteca();

        Libro libro1 = new Libro("El Señor de los Anillos", "J.R.R. Tolkien", 10);
        miBiblioteca.agregarLibro(libro1);

        Libro libro2 = new Libro("1984", "George Orwell", 5);
        miBiblioteca.agregarLibro(libro2);

        Usuario usuario1 = new Usuario(1, "Ana");
        miBiblioteca.registrarUsuario(usuario1);

        Usuario usuario2 = new Usuario(2, "Luis");
        miBiblioteca.registrarUsuario(usuario2);

        // Préstamo controlado
        miBiblioteca.prestarLibro("1984", usuario2.getId());

        // Marcar usuario como moroso
        usuario1.setEsMoroso(true);
        miBiblioteca.prestarLibro("El Señor de los Anillos", usuario1.getId());

        // Consultas
        log.info("Stock final de 'El Señor de los Anillos': {}", miBiblioteca.consultarStock("El Señor de los Anillos").orElse(-1));
        log.info("Stock final de '1984': {}", miBiblioteca.consultarStock("1984").orElse(-1));
        log.info("Libros prestados a Luis: {}", usuario2.getLibrosPrestados());
        log.info("Libros prestados a Ana: {}", usuario1.getLibrosPrestados());
    }
}