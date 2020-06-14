/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import co.edu.javeriana.algoritmos.proyecto.JugadorHex;

/**
 * @author danilo
 *
 */
public class CargadorJugadores 
{
	public JugadorHex[] cargarJugadores() {
		JugadorHex[] jugadores = new JugadorHex[2];
		
		jugadores[0] = cargarJugador( LectorPropiedades.instancia().claseJugadorUno() );
		jugadores[1] = cargarJugador( LectorPropiedades.instancia().claseJugadorDos() );

		return jugadores;
	}

	private JugadorHex cargarJugador( String claseJugador ) {
		try {
			return ( JugadorHex ) Class.forName( claseJugador ).newInstance();
		} 
		catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
}
