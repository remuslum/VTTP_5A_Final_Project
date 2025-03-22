package sg.nus.edu.iss.vttp_5a_final_project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LoginService {

    @Value("${firebase.api.key}")
    private String firebaseAPIKey;

    private final String FIREBASE_AUTH_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    
    public String getUserToken(String email, String password){
        String url = UriComponentsBuilder.fromUriString(FIREBASE_AUTH_URL).queryParam("key", firebaseAPIKey).build().toString();

        // Initialise headers
        Map<String, Object> request = new HashMap<>();
        request.put("email", email);
        request.put("password", password);
        request.put("returnSecureToken", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create an entity with headers and request body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request,headers);

        // Send request to firebase for Auth
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().get("idToken").toString();
        }
        return "invalid credentials";
    }
}
