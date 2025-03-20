package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandCentre {

    private static final Logger log = LogManager.getLogger(CommandCentre.class);
    private Drone drone;
    private Phase phase = Phase.FIND_LAND;

    private Integer distanceToLand;
    private List<Coordinate> travelLog = new ArrayList<>();
    private Integer consecutiveTurns = 0;
    private List<Object> pois = new ArrayList<>();
    private List<List<Object>> biomeHistory = new ArrayList<>();

    public CommandCentre(Direction direction, Integer BatteryLevel) {
        this.drone = new Drone(direction, BatteryLevel);
    }

    public Action getDecision(Response prevResponse) {
        Action decision = null;

        if (prevResponse == null) {
            // this will trigger on the very first call
            return drone.echo();
        }

        String prevResType = prevResponse.getType();
        drone.drainBattery(prevResponse.getCost());

        if (drone.getBattery() <= 25) {
            log.info("*** low battery ({}), ending search.", drone.getBattery());
            return drone.stop();
        }

        if (phase == Phase.FIND_LAND) {
            log.info("* finding land");
            if (prevResType.equals("echo")) {
                String found = ((EchoResponse) prevResponse).getFound();
                if (Objects.equals(found, "OUT_OF_RANGE")) {
                    decision = (drone.getDirection() == Direction.SOUTH) ? drone.turnLeft() : drone.turnRight();
                } else {
                    distanceToLand = ((EchoResponse) prevResponse).getRange();
                    updatePhase(Phase.APPROACH_LAND);
                    decision = getDecision(prevResponse);
                }

            } else if (prevResType.equals("heading")) {
                decision = drone.echo();
            }

        } else if (phase == Phase.APPROACH_LAND) {
            log.info("* approaching land");
            if (distanceToLand >= 0) {
                decision = drone.fly();
                distanceToLand -= 1;
            } else {
                decision = drone.scan();
                travelLog.add(drone.getPosition());
                updatePhase(Phase.SCAN_LAND);
            }

        } else if (phase == Phase.SCAN_LAND) {
            log.info("* scanning land");
            if (prevResType.equals("scan")) {
                List<Object> biomes = ((ScanResponse) prevResponse).getBiomes();
                biomeHistory.add(biomes);
                List<Object> creeks = ((ScanResponse) prevResponse).getCreeks();
                List<Object> sites = ((ScanResponse) prevResponse).getSites();


                if (!creeks.isEmpty()) {
                    log.info("*** found creek {}", creeks.toString());
                    if (!pois.contains(creeks.getFirst())) {
                        pois.add(creeks.getFirst());
                    }
                }

                if (!sites.isEmpty()) {
                    log.info("*** found site {}", sites.toString());
                    if (!pois.contains(sites.getFirst())) {
                        pois.add(sites.getFirst());
                    }
                }

                if (biomes.contains("OCEAN")) {

                    if (consecutiveTurns == 2) {
                        decision = drone.turnLeft();
                        consecutiveTurns = 0;
                    } else {
                        decision = drone.turnRight();
                        consecutiveTurns++;
                    }

                } else {
                    decision = drone.fly();
                }

            } else if (prevResType.equals("fly") || prevResType.equals("heading")) {
                    decision = drone.scan();
            }
        }

        log.info("*** drone coords: {}", drone.getPosition().toString());

        return decision;
    }

    public void analyzePreviousResponse(Response pr) {
    }

    public void updatePhase(Phase p){
        this.phase = p;
    }

    public List<Object> getPois() {
        return pois;
    }
}
