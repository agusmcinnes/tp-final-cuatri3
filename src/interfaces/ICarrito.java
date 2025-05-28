package interfaces;

import model.Producto;

public interface ICarrito {
    boolean agregarProducto(Producto producto);
    boolean eliminarProducto(Producto producto);
    int calcularTotal();
    int cantProductos();
}
