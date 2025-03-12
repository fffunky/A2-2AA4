package ca.mcmaster.se2aa4.island.team28;
public class Decision {

    private JSONObject action = new JSONObject();
    private JSONObject parameters = new JSONObject();

    public Decision(String type){
        action.put("action", type);
    }

    public Decision(String type, Direction d){
        action.put("action", type);
        parameters.put("direction", d);
        action.put("parameters", parameters);
    }

    public Decision(String type, Direction d, Integer range){
        action.put("action", type);
        parameters.put("direction", d);
        parameters.put("range", range);
        action.put("parameters", parameters);
    }

    public Decision(String type, String creekId, Integer people){
        action.put("action", type);
        parameters.put("creek", creekId);
        parameters.put("people", people);
        action.put("parameters", parameters);
    }

    public JSONObject getDecision(){
        return action;
    }
}