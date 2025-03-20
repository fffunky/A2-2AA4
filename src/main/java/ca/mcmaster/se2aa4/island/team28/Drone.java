package ca.mcmaster.se2aa4.island.team28;

public class Drone {

    private Direction direction;
    private Integer battery;
    private Coordinate pos;

    public Drone(Direction direction, Integer batteryLevel) {
        this.direction = direction;
        this.battery = batteryLevel;
        this.pos = new Coordinate(1, 1);
    }

    public Action fly() {
        // change coordinates
        if (this.direction == Direction.NORTH) {
            pos.decrementY();
        } else if (this.direction == Direction.SOUTH) {
            pos.incrementY();
        } else if (this.direction == Direction.EAST) {
            pos.incrementX();
        } else {
            pos.decrementX();
        }

        return new Action("fly");

    }

    public Action turnLeft() {
        // change coordinates
        if (this.direction == Direction.NORTH) {
            pos.decrementY();
            pos.decrementX();
        } else if (this.direction == Direction.SOUTH) {
            pos.incrementY();
            pos.incrementX();
        } else if (this.direction == Direction.EAST) {
            pos.decrementY();
            pos.incrementX();
        } else {
            pos.incrementY();
            pos.decrementX();
        }

        // set new heading
        this.direction = this.direction.toLeft();

        return new Action("heading", this.direction);
    }

    public Action turnRight() {
        if (this.direction == Direction.NORTH) {
            pos.incrementX();
            pos.decrementY();
        } else if (this.direction == Direction.SOUTH) {
            pos.decrementX();
            pos.incrementY();
        } else if (this.direction == Direction.EAST) {
            pos.incrementX();
            pos.incrementY();
        } else {
            pos.decrementX();
            pos.decrementY();
        }

        this.direction = this.direction.toRight();
        return new Action("heading", this.direction);
    }

    public Action echo() {
        return new Action("echo", this.direction);
    }

    public Action echo(Direction d) {
        return new Action("echo", d);
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

    public Integer getBattery() {
        return this.battery;
    }

    public Direction getDirection() {
        return direction;
    }

    public Coordinate getPosition() {
        return pos;
    }

}
