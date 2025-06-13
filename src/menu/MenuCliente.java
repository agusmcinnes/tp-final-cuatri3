package menu;

import enums.CategoriaJuego;
import exceptions.OpcionInvalidaException;
import exceptions.ProductoNoEncontrado;
import model.Carrito;
import model.Orden;
import model.Producto;
import model.usuario.Sesion;
import model.usuario.UserCliente;
import org.json.*;
import service.JsonUtiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private static final String ARCHIVO_PRODUCTOS = "src/productos.json";

    /*==================================================================*/
    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        UserCliente cliente = (UserCliente) Sesion.getUsuarioActual();
        Carrito carrito = cliente.getCarrito();

        while (true) {
            System.out.println("""
                    
                    ╔══════════════════════════════╗
                    ║        MENU CLIENTE          ║
                    ╚══════════════════════════════╝
                    1) Ver juegos disponibles
                    2) Agregar juego al carrito
                    3) Quitar juego del carrito
                    4) Ver carrito
                    5) Finalizar compra
                    6) Ver juegos adquiridos
                    0) Cerrar sesión""");
            System.out.print("Seleccione: ");

            int op;
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                op = -1;
            }

            try {
                switch (op) {
                    case 1 -> verJuegosDisponibles();
                    case 2 -> agregarAlCarrito(sc, carrito,cliente);
                    case 3 -> quitarDelCarrito(sc, carrito);
                    case 4 -> carrito.mostrarCarrito();
                    case 5 -> finalizarCompra(cliente, carrito);
                    case 6 -> verJuegosAdquiridos(cliente);
                    case 0 -> {System.out.println("👋 Sesión cerrada."); Sesion.cerrarSesion(); Menu.mostrarLogin();}
                    default -> throw new OpcionInvalidaException("Opción " + op + " no válida.");
                }
            } catch (OpcionInvalidaException | ProductoNoEncontrado e) {
                System.out.println("❌ " + e.getMessage() + "\n");
            } catch (JSONException e) {
                System.out.println("⚠ Error de datos: " + e.getMessage());
            }
        }
    }

    /*==================== 1. VER DISPONIBLES ==========================*/
    private static void verJuegosDisponibles() throws JSONException {
        JSONArray productos = leerProductos();
        if (productos.length() == 0) {
            System.out.println("📭 No hay juegos disponibles.\n");
            return;
        }
        System.out.println("\n🎮 JUEGOS DISPONIBLES");
        for (int i = 0; i < productos.length(); i++) {
            JSONObject p = productos.getJSONObject(i);
            System.out.printf("🔹 %s | $%d | %s | ID:%d%n   %s%n",
                    p.getString("nombre"),
                    p.getInt("precio"),
                    p.getString("categoria"),
                    p.getInt("id"),
                    p.getString("descripcion"));
            System.out.println("----------------------------------");
        }
        System.out.println();
    }

    /*==================== 2. AGREGAR AL CARRITO =======================*/
    private static void agregarAlCarrito(Scanner sc, Carrito carrito,UserCliente cliente) throws JSONException {
        JSONArray productos = leerProductos();

        System.out.print("Ingrese el ID del juego a agregar: ");
        int id = Integer.parseInt(sc.nextLine());

        if (cliente.yaPoseeJuego(id)) {
            System.out.println("⚠ Ya posees la licencia de este juego. No puedes comprarla de nuevo.");
            return;
        }
        JSONObject obj = buscarPorId(productos, id);
        if (obj == null) {
            System.out.println("❌ No existe un juego con ese ID.");
            return;
        }
        Producto juego = jsonToProducto(obj);

        carrito.agregar(juego);
    }

    /*==================== 3. QUITAR DEL CARRITO =======================*/
    private static void quitarDelCarrito(Scanner sc, Carrito carrito) {
        if (carrito.estaVacio()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        System.out.print("ID del juego a quitar: ");
        int id = Integer.parseInt(sc.nextLine());
        carrito.eliminar(id);
    }

    /*==================== 5. FINALIZAR COMPRA =========================*/
    private static void finalizarCompra(UserCliente cliente, Carrito carrito) {
        if (carrito.estaVacio()) {
            System.out.println("🛒 El carrito está vacío.\n");
            return;
        }

        List<Producto> items = new ArrayList<>(carrito.getElementos());

        Orden orden = new Orden(items);

        cliente.agregarOrden(orden);
        cliente.getJuegosComprados().addAll(items);

        carrito.vaciar();

        System.out.println("""
            ✅ Compra realizada con éxito.
            %s
            Total pagado: $%d
            """.formatted(orden, orden.calcularTotal()));
    }

    /*==================== 6. VER COMPRADOS ============================*/
    private static void verJuegosAdquiridos(UserCliente cliente) {
        List<Producto> juegos = cliente.getJuegosComprados();
        if (juegos.isEmpty()) {
            System.out.println("Aún no has comprado ningún juego.\n");
            return;
        }
        System.out.println("🎉 Tus juegos adquiridos:");
        juegos.forEach(j -> System.out.println("• " + j.getNombre()));
        System.out.println();
    }

    /*==================== UTILIDADES ================================*/
    private static JSONArray leerProductos() throws JSONException {
        JSONTokener tok = JsonUtiles.leerUnJson(ARCHIVO_PRODUCTOS);
        return (tok == null) ? new JSONArray() : new JSONArray(tok);
    }

    private static JSONObject buscarPorId(JSONArray arr, int id) throws JSONException {
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).getInt("id") == id) return arr.getJSONObject(i);
        }
        return null;
    }

    private static Producto jsonToProducto(JSONObject o) throws JSONException {
        return new Producto(
                o.getString("nombre"),
                CategoriaJuego.valueOf(o.getString("categoria").toUpperCase()),
                o.getString("descripcion"),
                o.getInt("precio"),
                o.getInt("id")
        );
    }
}
