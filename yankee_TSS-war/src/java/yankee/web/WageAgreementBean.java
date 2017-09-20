package yankee.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.ENUM.RoleTypeEnum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class WageAgreementBean {    

    private int workingDaysPerWeek;
     private int vacationDaysPerYear;

    public int getWorkingDaysPerWeek() {
        workingDaysPerWeek = 5;
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(int workingDaysPerWeek) {        
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public int getVacationDaysPerYear() {
        vacationDaysPerYear = 20;
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(int vacationDaysPerYear) {
        this.vacationDaysPerYear = vacationDaysPerYear;
    }
   
    
}
