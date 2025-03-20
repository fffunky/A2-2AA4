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

    public void setX(int x) {
        this.x = x;
    }

    public void incrementX() {
        this.x++;
    }

    public void decrementX() {
        this.x--;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementY() {
        this.y++;
    }

    public void decrementY() {
        this.y--;
    }

    public boolean equals(Coordinate c) {
        return this.x == c.x && this.y == c.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
