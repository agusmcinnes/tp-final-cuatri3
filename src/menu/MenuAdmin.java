package menu;

import enums.CategoriaJuego;
import exceptions.OpcionInvalidaException;
import exceptions.ProductoNoEncontrado;
import model.usuario.Sesion;
import model.usuario.UserAdmin;
import org.json.*;
import service.JsonUtiles;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAdmin {

    private static final String ARCHIVO_PRODUCTOS = "src/productos.json";

    /*==================================================================*/
    public static void mostrarMenu(UserAdmin admin) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    
                    ╔══════════════════════════════╗
                    ║        MENU  ADMIN           ║
                    ╚══════════════════════════════╝
                    1) Agregar producto
                    2) Eliminar producto
                    3) Modificar producto
                    4) Ver productos
                    0) Cerrar sesión""");
            System.out.print("Seleccione: ");

            int op;
            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                op = -1;
            }

            try {
                switch (op) {
                    case 1 -> agregarProducto(scanner);
                    case 2 -> eliminarProducto(scanner);
                    case 3 -> modificarProducto(scanner);
                    case 4 -> verProductos();
                    case 0 -> { System.out.println("👋 Sesión cerrada."); Sesion.cerrarSesion(); Menu.mostrarLogin();}
                    default -> throw new OpcionInvalidaException("Opción " + op + " no válida.");
                }
            } catch (ProductoNoEncontrado | OpcionInvalidaException | JSONException e) {
                System.out.println("❌ " + e.getMessage() + "\n");
            }
        }
    }

    /*==================== 1. AGREGAR ==================================*/
    private static void agregarProducto(Scanner sc) {
        try {
            JSONArray productos = leerProductos();
            int nuevoId = siguienteId(productos);

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Precio (ARS): ");
            int precio = Integer.parseInt(sc.nextLine());

            System.out.println("Categoría:");
            for (CategoriaJuego c : CategoriaJuego.values()) {
                System.out.println(" " + c.ordinal() + " → " + c);
            }
            System.out.print("Seleccione: ");
            int idxCat = Integer.parseInt(sc.nextLine());
            CategoriaJuego cat = CategoriaJuego.values()[idxCat];

            System.out.print("Descripción: ");
            String desc = sc.nextLine();

            JSONObject nuevo = new JSONObject()
                    .put("id", nuevoId)
                    .put("nombre", nombre)
                    .put("precio", precio)
                    .put("categoria", cat.toString())
                    .put("descripcion", desc);

            productos.put(nuevo);
            grabarProductos(productos);
            System.out.println("✅ Agregado ID " + nuevoId + ".\n");

        } catch (JSONException | InputMismatchException
                 | ArrayIndexOutOfBoundsException e) {
            System.out.println("❌ Error al agregar: " + e.getMessage() + "\n");
        }
    }

    /*==================== 2. ELIMINAR ================================*/
    private static void eliminarProducto(Scanner sc) throws ProductoNoEncontrado, JSONException {
        JSONArray productos = leerProductos();

        System.out.print("ID a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < productos.length(); i++) {
            if (productos.getJSONObject(i).getInt("id") == id) {
                productos.remove(i);
                grabarProductos(productos);
                System.out.println("🗑 Eliminado.\n");
                return;
            }
        }
        throw new ProductoNoEncontrado("No existe un producto con ID " + id);
    }

    /*==================== 3. MODIFICAR ===============================*/
    private static void modificarProducto(Scanner sc) throws ProductoNoEncontrado, JSONException {
        JSONArray productos = leerProductos();

        System.out.print("ID a modificar: ");
        int id = Integer.parseInt(sc.nextLine());

        JSONObject p = null;
        for (int i = 0; i < productos.length(); i++) {
            if (productos.getJSONObject(i).getInt("id") == id) {
                p = productos.getJSONObject(i);
                break;
            }
        }
        if (p == null) {
            throw new ProductoNoEncontrado("No existe un producto con ID " + id);
        }

        System.out.println("Enter mantiene valor actual.");
        System.out.print("Nombre (" + p.getString("nombre") + "): ");
        String nom = sc.nextLine();
        if (!nom.isBlank()) p.put("nombre", nom);

        System.out.print("Precio (" + p.getInt("precio") + "): ");
        String pre = sc.nextLine();
        if (!pre.isBlank()) p.put("precio", Integer.parseInt(pre));

        System.out.print("Descripción (" + p.getString("descripcion") + "): ");
        String des = sc.nextLine();
        if (!des.isBlank()) p.put("descripcion", des);

        System.out.print("¿Cambiar categoría? (s/N): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            for (CategoriaJuego c : CategoriaJuego.values()) {
                System.out.println(" " + c.ordinal() + " → " + c);
            }
            System.out.print("Seleccione: ");
            int idxCat = Integer.parseInt(sc.nextLine());
            p.put("categoria", CategoriaJuego.values()[idxCat]);
        }

        grabarProductos(productos);
        System.out.println("✏️ Actualizado.\n");
    }

    /*==================== 4. VER =====================================*/
    private static void verProductos() {
        try {
            JSONArray productos = leerProductos();
            if (productos.length() == 0) {
                System.out.println("📭 Sin productos.\n");
                return;
            }
            System.out.println("\n🛒 INVENTARIO");
            for (int i = 0; i < productos.length(); i++) {
                JSONObject p = productos.getJSONObject(i);
                System.out.printf("ID:%d | %-25s | $%d | %s%n",
                        p.getInt("id"),
                        p.getString("nombre"),
                        p.getInt("precio"),
                        p.getString("categoria"));
            }
            System.out.println();
        } catch (JSONException e) {
            System.out.println("❌ Error: " + e.getMessage() + "\n");
        }
    }

    /*==================== UTILIDADES ================================*/
    private static JSONArray leerProductos() throws JSONException {
        JSONTokener tok = JsonUtiles.leerUnJson(ARCHIVO_PRODUCTOS);
        return (tok == null) ? new JSONArray() : new JSONArray(tok);
    }

    private static void grabarProductos(JSONArray arr) {
        JsonUtiles.grabarUnJson(arr, ARCHIVO_PRODUCTOS);
    }

    private static int siguienteId(JSONArray arr) throws JSONException {
        int max = 0;
        for (int i = 0; i < arr.length(); i++) {
            max = Math.max(max, arr.getJSONObject(i).getInt("id"));
        }
        return max + 1;
    }
}
