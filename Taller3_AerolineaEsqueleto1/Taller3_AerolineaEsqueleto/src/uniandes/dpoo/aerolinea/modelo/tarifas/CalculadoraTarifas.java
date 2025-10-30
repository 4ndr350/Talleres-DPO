package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public abstract class CalculadoraTarifas {

    public static final double IMPUESTO = 0.28;

    protected int calcularDistanciaVuelo(Ruta ruta) {
        Aeropuerto o = ruta.getOrigen();
        Aeropuerto d = ruta.getDestino();
        return Aeropuerto.calcularDistancia(o, d);
    }

    public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
        int base = calcularCostoBase(vuelo, cliente);
        double desc = calcularPorcentajeDescuento(cliente);
        int baseConDesc = (int)Math.round(base * (1.0 - desc));
        int imp = calcularValorImpuestos(baseConDesc);
        return baseConDesc + imp;
    }

    protected int calcularValorImpuestos(int costoBase) {
        return (int)Math.round(IMPUESTO * costoBase);
    }

    protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
    protected abstract double calcularPorcentajeDescuento(Cliente cliente);
}
