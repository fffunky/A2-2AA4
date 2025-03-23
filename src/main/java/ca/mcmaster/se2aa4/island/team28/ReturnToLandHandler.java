package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

public class ReturnToLandHandler implements PhaseHandler {

    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        if (prevResponse.getType().equals("scan") || prevResponse.getType().equals("echo")) {
            if (Objects.equals(context.getLastEcho(), null)) {
                context.setLastEcho("left");
                return context.getDrone().echo(context.getDrone().getDirection().toLeft());
            }
            context.updateEchoCheck(context.getLastEcho(), (EchoResponse) prevResponse);

            if (Objects.equals(context.getLastEcho(), "left")) {
                context.setLastEcho("right");
                return context.getDrone().echo(context.getDrone().getDirection().toRight());
            }
            context.updateEchoCheck(context.getLastEcho(), (EchoResponse) prevResponse);

            if (Objects.equals(context.getLastEcho(), "right")) {
                context.setLastEcho("forward");
                return context.getDrone().echo(context.getDrone().getDirection());
            }
            context.updateEchoCheck(context.getLastEcho(), (EchoResponse) prevResponse);

            Integer leftRange = context.getEchoCheck().get("left").getRange();
            Integer rightRange = context.getEchoCheck().get("right").getRange();
            Integer forwardRange = context.getEchoCheck().get("forward").getRange();

            if ((leftRange < rightRange && leftRange < forwardRange) && !context.getEchoCheck().get("left").getFound().equals("OUT_OF_RANGE")) {
                context.setDistanceToLand(leftRange);
                return context.getDrone().turnLeft();
            } else if (rightRange < leftRange && rightRange < forwardRange && !context.getEchoCheck().get("right").getFound().equals("OUT_OF_RANGE")) {
                context.setDistanceToLand(rightRange);
                return context.getDrone().turnRight();
            } else if (forwardRange < leftRange && forwardRange < rightRange && !context.getEchoCheck().get("forward").getFound().equals("OUT_OF_RANGE")){
                context.setDistanceToLand(forwardRange);
                return context.getDrone().fly();
            } else {
                //context.setLastEcho(null);
                //context.resetEchoCheck();
                return context.getDrone().turnRight();
            }
        } else {
            context.setLastEcho(null);
            context.resetEchoCheck();
            context.updatePhase(Phase.APPROACH_LAND);
            return null;
        }


    }
}
