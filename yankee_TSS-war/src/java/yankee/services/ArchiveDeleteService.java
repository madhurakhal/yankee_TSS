/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.services;

import java.time.LocalDate;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ArchiveDeleteService {

    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    @EJB
    private AdministrationBusinessLogic administrationBusinessLogic;

    //@Schedule(minute = "*/1", hour = "*", persistent = false)
    
    @Schedule(dayOfMonth="Last", hour="23", persistent = false)
    public void runDeleteTask() {
        if (administrationBusinessLogic.getAdminSettingsInfo().isReminderServiceOn()) {
            System.out.println("This task is executed from run delete task");
            getDetailsToDeleteTimeSheetsAndContracts(2);
        }
    }

    private void getDetailsToDeleteTimeSheetsAndContracts(int yearToSubtract) {

        LocalDate today = LocalDate.now();
        LocalDate x = today.minusYears(yearToSubtract);
        
        try {
            timeSheetBusinessLogic.deleteOldTimeSheetSignedBySupervisor(x);
        } catch (NumberFormatException ne) {
            System.out.println("Exception Occured from timeSheetBusinessLogic!!");
        }
    }
}
