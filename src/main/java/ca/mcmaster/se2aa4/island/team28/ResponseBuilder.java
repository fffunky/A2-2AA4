package ca.mcmaster.se2aa4.island.team28;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class ResponseBuilder {

    private static final Logger log = LogManager.getLogger(ResponseBuilder.class);
    private Response response;

    public ResponseBuilder(String responseString, Decision previousDecision) {
        JSONObject responseJson = new JSONObject(new JSONTokener(new StringReader(responseString)));
        this.response = buildResponse(responseJson, previousDecision);
    }

    private Response buildResponse(JSONObject responseJson, Decision previousDecision) {
        String lastAction = previousDecision.getDecision().getString("action");

        Response res = null;
        JSONObject extraInfo;
        Integer cost = responseJson.getInt("cost");
        String status = responseJson.getString("status");

        switch (lastAction) {
            case "fly":
                res = new FlyResponse("fly", cost, status);
                break;
            case "heading":
                res = new HeadingResponse("heading", cost, status);
                break;
            case "echo":
                extraInfo = responseJson.getJSONObject("extras");
                res = new EchoResponse("echo", cost, status,
                        extraInfo.getInt("range"),
                        extraInfo.getString("foudn"));
                break;
            case "scan":
                extraInfo = responseJson.getJSONObject("extras");
                res = new ScanResponse("scan", cost, status,
                        extraInfo.getJSONArray("biomes").toList(),
                        extraInfo.getJSONArray("creeks").toList(),
                        extraInfo.getJSONArray("sites").toList());
                break;
            case "stop":
                res = new StopResponse("stop", cost, status);
                break;
            default:
                log.error("Unknown last action: {}", lastAction);
        }

        return res;
    }

    public Response getResponse() {
        return response;
    }
}
