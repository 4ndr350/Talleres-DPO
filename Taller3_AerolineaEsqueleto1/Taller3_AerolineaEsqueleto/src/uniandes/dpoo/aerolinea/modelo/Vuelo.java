package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo {
	private Avion avion;
	private String fecha;
	private Ruta ruta;
	private Map<String,Tiquete> tiquetes;
	
	public Vuelo(Avion avion, String fecha, Ruta ruta) {
		this.avion = avion;
		this.fecha = fecha;
		this.ruta = ruta;
		this.tiquetes = new java.util.HashMap<String,Tiquete>();
	}
	
	public Avion getAvion() {
		return avion;
	}
	public String getFecha() {
		return fecha;
	}
	public Ruta getRuta() {
		return ruta;
	}
	public Collection<Tiquete> getTiquetes() {
		return tiquetes.values();
	}
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
	    int ocupados = tiquetes.size();
	    int capacidad = avion.getCapacidad();
	    if (ocupados + cantidad > capacidad) {
	        throw new VueloSobrevendidoException(this);
	    }

	    int total = 0;
	    for (int i = 0; i < cantidad; i++) {
	        int tarifa = calculadora.calcularTarifa(this, cliente);
	        Tiquete t = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
	        String cod = t.getCodigo();
	        tiquetes.put(cod, t);
	        cliente.agregarTiquete(t);
	        total += tarifa;
	    }
	    return total;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Vuelo other = (Vuelo) obj;
	    return this.fecha.equals(other.fecha)
	        && this.ruta.getCodigoRuta().equals(other.ruta.getCodigoRuta());
	}

}
