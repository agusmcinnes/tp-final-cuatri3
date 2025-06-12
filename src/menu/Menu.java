package menu;

import exceptions.UsuarioNoEncontrado;
import model.usuario.Sesion;
import model.usuario.UserAdmin;
import model.usuario.UserCliente;
import org.json.JSONException;
import service.JsonUtiles;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class Menu {

    public static void mostrarLogin() {
        Scanner scanner = new Scanner(System.in);
        boolean loginExitoso = false;

        do {
            System.out.println("╔════════════════════════════╗");
            System.out.println("║           LOGIN            ║");
            System.out.println("╚════════════════════════════╝");

            System.out.print("📧 Ingrese su Gmail: ");
            String gmail = scanner.nextLine();

            System.out.print("🔒 Ingrese su Contraseña: ");
            String contraseña = scanner.nextLine();

            try {
                JSONArray usuariosJson = new JSONArray(JsonUtiles.leer("src/usuarios"));

                for (int i = 0; i < usuariosJson.length(); i++) {
                    JSONObject obj = usuariosJson.getJSONObject(i);

                    String gmailJson = obj.getString("gmail");
                    String contraseñaJson = obj.getString("contraseña");
                    int idJson = obj.getInt("id");

                    if (gmail.equals(gmailJson) && contraseña.equals(contraseñaJson)) {
                        String rol = obj.getString("rol");
                        loginExitoso = true;

                        System.out.println("✅ Login exitoso como " + rol);

                        if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
                            UserAdmin admin = new UserAdmin(gmailJson, contraseñaJson, idJson);
                            Sesion.iniciarSesion(admin);
                            MenuAdmin.mostrarMenu();
                        } else if (rol.equalsIgnoreCase("CLIENTE")) {
                            UserCliente cliente = new UserCliente(gmailJson, contraseñaJson, idJson);
                            Sesion.iniciarSesion(cliente);
                            MenuCliente.mostrarMenu();
                        } else {
                            System.out.println("❌ Rol desconocido.");
                        }
                        break;
                    }
                }
                if (!loginExitoso) {
                    throw new UsuarioNoEncontrado("❌ Usuario o contraseña incorrectos.");
                }
            } catch (JSONException e) {
                System.out.println("⚠ Error al leer los datos del usuario JSON: " + e.getMessage());
            }
            catch (UsuarioNoEncontrado e){
                System.out.println(e.getMessage());
            }
        } while (loginExitoso!=true);
        scanner.close();
    }
}
