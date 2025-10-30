package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

@DisplayName("Pruebas de la clase ProductoAjustado")
public class ProductoAjustadoTest {

    /** Normaliza saltos de línea a '\n' para que no fallen en Windows por CRLF. */
    private static String norm(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    @DisplayName("Nombre del ajustado hereda el del producto base")
    void testGetNombre() {
        ProductoMenu base = new ProductoMenu("Hamburguesa Sencilla", 12000);
        ProductoAjustado aj = new ProductoAjustado(base);
        assertEquals("Hamburguesa Sencilla", aj.getNombre());
    }

    @Test
    @DisplayName("Precio del ajustado sin agregados es el del producto base")
    void testGetPrecioSinAgregados() {
        ProductoMenu base = new ProductoMenu("Hamburguesa Sencilla", 12000);
        ProductoAjustado aj = new ProductoAjustado(base);
        assertEquals(12000, aj.getPrecio());
    }

    @Test
    @DisplayName("generarTextoFactura (sin agregados/ni eliminados)")
    void testGenerarTextoFactura_SinMods_FormatoFlexible() {
        ProductoMenu base = new ProductoMenu("Hamburguesa Sencilla", 12000);
        ProductoAjustado aj = new ProductoAjustado(base);

        String factura = norm(aj.generarTextoFactura());

        boolean encabezadoOk = factura.startsWith("Hamburguesa Sencilla")|| factura.startsWith(base.toString());
        assertTrue(encabezadoOk, "El encabezado no coincide con nombre ni con toString() del producto base.");
    }


    @Test
    @DisplayName("generarTextoFactura con agregados y eliminados")
    @SuppressWarnings("unchecked")
    void testGenerarTextoFactura_ConAgregadosYEliminados() throws Exception {
        ProductoMenu base = new ProductoMenu("Hamburguesa Sencilla", 12000);
        ProductoAjustado aj = new ProductoAjustado(base);

        Field fAgregados = ProductoAjustado.class.getDeclaredField("agregados");
        fAgregados.setAccessible(true);
        ArrayList<Ingrediente> agregados = (ArrayList<Ingrediente>) fAgregados.get(aj);

        Field fEliminados = ProductoAjustado.class.getDeclaredField("eliminados");
        fEliminados.setAccessible(true);
        ArrayList<Ingrediente> eliminados = (ArrayList<Ingrediente>) fEliminados.get(aj);

        Ingrediente queso = new Ingrediente("queso", 1000);
        Ingrediente cebolla = new Ingrediente("cebolla", 500);
        agregados.add(queso);
        eliminados.add(cebolla);

        String factura = norm(aj.generarTextoFactura());

        assertTrue(factura.contains("+queso"), "No listó ingrediente agregado con prefijo '+'");
        assertTrue(factura.contains("1000"), "No listó el costo del ingrediente agregado");

        assertTrue(factura.contains("-cebolla"), "No listó ingrediente eliminado con prefijo '-'");

        int esperado = 12000 + 1000;
        assertEquals(esperado, aj.getPrecio(), "El precio del ajustado no refleja los agregados.");
    }
}
