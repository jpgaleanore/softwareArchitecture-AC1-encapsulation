# Sistema de Biblioteca – Análisis y Mejora de Encapsulamiento

## Presentación del Problema
Este proyecto simula la gestión básica de una biblioteca con libros, usuarios y un sistema de préstamos.  
Aunque funcional, el código original y su refactor tienen problemas de **encapsulamiento** que permiten modificar datos críticos sin validación, lo que puede generar inconsistencias.

---

## Fase de Análisis

### 1. Dónde se viola el principio de encapsulamiento
En el código refactorizado, los atributos ya no son públicos, pero:
- Se exponen las colecciones (`coleccionLibros` y `miembros`) a través de getters.
- Los setters (`setStock()`, `setEsMoroso()`) permiten modificar datos sin validación.
- Se puede acceder a las listas internas (`getLibrosPrestados()`) y modificarlas libremente.

Ejemplos de violación:
```java
miBiblioteca.getColeccionLibros().get("El Señor de los Anillos").setStock(-2);
miBiblioteca.getMiembros().get(usuario1.getId()).setEsMoroso(true);
```

---

### 2. Clases principales
- **Biblioteca:** administra colecciones de libros y usuarios.
- **Libro:** representa un libro con título, autor, stock y disponibilidad.
- **Usuario:** representa un usuario con datos personales, libros prestados y estado de morosidad.
- **Main:** ejecuta la aplicación.

---

### 3. Cómo se manipulan los datos
Actualmente se hace de forma directa desde `Main`, por ejemplo:
```java
miBiblioteca.getColeccionLibros().get("1984").setStock(libro2.getStock() - 1);
miBiblioteca.getMiembros().get(usuario2.getId()).getLibrosPrestados().add("1984");
```
---

### 4. Diagrama de Clases UML – Estado Actual

```text
+------------------+
|     Biblioteca   |
+------------------+
| - coleccionLibros: Map<String,Libro> |
| - miembros: Map<Integer,Usuario>     |
+------------------+
| + agregarLibro(libro: Libro)         |
| + registrarUsuario(usuario: Usuario) |
| + getColeccionLibros(): Map          |
| + getMiembros(): Map                 |
+------------------+

+-------------+
|   Libro     |
+-------------+
| - titulo: String     |
| - autor: String      |
| - stock: int         |
| - disponible: boolean|
+-------------+
| + getTitulo()        |
| + getAutor()         |
| + getStock()         |
| + setStock(int)      |
| + isDisponible()     |
| + setDisponible(boolean) |
+-------------+

+-------------+
|  Usuario    |
+-------------+
| - id: int                |
| - nombre: String          |
| - librosPrestados: List<String> |
| - esMoroso: boolean       |
+-------------+
| + getId()                 |
| + getNombre()             |
| + getLibrosPrestados()    |
| + setEsMoroso(boolean)    |
| + isEsMoroso()            |
+-------------+
```

---

### 5. Problemas detectados y riesgos

| Problema | Descripción | Riesgo |
|----------|-------------|--------|
| Setters sin validación | Permiten valores inválidos (ej. stock negativo) | Inconsistencia de datos |
| Exposición de listas reales | Se pueden modificar sin control | Pérdida de integridad |
| Lógica de negocio en `Main` | No hay centralización | Código difícil de mantener |
| Acceso directo a colecciones | Se pueden manipular objetos sin pasar por reglas | Violación de encapsulamiento |

---

### Nuevo Diagrama de Clases UML

```text
+------------------+
|     Biblioteca   |
+------------------+
| - coleccionLibros: Map<String,Libro> |
| - miembros: Map<Integer,Usuario>     |
+------------------+
| + agregarLibro(libro: Libro)         |
| + registrarUsuario(usuario: Usuario) |
| + prestarLibro(usuarioId: int, titulo: String) |
| + marcarUsuarioMoroso(usuarioId: int) |
| + obtenerLibrosPrestados(usuarioId: int): List<String> |
+------------------+

+-------------+
|   Libro     |
+-------------+
| - titulo: String     |
| - autor: String      |
| - stock: int         |
| - disponible: boolean|
+-------------+
| + getTitulo()        |
| + getAutor()         |
| + getStock()         |
| - setStock(int)      |
| + reducirStock()     |
| + aumentarStock()    |
| + isDisponible()     |
+-------------+

+-------------+
|  Usuario    |
+-------------+
| - id: int                |
| - nombre: String          |
| - librosPrestados: List<String> |
| - esMoroso: boolean       |
+-------------+
| + getId()                 |
| + getNombre()             |
| + isEsMoroso()            |
| + agregarLibroPrestado(String) |
| + devolverLibro(String)   |
| - setEsMoroso(boolean)    |
+-------------+
```

## Conclusión
El principio de **encapsulamiento** no se trata solo de usar `private`, sino de **proteger el estado interno** para que solo pueda modificarse de manera controlada.  
Con el rediseño propuesto, el sistema es más robusto, mantenible y seguro frente a errores lógicos.
