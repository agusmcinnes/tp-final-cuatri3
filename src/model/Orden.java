package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orden {

    private final List<Producto> productos;
    private final int id;
    private static int numId = 0;
    private final LocalDateTime fecha;

    public Orden(List<Producto> productos) {
        this.productos = new ArrayList<>(productos);
        this.id = numId++;
        this.fecha = LocalDateTime.now();
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public int getId()       { return id; }
    public LocalDateTime getFecha() { return fecha; }

    public int calcularTotal() {
        int total = 0;
        for (Producto p : productos) total += p.getPrecio();
        return total;
    }

    @Override
    public String toString() {
        return "Orden #" + id +
                " (" + fecha.toLocalDate() + ") -> $" + calcularTotal();
    }
}
