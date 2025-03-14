package ca.mcmaster.se2aa4.island.team28;

public class CommandCentre {

    private Drone drone;
    private Phase phase = Phase.FIND_LAND;

    public CommandCentre(Direction direction, Integer BatteryLevel) {
        this.drone = new Drone(direction, BatteryLevel);
    }

    public void analyzePreviousResponse(Response pr) {
        if (pr.getType() == "scan") {
            updatePhase(Phase.FIND_SITE);
        }
    }

    public void updatePhase(Phase p){
        this.phase = p;
    }
}
