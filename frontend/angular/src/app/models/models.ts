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
    name : string
    date : string
    amount : number
    category : string
    description : string
    email : string
}

export interface Loan {
    amount : number
    description : string
    email : string
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