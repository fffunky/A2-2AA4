package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandCentre {

    private static final Logger log = LogManager.getLogger(CommandCentre.class);
    private final DecisionContext context;
    private final Map<Phase, PhaseHandler> handlers = new EnumMap<>(Phase.class);

    private Integer distanceToLand;
    private List<Coordinate> travelLog = new ArrayList<>();
    private List<Object> pois = new ArrayList<>();
    private List<List<Object>> biomeHistory = new ArrayList<>();
    private Map<String, Boolean> echoCheck = new HashMap<>();
    private Direction lastEcho = null;

    public CommandCentre(Direction direction, Integer BatteryLevel) {
        this.context = new DecisionContext(new Drone(direction, BatteryLevel));
        handlers.put(Phase.FIND_LAND, new FindLandHandler());
        handlers.put(Phase.APPROACH_LAND, new ApproachLandHandler());
        handlers.put(Phase.SCAN_LAND, new ScanLandHandler());
        handlers.put(Phase.RETURN_TO_LAND, new ReturnToLandHandler());
    }

    public Action getDecision(Response prevResponse) {
        if (prevResponse == null) {
            // this will trigger on the very first call
            return context.getDrone().echo();
        }

        context.getDrone().drainBattery(prevResponse.getCost());

        if (context.getDrone().getBattery() <= 25) {
            log.info("*** low battery ({}), ending search.", context.getDrone().getBattery());
            return context.getDrone().stop();
        }

        Phase currentPhase;
        Action decision;
        do {
            currentPhase = context.getPhase();
            log.info("*** phase {} ***", currentPhase);
            PhaseHandler handler = handlers.get(currentPhase);
            decision = handler.handle(context, prevResponse);
        } while (context.getPhase() != currentPhase); // Reprocess if phase changed

        return decision;
    }

    public List<Object> getPois() {
        return pois;
    }
}
