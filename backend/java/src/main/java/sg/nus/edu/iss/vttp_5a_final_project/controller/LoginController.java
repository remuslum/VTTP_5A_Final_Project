package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.model.User;
import sg.nus.edu.iss.vttp_5a_final_project.service.FirebaseAuthService;
import sg.nus.edu.iss.vttp_5a_final_project.service.LoginService;
import sg.nus.edu.iss.vttp_5a_final_project.service.SQLService;

@RestController
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired 
    private LoginService loginService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private SQLService sqlService;
    
    @PostMapping(path="/login")
    public ResponseEntity<String> getLogin(@RequestBody String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        String email = object.getString("email");
        String password = object.getString("password");
        try {
            String token = loginService.logUserInAndGetToken(email, password);
            JsonObject response = Json.createObjectBuilder().add("token",token).build();
            return ResponseEntity.ok(response.toString());
        } catch (HttpClientErrorException e) {
            JsonObject error = Json.createObjectBuilder().add("error", "Login failed: " + e.getMessage()).build();
            return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);
        }
    } 

    @PostMapping(path="/signup")
    public ResponseEntity<String> createAccount(@RequestBody String payload){
        try {
            JsonObject object = Json.createReader(new StringReader(payload)).readObject();
            User user = User.convertJsonToUser(payload);
            String token = loginService.createAnAccount(user.getEmail(), object.getString("password"));
            sqlService.insertUser(user);
            JsonObject response = Json.createObjectBuilder().add("token", token).build();
            return ResponseEntity.ok(response.toString());
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
            // Extract the Firebase ID token from the Authorization header
            String idToken = authHeader.replace("Bearer ", "");
            
            // Verify the ID token
            String logout = firebaseAuthService.logOut(idToken);
            if(logout.isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Error logging the user out").build();
                return ResponseEntity.badRequest().body(error.toString());
            } else {
                JsonObject response = Json.createObjectBuilder().add("message","success").build();
                return ResponseEntity.ok(response.toString());
            }
            
    }
}
