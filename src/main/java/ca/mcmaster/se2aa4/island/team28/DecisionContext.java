package ca.mcmaster.se2aa4.island.team28;

import eu.ace_design.island.game.actions.Echo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionContext {
    private final Drone drone;
    private Phase phase = Phase.FIND_LAND;
    private Integer distanceToLand;
    private Map<String,EchoResponse> echoCheck = new HashMap<>();
    private String lastEcho = null;
    private Map<String, Integer> turnCheck = new HashMap<>();
    private String creekId;
    private String emergencySiteId;
    private List<Coordinate> positionHistory = new ArrayList<>();

    public DecisionContext(Drone drone) {
        this.drone = drone;
        echoCheck.put("left", null);
        echoCheck.put("right", null);
        echoCheck.put("forward", null);
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

    public String getLastEcho() {
        return lastEcho;
    }

    public void setLastEcho(String lastEcho) {
        this.lastEcho = lastEcho;
    }

    public Map<String,EchoResponse> getEchoCheck() {
        return echoCheck;
    }

    public void updateEchoCheck(String key, EchoResponse value) {
        echoCheck.put(key, value);
    }

    public Map<String,Integer> getTurnCheck() {
        return turnCheck;
    }

    public void updateTurnCheck(String key, Integer value) {
        turnCheck.put(key, value);
    }

    public void resetEchoCheck() {
        echoCheck.put("left", null);
        echoCheck.put("right", null);
        echoCheck.put("forward", null);
    }

    public String getCreekId() {
        return creekId;
    }

    public void setCreekId(String creekId) {
        this.creekId = creekId;
    }

    public String getEmergencySiteId() {
        return emergencySiteId;
    }

    public void setEmergencySiteId(String emergencySiteId) {
        this.emergencySiteId = emergencySiteId;
    }

    public void updatePositionHistory() {
        positionHistory.add(drone.getPosition());
    }

    public Boolean hasCoordinateBeenVisited(Coordinate c) {
        return positionHistory.contains(c);
    }
}
