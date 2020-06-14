/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.io.IOException;

import co.edu.javeriana.algoritmos.proyecto.JugadorHex;

/**
 * @author danilo
 *
 */
public class Main 
{
	/**
	 * @param args
	 */
	public static void main( String[] args ) 
	{
		new Main().ejecutarRobot( args );
	}

	void ejecutarRobot( String[] args ) 
	{
		try {
			LectorPropiedades lector = LectorPropiedades.instancia( args[0] );
			int[] marcadorJugadores = new int[] {0, 0};
			int chicosJugados = 0;
			int paraGanar = lector.numeroChicosParaGanar();
			JugadorHex[] jugadores = new CargadorJugadores().cargarJugadores();
			
			if ( jugadores[0] == null ) {
				marcadorJugadores[1] = paraGanar;
			}
			else if ( jugadores[1] == null ) {
				marcadorJugadores[0] = paraGanar;
			}
			
			while ( marcadorJugadores[0] < paraGanar && marcadorJugadores[1] < paraGanar ) {
				chicosJugados = jugarChico( marcadorJugadores, chicosJugados, jugadores );
			}
			
			System.out.println( "**** MARCADOR FINAL ****" );
			for ( int i = 0; i < 2 ;i++ )
			    System.out.println( 
			            "El jugador " + jugadores[i].nombreJugador() 
			            + " hizo " + marcadorJugadores[i] + " punto(s).");
		} 
		catch ( IOException e ) {
			System.out.println( "Error al cargar propiedades: " + e.getMessage() );
		}
	}

    int jugarChico( int[] marcadorJugadores, int chicosJugados, JugadorHex[] jugadores ) 
    {
        Chico chico = new Chico( 
                jugadores, chicosJugados, LectorPropiedades.instancia().numeroChicosParaGanar() );
        
        chico.jugar();
        
        for ( int i = 0; i < 2 ;i++ ) {
        	marcadorJugadores[i] += chico.obtenerPuntajeParaJugador( i ); 
        }
        
        System.out.println( 
                "Marcador: " + jugadores[0].nombreJugador() + " " + marcadorJugadores[0] 
                + " - " + marcadorJugadores[1] + " " + jugadores[1].nombreJugador() );
        chicosJugados++;
        
        return chicosJugados;
    }

}
