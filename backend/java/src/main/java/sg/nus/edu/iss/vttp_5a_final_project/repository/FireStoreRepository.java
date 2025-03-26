package sg.nus.edu.iss.vttp_5a_final_project.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;

import sg.nus.edu.iss.vttp_5a_final_project.model.Expense;
import sg.nus.edu.iss.vttp_5a_final_project.model.Loan;

@Repository
public class FireStoreRepository {
    
    @Autowired
    private Firestore firestore;

    private final String OPERATION = "operation";
    private final String TIMESTAMP = "timestamp";
    private final String TYPE = "type";
    private final String ITEM = "item";

    // Variables to add
    private final String LOAN = "loan";
    private final String EXPENSE = "expense";

    // types of operation
    private final String ADD = "add";
    


    public boolean addExpensesIntoCollection(List<Expense> expenses) throws ExecutionException, InterruptedException{
        WriteBatch writeBatch = firestore.batch();

        // Collection name is the email of the user
        String email = expenses.get(0).getEmail();

        expenses.forEach(e -> {
            // Generate document Id from FireStore
            String documentId = firestore.collection(email).document().getId();

            // Add information about the operation
            Map<String,Object> docData = new HashMap<>();
            docData.put(OPERATION, ADD);
            docData.put(TIMESTAMP,LocalDateTime.now().toString());
            docData.put(TYPE,EXPENSE);

            // Add expense variables
            Map<String, Object> expenseData = new HashMap<>();
            expenseData.put("name",e.getName());
            expenseData.put("date",e.getDate().toString());
            expenseData.put("amount",e.getAmount());
            expenseData.put("category",e.getCategory());
            expenseData.put("description",e.getDescription());
            expenseData.put("email",e.getEmail());

            // Add expense to main docData
            docData.put(ITEM,expenseData);

            // Add a set operation to the batch
            writeBatch.set(firestore.collection(email).document(documentId),docData);
        });
        // Commit the batch
        return !writeBatch.commit().get().isEmpty();
    }

    public boolean addLoansIntoCollection(Loan loan){
        // Collection name is the email of the user
        String email = loan.getEmail();
        String documentId = firestore.collection(email).document().getId();

        // Add information about the operation
        Map<String,Object> docData = new HashMap<>();
        docData.put(OPERATION, ADD);
        docData.put(TIMESTAMP,LocalDateTime.now().toString());
        docData.put(TYPE,LOAN);

        // Add loan variables
        Map<String, Object> loanData = new HashMap<>();
        loanData.put("amount", loan.getAmount());
        loanData.put("description",loan.getDescription());
        loanData.put("email",loan.getEmail());
        loanData.put("id", loan.getId());

        // Add loan to main document
        docData.put(ITEM,loanData);
        try {
            DocumentReference docRef = firestore.collection(email).document(documentId);
            docRef.set(docData).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }

    }
}
