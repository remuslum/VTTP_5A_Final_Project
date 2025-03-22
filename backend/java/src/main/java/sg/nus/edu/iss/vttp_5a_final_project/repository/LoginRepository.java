package sg.nus.edu.iss.vttp_5a_final_project.repository;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Repository
public class LoginRepository {

    @Value("${firebase.api.key}")
    private String firebaseAPIKey;

    public String getTokenFromRequest(String email, String password, String requestUrl){
        String url = UriComponentsBuilder.fromUriString(requestUrl).queryParam("key", firebaseAPIKey).build().toString();

        // Headers
        HttpEntity<Map<String, Object>> entity = initialiseHeaders(email, password);
       
        // Send request to firebase for Auth
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if(response.getStatusCode().is2xxSuccessful()){
            JsonObject object = Json.createReader(new StringReader(response.getBody())).readObject();
            return object.getString("idToken");
        } else {
            return response.getBody();
        } 
    }

    // Initialise headers
    private HttpEntity<Map<String, Object>> initialiseHeaders(String email, String password){
        Map<String, Object> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        request.put("returnSecureToken", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create an entity with headers and request body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request,headers);
        return entity;
   }
}
