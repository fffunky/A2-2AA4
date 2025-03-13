package ca.mcmaster.se2aa4.island.team28;

import java.util.ArrayList;
import java.util.List;

public class ScanResponse extends Response {

    public List<Object> biomes = new ArrayList<>();
    public List<Object> creeks = new ArrayList<>();
    public List<Object> sites = new ArrayList<>();

    public ScanResponse(String type, Integer cost, String status, List<Object> biomes, List<Object> creeks, List<Object> sites) {
        super(type, cost, status);
        this.biomes = biomes;
        this.creeks = creeks;
        this.sites = sites;
    }

    public List<Object> getBiomes() {
        return biomes;
    }

    public List<Object> getCreeks() {
        return creeks;
    }

    public List<Object> getSites() {
        return sites;
    }


}
