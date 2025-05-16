package com.mantenimiento.test.test_cases.docs.DocsCP_015.newVersion;

public class GestorTareas {
    public void agregarTarea(String tarea) {
        System.out.println("Tarea agregada: " + tarea);
    }

    public void eliminarTarea(String tarea) {
        System.out.println("Tarea eliminada: " + tarea);
    }

    public void mostrarTareaDetallada(String tarea, String descripcion) {
        System.out.println("Tarea: " + tarea + " | Descripción detallada: " + descripcion + ". Esta línea es extremadamente larga y debe ser dividida por el formateador automático.");
    }
}

