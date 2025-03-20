package ca.mcmaster.se2aa4.island.team28;

public class Drone {

    private Direction direction;
    private Integer battery;

    public Drone(Direction direction, Integer batteryLevel) {
        this.direction = direction;
        this.battery = batteryLevel;
    }

    public Action fly() {
        return new Action("fly");
    }

    public Action turnLeft() {
        this.direction = this.direction.toLeft();
        return new Action("heading", this.direction);
    }

    public Action turnRight() {
        this.direction = this.direction.toRight();
        return new Action("heading", this.direction);
    }

    public Action echo() {
        return new Action("echo", this.direction);
    }

    public Action scan() {
        return new Action("scan");
    }

    public Action stop() {
        return new Action("stop");
    }

    public void drainBattery(Integer cost){
        this.battery -= cost;
    }

    public Direction getDirection() {
        return direction;
    }

}
