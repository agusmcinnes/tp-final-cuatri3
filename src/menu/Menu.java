package menu;

import enums.TipoUsuario;
import exceptions.UsuarioNoEncontrado;
import model.usuario.*;
import org.json.*;
import service.JsonUtiles;

import java.util.Scanner;

public class Menu {

    private static final String ARCHIVO_USUARIOS = "src/usuarios.json";

    /*==================== MENÚ PRINCIPAL ====================*/
    public static void iniciar() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    ╔════════════════════════════╗
                    ║      ESTIM    –  INICIO    ║
                    ╚════════════════════════════╝
                    1) Iniciar sesión
                    2) Registrarse
                    0) Salir""");
            System.out.print("Seleccione: ");

            String op = sc.nextLine().trim();
            switch (op) {
                case "1" -> mostrarLogin(sc);
                case "2" -> registrarUsuario(sc);
                case "0" -> { System.out.println("¡Hasta luego!"); return; }
                default  -> System.out.println("Opción inválida.\n");
            }
        }
    }

    /*==================== 1. LOGIN =========================*/
    private static void mostrarLogin(Scanner sc) {
        boolean loginExitoso = false;

        do {
            System.out.println("\n╔════════════════════════════╗");
            System.out.println(  "║           LOGIN            ║");
            System.out.println(  "╚════════════════════════════╝");

            System.out.print("📧 Gmail: ");
            String gmail = sc.nextLine();

            System.out.print("🔒 Contraseña: ");
            String pass = sc.nextLine();

            try {
                JSONArray usuarios = leerUsuarios();

                for (int i = 0; i < usuarios.length(); i++) {
                    JSONObject u = usuarios.getJSONObject(i);
                    if (gmail.equals(u.getString("gmail")) &&
                            pass.equals(u.getString("contraseña"))) {

                        loginExitoso = true;
                        String rol = u.getString("rol");
                        int id      = u.getInt("id");

                        System.out.println("✅ Login exitoso como " + rol);

                        if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
                            UserAdmin admin = new UserAdmin(gmail, pass, id);
                            Sesion.iniciarSesion(admin);
                            MenuAdmin.mostrarMenu(admin);
                        } else {                                   // CLIENTE por defecto
                            UserCliente cliente = new UserCliente(gmail, pass, id);
                            Sesion.iniciarSesion(cliente);
                            MenuCliente.mostrarMenu();
                        }
                        break;
                    }
                }
                if (!loginExitoso) throw new UsuarioNoEncontrado(
                        "❌ Usuario o contraseña incorrectos.");

            } catch (JSONException e) {
                System.out.println("⚠ Error de lectura JSON: " + e.getMessage());
            } catch (UsuarioNoEncontrado e) {
                System.out.println(e.getMessage());
            }
        } while (!loginExitoso);
    }

    /*==================== 2. REGISTRO ======================*/
    private static void registrarUsuario(Scanner sc) {

        try {
            JSONArray usuarios = leerUsuarios();

            System.out.println("\n📝 REGISTRO DE NUEVO CLIENTE");
            System.out.print("Gmail: ");
            String gmail = sc.nextLine().trim();

            for (int i = 0; i < usuarios.length(); i++) {
                if (gmail.equalsIgnoreCase(usuarios.getJSONObject(i).getString("gmail"))) {
                    System.out.println("⚠ Ya existe un usuario con ese Gmail.\n");
                    return;
                }
            }

            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            int maxId = 0;
            for (int i = 0; i < usuarios.length(); i++) {
                maxId = Math.max(maxId, usuarios.getJSONObject(i).getInt("id"));
            }
            int nuevoId = maxId + 1;

            JSONObject nuevo = new JSONObject()
                    .put("id", nuevoId)
                    .put("gmail", gmail)
                    .put("contraseña", pass)
                    .put("rol", TipoUsuario.CLIENTE);

            usuarios.put(nuevo);
            grabarUsuarios(usuarios);

            System.out.println("✅ Registro exitoso. ¡Ahora podés iniciar sesión!\n");

        } catch (JSONException e) {
            System.out.println("⚠ Error al registrar usuario: " + e.getMessage());
        }
    }

    /*==================== UTILIDADES =======================*/
    private static JSONArray leerUsuarios() throws JSONException {
        JSONTokener tok = JsonUtiles.leerUnJson(ARCHIVO_USUARIOS);
        return (tok == null) ? new JSONArray() : new JSONArray(tok);
    }

    private static void grabarUsuarios(JSONArray usuarios) {
        JsonUtiles.grabarUnJson(usuarios, ARCHIVO_USUARIOS);
    }
}
