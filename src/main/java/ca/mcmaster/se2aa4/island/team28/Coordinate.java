package ca.mcmaster.se2aa4.island.team28;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Coordinate c) {
        return this.x == c.x && this.y == c.y;
    }
}
