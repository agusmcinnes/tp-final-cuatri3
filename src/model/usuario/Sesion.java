package model.usuario;

import model.usuario.Usuario;

public class Sesion {

    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static boolean hayUsuarioActivo() {
        return usuarioActual != null;
    }
}
