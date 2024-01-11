package common.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordinate implements Serializable {

    private String rowBow;
    private int columnBow;
    private String direction;

    public Coordinate(){}

    public Coordinate(String rowBow, int columnBow, String direction) {

        this.rowBow = rowBow;
        this.columnBow = columnBow;
        this.direction = direction;
    }

    public List<Coordinate> getOccupiedCoordinates() {
        List<Coordinate> occupiedCoordinates = new ArrayList<>();
        int rowInt = rowBow.toUpperCase().charAt(0) - 'A';
        int columnInt = columnBow - 1;

        // Check if the ship is out of bounds of the board and return an empty list if so
        for (int i = 0; i < 3; i++) {
            if (direction.equalsIgnoreCase("h")) {
                if (columnInt + i < 10) {
                    occupiedCoordinates.add(new Coordinate(String.valueOf((char) ('A' + rowInt)), columnInt + i + 1, "-"));
                } else {
                    return new ArrayList<>();
                }
            } else {
                if (rowInt + i < 10) {
                    occupiedCoordinates.add(new Coordinate(String.valueOf((char) ('A' + rowInt + i)), columnInt + 1, "-"));
                } else {
                    return new ArrayList<>();
                }
            }
        }

        return occupiedCoordinates;
    }

    public String getRowBow() {
        return rowBow;
    }

    public Integer getColumnBow() {
        return columnBow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return columnBow == that.columnBow &&
                Objects.equals(rowBow, that.rowBow) &&
                Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowBow, columnBow, direction);
    }
}
