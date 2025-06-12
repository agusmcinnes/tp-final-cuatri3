package service;

import exceptions.DatosIncompletosException;
import exceptions.ProductoNoEncontrado;
import interfaces.ObtenerID;
import model.Producto;

import java.util.ArrayList;
import java.util.List;

public class GestionCrud<T extends ObtenerID> {

    protected List<T> elementos;

    public GestionCrud() {
        this.elementos = new ArrayList<>();
    }

    // Agregar un elemento
    public void agregarProducto(T producto) {
        // Validación solo si T es Producto
        if (producto instanceof Producto) {
            Producto p = (Producto) producto;
            if (p.getNombre() == null || p.getNombre().isBlank()
                    || p.getPrecio() <= 0
                    || p.getDescripcion() == null || p.getDescripcion().isBlank()) {
                throw new DatosIncompletosException("No se puede agregar el producto: datos incompletos o inválidos.");
            }
        }

        elementos.add(producto);
    }

    public void eliminarProducto(int id) {
        T encontrado = buscarPorId(id);
        if (encontrado != null) {
            elementos.remove(encontrado);
        } else {
            throw new ProductoNoEncontrado("No se encontró el producto con ID " + id);
        }
    }

    public void modificarProducto(int id, T nuevoProducto) {
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).getId() == id) {
                elementos.set(i, nuevoProducto);
                return;
            }
        }
        throw new ProductoNoEncontrado("No se encontró el producto con ID " + id + " para modificar");
    }

    public void mostrarInventario() {
        if (elementos.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        for (T p : elementos) {
            System.out.println(p.toString());
        }
    }

    private T buscarPorId(int id) {
        for (T e : elementos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<T> getElementos() {
        return elementos;
    }
}
