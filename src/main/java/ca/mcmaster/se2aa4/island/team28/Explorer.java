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

    public boolean scanOrFly = false;
    public boolean leftOrRight = false;
    public String direction;
    public Integer batteryLevel;
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
        Decision decision;

        if (previousResponse == null ||
                previousResponse.getBiomes().isEmpty() ||
                previousResponse.getBiomes().contains("OCEAN") ||
                batteryLevel > 6800
        ) {
            if (scanOrFly) {
                if (leftOrRight) {
                    decision = new Decision("heading", Direction.EAST);
                    leftOrRight = false;
                } else {
                    decision = new Decision("heading", Direction.SOUTH);
                    leftOrRight = true;
                }
                scanOrFly = false;
            } else{
                decision = new Decision("scan");
                scanOrFly = true;
            }
        } else {
            decision = new Decision("stop");
        }

        logger.info("** Decision: {}",decision.getDecision().toString());
        return decision.getDecision().toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");

        JSONArray biomes;
        try {
             biomes = extraInfo.getJSONArray("biomes");
        } catch (JSONException e) {
             biomes = new JSONArray();
        }

        previousResponse = new Response(response.getInt("cost"), response.getString("status"), biomes.toList());
        logger.info("** Acknowledgement results:\n {}",previousResponse.toString());

        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        batteryLevel -= cost;
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
