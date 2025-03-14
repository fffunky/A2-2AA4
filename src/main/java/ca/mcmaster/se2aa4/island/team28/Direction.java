package ca.mcmaster.se2aa4.island.team28;

public enum Direction{
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction fromString(String d) {
        return switch (d) {
            case "N" -> NORTH;
            case "E" -> EAST;
            case "S" -> SOUTH;
            case "W" -> WEST;
            default -> null;
        };
    }

    public Direction toRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Direction toLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    @Override
    public String toString(){
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }
}
