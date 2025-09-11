package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

    protected final int COSTO_POR_KM_CORPORATIVO = 900;
    protected final int COSTO_POR_KM_NATURAL = 600;
    protected final double DESCUENTO_GRANDES = 0.2;
    protected final double DESCUENTO_MEDIANAS = 0.1;
    protected final double DESCUENTO_PEQ = 0.02;

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int dist = calcularDistanciaVuelo(vuelo.getRuta());
        if (cliente instanceof ClienteCorporativo) {
            return COSTO_POR_KM_CORPORATIVO * dist;
        }
        return COSTO_POR_KM_NATURAL * dist;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente) {
        if (cliente instanceof ClienteCorporativo) {
            ClienteCorporativo corp = (ClienteCorporativo) cliente;
            int t = corp.getTamanoEmpresa();
            if (t == ClienteCorporativo.GRANDE) return DESCUENTO_GRANDES;
            if (t == ClienteCorporativo.MEDIANA) return DESCUENTO_MEDIANAS;
            return DESCUENTO_PEQ;
        }
        return 0.0;
    }
}
