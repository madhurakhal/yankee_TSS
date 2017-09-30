package yankee.services;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.to.TimeSheet;
import yankee.web.ReminderBean;


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
                reminderBean.sendReminderForTimeSheets(timeSheets);
                //programmatic timer which runs 5:59 AM every day
                programmaticTimer.createTimer("timerId", 05, 59, 59);
            }
        }
    }
}
