import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from "@angular/router";
import { AuthenticationService } from "../../service/authentication.service";
import { inject } from "@angular/core";

export const checkIfAuthenticated =
    (route: ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
        const authSvc = inject(AuthenticationService)
        const router = inject(Router)

        if(authSvc.isAuthenticated()){
            return true
        }
        return router.parseUrl("/")
    }