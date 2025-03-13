package sg.nus.edu.iss.vttp_5a_final_project.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_final_project.service.SQLService;

@RestController
public class SQLController {
    
    @Autowired
    private SQLService sqlService;

    // Insert expense
    @PostMapping(path="/api/insert/expenses", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertExpenses(@RequestBody String payload){
        Document d = new Document();
        try {
            boolean isInserted = sqlService.insertExpenses(payload);
            if (isInserted){
                d.append("message", "Successfully updated");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(201));
            } else {
                d.append("message","Unsuccessful update, please check the parameters");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(404));
            }
        } catch (DataAccessException e) {
            d.append("message", "Unsuccessful update, SQL Server is not running");
            return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
        }
    }

    // Insert Loan
    @PostMapping(path="/api/insert/loans", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertLoans(@RequestBody String payload){
        Document d = new Document();
        try {
            boolean isInserted = sqlService.insertLoan(payload);
            if (isInserted){
                d.append("message", "Successfully updated");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(201));
            } else {
                d.append("message","Unsuccessful update, please check the parameters");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(404));
            }
        } catch (DataAccessException e) {
            d.append("message", "Unsuccessful update, SQL Server is not running");
            return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
        }
    }

    // Insert LoanPayment
    @PostMapping(path="/api/insert/loanpayments", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertLoanPayments(@RequestBody String payload){
        Document d = new Document();
        try {
            boolean isInserted = sqlService.insertLoanPayments(payload);
            if (isInserted){
                d.append("message", "Successfully updated");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(201));
            } else {
                d.append("message","Unsuccessful update, please check the parameters");
                return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(404));
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            d.append("message", "Unsuccessful update, SQL Server is not running");
            return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
        }
    }


}
