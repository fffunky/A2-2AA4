package ca.mcmaster.se2aa4.island.team28;

public class ScanLandHandler implements PhaseHandler {

    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        String prevResType = prevResponse.getType();
        if (prevResType.equals("scan")) {
            ScanResponse sr = (ScanResponse) prevResponse;

            if (!sr.getCreeks().isEmpty()) {
                context.setCreekId(sr.getCreeks().getFirst().toString());
            }

            if (!sr.getSites().isEmpty()) {
                context.setEmergencySiteId(sr.getSites().getFirst().toString());
            }

            if (sr.getBiomes().contains("OCEAN")) {
                context.updatePhase(Phase.RETURN_TO_LAND);
                context.resetEchoCheck();
                return null;
            } else {
                return context.getDrone().fly();
            }
        } else {
            return context.getDrone().scan();
        }
    }
}
