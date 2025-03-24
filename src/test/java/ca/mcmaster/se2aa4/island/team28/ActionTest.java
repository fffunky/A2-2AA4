package ca.mcmaster.se2aa4.island.team28;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

public class ActionTest {

    @Test
    public void getActionTest() {
        Action a = new Action("scan");
        assertTrue(a.getAction().toString().equals("{\"action\":\"scan\"}"));
    }

    @Test
    public void getActionTest2() { //passing in more vars
        Direction d = Direction.NORTH;
        Action a = new Action("echo", d);
        assertTrue(a.getAction().toString().equals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}"));
    }


}
