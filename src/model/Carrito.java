package model;

import exceptions.ProductoNoEncontrado;
import interfaces.ICarrito;

import java.util.HashMap;
import java.util.Map;

public class Carrito implements ICarrito {
    private Map<Producto, Integer> productos;

    public Carrito() {
        productos = new HashMap<>();
    }

    public boolean eliminarProducto(Producto producto) {

        if (productos.containsKey(producto)) {
            productos.remove(producto);
            return true;
        }
        else throw new ProductoNoEncontrado("El producto no existe en el carrito");


    }

    public boolean agregarProducto(int id){
        if (productos.containsKey(producto)) {
            productos.put(producto, productos.get(producto) + 1);
            return true;
        }

    }

    int calcularTotal(){}

    int cantProductos(){}


}
