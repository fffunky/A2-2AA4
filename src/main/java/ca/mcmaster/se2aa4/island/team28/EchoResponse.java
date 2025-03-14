package ca.mcmaster.se2aa4.island.team28;

public class EchoResponse extends Response {
    private final Integer range;
    private final String found;
    public EchoResponse(String type, Integer cost, String status, Integer range, String found) {
        super(type, cost, status);
        this.range = range;
        this.found = found;
    }

    public Integer getRange() {
        return range;
    }

    public String getFound() {
        return found;
    }

    @Override
    public String toString() {
        return "EchoResponse [cost=" + this.getCost() + ", status=" + this.getStatus() + ", range=" + range + ", found=" + found + "]";
    }
}
