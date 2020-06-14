package co.edu.javeriana.algoritmos.robot;

import static org.junit.Assert.*;

import org.junit.Test;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;

public class TableroImplementacionTest {

    @Test
    public void testDeberiaCantarGanadorAlBlanco() 
    {
        String[] tablero = new String[] {
                "B N N B B B N N B N B", 
                "N N B N B N N N N B B", 
                "B N B N N N N B N B N", 
                "B N N B N B N N N B N", 
                "N N N B B B N B N N B", 
                "B B B N N B B B B B N", 
                "N B B B N N B B B B B", 
                "N N N B N N N B B B N", 
                "* N * N N N B B B B N", 
                "B B B N B N B B B B B", 
                "B N B N N N N N B N N" };
        TableroImplementacion t = new TableroImplementacion( tablero );
        t.imprimirTablero();
        assertEquals( ColorJugador.BLANCO, t.ganador() );
    }

}
