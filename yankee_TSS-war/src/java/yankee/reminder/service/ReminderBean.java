package yankee.reminder.service;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Employee;
import yankee.logic.to.Secretary;
import yankee.logic.to.Supervisor;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimesheetT;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pradipgiri
 */
@Stateless
public class ReminderBean {

    @EJB
    ProgrammaticTimer programmaticTimer;

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

    @EJB
    Reminder reminder;

    private Employee employee;

    private List<Employee> employees;
    private List<TimeSheet> timeSheets;
    private List<Secretary> secretaries;
    private List<Assistant> assistants;

    private TimesheetStatusEnum timesheetstatus;

    public List<TimeSheet> getTimeSheetsToSendReminder() {
        //Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Current Date=" + today);
        try {
            timeSheets = timeSheetBusinessLogic.getAllTimeSheetsByGivenDate(today);
            System.out.println("list of timesheets = " + timeSheets);
        } catch (NumberFormatException ne) {
            System.out.println("Exception Occured from timeSheetBusinessLogic!!");
        }
        return timeSheets;
    }

    public void sendReminderForTimeSheets(List<TimeSheet> timeSheets) {
        for (TimeSheet t : timeSheets) {
            String contractId = t.getContract().getUuid();
            System.out.println("Status =" + t.getStatus());
            timesheetstatus = t.getStatus();

            switch (timesheetstatus) {
                case IN_PROGRESS:
                    // send email to employee
                    try {
                        employee = employeeBusinessLogic.getEmployeeByContract(contractId);
                        if (employee != null) {
                            sendReminderToEmployee(employee);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception Occured from employee business logic!!");
                    }
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

    private void sendReminder(String email, String subject, String message) {
        try {
            reminder.sendMail(email, subject, message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendReminderToEmployee(Employee e) {
        String message = " Dear " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                + "\n\n " + "This is the reminder email to sign yourtimesheet."
                + "You have remaining timesheet to sign Please sign your timesheet."
                + "\n\n Regards" + "\n\n" + "Your System";

        String subject = "Reminder to Sign your TimeSheet";
        String email = e.getPerson().getEmailAddress();
        sendReminder(email, subject, message);
    }

    private void sendReminderToSupervisorAssistant(String contractId) {
        try {
            Supervisor s = supervisorBusinessLogic.getSupervisorByContract(contractId);
            if (s != null) {
                String message = " Dear " + s.getPerson().getFirstName() + " " + s.getPerson().getLastName()
                        + "\n\n " + "This is the reminder email to sign the timesheet for your employee."
                        + "Please sign the timesheet for your employee."
                        + "\n\n Regards \n\n Your System.";

                String subject = "Reminder to sign the timesheet for your employee";
                String email = s.getPerson().getEmailAddress();
                sendReminder(email, subject, message);
            }
        } catch (Exception ex) {

        }

        try {
            assistants = assistantBusinessLogic.getAssistantsByContract(contractId);
            if (assistants != null) {
                for (Assistant a : assistants) {
                    System.out.println(a.getPerson().getFirstName());
                    String message = " Dear " + a.getPerson().getFirstName() + " " + a.getPerson().getLastName()
                            + "\n\n " + "This is the reminder email to archive the "
                            + "timesheet for your employee. Please archieve the time sheet "
                            + "which is signed by supervisor"
                            + "\n\n Regards \n\n Your System";

                    String subject = "Reminder to archive the timesheet of your employee";
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
                            + "\n\n " + "This is the reminder email to archive the"
                            + "timesheet for your employee. Please archieve the time sheet"
                            + "which is signed by supervisor."
                            + "\n\n Regards" + "\n\n" + "Your System";

                    String subject = "Reminder to archive the timesheet of your employee";
                    String email = s.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
                }
            }
        } catch (Exception ex) {
        }
    }
}
