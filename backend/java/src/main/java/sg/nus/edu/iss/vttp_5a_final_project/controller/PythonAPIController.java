package sg.nus.edu.iss.vttp_5a_final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_final_project.repository.PythonAPIRepo;

@RestController
public class PythonAPIController {

    @Autowired
    private PythonAPIRepo pythonAPIrepo;
    
    @GetMapping(path="/api/python/ma", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMAResults(@RequestParam String symbol){
        return pythonAPIrepo.getMAResults(symbol);
    }
}
