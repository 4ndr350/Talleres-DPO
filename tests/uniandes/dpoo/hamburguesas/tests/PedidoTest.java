package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

@DisplayName("Pruebas de la clase Pedido")
public class PedidoTest {

    private Pedido pedido;
    private ProductoMenu hamburguesa;
    private ProductoMenu papas;

    @BeforeEach
    void setup() {
        pedido = new Pedido("Yesid", "Bogota");
        hamburguesa = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas = new ProductoMenu("Papas medianas", 6000);
    }

    @Test
    @DisplayName("Agregar producto acumula precio neto correctamente")
    void testAgregarProductoYPrecioNeto() {
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);
        // Precio neto esperado: 18000, IVA: 3420, Total: 21420
        assertEquals(21420, pedido.getPrecioTotalPedido(), "El total del pedido no es el esperado con IVA.");
    }

    @Test
    @DisplayName("Getters basicos del pedido")
    void testGettersBasicos() {
        assertTrue(pedido.getIdPedido() > 0);
        assertEquals("Yesid", pedido.getNombreCliente());
        assertEquals("Bogotá", pedido.getDireccionCliente());
    }

    @Test
    @DisplayName("generarTextoFactura contiene cabecera, items, neto, IVA y total")
    void testGenerarTextoFactura() {
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);
        String factura = pedido.generarTextoFactura();
        assertTrue(factura.contains("Cliente: Yesid"));
        assertTrue(factura.contains("Direccion: Bogota") || factura.contains("Direcci\u00f3n: Bogotá"));
        assertTrue(factura.contains("Hamburguesa Sencilla"));
        assertTrue(factura.contains("Papas medianas"));
        assertTrue(factura.contains("IVA"));
        assertTrue(factura.contains("Total"));
    }

    @Test
    @DisplayName("guardarFactura escribe archivo con el texto de la factura")
    void testGuardarFacturaEscribeArchivo(@TempDir File tmp) throws IOException {
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);
        File destino = new File(tmp, "factura.txt");
        pedido.guardarFactura(destino);
        assertTrue(destino.exists(), "No se crea el archivo de la factura.");
        List<String> lineas = Files.readAllLines(destino.toPath());
        assertFalse(lineas.isEmpty(), "La factura esta vacia.");
        assertTrue(String.join("\n", lineas).contains("Hamburguesa Sencilla"));
    }
}