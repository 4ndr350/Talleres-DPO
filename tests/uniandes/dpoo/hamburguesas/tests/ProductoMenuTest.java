package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

@DisplayName("Pruebas de la clase ProductoMenu")
public class ProductoMenuTest {

    private ProductoMenu menu1;

    @BeforeEach
    void setUp() {
        menu1 = new ProductoMenu("Hamburguesa Sencilla", 10000);
    }

    @AfterEach
    void tearDown() {
        menu1 = null;
    }

    @Test
    @DisplayName("getNombre retorna el nombre del producto")
    void testGetNombre() {
        assertEquals("Hamburguesa Sencilla", menu1.getNombre(), "Nombre incorrecto");
    }

    @Test
    @DisplayName("getPrecio retorna el precio base")
    void testGetPrecio() {
        assertEquals(10000, menu1.getPrecio(), "Precio incorrecto");
    }

    @Test
    @DisplayName("generarTextoFactura contiene nombre y precio con el formato esperado")
    void testGenerarTextoFactura() {
        String esperado = new StringBuilder()
            .append(menu1.getNombre() + "\n")
            .append("            " + menu1.getPrecio() + "\n")
            .toString();
        assertEquals(esperado, menu1.generarTextoFactura(), "Texto de factura inesperado");
    }
}