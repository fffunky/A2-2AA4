package ca.mcmaster.se2aa4.island.team28;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

public class DirectionTest {

    @Test
    public void fromStringTest() {
        Direction d = Direction.fromString("N");
        assertTrue(d == Direction.NORTH);
    }

    @Test
    public void toRightTest() { 
        Direction d = Direction.NORTH;
        Direction dR = d.toRight();
        assertTrue(dR == Direction.EAST);
    }

    @Test
    public void toLeftTest() { 
        Direction d = Direction.NORTH;
        Direction dR = d.toLeft();
        assertTrue(dR == Direction.WEST);
    }

    @Test
    public void OppositeToTest() { 
        Direction d = Direction.NORTH;
        assertTrue(d.OppositeTo(Direction.SOUTH));
    }

    @Test
    public void isEqualTest() { 
        Direction d = Direction.NORTH;
        assertTrue(d.isEqual(Direction.NORTH));
    }

    @Test
    public void toStringTest() { 
        Direction d = Direction.NORTH;
        assertTrue(d.toString().equals("N"));
    }

}
