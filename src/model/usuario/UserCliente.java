package model.usuario;

import enums.TipoUsuario;
import model.Carrito;
import model.Orden;
import model.Producto;

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

    public UserCliente (String gmail, String contraseña, int id){
        super(gmail, contraseña, id, TipoUsuario.CLIENTE);
        this.carrito = new Carrito();
        this.historial = new ArrayList<>();
    }

    public Carrito getCarrito() {
        return carrito;
    }
    public List<Producto> getJuegosComprados() {
        List<Producto> juegos = new ArrayList<>();

        for (Orden o : historial) {
            // Sin duplicados: evitamos añadir si ya existe ese ID
            for (Producto p : o.getProductos()) {
                if (juegos.stream().noneMatch(j -> j.getId() == p.getId())) {
                    juegos.add(p);
                }
            }
        }
        return juegos;
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
