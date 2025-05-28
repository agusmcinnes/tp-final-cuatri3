package model;

import java.time.LocalDateTime;
import java.util.Map;

public class Orden {
    private Map<Producto, Integer> productos;
    private int id;
    private static int numid = 0;
    private LocalDateTime fecha;

    public Orden(Map<Producto, Integer> productos) {
        this.productos = productos;
        this.id = numid++;
        this.fecha = LocalDateTime.now();
    }

    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            total += p.getPrecio() * cantidad;
        }
        return total;
    }
}
