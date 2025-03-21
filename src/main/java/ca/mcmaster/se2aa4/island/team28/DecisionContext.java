package ca.mcmaster.se2aa4.island.team28;

import java.util.HashMap;
import java.util.Map;

public class DecisionContext {
    private final Drone drone;
    private Phase phase = Phase.FIND_LAND;
    private Integer distanceToLand;
    private Map<String,Boolean> echoCheck = new HashMap<>();

    public DecisionContext(Drone drone) {
        this.drone = drone;
    }

    public Drone getDrone() {
        return drone;
    }

    public Phase getPhase() {
        return phase;
    }

    public void updatePhase(Phase p) {
        this.phase = p;
    }

    public Integer getDistanceToLand() {
        return distanceToLand;
    }

    public void setDistanceToLand(Integer distanceToLand) {
        this.distanceToLand = distanceToLand;
    }

    public Map<String,Boolean> getEchoCheck() {
        return echoCheck;
    }

    public void updateEchoCheck(String key, Boolean value) {
        echoCheck.put(key, value);
    }

    public void resetEchoCheck() {
        echoCheck.put("left", false);
        echoCheck.put("right", false);
    }
}
