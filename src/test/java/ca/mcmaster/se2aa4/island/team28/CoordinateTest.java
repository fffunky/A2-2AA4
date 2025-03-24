package ca.mcmaster.se2aa4.island.team28;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CoordinateTest {

    @Test
    public void incrementTest() {
        Coordinate c = new Coordinate(4, 13);
        c.incrementX();
        c.incrementY();
        assertTrue(c.getX() == 5 && c.getY() == 14);
    }

    @Test
    public void decrementTest() {
        Coordinate c = new Coordinate(4, 13);
        c.decrementX();
        c.decrementY();
        assertTrue(c.getX() == 3 && c.getY() == 12);
    }

    @Test
    public void setTest() {
        Coordinate c = new Coordinate(4, 13);
        c.setX(0);
        c.setY(0);
        assertTrue(c.getX() == 0 && c.getY() == 0);
    }

    @Test
    public void equalsTest() {
        Coordinate c = new Coordinate(7, 13);
        c.incrementX();
        c.decrementY();
        assertTrue(c.equals(new Coordinate(8, 12)));
    }
    


}
