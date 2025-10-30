package uniandes.dpoo.aerolinea.consola;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.TipoInvalidoException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class ConsolaArerolinea extends ConsolaBasica
{
    private Aerolinea unaAerolinea;

    /**
     * Es un método que corre la aplicación y realmente no hace nada interesante: sólo muestra cómo se podría utilizar la clase Aerolínea para hacer pruebas.
     */
    public void correrAplicacion( )
    {
        try
        {
            unaAerolinea = new Aerolinea( );
            unaAerolinea.cargarAerolinea("./datos/aerolinea.json", CentralPersistencia.JSON);
            unaAerolinea.cargarTiquetes("./datos/tiquetes.json", CentralPersistencia.JSON);
        }
        catch( TipoInvalidoException e )
        {
            e.printStackTrace( );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        catch( InformacionInconsistenteException e )
        {
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        ConsolaArerolinea ca = new ConsolaArerolinea( );
        ca.correrAplicacion( );
    }
}
