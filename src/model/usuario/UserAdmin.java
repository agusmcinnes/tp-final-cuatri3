package model.usuario;

import model.Producto;
import enums.TipoUsuario;
import model.Orden;

import java.util.ArrayList;
import java.util.List;

public class UserAdmin extends Usuario {
    //private GestionCrud<Producto> gestionProductos;
    private List<Orden> ordenesTotales;

    public UserAdmin(String gmail, String contraseña) {
        super(gmail, contraseña, TipoUsuario.ADMINISTRADOR);
      //  this.gestionProductos = new GestionCrud<>();
        this.ordenesTotales = new ArrayList<>();
    }

//    public GestionCrud<Producto> getGestionProductos() {
//        return gestionProductos;
//    }

    public List<Orden> getOrdenesTotales() {
        return ordenesTotales;
    }

//    @Override
//    public void getMenu() {
//        MenuAdmin menu = new MenuAdmin();
//        menu.mostrarMenu();
//    }
}
