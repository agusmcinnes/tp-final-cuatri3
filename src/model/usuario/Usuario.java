package model.usuario;

import enums.TipoUsuario;

import java.util.Objects;

public abstract class Usuario {
    protected static int contadorIds = 1;

    protected int id;
    protected String gmail;
    protected String contraseña;
    protected TipoUsuario rol;

    public Usuario(String gmail, String contraseña, TipoUsuario rol) {
        this.id = contadorIds++;
        this.gmail = gmail;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    public Usuario(String gmail, String contraseña, int id, TipoUsuario rol) {
        this.id = id;
        this.gmail = gmail;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TipoUsuario getRol() {
        return rol;
    }

   // public abstract void getMenu();


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", gmail='" + gmail + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

