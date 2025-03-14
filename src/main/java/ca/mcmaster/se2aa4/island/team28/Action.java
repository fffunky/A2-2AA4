package ca.mcmaster.se2aa4.island.team28;

import org.json.JSONObject;

public class Action {

    private JSONObject action = new JSONObject();
    private JSONObject parameters = new JSONObject();

    public Action(String type){
        action.put("action", type);
    }

    public Action(String type, Direction d){
        action.put("action", type);
        parameters.put("direction", d.toString());
        action.put("parameters", parameters);
    }

    public Action(String type, Direction d, Integer range){
        action.put("action", type);
        parameters.put("direction", d.toString());
        parameters.put("range", range);
        action.put("parameters", parameters);
    }

    public Action(String type, String creekId, Integer people){
        action.put("action", type);
        parameters.put("creek", creekId);
        parameters.put("people", people);
        action.put("parameters", parameters);
    }

    public JSONObject getAction(){
        return action;
    }
}