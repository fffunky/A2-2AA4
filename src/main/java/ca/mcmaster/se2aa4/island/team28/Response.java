package ca.mcmaster.se2aa4.island.team28;

public abstract class Response {
    private final Integer cost;
    private final String status;
    private final String type;

    public Response(String type, Integer cost, String status) {
        this.cost = cost;
        this.status = status;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Response [cost=" + cost + ", status=" + status + "]";
    }
}

