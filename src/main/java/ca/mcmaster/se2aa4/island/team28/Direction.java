package ca.mcmaster.se2aa4.island.team28;

public enum Direction{
    NORTH,
    EAST,
    SOUTH,
    WEST;

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
