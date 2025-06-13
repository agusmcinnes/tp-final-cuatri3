package model;

import enums.CategoriaJuego;
import interfaces.ObtenerID;

public class Producto implements ObtenerID {
    private String nombre;
    private int id;
    private static int numId = 0;
    private int precio;
    private CategoriaJuego categoria;
    private String descripcion;

    public Producto(String nombre, CategoriaJuego categoria, String descripcion, int precio) {
        this.nombre = nombre;
        this.id = numId++;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static int getNumId() {
        return numId;
    }

    public static void setNumId(int numId) {
        Producto.numId = numId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoriaJuego getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaJuego categoria) {
        this.categoria = categoria;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


}
