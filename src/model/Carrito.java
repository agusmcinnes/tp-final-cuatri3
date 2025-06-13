package model;

import exceptions.ProductoNoEncontrado;
import service.GestionCrud;

public class Carrito extends GestionCrud<Producto> {

    public void agregar(Producto p) {
        if (buscarPorId(p.getId()) != null) {
            System.out.println("⚠ El juego \"" + p.getNombre() + "\" ya está en el carrito.");
            return;
        }
        super.agregar(p);
        System.out.println("✅ \"" + p.getNombre() + "\" agregado al carrito.");
    }


    public void eliminar(int idProducto) {
        super.eliminar(idProducto);
        System.out.println("🗑 Producto eliminado del carrito.");
    }

    public void vaciar() {
        elementos.clear();
    }

    public int calcularTotal() {
        int price = 0;
        for (Producto p : elementos) {
            price += p.getPrecio();
        }
        return price;
    }

    public int cantProductos() {
        return elementos.size();
    }

    public boolean estaVacio() {
        return elementos.isEmpty();
    }

    public void mostrarCarrito() {
        if (estaVacio()) {
            System.out.println("🛒 El carrito está vacío.");
            return;
        }
        System.out.println("=== Tu carrito ===");
        elementos.forEach(p ->
                System.out.println("- " + p.getNombre() + " — $" + p.getPrecio()));
        System.out.println("Total a pagar: $" + calcularTotal());
    }
}
