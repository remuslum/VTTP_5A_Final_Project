export interface MonthlyPayment {
    amount : number
    interestRate : number
    payment : number
    frequency : string
}

export interface Duration {
    amount : number
    interestRate : number
    duration : number 
    frequency : string
}

export interface Expense {
    id: number
    name : string
    date : string
    amount : number
    category : string
    description : string
    email : string
}

export interface Loan {
    id:string
    amount : number
    description : string
}

export interface LoanPayment {
    id:number
    loan_id:string
    amount:number
    description:string
}

export interface UserDetails {
    email : string
    password : string
}

export interface CurrentSpending {
    amount: number
}

export interface CategorySpending {
    spending:number
    month:number
    category:string
}

export interface MonthSpending{
    spending:number
    month:number
}

export interface MaxSpending{
    category:string
    spending:number
}

export interface SumOfLoanPayments{
    loan_id:string
    total_sum:number
    amount:number
    description:string
}

export interface LatestLoanPayment{
    amount:number
    description:string
    date:string
}

export interface addLoan {
    amount:number
    description:string
    email:string
}

export interface LoanPaymentTableItem {
    id:number
    amount:number
    description:string
    date:string
}

export interface User {
    firstName:string
    lastName:string
    email:string
    password:string
    dateOfBirth:string
}