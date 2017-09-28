package yankee.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.impl.AdministrationBusinessLogicImpl;
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
    
    public boolean startScheduler = false;

    @EJB
    AdministrationBusinessLogic administrationBusinessLogic;

    /*
        @Schedules ({
            @Schedule(dayOfMonth="Last"),
            @Schedule(dayOfWeek="Fri", hour="23"), dayOfMonth = "-7"
        })
     */
    //@Schedule(hour = "06", minute = "59", second = "59", persistent = false)
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void runTask() {
        System.out.println("This task is executed");
        programmaticTimer.cancelTimer("timerId");
        if (administrationBusinessLogic.getAdminSettingsInfo().isReminderServiceOn()) {           
            List<TimeSheet> timeSheets = reminderBean.getTimeSheetsToSendReminder();
            if (timeSheets != null && timeSheets.size() > 0) {
                System.out.println("called from reminder service = " + timeSheets);
                reminderBean.sendReminderForTimeSheets(timeSheets);
                programmaticTimer.createTimer("timerId", 1);
            }
        }
    }
}
