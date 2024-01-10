package common.database;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Jugador implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Coordinate shipOne;
    private Coordinate shipTwo;
    List<String> shots;
    boolean[][] board;
    String password;
    String username;
    HashMap<Integer, Integer> gamePoints;
    Integer clienteID;


    //--------------------------------- CONSTRUCTORES ---------------------------------
    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
        this.shipOne = null;
        this.shipTwo = null;
        this.shots = null;
        this.board = new boolean[10][10];
        this.gamePoints = new HashMap<>();
        this.clienteID = null;
    }


    //--------------------------------- GETTERS & SETTERS ---------------------------------
    public Coordinate getShipOne() {
        return shipOne;
    }

    public void setShipOne(Coordinate shipOne) {
        this.shipOne = shipOne;
    }

    public Coordinate getShipTwo() {
        return shipTwo;
    }

    public void setShipTwo(Coordinate shipTwo) {
        this.shipTwo = shipTwo;
    }

    public List<String> getShots() {
        return shots;
    }

    public void setShots(List<String> shots) {
        this.shots = shots;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<Integer, Integer> getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(HashMap<Integer, Integer> gamePoints) {
        this.gamePoints = gamePoints;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalPoints() {
        int totalPoints = 0;
        for (Integer points : gamePoints.values()) {
            totalPoints += points;
        }
        return totalPoints;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }
}
