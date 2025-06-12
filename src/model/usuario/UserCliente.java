package model.usuario;

import enums.TipoUsuario;
import model.Carrito;
import model.Orden;
import java.util.ArrayList;
import java.util.List;

public class UserCliente extends Usuario {
    private Carrito carrito;
    private List<Orden> historial;

    public UserCliente(String gmail, String contraseña) {
        super(gmail, contraseña, TipoUsuario.CLIENTE);
        this.carrito = new Carrito();
        this.historial = new ArrayList<>();
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public List<Orden> getHistorial() {
        return historial;
    }

//    @Override
//    public void getMenu() {
//        MenuCliente menu = new MenuCliente();
//        menu.mostrarMenu();
//    }
}
