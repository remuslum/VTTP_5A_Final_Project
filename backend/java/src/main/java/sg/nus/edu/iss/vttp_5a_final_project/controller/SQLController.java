package sg.nus.edu.iss.vttp_5a_final_project.controller;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_final_project.service.FirebaseAuthService;
import sg.nus.edu.iss.vttp_5a_final_project.service.SQLService;

@RestController
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class SQLController {
    
    @Autowired
    private SQLService sqlService;

    @Autowired
    private FirebaseAuthService firebaseSvc;

    // Insert expense
    @PostMapping(path="/insert/expenses", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertExpenses(@RequestHeader("Authorization") String authHeader, @RequestBody String payload){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
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
                } catch (DataAccessException|InterruptedException|ExecutionException e) {
                    d.append("message", "Unsuccessful update, SQL Server is not running");
                    return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
        }
    }

    // Insert Loan
    @PostMapping(path="/insert/loan", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertLoans(@RequestHeader("Authorization") String authHeader, @RequestBody String payload){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
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
                } catch (DataAccessException|InterruptedException|ExecutionException e) {
                    d.append("message", "Unsuccessful update, SQL Server is not running");
                    return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Insert LoanPayment
    @PostMapping(path="/insert/loanpayments", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertLoanPayments(@RequestHeader("Authorization") String authHeader, @RequestBody String payload){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
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
                    d.append("message", "Unsuccessful update, SQL Server is not running");
                    return new ResponseEntity<>(d.toJson(), HttpStatusCode.valueOf(500));
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
        }
        
    }

    @GetMapping("/get/currentspending")
    public ResponseEntity<String> getCurrentSpending(@RequestHeader("Authorization") String authHeader,@RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                Optional<Integer> currOptional = sqlService.getCurrentSpending(email);
                return currOptional.map(value -> {
                    JsonObject response = Json.createObjectBuilder().add("amount",value).build();
                    return ResponseEntity.ok(response.toString());
                }).orElseGet(() -> {
                    JsonObject error = Json.createObjectBuilder().add("error","Unable to get data").build();
                    return ResponseEntity.badRequest().body(error.toString());
                });
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/get/spending/category")
    public ResponseEntity<String> getSpendingByCategory(@RequestHeader("Authorization") String authHeader,@RequestParam String email){
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getSpendingByCategory(email));
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString());
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
        }
        
    }

    @GetMapping("/get/spending/month")
    public ResponseEntity<String> getSpendingByMonthAndYear(@RequestHeader("Authorization") String authHeader,@RequestParam String email){
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getSpendingByMonthAndYear(email));
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString());
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED); 
        }  
    }

    @GetMapping("/get/maxspending")
    public ResponseEntity<String> getMaxSpending(@RequestHeader("Authorization") String authHeader,@RequestParam String email){
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getMaxSpending(email));
                } catch (SQLException e){
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString());
                }
            }
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED); 
        }
    }

    @GetMapping("/get/expenses")
    public ResponseEntity<String> getAllExpenses(@RequestHeader("Authorization") String authHeader,@RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getAllExpenses(email).toString());
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        }
    }

    @GetMapping("/get/loans")
    public ResponseEntity<String> getAllLoans(@RequestHeader("Authorization") String authHeader, @RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getAllLoans(email).toString());
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        } 
    }

    @GetMapping("/get/sum/loanpayments")
    public ResponseEntity<String> getSumOfLoanPayments(@RequestHeader("Authorization") String authHeader, @RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getSumOfLoanPayments(email).toString());
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        }  
    }

    @GetMapping("/get/loanpayment/latest")
    public ResponseEntity<String> getLatestLoanPayment(@RequestHeader("Authorization") String authHeader, @RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getLatestLoanPayment(email));
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        }  
    }

    @GetMapping("/get/loanpayments")
    public ResponseEntity<String> getLoanPayments(@RequestHeader("Authorization") String authHeader, @RequestParam String email){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    return ResponseEntity.ok(sqlService.getLatestLoanPaymentsTable(email).toString());
                } catch (SQLException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        }  
    }

    @PutMapping("/update/expense")
   public ResponseEntity<String> updatePayment(@RequestHeader("Authorization") String authHeader, @RequestBody String payload){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            if(firebaseSvc.verifyToken(token).isBlank()){
                JsonObject error = Json.createObjectBuilder().add("error","Unauthorized access").build();
                return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);
            } else {
                try {
                    if(sqlService.updateExpense(payload)){
                        JsonObjectBuilder builder = Json.createObjectBuilder();
                        builder.add("message","Update successful");
                        return ResponseEntity.ok(builder.build().toString());
                    } else {
                        JsonObjectBuilder builder = Json.createObjectBuilder();
                        builder.add("error","Update unsuccessful"); 
                        return ResponseEntity.badRequest().body(builder.build().toString());
                    }

                } catch (DataAccessException e) {
                    JsonObject error = Json.createObjectBuilder().add("error",e.getMessage()).build();
                    return ResponseEntity.badRequest().body(error.toString()); 
                }
            } 
        } else {
            JsonObject error = Json.createObjectBuilder().add("error","Authorization token missing").build();
            return new ResponseEntity<>(error.toString(),HttpStatus.UNAUTHORIZED);  
        }  
    } 



}
