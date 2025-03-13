package ca.mcmaster.se2aa4.island.team28;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private ResponseBuilder rb = null;

    public boolean leftOrRight = false;
    public String direction;
    public Integer batteryLevel;
    public Decision previousDecision = null;
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

    }

    @Override
    public String takeDecision() {
        Decision decision = null;

        if (previousResponse == null) {
            decision = new Decision("scan");
        } else if (previousResponse instanceof ScanResponse) {
            if (((ScanResponse) previousResponse).getBiomes().isEmpty() ||
                    (((ScanResponse) previousResponse).getBiomes().contains("OCEAN"))) {
                if (leftOrRight) {
                    decision = new Decision("heading", Direction.EAST);
                    leftOrRight = false;
                } else {
                    decision = new Decision("heading", Direction.SOUTH);
                    leftOrRight = true;
                }
            } else {
                decision = new Decision("stop");
            }
        } else if (previousResponse.getType().equals("heading")) {
                decision = new Decision("scan");
        } else {
            decision = new Decision("stop");
        }

        previousDecision = decision;
        logger.info("** Decision: {}",decision.getDecision().toString());
        return decision.getDecision().toString();
    }

    @Override
    public void acknowledgeResults(String s) {

        rb = new ResponseBuilder(s, previousDecision);

        previousResponse = rb.getResponse();

        logger.info("** Acknowledgement results:\n {}",previousResponse.toString());

        batteryLevel -= previousResponse.getCost();
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
