package ca.mcmaster.se2aa4.island.team28;

public enum Phase {
    FIND_LAND,
    APPROACH_LAND,
    RETURN_TO_LAND,
    UTURN,
    REORIENT,
    SCAN_LAND;

    @Override
    public String toString() {
        return switch (this) {
            case FIND_LAND -> "Find Land";
            case APPROACH_LAND -> "Approach Land";
            case RETURN_TO_LAND -> "Return to Land";
            case SCAN_LAND -> "Scan Land";
            case UTURN -> "U-Turn";
            case REORIENT -> "Reorient";
        };
    }
}
