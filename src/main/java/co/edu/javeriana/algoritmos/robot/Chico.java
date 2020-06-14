/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.security.SecureRandom;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;


/**
 * @author danilo
 *
 */
public class Chico 
{
	JugadorHex[] jugadores = null;
	ColorJugador[] colores = new ColorJugador[] {ColorJugador.NEGRO, ColorJugador.BLANCO};
	int primerJugador = 0, segundoJugador = 1;
	boolean puedeHaberEmpate = true;
	boolean chicoDecisivo = false;
	int jugadorGanador = -1;
	TableroImplementacion tablero = null;
	
	public Chico( JugadorHex[] jugadores, int chicosJugados, int chicosParaGanar ) 
	{
		this.jugadores = jugadores;
		chicoDecisivo = chicosJugados >= 2 * chicosParaGanar - 2;
		
		if ( chicoDecisivo ) {
            primerJugador = new SecureRandom().nextInt( 2 );
		}
		else {
            primerJugador = chicosJugados % 2;
		}
		
		segundoJugador = (primerJugador + 1) % 2;
		if ( primerJugador == 1 ) {
		    invertirColores();
		}
		tablero = new TableroImplementacion();
	}

	public void jugar() 
	{
        int turno = 1;
        int jugadorEnTurno = primerJugador;
        ColorJugador colorGanador = null;
		
        while ( ( colorGanador = tablero.ganador() ) == null && turno < 120 ) {
		    System.out.println( "Turno no. " + turno );
		    
		    try {
	            recogerYAplicarJugada( turno, jugadorEnTurno );
                jugadorEnTurno = (jugadorEnTurno + 1) % 2;
                if ( jugadorEnTurno == primerJugador ) {
                    turno++;
                    tablero.imprimirTablero();
                }
            }
            catch ( Exception e ) {
                System.out.println( 
                        "Se recibió una excepción al jugar el jugador " 
                        + jugadores[jugadorEnTurno].nombreJugador() 
                        + ": " + e.getMessage() + ". El jugador pierde el chico." );
                jugadorGanador = (jugadorEnTurno + 1) % 2;
                return;
            }
		}
		tablero.imprimirTablero();
        if ( turno >= 120 ) {
		    System.out.println( "Chico declarado desierto: Límite de jugadas alcanzado" );
		    jugadorGanador = -1;
		}
		else {
		    jugadorGanador = ( colorGanador == colores[0] ) ? 0 : 1;
		}
	}

    void recogerYAplicarJugada( int turno, int jugadorEnTurno ) 
    {
        long tiempo = System.currentTimeMillis();
        Jugada jugada = jugadores[jugadorEnTurno].jugar( tablero.clone(), colores[jugadorEnTurno] );
        validarJugada( jugada, turno, jugadorEnTurno );
        System.out.println( 
                "Jugada del jugador " + jugadores[jugadorEnTurno] 
                + " con el color " + colores[jugadorEnTurno] + ": " + jugada );
        if ( jugada.isCambioColores() ) {
            invertirColores();
        }
        else {
            tablero.aplicarJugada( jugada, colores[jugadorEnTurno] );
        }
        if ( System.currentTimeMillis() - tiempo > 2000 ) {
            throw new IllegalStateException( "Se sobrepasó el tiempo de espera para la jugada" );
        }
    }

	private void validarJugada( Jugada jugada, int turno, int jugadorEnTurno ) 
	{
        if ( tablero.casilla( jugada.getFila(), jugada.getColumna() ) != null ) {
            throw new IllegalArgumentException( "Casilla ya ocupada" );
        }
        if ( jugada.isCambioColores() ) {
            if ( turno != 1 || jugadorEnTurno == primerJugador ) {
                throw new IllegalArgumentException( 
                        "Cambio de color sólo vale en la primera jugada del segundo jugador" );
            }
        }
    }

	public int obtenerPuntajeParaJugador( int numeroJugador ) 
	{
		return ( numeroJugador == jugadorGanador ) ? 1 : 0;
	}

	void invertirColores()
	{
	    ColorJugador temp = colores[0];
	    colores[0] = colores[1];
	    colores[1] = temp;
	}
}
