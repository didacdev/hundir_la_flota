package common.database;

public class Coordinate {

    private Row rowBow;
    private Column columnBow;
    private Direction direction;

    public enum Direction {
        H,
        V
    }

    public enum Row {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J
    }

    public enum Column {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN
    }

    public Coordinate(Row rowBow, Column columnBow, Direction direction) {
        this.rowBow = rowBow;
        this.columnBow = columnBow;
        this.direction = direction;
    }

    public Row getRowBow() {
        return rowBow;
    }

    public void setRowBow(Row rowBow) {
        this.rowBow = rowBow;
    }

    public Column getColumnBow() {
        return columnBow;
    }

    public void setColumnBow(Column columnBow) {
        this.columnBow = columnBow;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
