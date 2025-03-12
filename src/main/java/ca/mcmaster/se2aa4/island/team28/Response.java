package ca.mcmaster.se2aa4.island.team28;

import java.util.List;

public class Response {
    private Integer cost;
    private String status;
    private List<Object> biomes;

    public Response(Integer cost, String status, List<Object> biomes) {
        this.cost = cost;
        this.status = status;
        this.biomes = biomes;
    }

    public Integer getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public List<Object> getBiomes() {
        return biomes;
    }

    @Override
    public String toString() {
        return "Response [cost=" + cost + ", status=" + status + ", biome=" + biomes + "]";
    }
}
