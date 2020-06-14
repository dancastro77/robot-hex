package co.edu.javeriana.algoritmos.robot;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class ChicoTest {

    @Test
    public void testDeberiaMostrarJuegoCorrectamenteSiDosJugadoresValidos() 
    {
        Chico chico = new Chico( new JugadorHex[] {new JugadorAleatorio(), new JugadorAleatorio()}, 0, 1 );
        chico.jugar();
        assertEquals( 1, chico.obtenerPuntajeParaJugador( 0 ) + chico.obtenerPuntajeParaJugador( 1 ) );
    }

    @Test
    public void testDeberiaDescalificarJugadorLento()
    {
        Chico chico = new Chico( new JugadorHex[] {new JugadorAleatorio(), new JugadorLento()}, 0, 1 );
        chico.jugar();
        assertEquals( 1, chico.obtenerPuntajeParaJugador( 0 ) + chico.obtenerPuntajeParaJugador( 1 ) );
    }

    @Test
    public void testDeberiaDescalificarJugadorPicho()
    {
        Chico chico = new Chico( new JugadorHex[] {new JugadorAleatorio(), new JugadorPicho()}, 0, 1 );
        chico.jugar();
        assertEquals( 1, chico.obtenerPuntajeParaJugador( 0 ) + chico.obtenerPuntajeParaJugador( 1 ) );
    }

    @Test
    public void testDeberiaDescalificarJugadorRepetidor()
    {
        Chico chico = new Chico( new JugadorHex[] {new JugadorAleatorio(), new JugadorRepetidor()}, 0, 1 );
        chico.jugar();
        assertEquals( 1, chico.obtenerPuntajeParaJugador( 0 ) + chico.obtenerPuntajeParaJugador( 1 ) );
    }

    @Test
    public void testDeberiaDescalificarJugadorCambiador()
    {
        Chico chico = new Chico( new JugadorHex[] {new JugadorAleatorio(), new JugadorCambiador()}, 0, 3 );
        chico.jugar();
        assertEquals( 1, chico.obtenerPuntajeParaJugador( 0 ) + chico.obtenerPuntajeParaJugador( 1 ) );
    }

    static class JugadorAleatorio implements JugadorHex
    {
        private Random rnd = new Random( System.currentTimeMillis() );
        
        @Override
        public Jugada jugar( Tablero tablero, ColorJugador color ) 
        {
            int fila = rnd.nextInt( 11 );
            int columna = rnd.nextInt( 11 );
            while ( tablero.casilla( fila, columna ) != null ) {
                fila = rnd.nextInt( 11 );
                columna = rnd.nextInt( 11 );                
            }
            return new Jugada( fila, columna );
        }

        @Override
        public String nombreJugador() 
        {
            return "Random";
        }
        
    }
    
    static class JugadorLento implements JugadorHex
    {

        @Override
        public Jugada jugar( Tablero tablero, ColorJugador color ) 
        {
            try {
                Thread.sleep( 4000L );
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            return new Jugada( 1, 1 );
        }

        @Override
        public String nombreJugador() {
            return "Lento";
        }
        
    }
    
    static class JugadorPicho implements JugadorHex
    {

        @Override
        public Jugada jugar( Tablero tablero, ColorJugador color ) 
        {
            throw new RuntimeException();
        }

        @Override
        public String nombreJugador() {
            return "Picho";
        }
        
    }
    
    static class JugadorRepetidor implements JugadorHex
    {
        @Override
        public Jugada jugar( Tablero tablero, ColorJugador color ) 
        {
            return new Jugada( 1, 1 );
        }

        @Override
        public String nombreJugador() {
            return "Repetidor";
        }
    }
    
    static class JugadorCambiador implements JugadorHex
    {
        @Override
        public Jugada jugar( Tablero tablero, ColorJugador color ) 
        {
            return new Jugada( true, 0, 0 );
        }

        @Override
        public String nombreJugador() {
            return "Cambiador";
        }
    }
}
