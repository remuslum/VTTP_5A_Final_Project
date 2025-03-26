import { AbstractControl, ValidatorFn } from "@angular/forms";


export function noFutureDateValidator(): ValidatorFn{
    return (control : AbstractControl) => {
        if(!control.value) return null
        const inputDate = new Date(control.value)
        const today = new Date()

        if (inputDate > today){
            return {futureDate : true}
        } 
        return null // null is returned if values passes validator
    }
}

export function noYoungerThan21():ValidatorFn{
    return (control : AbstractControl) => {
        if(!control.value) return null
        const inputDate = new Date(control.value)
        const today = new Date()
        const difference = Math.abs(inputDate.getFullYear() - today.getFullYear())

        if (difference < 21){
            return {futureDate : true}
        } 
        return null // null is returned if values passes validator
    } 
}