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

            if ((sr.getBiomes().contains("OCEAN") && sr.getBiomes().size() == 1)) {
                context.setLastFlyDirection(context.getDrone().getDirection());
                context.resetEchoCheck();
                return context.getDrone().echo();
            } else{
                return context.getDrone().fly();
            }
        } else if (prevResType.equals("fly") || prevResType.equals("heading")) {
            return context.getDrone().scan();
        } else if (prevResType.equals("echo")) {
            EchoResponse er = (EchoResponse) prevResponse;
            if (er.getFound().equals("OUT_OF_RANGE")) {
                context.updatePhase(Phase.UTURN);
            } else {
                context.setDistanceToLand(er.getRange());
                context.updatePhase(Phase.APPROACH_LAND);
            }
            return null;
        }
        return null;
    }
}
