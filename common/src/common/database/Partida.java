package common.database;

import java.io.Serial;
import java.io.Serializable;

public class Partida implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Jugador playerOne;
    private Jugador playerTwo;

    //--------------------------------- CONSTRUCTORES ---------------------------------
    public Partida(Jugador playerOne) {
        this.playerOne = playerOne;
        this.playerTwo = null;
    }

    //--------------------------------- GETTERS & SETTERS ---------------------------------
    public Jugador getPlayerOne() {
        return playerOne;
    }

    public void setPlayerTwo(Jugador playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Jugador getPlayerTwo() {
        return playerTwo;
    }
}
