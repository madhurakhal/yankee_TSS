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

    /*
        @Schedules ({
            @Schedule(dayOfMonth="Last"),
            @Schedule(dayOfWeek="Fri", hour="23"), dayOfMonth = "-7"
        })
     */
    // Scheduler ejb to do certain task based on param, - 7 means last 7 days of the month
    //@Schedule(hour = "06", minute = "59", second = "59", persistent = false)
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void runTask() {
        System.out.println("This task is executed");
        programmaticTimer.cancelTimer("timerId");
        List<TimeSheet> timeSheets = reminderBean.getTimeSheetsToSendReminder();
        if (timeSheets!= null && timeSheets.size() > 0) {
            System.out.println("called from reminder service = " + timeSheets);
            reminderBean.sendReminderForTimeSheets(timeSheets);
            programmaticTimer.createTimer("timerId", 1);
        }
    }

    /*public void getRequiredData() {
        try {
            employees = employeeBusinessLogic.getEmployeeList();
            System.out.println("list of employees  " + employees);
        } catch (Exception e) {
            System.out.println("Exception Occured from employee business logic!!");
        }

        if (employees != null) {
            for (Employee e : employees) {
                String contractId = e.getContract().getUuid();
                TimesheetFrequencyEnum contractFrequency = e.getContract().getFrequency();
                try {
                    timeSheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(contractId);
                    System.out.println("list of timesheets  " + timeSheets);
                } catch (NumberFormatException ne) {
                    System.out.println("Exception Occured from timeSheetBusinessLogic!!");
                }
                if (timeSheets != null) {
                    for (TimeSheet t : timeSheets) {
                        System.out.println("Status =" + t.getStatus());
                        timesheetstatus = t.getStatus();

                        switch (timesheetstatus) {
                            case IN_PROGRESS:
                                // send email to employee
                                sendReminderToEmployee(e);
                                break;
                            case SIGNED_BY_EMPLOYEE:
                                // send email to supervisor and assistant
                                sendReminderToSupervisorAssistant(contractId);
                                break;

                            case SIGNED_BY_SUPERVISOR:
                                // send eamil to secretaries
                                sendReminderToSecretaries(contractId);
                                break;
                        }
                    }
                }
            }
        }
    } */
}
