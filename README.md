# VTTP_5A_Final_Project
## Backend (Java Spring) Endpoints

POST /api/insert/expenses
Headers : 
```
  Content-Type:"application/json"
  Authorization: Bearer <token>
```
Body :
```json
{
  "name" : "Clothes",
  "date" : "2025-02-01",
  "amount" : "20.50",
  "category" : "Needs",
  "description" : "Bought T-Shirts from Uniqlo",
  "email" : "remus@abc.com"
}
```

POST /api/insert/loans
Headers : 
```
  Content-Type:"application/json"
  Authorization: Bearer <token>
```
Body :
```json
{
  "amount" : "100000",
  "description" : "Housing loan",
  "email" : "remus@abc.com"
}
```

POST /api/insert/loanpayments
Headers : 
```
  Content-Type:"application/json"
  Authorization: Bearer <token>
```
Body :
```json
{
  "amount" : "100000",
  "date": "2025-02-01",
  "loan_id" : "1234zxcv"
}
```

GET /api/loan/amount
Headers : 
```
  Authorization: Bearer <token>
  Query Params: loanAmount, annualInterest, duration, paymentType
```

GET /api/loan/duration
Headers:
```
  Authorization: Bearer <token>
  Query Params: loanAmount, annualInterest, paymentPerPeriod, paymentType
```