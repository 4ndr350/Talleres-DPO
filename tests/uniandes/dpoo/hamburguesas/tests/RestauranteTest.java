package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

@DisplayName("Pruebas de la clase Restaurante")
public class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    void setup() {
        restaurante = new Restaurante();
    }

    /** Intenta localizar los archivos de datos probando varias rutas relativas comunes en IDE/CI. */
    private File mustFind(String... candidates) {
        for (String c : candidates) {
            File f = new File(c);
            if (f.exists()) return f;
        }
        fail("No se encontraron los archivos de datos en: " + Arrays.toString(candidates));
        return null;
    }

    @Test
    @DisplayName("cargarInformacionRestaurante carga ingredientes, menú y combos válidos (rutas robustas)")
    void testCargarInformacion() throws Exception {
        File ingredientes = mustFind(
            "Taller 5 Hamburguesas/data/ingredientes.txt",
            "data/ingredientes.txt",
            "../data/ingredientes.txt"
        );
        File menu = mustFind(
            "Taller 5 Hamburguesas/data/menu.txt",
            "data/menu.txt",
            "../data/menu.txt"
        );
        File combos = mustFind(
            "Taller 5 Hamburguesas/data/combos.txt",
            "data/combos.txt",
            "../data/combos.txt"
        );

        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        ArrayList<Ingrediente> ings = restaurante.getIngredientes();
        ArrayList<ProductoMenu> baseMenu = restaurante.getMenuBase();
        ArrayList<Combo> combosList = restaurante.getMenuCombos();

        assertTrue(ings.size() > 0, "No se cargaron ingredientes");
        assertTrue(baseMenu.size() > 0, "No se cargó el menú base");
        assertTrue(combosList.size() > 0, "No se cargaron combos");
    }

    @Test
    @DisplayName("Getters básicos (pedido en curso, listas) funcionan")
    void testGettersBasicos() {
        assertNull(restaurante.getPedidoEnCurso());
        assertNotNull(restaurante.getIngredientes());
        assertNotNull(restaurante.getMenuBase());
        assertNotNull(restaurante.getMenuCombos());
        assertNotNull(restaurante.getPedidos());
    }

    @Test
    @DisplayName("iniciarPedido crea pedido en curso; un segundo inicio lanza excepción")
    void testIniciarPedido() throws Exception {
        restaurante.iniciarPedido("Yesid", "Bogotá");
        Pedido p = restaurante.getPedidoEnCurso();
        assertNotNull(p);
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {restaurante.iniciarPedido("Otro", "Dirección");});
    }

    @Test
    @DisplayName("cerrarYGuardarPedido sin pedido en curso lanza excepción")
    void testCerrarSinPedido() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> restaurante.cerrarYGuardarPedido());
    }

    @Test
    @DisplayName("cerrarYGuardarPedido guarda factura y mueve pedido a histórico")
    void testCerrarYGuardarPedido(@TempDir File tmp) throws Exception {
        
        File ingredientes = mustFind(
            "Taller 5 Hamburguesas/data/ingredientes.txt",
            "data/ingredientes.txt",
            "../data/ingredientes.txt"
        );
        File menu = mustFind(
            "Taller 5 Hamburguesas/data/menu.txt",
            "data/menu.txt",
            "../data/menu.txt"
        );
        File combos = mustFind(
            "Taller 5 Hamburguesas/data/combos.txt",
            "data/combos.txt",
            "../data/combos.txt"
        );
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        
        File carpetaFacturas = new File("facturas");
        if (!carpetaFacturas.exists()) {
            assertTrue(carpetaFacturas.mkdirs(), "No se pudo crear la carpeta ./facturas requerida por la factura.");
        }

        
        restaurante.iniciarPedido("Yesid", "Bogotá");
        ProductoMenu primero = restaurante.getMenuBase().get(0);
        restaurante.getPedidoEnCurso().agregarProducto(primero);

        
        assertDoesNotThrow(() -> {
            try {
                restaurante.cerrarYGuardarPedido();
            } catch (IOException e) {
                fail("Fallo guardando la factura: " + e.getMessage());
            }
        });

        
        assertNull(restaurante.getPedidoEnCurso(), "Debe limpiar el pedido en curso al cerrar.");
        assertTrue(restaurante.getPedidos().size() >= 1, "El pedido debe quedar en el histórico.");
    }


    @Test
    @DisplayName("cargarInformacionRestaurante con archivos inconsistentes lanza HamburguesaException")
    void testCargarInformacionInvalida(@TempDir File tmp) throws Exception {
        
        File fIng = new File(tmp, "ingredientes.txt");
        try (FileWriter w = new FileWriter(fIng)) {
            w.write("queso;1000\n");
        }
        
        File fMenu = new File(tmp, "menu.txt");
        try (FileWriter w = new FileWriter(fMenu)) {
            w.write("productoX;9999\n");
        }
        
        File fComb = new File(tmp, "combos.txt");
        try (FileWriter w = new FileWriter(fComb)) {
            w.write("Combo Bug;10%;hamburguesa;papa;gaseosa\n");
        }

        assertThrows(HamburguesaException.class, () -> {
            restaurante.cargarInformacionRestaurante(fIng, fMenu, fComb);
        });
    }
}
