/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ReminderService {
    /*  -- On the last day of a time sheet (either end of week or end of month), the TSS MUST 
        send a reminder mail to the employee if the time sheet is in state IN_PROGRESS.
        -- The TSS MUST send a reminder mail to the supervisor and the assistants if the 
        time sheet is in state SIGNED_BY_EMPLOYEE. (then, the supervisor may reject or 
        sign the time sheet
        The TSS MUST send a reminder mail to the secretaries if the time sheet is in state 
        SIGNED_BY_SUPERVISOR.
        The TSS SHOULD repeat reminders every day.
        The TSS MAY collect all reminders so that a person receives at most one e-mail per day.
    */
    
    /*
        @Schedules ({
            @Schedule(dayOfMonth="Last"),
            @Schedule(dayOfWeek="Fri", hour="23")
        })
    */

    // Scheduler ejb to do certain task based on param, - 7 means last 7 days of the month
    @Schedule(hour = "13", minute = "05", second = "59", dayOfMonth = "-7")
    public void runTask() {
        System.out.println("This task is executed");
        
        // check the time sheet state
    }
}
