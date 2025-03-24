package ca.mcmaster.se2aa4.island.team28;

public class ApproachLandHandler implements PhaseHandler {
    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        context.setLastFlyDirection(context.getDrone().getDirection());
        if (context.getDistanceToLand() >= 0) {
            context.setDistanceToLand(context.getDistanceToLand() - 1);
            return context.getDrone().fly();
        } else {
            context.updatePhase(Phase.SCAN_LAND);
            return null;
        }
    }
}
