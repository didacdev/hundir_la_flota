package common.database;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Jugador implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Coordinate shipOne;
    private Coordinate shipTwo;
    List<Coordinate> receivedShots;
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
        this.receivedShots = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public HashMap<Integer, Integer> getGamePoints() {
        return gamePoints;
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

    public List<Coordinate> getReceivedShots() {
        return receivedShots;
    }

    public void addReceivedShot(Coordinate shot) {
        Coordinate coordinate = new Coordinate(shot.getRowBow().toUpperCase(), shot.getColumnBow(), "-");
        receivedShots.add(coordinate);
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public void addShipsToBoard(Coordinate shipOne, Coordinate shipTwo) {
        List<Coordinate> shipOneCoordinates = shipOne.getOccupiedCoordinates();
        List<Coordinate> shipTwoCoordinates = shipTwo.getOccupiedCoordinates();

        for (Coordinate coordinate : shipOneCoordinates) {
            int row = coordinate.getRowBow().toUpperCase().charAt(0) - 'A';
            int column = coordinate.getColumnBow() - 1;
            board[row][column] = true;
        }

        for (Coordinate coordinate : shipTwoCoordinates) {
            int row = coordinate.getRowBow().toUpperCase().charAt(0) - 'A';
            int column = coordinate.getColumnBow() - 1;
            board[row][column] = true;
        }
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                Coordinate currentCoordinate = new Coordinate(String.valueOf((char) ('A' + i)), j + 1, "-");
                if (!receivedShots.isEmpty() && receivedShots.contains(currentCoordinate)) {
                    System.out.print("X ");
                } else if (board[i][j]) {
                    System.out.print("O ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void addGamePoint() {
        int currentPoints = gamePoints.getOrDefault(clienteID, 0);
        gamePoints.put(clienteID, currentPoints + 1);
    }

    public void addVictoryPoints() {
        int currentPoints = gamePoints.getOrDefault(clienteID, 0);
        gamePoints.put(clienteID, currentPoints + 4);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Jugador jugador = (Jugador) obj;
        return clienteID != null && clienteID.equals(jugador.clienteID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteID);
    }
}
