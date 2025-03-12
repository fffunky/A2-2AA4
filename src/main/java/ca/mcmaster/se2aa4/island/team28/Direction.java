package ca.mcmaster.se2aa4.island.team28;

public enum Direction{
    NORTH,
    EAST,
    SOUTH,
    WEST;

    @Override
    public String toString(){
        switch(this){
            case NORTH:
                return "N";
                break;
            case EAST:
                return "E";
                break;
            case SOUTH:
                return "S";
                break;
            case WEST:
                return "W";
                break;
            
        }
    }
}
