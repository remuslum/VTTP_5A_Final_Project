package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.service.LoanCalculatorService;

@RestController
public class LoanPaymentController {
    
    @Autowired
    private LoanCalculatorService loanService;

    @PostMapping(path="/api/loan/amount", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLoanAmount(@RequestBody String payload){
        // Parse payload
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        int loanAmount = object.getInt("amount");
        double annualInterest = object.getJsonNumber("interestRate").doubleValue();
        int duration = object.getInt("duration");
        String paymentType = object.getString("frequency");

        return new ResponseEntity<>(loanService.getLoanAmount(loanAmount, annualInterest, duration, paymentType), HttpStatusCode.valueOf(200));
    }

    @PostMapping(path="/api/loan/duration", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDuration(@RequestBody String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        int loanAmount = object.getInt("amount");
        double annualInterest = object.getJsonNumber("interestRate").doubleValue();
        double paymentPerPeriod = object.getJsonNumber("payment").doubleValue();
        String paymentType = object.getString("frequency");

        return new ResponseEntity<>(loanService.getNumberOfPeriods(loanAmount, annualInterest, paymentPerPeriod, paymentType), HttpStatusCode.valueOf(200));
    }

    
}
