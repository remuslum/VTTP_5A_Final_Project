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

