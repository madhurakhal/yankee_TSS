/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.services;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import yankee.logic.to.TimeSheet;
import yankee.web.ReminderBean;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ProgrammaticTimer {

    @Resource
    TimerService timerService;

    private String timerId;

    @EJB
    ReminderBean reminderBean;

    public void createTimer(String timerId, int hour, int minute, int second) {
        this.timerId = timerId;
        ScheduleExpression expression = new ScheduleExpression();
        expression.hour(hour);
        expression.minute(minute);
        expression.second(second);
        timerService.createCalendarTimer(expression, new TimerConfig(timerId, true));
    }

    public void editTimer(String timerId, int freq) {
        cancelTimer(timerId);
        ScheduleExpression expression = new ScheduleExpression();
        expression.minute("*/" + freq).hour("*");
        timerService.createCalendarTimer(expression, new TimerConfig(timerId, true));
    }

    public void cancelTimer(String timerId) {
        if (timerService.getTimers() != null) {
            for (Timer timer : timerService.getTimers()) {
                if (timer.getInfo().equals(timerId)) {
                    timer.cancel();
                }
            }
        }
    }

    @Timeout
    public void execute() {
        List<TimeSheet> timeSheets = reminderBean.getTimeSheetsToSendReminder();
        if (timeSheets != null && timeSheets.size() > 0) {
            reminderBean.sendReminderForTimeSheets(reminderBean.getTimeSheetsToSendReminder());
        } else {
            cancelTimer(timerId);
        }
    }
}
