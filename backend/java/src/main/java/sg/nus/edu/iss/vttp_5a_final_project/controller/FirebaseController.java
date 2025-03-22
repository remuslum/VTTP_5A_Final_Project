package sg.nus.edu.iss.vttp_5a_final_project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_final_project.util.FirebaseDetails;

@RestController
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class FirebaseController {
    @Value("${firebase.api.key}")
    private String apiKey;
    
    @GetMapping("/firebase/details")
    public ResponseEntity<String> getFirebaseDetails(){
        FirebaseDetails firebaseDetails = new FirebaseDetails();
        firebaseDetails.setApiKey(apiKey);
        return ResponseEntity.ok(firebaseDetails.toJson().toString());
    }
}
