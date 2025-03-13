package sg.nus.edu.iss.vttp_5a_final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_final_project.service.LoanCalculatorService;

@RestController
public class LoanPaymentController {
    
    @Autowired
    private LoanCalculatorService loanService;

    @GetMapping(path="/api/loan/amount", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLoanAmount(@RequestParam int loanAmount, @RequestParam double annualInterest, 
    @RequestParam int duration, @RequestParam String paymentType){
        return new ResponseEntity<>(loanService.getLoanAmount(loanAmount, annualInterest, duration, paymentType), HttpStatusCode.valueOf(200));
    }

    @GetMapping(path="/api/loan/duration", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLoanAmount(@RequestParam int loanAmount, @RequestParam double annualInterest, 
    @RequestParam double paymentPerPeriod, @RequestParam String paymentType){
        return new ResponseEntity<>(loanService.getNumberOfPeriods(loanAmount, annualInterest, paymentPerPeriod, paymentType), HttpStatusCode.valueOf(200));
    }

    
}
