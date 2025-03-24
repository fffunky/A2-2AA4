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
    private String creekId;
    private String emergencySiteId;
    private List<Coordinate> positionHistory = new ArrayList<>();
    private Integer uTurnCount = 0;
    private Direction lastFlyDirection;
    private Boolean searchingEast = true;
    private Integer reorientCount = 0;

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

    public Integer getUTurnCount() {
        return uTurnCount;
    }

    public void setUTurnCount(Integer uTurnCount) {
        this.uTurnCount = uTurnCount;
    }

    public Direction getLastFlyDirection() {
        return lastFlyDirection;
    }

    public void setLastFlyDirection(Direction lastFlyDirection) {
        this.lastFlyDirection = lastFlyDirection;
    }

    public Boolean searchingEast() {
        return this.searchingEast;
    }

    public void startSearchingWest() {
        this.searchingEast = false;
    }

    public Integer getReorientCount() {
        return reorientCount;
    }

    public void setReorientCount(Integer reorientCount) {
        this.reorientCount = reorientCount;
    }
}
