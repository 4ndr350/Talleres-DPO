package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {

    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException, InformacionInconsistenteException {
    	
    	String contenido = new String(Files.readAllBytes(Paths.get(archivo)));
    	JSONObject raiz = new JSONObject(contenido);

    	Map<String, Aeropuerto> mapaAeropuertos = new HashMap<>();
    	JSONArray aeros = raiz.optJSONArray("aeropuertos");
    	if (aeros != null) {
    	    for (int i = 0; i < aeros.length(); i++) {
    	        JSONObject obj = aeros.getJSONObject(i);
    	        String nombre = obj.getString("nombre");
    	        String codigo = obj.getString("codigo");
    	        String nombreCiudad = obj.getString("nombreCiudad");
    	        double lat = obj.getDouble("latitud");
    	        double lon = obj.getDouble("longitud");
    	        try {
    	            Aeropuerto a =
    	                new Aeropuerto(nombre, codigo, nombreCiudad, lat, lon);
    	            mapaAeropuertos.put(codigo, a);
    	        } catch (Exception e) {
    	            throw new InformacionInconsistenteException(e.getMessage());
    	        }
    	    }
    	}

    	JSONArray avionesJson = raiz.optJSONArray("aviones");
    	if (avionesJson != null) {
    	    for (int i = 0; i < avionesJson.length(); i++) {
    	        JSONObject obj = avionesJson.getJSONObject(i);
    	        String nombre = obj.getString("nombre");
    	        int capacidad = obj.getInt("capacidad");
    	        aerolinea.agregarAvion(new Avion(nombre, capacidad));
    	    }
    	}

    	JSONArray rutasJson = raiz.optJSONArray("rutas");
    	if (rutasJson != null) {
    	    for (int i = 0; i < rutasJson.length(); i++) {
    	        JSONObject obj = rutasJson.getJSONObject(i);
    	        String codigoRuta = obj.getString("codigoRuta");
    	        String horaSalida = obj.getString("horaSalida");
    	        String horaLlegada = obj.getString("horaLlegada");
    	        String codOrg = obj.getString("origen");
    	        String codDes = obj.getString("destino");
    	        Aeropuerto org = mapaAeropuertos.get(codOrg);
    	        Aeropuerto des = mapaAeropuertos.get(codDes);
    	        if (org == null || des == null) throw new InformacionInconsistenteException("Ruta con aeropuertos inexistentes: " + codigoRuta);
    	        Ruta ruta =
    	            new Ruta(org, des, codigoRuta, horaSalida, horaLlegada);
    	        aerolinea.agregarRuta(ruta);
    	    }
    	}

    	JSONArray vuelosJson = raiz.optJSONArray("vuelos");
    	if (vuelosJson != null) {
    	    for (int i = 0; i < vuelosJson.length(); i++) {
    	        JSONObject obj = vuelosJson.getJSONObject(i);
    	        String fecha = obj.getString("fecha");
    	        String codigoRuta = obj.getString("codigoRuta");
    	        String nombreAvion = obj.getString("nombreAvion");
    	        if (aerolinea.getRuta(codigoRuta) == null) throw new InformacionInconsistenteException("Vuelo con ruta inexistente: " + codigoRuta);
    	        boolean existeAvion = false;
    	        for (Avion a : aerolinea.getAviones()) {
    	            if (a.getNombre().equals(nombreAvion)) { existeAvion = true; break; }
    	        }
    	        if (!existeAvion) throw new InformacionInconsistenteException("Vuelo con avión inexistente: " + nombreAvion);
    	        try {
    	            aerolinea.programarVuelo(fecha, codigoRuta, nombreAvion);
    	        } catch (Exception e) {
    	            throw new InformacionInconsistenteException(e.getMessage());
    	        }
    	    }
    	}

        }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
    	JSONObject raiz = new JSONObject();

    	JSONArray avionesJson = new JSONArray();
    	for (Avion a : aerolinea.getAviones()) {
    	    JSONObject obj = new JSONObject();
    	    obj.put("nombre", a.getNombre());
    	    obj.put("capacidad", a.getCapacidad());
    	    avionesJson.put(obj);
    	}
    	raiz.put("aviones", avionesJson);

    	Map<String, Aeropuerto> mapaAeropuertos = new HashMap<>();
    	JSONArray rutasJson = new JSONArray();
    	for (Ruta r : aerolinea.getRutas()) {
    	    mapaAeropuertos.put(r.getOrigen().getCodigo(), r.getOrigen());
    	    mapaAeropuertos.put(r.getDestino().getCodigo(), r.getDestino());
    	    JSONObject obj = new JSONObject();
    	    obj.put("codigoRuta", r.getCodigoRuta());
    	    obj.put("horaSalida", r.getHoraSalida());
    	    obj.put("horaLlegada", r.getHoraLlegada());
    	    obj.put("origen", r.getOrigen().getCodigo());
    	    obj.put("destino", r.getDestino().getCodigo());
    	    rutasJson.put(obj);
    	}
    	raiz.put("rutas", rutasJson);

    	JSONArray aerosJson = new JSONArray();
    	for (Aeropuerto ap : mapaAeropuertos.values()) {
    	    JSONObject obj = new JSONObject();
    	    obj.put("nombre", ap.getNombre());
    	    obj.put("codigo", ap.getCodigo());
    	    obj.put("nombreCiudad", ap.getNombreCiudad());
    	    obj.put("latitud", ap.getLatitud());
    	    obj.put("longitud", ap.getLongitud());
    	    aerosJson.put(obj);
    	}
    	raiz.put("aeropuertos", aerosJson);

    	JSONArray vuelosJson = new JSONArray();
    	for (Vuelo v : aerolinea.getVuelos()) {
    	    JSONObject obj = new JSONObject();
    	    obj.put("fecha", v.getFecha());
    	    obj.put("codigoRuta", v.getRuta().getCodigoRuta());
    	    obj.put("nombreAvion", v.getAvion().getNombre());
    	    vuelosJson.put(obj);
    	}
    	raiz.put("vuelos", vuelosJson);

    	Files.write(Paths.get(archivo), raiz.toString(2).getBytes());

    }
}
