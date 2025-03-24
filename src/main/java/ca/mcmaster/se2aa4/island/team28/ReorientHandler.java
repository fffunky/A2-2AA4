package ca.mcmaster.se2aa4.island.team28;

public class ReorientHandler implements PhaseHandler {
    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        if (context.getReorientCount() == 0) {
            context.setReorientCount(1);
            if (context.getLastFlyDirection().isEqual(Direction.NORTH)) {
                return context.getDrone().turnLeft();
            } else {
                return context.getDrone().turnRight();
            }
        } else if (context.getReorientCount() == 1) {
            context.setReorientCount(2);
            return context.getDrone().fly();
        } else if (context.getReorientCount() == 2) {
            context.setReorientCount(3);
            if (context.getLastFlyDirection().isEqual(Direction.NORTH)) {
                return context.getDrone().turnRight();
            } else {
                return context.getDrone().turnLeft();
            }
        } else {
            context.setReorientCount(0);
            context.updatePhase(Phase.SCAN_LAND);
            return null;
        }
    }
}
