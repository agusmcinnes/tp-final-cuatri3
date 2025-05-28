package model;

import enums.TipoProducto;

public class Producto {
    private String nombre;
    private int id;
    private static int numId = 0;
    private int stock;
    private int precio;
    private TipoProducto tipo;
    private String descripcion;

    public Producto(String nombre, int stock, TipoProducto tipo, String descripcion, int precio) {
        this.nombre = nombre;
        this.id = numId++;
        this.stock = stock;
        this.precio = precio;
        this.tipo = tipo;
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

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
