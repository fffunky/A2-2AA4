package ca.mcmaster.se2aa4.island.team28;

import java.io.StringReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private ResponseBuilder rb = null;
    private CommandCentre cc = null;

    public boolean leftOrRight = false;
    public String direction;
    public Integer batteryLevel;
    public Action previousAction = null;
    public Response previousResponse;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        direction = info.getString("heading");
        batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        cc = new CommandCentre(Direction.fromString(direction) , batteryLevel);

    }

    @Override
    public String takeDecision() {
        Action action = cc.getDecision(previousResponse);

        previousAction = action;
        logger.info("** Decision: {}", action.getAction().toString());
        return action.getAction().toString();
    }

    @Override
    public void acknowledgeResults(String s) {

        rb = new ResponseBuilder(s, previousAction);
        previousResponse = rb.getResponse();

        logger.info("** Acknowledgement results:\n{}\n",previousResponse.toString());
    }

    @Override
    public String deliverFinalReport() {
        StringBuilder sb = new StringBuilder();

        if (!cc.getPois().isEmpty()) {
            for (Object s : cc.getPois()) {
                sb.append(s).append("\n");
            }
        } else {
            sb.append("No Creeks or Emergency sites found.\n");
        }

        logger.info(sb.toString());
        return sb.toString();
    }

}
