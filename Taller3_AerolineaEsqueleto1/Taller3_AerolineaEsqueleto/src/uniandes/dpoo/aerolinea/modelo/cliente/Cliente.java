package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.Collection;
import java.util.List;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public abstract class Cliente {
	
	private List<Tiquete> tiquetesSinUsar;
	private List<Tiquete> tiquetesUsados;
	
	public Cliente() {
        this.tiquetesSinUsar = new java.util.ArrayList<>();
        this.tiquetesUsados = new java.util.ArrayList<>();
    }
	
	public void agregarTiquete(Tiquete tiquete) {
	    tiquetesSinUsar.add(tiquete);
	}

	public void usarTiquetes(Vuelo vuelo) {
	    Collection<Tiquete> tiquetes = vuelo.getTiquetes();
	    for (Tiquete t : tiquetes) {
	        if (tiquetesSinUsar.contains(t)) {
	            tiquetesSinUsar.remove(t);
	            t.marcarComoUsado();
	            tiquetesUsados.add(t);
	        }
	    }
	}
	
	public int calcularValorTotalTiquetes() {
	    int total = 0;
	    for (Tiquete t : tiquetesSinUsar) {
	        total += t.getTarifa();
	    }
	    return total;
	}


	public void usarTiquetes​(Vuelo vuelo) {
		Collection<Tiquete> tiquetes = vuelo.getTiquetes();
		for(Tiquete t : tiquetes) {
			if(tiquetesSinUsar.contains(t)) {
				tiquetesSinUsar.remove(t);
				tiquetesUsados.add(t);
			}
		}
	}
	public abstract String getIdentificador();
	public abstract String getTipoCliente();

}
