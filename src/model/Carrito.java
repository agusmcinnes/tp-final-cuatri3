package model;

import exceptions.ProductoNoEncontrado;
import interfaces.ICarrito;
import java.util.HashMap;
import java.util.Map;

public class Carrito implements ICarrito {

    private Map<Producto, Integer> productos;

    public Carrito() {
        this.productos = new HashMap<>();
    }

    @Override
    public boolean agregarProducto(Producto producto) {
        productos.put(producto, productos.getOrDefault(producto, 0) + 1);
        return true;
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        if (!productos.containsKey(producto)) {
            throw new ProductoNoEncontrado("El producto no está en el carrito.");
        }
        productos.remove(producto);
        return true;
    }

    @Override
    public int calcularTotal() {
        int total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            total += p.getPrecio() * cantidad;
        }
        return total;
    }

    @Override
    public int cantProductos() {
        int totalUnidades = 0;
        for (int cantidad : productos.values()) {
            totalUnidades += cantidad;
        }
        return totalUnidades;
    }

    public void mostrarCarrito() {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        System.out.println("=== Productos en el carrito ===");
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println("- " + p.getNombre() + " (x" + cantidad + ") - $" + p.getPrecio());
        }
        System.out.println("Total: $" + calcularTotal());
    }

    public void vaciarCarrito() {
        productos.clear();
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }
}
