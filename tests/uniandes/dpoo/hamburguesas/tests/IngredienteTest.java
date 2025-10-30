package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;

@DisplayName("Pruebas de la clase Ingrediente")
public class IngredienteTest {

    @Test
    void testGetters() {
        Ingrediente ingrediente1 = new Ingrediente("tomate", 1000);
        assertEquals("tomate", ingrediente1.getNombre(), "El nombre del ingrediente no es el esperado.");
        assertEquals(1000, ingrediente1.getCostoAdicional(), "El costo adicional del ingrediente no es el esperado.");
    }
}