package ca.mcmaster.se2aa4.island.team28;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

public class DroneTest {

    @Test
    public void flyTest() {
        Drone d = new Drone(Direction.EAST, 7000);
        d.fly();
        assertTrue(d.getPosition().equals(new Coordinate(2, 1)));
    }

    @Test
    public void turnLeftTest() {
        Drone d = new Drone(Direction.SOUTH, 7000);
        d.turnLeft();
        assertTrue(d.getDirection().isEqual(Direction.EAST) && d.getPosition().equals(new Coordinate(2, 2)));
    }

    @Test
    public void turnRightTest() {
        Drone d = new Drone(Direction.EAST, 7000);
        d.turnRight();
        assertTrue(d.getDirection().isEqual(Direction.SOUTH) && d.getPosition().equals(new Coordinate(2, 2)));
    }

    @Test
    public void batteryTest() {
        Drone d = new Drone(Direction.EAST, 7000);
        d.drainBattery(13);
        assertTrue(d.getBattery() == 6987);
        d.drainBattery(22);
        assertTrue(d.getBattery() == 6965);
    }
}
