package ca.mcmaster.se2aa4.island.team28;

public class FindLandHandler implements PhaseHandler{
    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        String prevResType = prevResponse.getType();

        if (prevResType.equals("echo")) {
            EchoResponse er = (EchoResponse) prevResponse;
            if (er.getFound().equals("OUT_OF_RANGE")) {
                Direction curDirection = context.getDrone().getDirection();
                return (curDirection == Direction.SOUTH) ?
                        context.getDrone().turnLeft() : context.getDrone().turnRight();
            } else {
                context.setDistanceToLand(er.getRange());
                context.updatePhase(Phase.APPROACH_LAND);
                return null;
            }
        } else {
            return context.getDrone().echo();
        }
    }
}
