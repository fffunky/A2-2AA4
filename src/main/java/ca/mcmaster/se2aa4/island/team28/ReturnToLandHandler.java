package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReturnToLandHandler implements PhaseHandler {

    @Override
    public Action handle(DecisionContext context, Response prevResponse) {
        return context.getDrone().stop();
    }
}
