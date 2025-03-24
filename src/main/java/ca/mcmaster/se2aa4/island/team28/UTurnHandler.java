package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UTurnHandler implements PhaseHandler {
    private static final Logger log = LogManager.getLogger(UTurnHandler.class);

    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        if (prevResponse.getType().equals("heading")) {
            if (context.getUTurnCount() == 1) {
                context.setUTurnCount(2);
                return getTurnDirection(context);
            } else if (context.getUTurnCount() == 2) {
                return context.getDrone().scan();
            }
        } else if (prevResponse.getType().equals("scan")) {
            if (context.getUTurnCount() == 2) {
                ScanResponse sr = (ScanResponse) prevResponse;
                if (sr.getBiomes().contains("OCEAN") && sr.getBiomes().size() == 1) {
                    return context.getDrone().echo();
                } else {
                    context.setUTurnCount(0);
                    context.updatePhase(Phase.SCAN_LAND);
                    return null;
                }
            } else {
                context.setUTurnCount(1);
                return getTurnDirection(context);
            }
        } else if (prevResponse.getType().equals("echo")) {
            if (context.getUTurnCount() == 0) {
                context.setUTurnCount(1);
                return getTurnDirection(context);
            } else {
                context.setUTurnCount(0);
                EchoResponse er = (EchoResponse) prevResponse;
                if (er.getFound().equals("OUT_OF_RANGE")) {
                    if (context.searchingEast()) {
                        context.setLastFlyDirection(context.getDrone().getDirection());
                        context.startSearchingWest();
                        context.updatePhase(Phase.REORIENT);
                    } else {
                        return context.getDrone().stop();
                    }
                } else {
                    context.setDistanceToLand(er.getRange());
                    context.updatePhase(Phase.APPROACH_LAND);
                }
                return null;
            }
        }
        return null;
    }

    private Action getTurnDirection(DecisionContext context) {
        if (context.searchingEast()) {
            if (context.getLastFlyDirection().isEqual(Direction.SOUTH)) {
                return context.getDrone().turnLeft();
            }
            return context.getDrone().turnRight();
        } else {
            if (context.getLastFlyDirection().isEqual(Direction.SOUTH)) {
                return context.getDrone().turnRight();
            }
            return context.getDrone().turnLeft();
        }
    }
}
