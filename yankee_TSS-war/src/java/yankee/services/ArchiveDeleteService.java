/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.services;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.TimeSheet;

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

    private List<TimeSheet> timeSheets;

    //@Schedule(minute = "*/1", hour = "*", persistent = false)
    public void runDeleteTask() {
        if (administrationBusinessLogic.getAdminSettingsInfo().isReminderServiceOn()) {
            System.out.println("This task is executed from run delete task");
            getDetailsToDeleteTimeSheetsAndContracts(2);
        }
    }

    private void getDetailsToDeleteTimeSheetsAndContracts(int yearToSubtract) {

        LocalDate today = LocalDate.now();
        LocalDate x = today.minusYears(yearToSubtract);
        System.out.println("The date before two year is = " + x);

        try {
            timeSheetBusinessLogic.deleteOldTimeSheetSignedBySupervisor(x);
        } catch (NumberFormatException ne) {
            System.out.println("Exception Occured from timeSheetBusinessLogic!!");
        }
    }
}
