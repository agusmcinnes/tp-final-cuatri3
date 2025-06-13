package menu;

import exceptions.OpcionInvalidaException;
import model.usuario.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import model.Producto;
import org.json.*;
import service.JsonUtiles;
import model.usuario.Sesion;
import model.usuario.UserCliente;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private static final String ARCHIVO_PRODUCTOS = "src/productos.json";

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    
                    ╔══════════════════════════════╗
                    ║        MENU CLIENTE          ║
                    ╚══════════════════════════════╝
                    1) Ver juegos disponibles
                    2) Agregar juego al carrito
                    3) Sacar juego del carrito
                    4) Ver carrito
                    5) Finalizar compra
                    6) Ver juegos adquiridos
                    0) Cerrar sesión""");
            System.out.print("Seleccione: ");

            int op;
            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                op = -1;
            }

            try {

                Usuario cliente = Sesion.getUsuarioActual();

                switch (op) {
                    case 1 -> verJuegosDisponibles();
                    case 2 -> agregarAlCarrito(scanner);
                    case 3 -> sacarDelCarrito(scanner);
                    case 4 -> verCarrito();
                    case 5 -> finalizarCompra();
                    case 6 -> verJuegosAdquiridos();
                    case 0 -> {
                        System.out.println("👋 Sesión cerrada.");
                        return;
                    }
                    default -> throw new OpcionInvalidaException("Opción " + op + " no válida.");
                }
            } catch (OpcionInvalidaException e) {
                System.out.println("❌ " + e.getMessage() + "\n");
            }
        }
    }

    private static void verJuegosDisponibles() {
        try {
            JSONArray productos = leerProductos();
            if (productos.length() == 0) {
                System.out.println("📭 No hay juegos disponibles.\n");
                return;
            }

            System.out.println("\n🎮 JUEGOS DISPONIBLES");
            for (int i = 0; i < productos.length(); i++) {
                JSONObject p = productos.getJSONObject(i);
                System.out.println("🔹 " + p.getString("nombre"));
                System.out.println("   🧾 Descripción: " + p.getString("descripcion"));
                System.out.println("   💰 Precio: $" + p.getInt("precio"));
                System.out.println("   🏷️ Categoría: " + p.getString("categoria"));
                System.out.println("   🆔 ID: " + p.getInt("id"));
                System.out.println("----------------------------------");
            }
            System.out.println();
        } catch (JSONException e) {
            System.out.println("❌ Error al leer los juegos: " + e.getMessage() + "\n");
        }
    }

    private static void agregarAlCarrito(Scanner scanner) {
        try {
            JSONArray productos = leerProductos();

            System.out.println("🛒 Agregar al carrito");
            System.out.print("Ingrese el ID del juego que desea agregar: ");
            int idBuscado = Integer.parseInt(scanner.nextLine());

            JSONObject obj = null;

            for (int i = 0; i < productos.length(); i++) {
                JSONObject actual = productos.getJSONObject(i);
                if (actual.getInt("id") == idBuscado) {
                    obj = actual;
                    break;
                }
            }

            if (obj == null) {
                System.out.println("❌ No se encontró un juego con ese ID.");
                return;
            }

            // Convertir JSON → Producto
            String nombre = obj.getString("nombre");
            String descripcion = obj.getString("descripcion");
            int precio = obj.getInt("precio");
            String categoriaStr = obj.getString("categoria");
            enums.CategoriaJuego categoria = enums.CategoriaJuego.valueOf(categoriaStr.toUpperCase());

            Producto producto = new Producto(nombre, categoria, descripcion, precio);

            Usuario usuario = Sesion.getUsuarioActual();

            if (usuario instanceof UserCliente cliente) {
                cliente.getCarrito().agregarProducto(producto);
            }

            System.out.println("✅ Producto agregado al carrito: " + producto.getNombre());


        } catch (Exception e) {
            System.out.println("⚠ Error al agregar al carrito: " + e.getMessage());
        }
    }



    private static void sacarDelCarrito(Scanner scanner) {}
    private static void verCarrito() {}
    private static void finalizarCompra() {}
    private static void verJuegosAdquiridos(){}


    /*==================== UTILIDADES ================================*/
    private static JSONArray leerProductos() throws JSONException {
        JSONTokener tok = JsonUtiles.leerUnJson(ARCHIVO_PRODUCTOS);
        return (tok == null) ? new JSONArray() : new JSONArray(tok);
    }
}
