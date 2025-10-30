package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;

/**
 * Esta interfaz define las operaciones relacionadas con la persistencia de la información principal de la aerolínea (aviones, rutas, vuelos y clientes)
 */
public interface IPersistenciaAerolinea
{
    /**
     * Carga la información de la aerolínea a partir de un archivo
     * @param archivo El nombre del archivo.
     * @param aerolinea La aerolínea dentro de la cual debe almacenarse la información
     * @throws IOException Se lanza esta excepción si hay problemas leyendo el archivo
     * @throws InformacionInconsistenteException Se lanza esta excepción si durante la carga del archivo se encuentra información que no es consistente
     */
    public void cargarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException;

    /**
     * Salva en un archivo la información de la aerolínea
     * @param archivo El nombre del archivo.
     * @param aerolinea La aerolínea que tiene la información que se quiere almacenar
     * @throws IOException Se lanza esta excepción si hay problemas escribiendo el archivo
     */
    public void salvarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException;
}
