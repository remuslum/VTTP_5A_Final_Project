package sg.nus.edu.iss.vttp_5a_final_project.util;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class FirebaseDetails {
    private String apiKey;
    private final String authDomain = "finance-hub-d6197.firebaseapp.com";
    private final String projectId = "finance-hub-d6197";
    private final String storageBucket = "finance-hub-d6197.firebasestorage.app";
    private final String messagingSenderId = "526857629195";
    private final String appId = "1:526857629195:web:2b43e57470c11398bf210e";
    private final String measurementId = "G-0SRH2RXJYB";

    public FirebaseDetails() {

    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getStorageBucket() {
        return storageBucket;
    }

    public String getMessagingSenderId() {
        return messagingSenderId;
    }

    public String getAppId() {
        return appId;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public JsonObject toJson(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("apiKey", this.getApiKey());
        builder.add("authDomain", this.getAuthDomain());
        builder.add("projectId", this.getProjectId());
        builder.add("storageBucket",this.getStorageBucket());
        builder.add("messagingSenderId",this.getMessagingSenderId());
        builder.add("appId", this.getAppId());
        builder.add("measurementId",this.getMeasurementId());
        
        return builder.build();
    }
    
}
