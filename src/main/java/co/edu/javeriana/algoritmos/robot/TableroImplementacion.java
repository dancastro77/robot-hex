package co.edu.javeriana.algoritmos.robot;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class TableroImplementacion implements Tablero, Cloneable 
{
    private ColorJugador board[][];
    private int numeroJugadas = 0;

    public TableroImplementacion() 
    {
        super();
        board = new ColorJugador[11][11];
        for ( int i = 0; i < 11; ++i ) {
            for ( int j = 0; j < 11; ++j ) {
                board[i][j] = null;
            }
        }
    }
    
    public TableroImplementacion( String[] descripcion )
    {
        this();
        int i = 0;
        for ( String fila: descripcion ) {
            String[] casillas = fila.split( " " );
            int j = 0;
            for ( String casilla: casillas ) {
                switch ( casilla ) {
                    case "B":
                        board[i][j] = ColorJugador.BLANCO;
                        break;
                    case "N":
                        board[i][j] = ColorJugador.NEGRO;
                        break;
                    default:
                        board[i][j] = null;
                        break;
                }
                j++;
            }
            i++;
        }
    }

    @Override
    public Tablero clone() 
    {
        TableroImplementacion cloneBoard = new TableroImplementacion();
        cloneBoard.numeroJugadas = this.numeroJugadas;
        for ( int i = 0; i < 11; ++i ) {
            for ( int j = 0; j < 11; ++j ) {
                cloneBoard.board[i][j] = this.board[i][j];
            }
        }
        return cloneBoard;
    }

    @Override
    public void aplicarJugada( Jugada jugada, ColorJugador colorJugador ) 
    {
        int i = jugada.getFila();
        int j = jugada.getColumna();
        this.board[i][j] = colorJugador;
    }

    @Override
    public ColorJugador ganador() 
    {
        for ( int i = 0; i < 11; ++i ) {
            ColorJugador jugadorNegro = board[0][i];
            ColorJugador jugadorBlanco = board[i][0];

            if ( jugadorNegro == ColorJugador.NEGRO ) {
                boolean visited[][] = new boolean[11][11];
                if ( verificarGanador( jugadorNegro, 0, i, visited ) ) {
                    return jugadorNegro;
                }
            }

            if ( jugadorBlanco == ColorJugador.BLANCO ) {
                boolean visited[][] = new boolean[11][11];
                if ( verificarGanador( jugadorBlanco, i, 0, visited ) ) {
                    return jugadorBlanco;
                }
            }

        }
        return null;
    }

    private boolean verificarGanador( ColorJugador jugador, int row, int col, boolean visited[][] ) 
    {
        int posrows[] = { 0, 1, -1, 1, 0, -1 };
        int poscols[] = { -1, -1, 0, 0, 1, 1 };
        boolean esGanador = false; 

        if ( col == 10 && jugador == ColorJugador.BLANCO ) {
            return true;
        }
        if ( row == 10 && jugador == ColorJugador.NEGRO ) {
            return true;
        }

        visited[row][col] = true;
        for ( int i = 0; i < 6; ++i ) {
            int new_row = row + posrows[i];
            int new_col = col + poscols[i];
            if ( validbox( jugador, new_row, new_col, visited ) ) {
                esGanador = esGanador || verificarGanador( jugador, new_row, new_col, visited );
            }
        }

        return esGanador;
    }

    private boolean validbox( ColorJugador jugador, int i, int j, boolean visited[][] ) {
        boolean valid = (i >= 0 && i < 11) && (j >= 0 && j < 11);
        if ( valid && !visited[i][j] ) {
            return board[i][j] == jugador;
        }
        else {
            return false;
        }
    }

    @Override
    public ColorJugador casilla( int fila, int columna ) {
        int i = fila, j = columna;
        boolean valid = (i >= 0 && i < 11) && (j >= 0 && j < 11);
        if ( valid )
            return this.board[fila][columna];
        else
            return null;
    }
    
    public void imprimirTablero()
    {
        for ( int i = 0; i < 11 ;i++ ) {
            for ( int j = 0; j < 11 ;j++ ) {
                String chrCasilla = null;
                if ( board[i][j] == null )
                    chrCasilla = "*";
                else if ( board[i][j] == ColorJugador.NEGRO ) 
                    chrCasilla = "N";
                else if ( board[i][j] == ColorJugador.BLANCO )
                    chrCasilla = "B";
                else 
                    chrCasilla = " ";
                System.out.print( chrCasilla + " " );
            }
            System.out.println();
        }
    }

}
