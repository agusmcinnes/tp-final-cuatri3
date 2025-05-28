package exceptions;

public class ProductoNoEncontrado extends RuntimeException {
    public ProductoNoEncontrado(String message) {
        super(message);
    }
}
