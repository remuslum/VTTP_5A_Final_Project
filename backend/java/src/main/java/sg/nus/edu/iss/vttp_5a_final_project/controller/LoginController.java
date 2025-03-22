package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.service.FirebaseAuthService;
import sg.nus.edu.iss.vttp_5a_final_project.service.LoginService;

@RestController
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired 
    private LoginService loginService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;
    
    @PostMapping(path="/login")
    public ResponseEntity<String> getLogin(@RequestBody String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        String email = object.getString("email");
        String password = object.getString("password");
        try {
            String token = loginService.logUserInAndGetToken(email, password);
            return ResponseEntity.ok(token);
        } catch (HttpClientErrorException e) {
            System.out.println("Error response: " + e.getResponseBodyAsString());
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    } 

    @PostMapping(path="/signup")
    public ResponseEntity<String> createAccount(@RequestBody String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        String email = object.getString("email");
        String password = object.getString("password");
        try {
            String token = loginService.createAnAccount(email, password);
            return ResponseEntity.ok(token);
        } catch (HttpClientErrorException e) {
            System.out.println("Error response: " + e.getResponseBodyAsString());
            return new ResponseEntity<>("Sign up failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } 
    }

    @PostMapping(path="/authenticate")
    public ResponseEntity<String> checkIfAccountIsValid(@RequestBody String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        String token = object.getString("token");
        return ResponseEntity.ok(firebaseAuthService.verifyToken(token));
    }
}
