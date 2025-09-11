package uniandes.dpoo.aerolinea.modelo;

/**
 * Esta clase tiene la información de una ruta entre dos aeropuertos que cubre una aerolínea.
 */
public class Ruta
{
    private Aeropuerto origen;
    private Aeropuerto destino;
    private String codigoRuta;
    private String horaSalida;
    private String horaLlegada;
    

    public Ruta(Aeropuerto origen, Aeropuerto destino, String codigoRuta, String horaSalida, String horaLlegada) {
		this.origen = origen;
		this.destino = destino;
		this.codigoRuta = codigoRuta;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
	}
    

	public Aeropuerto getOrigen() {
		return origen;
	}


	public Aeropuerto getDestino() {
		return destino;
	}


	public String getCodigoRuta() {
		return codigoRuta;
	}


	public String getHoraSalida() {
		return horaSalida;
	}


	public String getHoraLlegada() {
		return horaLlegada;
	}
	
	public int getDuracion() {
	    int s = getHoras(this.horaSalida) * 60 + getMinutos(this.horaSalida);
	    int e = getHoras(this.horaLlegada) * 60 + getMinutos(this.horaLlegada);
	    if (e < s) e += 24 * 60;
	    return e - s;
	}



	/**
     * Dada una cadena con una hora y minutos, retorna los minutos.
     * 
     * Por ejemplo, para la cadena '715' retorna 15.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de minutos entre 0 y 59
     */
    public static int getMinutos( String horaCompleta )
    {
        int minutos = Integer.parseInt( horaCompleta ) % 100;
        return minutos;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna las horas.
     * 
     * Por ejemplo, para la cadena '715' retorna 7.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de horas entre 0 y 23
     */
    public static int getHoras( String horaCompleta )
    {
        int horas = Integer.parseInt( horaCompleta ) / 100;
        return horas;
    }

    
}
