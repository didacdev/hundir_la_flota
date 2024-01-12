package common.database;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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

    public boolean isGameOver() {
        Jugador playerOne = this.getPlayerOne();
        Jugador playerTwo = this.getPlayerTwo();

        List<Coordinate> receivedShotsPlayerOne = playerOne.getReceivedShots();
        List<Coordinate> receivedShotsPlayerTwo = playerTwo.getReceivedShots();

        List<Coordinate> occupiedCoordinatesPlayerOneShipOne = playerOne.getShipOne().getOccupiedCoordinates();
        List<Coordinate> occupiedCoordinatesPlayerOneShipTwo = playerOne.getShipTwo().getOccupiedCoordinates();

        List<Coordinate> occupiedCoordinatesPlayerTwoShipOne = playerTwo.getShipOne().getOccupiedCoordinates();
        List<Coordinate> occupiedCoordinatesPlayerTwoShipTwo = playerTwo.getShipTwo().getOccupiedCoordinates();

        boolean playerOneLost = receivedShotsPlayerOne.containsAll(occupiedCoordinatesPlayerOneShipOne) && receivedShotsPlayerOne.containsAll(occupiedCoordinatesPlayerOneShipTwo);
        boolean playerTwoLost = receivedShotsPlayerTwo.containsAll(occupiedCoordinatesPlayerTwoShipOne) && receivedShotsPlayerTwo.containsAll(occupiedCoordinatesPlayerTwoShipTwo);

        if (playerOneLost) {
            playerTwo.addVictoryPoints();
        } else if (playerTwoLost) {
            playerOne.addVictoryPoints();
        }

        return playerOneLost || playerTwoLost;
    }
}
