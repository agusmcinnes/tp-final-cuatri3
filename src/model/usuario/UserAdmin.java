package model.usuario;

import model.Producto;
import enums.TipoUsuario;
import model.Orden;

import java.util.ArrayList;
import java.util.List;

public class UserAdmin extends Usuario {

    public UserAdmin(String gmail, String contraseña) {
        super(gmail, contraseña, TipoUsuario.ADMINISTRADOR);
    }

    public UserAdmin (String gmail, String contraseña, int id){
        super(gmail, contraseña, id, TipoUsuario.ADMINISTRADOR);
    }
}
