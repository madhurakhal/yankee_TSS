package yankee.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.to.TimeSheet;
import yankee.web.ReminderBean;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ReminderService {

    @EJB
    ProgrammaticTimer programmaticTimer;

    @EJB
    ReminderBean reminderBean;

    @EJB
    AdministrationBusinessLogic administrationBusinessLogic;

    @Schedules({
        @Schedule(dayOfMonth = "Last")
        ,
        @Schedule(dayOfWeek = "Fri")
    })
    public void runTask() {
        programmaticTimer.cancelTimer("timerId");
        if (administrationBusinessLogic.getAdminSettingsInfo().isReminderServiceOn()) {
            List<TimeSheet> timeSheets = reminderBean.getTimeSheetsToSendReminder();
            if (timeSheets != null && timeSheets.size() > 0) {
                System.out.println("called from reminder service = " + timeSheets);
                reminderBean.sendReminderForTimeSheets(timeSheets);
                //programmatic timer which runs 5:59 AM every day
                programmaticTimer.createTimer("timerId", 05, 59, 59);
            }
        }
    }
}
