package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_final_project.service.SQLService;

@RestController
@RequestMapping("/api/telegram")
public class TelegramController {
    
    @Autowired
    private SQLService sqlService;


    @PostMapping(path="/insert/expense",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertExpense(@RequestBody String payload){
        try {
            sqlService.insertExpenses(payload);
            JsonObject response = Json.createObjectBuilder().add("message", "Successfully added expense").build();
            return ResponseEntity.ok(response.toString());
        } catch (DataAccessException|InterruptedException|ExecutionException e) {
            JsonObject response = Json.createObjectBuilder().add("message", "Unable to add expense").build();
            return ResponseEntity.internalServerError().body(response.toString());
        }
    }

    @PostMapping(path="/insert/loanpayment",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertLoanPayment(@RequestBody String payload){
        if(sqlService.insertLoanPayments(payload)){
            JsonObject response = Json.createObjectBuilder().add("message", "Successfully added expense").build();
            return ResponseEntity.ok(response.toString());
        } else {
            JsonObject response = Json.createObjectBuilder().add("message", "Unable to add expense").build();
            return ResponseEntity.internalServerError().body(response.toString());
        }
    }

    @GetMapping(path="/loans", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllLoans(@RequestParam String email){
        try {
            return ResponseEntity.ok(sqlService.getAllLoans(email).toString());
        } catch (SQLException e) {
            JsonObject object = Json.createObjectBuilder().add("message",e.getMessage()).build();
            return ResponseEntity.badRequest().body(object.toString());
        }
    }

    @GetMapping(path="/check/email/{email}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVerifiedEmail(@PathVariable String email){
        if(sqlService.checkValidEmail(email)){
            return ResponseEntity.ok("Email is verified");
        } else {
            return ResponseEntity.badRequest().body("Email cannot be found");
        }
    }

    @GetMapping(path="/check/loan/{loanId}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLoanId(@PathVariable String loanId){
        if(sqlService.checkValidLoanId(loanId)){
            return ResponseEntity.ok("Loan ID is valid");
        } else {
            return ResponseEntity.badRequest().body("Invalid Loan ID");
        }
    }
}
