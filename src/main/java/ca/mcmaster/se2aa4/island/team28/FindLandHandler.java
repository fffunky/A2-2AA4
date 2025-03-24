package ca.mcmaster.se2aa4.island.team28;

public class FindLandHandler implements PhaseHandler{
    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        String prevResType = prevResponse.getType();

        if (prevResType.equals("echo")) {
            EchoResponse er = (EchoResponse) prevResponse;
            if (er.getFound().equals("OUT_OF_RANGE")) {
                return context.getDrone().fly();
            } else {
                context.setDistanceToLand(er.getRange() - 1);
                return context.getDrone().turnRight();
            }
        } else if (prevResType.equals("fly")) {
            return context.getDrone().echo(context.getDrone().getDirection().toRight());
        } else {
            context.setLastFlyDirection(Direction.SOUTH);
            context.updatePhase(Phase.APPROACH_LAND);
            return null;
        }
    }
}
