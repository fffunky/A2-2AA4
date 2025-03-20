package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class CommandCentre {

    private static final Logger log = LogManager.getLogger(CommandCentre.class);
    private Drone drone;
    private Phase phase = Phase.FIND_LAND;

    private Integer distanceToLand;

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

        if (phase == Phase.FIND_LAND) {
            log.info("* find land phase");
            if (prevResType.equals("echo")) {
                log.info("** last action echo");
                String found = ((EchoResponse) prevResponse).getFound();
                if (Objects.equals(found, "OUT_OF_RANGE")) {
                    log.info("*** echo out of range");
                    decision = (drone.getDirection() == Direction.SOUTH) ? drone.turnLeft() : drone.turnRight();
                } else {
                    log.info("*** echo in range");
                    distanceToLand = ((EchoResponse) prevResponse).getRange();
                    updatePhase(Phase.APPROACH_LAND);
                    decision = getDecision(prevResponse);
                }

            } else if (prevResType.equals("heading")) {
                log.info("** last action heading");
                decision = drone.echo();
            }

        } else if (phase == Phase.APPROACH_LAND) {
            log.info("* approach phase");
            if (prevResType.equals("scan")) {
                log.info("** last action scan");
                if (distanceToLand >= 0) {
                    decision = drone.fly();
                    distanceToLand -= 1;
                    log.info("*** fly forward");
                } else {
                    log.info("*** stop");
                    decision = drone.stop();
                }
            } else {
                log.info("** last action not scan");
                decision = drone.scan();
            }
        }

        return decision;
    }

    public void analyzePreviousResponse(Response pr) {
    }

    public void updatePhase(Phase p){
        this.phase = p;
    }
}
