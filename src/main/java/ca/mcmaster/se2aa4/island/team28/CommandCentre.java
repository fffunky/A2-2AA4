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
    private List<Object> pois = new ArrayList<>();
    private List<List<Object>> biomeHistory = new ArrayList<>();
    private Map<String, Boolean> echoCheck = new HashMap<>();
    private Direction lastEcho = null;

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
                    updatePhase(Phase.RETURN_TO_LAND);
                    echoCheck.put("left", false);
                    echoCheck.put("right", false);
                    echoCheck.put("forward", false);
                    decision = getDecision(prevResponse);
                } else {
                    decision = drone.fly();
                }

            } else if (prevResType.equals("fly") || prevResType.equals("heading")) {
                    decision = drone.scan();
            }
        } else if (phase == Phase.RETURN_TO_LAND) {
            if (prevResType.equals("scan")) {
                if (!echoCheck.get("left")) {
                    echoCheck.put("left", true);
                    lastEcho = drone.getDirection().toLeft();
                    return drone.echo(drone.getDirection().toLeft());
                }

                if (!echoCheck.get("right")) {
                    echoCheck.put("right", true);
                    lastEcho = drone.getDirection().toRight();
                    return drone.echo(drone.getDirection().toRight());
                }

                // test if order of echo checks matters
                if (!echoCheck.get("forward")) {
                    echoCheck.put("forward", true);
                    lastEcho = drone.getDirection();
                    return drone.echo();
                }

            } else if (prevResType.equals("echo")) {
                String found = ((EchoResponse) prevResponse).getFound();
                if (!Objects.equals(found, "OUT_OF_RANGE")) {
                    distanceToLand = ((EchoResponse) prevResponse).getRange();
                    updatePhase(Phase.APPROACH_LAND);
                    if (lastEcho != drone.getDirection()) {
                        if (lastEcho == drone.getDirection().toLeft()) {
                            decision = drone.turnLeft();
                        } else if (lastEcho == drone.getDirection().toRight()) {
                            decision = drone.turnRight();
                        }
                    }
                } else {
                    if (echoCheck.get("left") && echoCheck.get("right") && echoCheck.get("forward")) {
                        return drone.stop();
                    }
                    decision = drone.turnRight();
                }
            } else if (prevResType.equals("heading")) {
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
