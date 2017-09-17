package yankee.reminder.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.MessagingException;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ENUM.TimesheetStatusEnum;
import static yankee.logic.ENUM.TimesheetStatusEnum.IN_PROGRESS;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Employee;
import yankee.logic.to.Secretary;
import yankee.logic.to.Supervisor;
import yankee.logic.to.TimeSheet;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ReminderService {

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;

    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;
    
    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;
    
    @EJB
    private AssistantBusinessLogic assistantBusinessLogic;
    
    

    private List<Employee> employees;
    private List<TimeSheet> timeSheets;
    private List<Secretary> secretaries;
    private List<Assistant> assistants;

    private TimesheetStatusEnum timesheetstatus;

    /*
        @Schedules ({
            @Schedule(dayOfMonth="Last"),
            @Schedule(dayOfWeek="Fri", hour="23"), dayOfMonth = "-7"
        })
     */
    // Scheduler ejb to do certain task based on param, - 7 means last 7 days of the month
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void runTask() {
        System.out.println("This task is executed");
        getDetailsToSendReminder();
    }

    public void getDetailsToSendReminder() {
        try {
            employees = employeeBusinessLogic.getEmployeeList();
            System.out.println("list of employees  " + employees);
        } catch (Exception e) {
            System.out.println("Exception Occured from employee business logic!!");
        }

        if (employees != null) {
            for (Employee e : employees) {
                String contractId = e.getContract().getUuid();
                try {
                    timeSheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(Long.parseLong(contractId));
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
    }

    private void sendReminder(String email, String subject, String message) {
        try {
            Reminder reminder = new Reminder();
            reminder.sendMail(email, subject, message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendReminderToEmployee(Employee e) {
        String message = " Dear " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                + "\n\n " + " Time sheet is signed by supervisor ....."
                + "Regards \n\n Your System.";

        String subject = "Time sheet need to be signed for ...";
        String email = e.getPerson().getEmailAddress();
        sendReminder(email, subject, message);
    }

    private void sendReminderToSupervisorAssistant(String contractId) {
        Supervisor s = supervisorBusinessLogic.getSupervisorByContract(contractId);
        if (s != null) {
            String message = " Dear " + s.getPerson().getFirstName() + " " + s.getPerson().getLastName()
                            + "\n\n " + " Time sheet is signed by supervisor ....."
                            + "Regards \n\n Your System.";

                    String subject = "Time sheet need to be signed for ...";
                    String email = s.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
        }
        
        try {
            assistants = assistantBusinessLogic.getAssistantsByContract(contractId);
            if (assistants != null) {
                for (Assistant a : assistants) {
                    System.out.println(a.getPerson().getFirstName());
                    String message = " Dear " + a.getPerson().getFirstName() + " " + a.getPerson().getLastName()
                            + "\n\n " + " Time sheet is signed by supervisor ....."
                            + "Regards \n\n Your System.";

                    String subject = "Time sheet need to be signed for ...";
                    String email = a.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
                }
            }
        } catch (Exception ex) {
        }

    }

    private void sendReminderToSecretaries(String contractId) {
        try {
            secretaries = secretaryBusinessLogic.getSecretariesByContract(contractId);
            if (secretaries != null) {
                for (Secretary s : secretaries) {
                    System.out.println(s.getPerson().getFirstName());
                    String message = " Dear " + s.getPerson().getFirstName() + " " + s.getPerson().getLastName()
                            + "\n\n " + " Time sheet is signed by supervisor ....."
                            + "Regards \n\n Your System.";

                    String subject = "Time sheet need to be signed for ...";
                    String email = s.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
                }
            }
        } catch (Exception ex) {
        }
    }
}
