package ca.mcmaster.se2aa4.island.team28;

public class CommandCentre {

    private Drone drone;

    public CommandCentre(Direction direction, Integer BatteryLevel) {
        this.drone = new Drone(direction, BatteryLevel);
    }
}
