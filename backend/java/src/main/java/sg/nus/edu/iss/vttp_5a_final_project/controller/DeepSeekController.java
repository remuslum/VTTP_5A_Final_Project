package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.nus.edu.iss.vttp_5a_final_project.service.DeepSeekService;



@RestController
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class DeepSeekController {

    @Autowired
    private DeepSeekService deepSeekService;
    
    @PostMapping("/deepseek")
    public ResponseEntity<String> getChatOutput(@RequestBody String payload){
        String question = Json.createReader(new StringReader(payload)).readObject().getString("question");
        return ResponseEntity.ok(deepSeekService.getChatResponse(question));
    }
}
