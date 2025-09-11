package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {

    protected final int COSTO_POR_KM = 1000;

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int dist = calcularDistanciaVuelo(vuelo.getRuta());
        return dist * COSTO_POR_KM;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente) {
        return 0.0;
    }
}
