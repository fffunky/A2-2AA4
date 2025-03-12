package ca.mcmaster.se2aa4.island.team28;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    public boolean scanOrFly = false;
    public boolean leftOrRight = false;
    public String direction;
    public Integer batteryLevel;

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
        JSONObject decision = new JSONObject();

        if (batteryLevel > 6900) {
            if (scanOrFly) {
                decision.put("action", "heading");
                JSONObject parameters = new JSONObject();
                if (leftOrRight) {
                    parameters.put("direction", "E");
                    leftOrRight = false;
                } else {
                    parameters.put("direction", "S");
                    leftOrRight = true;
                }
                decision.put("parameters", parameters);
                scanOrFly = false;
            } else{
                decision.put("action", "scan");
                scanOrFly = true;
            }
        } else {
            decision.put("action", "stop");
        }



        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        //JSONObject extraInfo = response.getJSONObject("extras");

        //Response res = new Response(response.getInt("cost"), response.getString("status"), extraInfo.);

        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        batteryLevel -= cost;
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
