package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

@DisplayName("Pruebas de la clase Combo")
public class ComboTest {

    private ProductoMenu hamburguesa;
    private ProductoMenu papas;
    private ProductoMenu gaseosa;
    private ArrayList<ProductoMenu> items;

    @BeforeEach
    void setup() {
        hamburguesa = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas = new ProductoMenu("Papas medianas", 6000);
        gaseosa = new ProductoMenu("Gaseosa", 4000);
        items = new ArrayList<>();
        items.add(hamburguesa);
        items.add(papas);
        items.add(gaseosa);
    }

    @Test
    @DisplayName("getNombre retorna el nombre del combo")
    void testGetNombre() {
        // descuento como FACTOR multiplicativo: 0.90 = 10% off
        Combo combo = new Combo("Combo Especial", 0.90, items);
        assertEquals("Combo Especial", combo.getNombre());
    }

    @Test
    @DisplayName("getPrecio aplica correctamente el factor de descuento configurado en el combo")
    void testGetPrecio() {
        // Según la implementación, 'descuento' es un FACTOR multiplicativo (1.0 = 100%, 0.90 = 10% off)
        Combo combo = new Combo("Combo Especial", 0.90, items);
        int suma = hamburguesa.getPrecio() + papas.getPrecio() + gaseosa.getPrecio(); // 22000
        int esperado = (int) Math.round(suma * 0.90); // 19800
        assertEquals(esperado, combo.getPrecio(), "El costo del combo no es el esperado.");
    }

    @Test
    @DisplayName("generarTextoFactura incluye nombre, factor de descuento y precio")
    void testGenerarTextoFactura() {
        Combo combo = new Combo("Combo Especial", 0.90, items);
        String esperado = new StringBuilder()
            .append("Combo Combo Especial\n")
            .append(" Descuento: 0.9\n")
            .append("            " + combo.getPrecio() + "\n")
            .toString();
        assertEquals(esperado, combo.generarTextoFactura());
    }
}
