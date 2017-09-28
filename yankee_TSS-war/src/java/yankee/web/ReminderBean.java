package yankee.web;

import yankee.services.ProgrammaticTimer;
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
import yankee.services.Reminder;

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
        LocalDate todayl = LocalDate.of(2017, 11, 7);
        System.out.println("Current Date=" + today);
        try {
            timeSheets = timeSheetBusinessLogic.getAllTimeSheetsByGivenDate(todayl);
            System.out.println("list of timesheets = " + timeSheets);
        } catch (NumberFormatException ne) {
            System.out.println("Exception Occured from timeSheetBusinessLogic!!");
        }
        return timeSheets;
    }

    public void sendReminderForTimeSheets(List<TimeSheet> timeSheets) {
        for (TimeSheet t : timeSheets) {
            System.out.println("Status =" + t.getStatus());
            String contractId = t.getContract().getUuid();
            timesheetstatus = t.getStatus();
            employee = null;
            try {
                employee = employeeBusinessLogic.getEmployeeByContract(contractId);
            } catch (Exception e) {
                System.out.println("Exception Occured from employee business logic!!");
            }

            switch (timesheetstatus) {
                case IN_PROGRESS:
                    // send email to employee
                    if (employee != null) {
                        sendReminderToEmployee(employee, t);
                    }
                    break;
                case SIGNED_BY_EMPLOYEE:
                    // send email to supervisor and assistant
                    sendReminderToSupervisorAssistant(employee, t);
                    break;

                case SIGNED_BY_SUPERVISOR:
                    // send eamil to secretaries
                    sendReminderToSecretaries(employee, t);
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

    private void sendReminderToEmployee(Employee e, TimeSheet t) {
        String message = "Dear Employee, " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                + ":"
                + "\n\n \n\n " + "This is a reminder that your timesheet is due at " + t.getEndDate()
                + ". Please log into the TimeSheet System(TSS) to complete, sign and submit your timesheets. "
                + "Failure to submit your timesheets may result in not being paid. "
                + "\n\n If you have any question regarding your timesheet in general then "
                + "please contact your SUPERVISOR."
                + "\n\n \n\n Regards," + "\n" + "Your System";

        String subject = "Reminder to Sign your TimeSheet";
        String email = e.getPerson().getEmailAddress();
        sendReminder(email, subject, message);
    }

    private void sendReminderToSupervisorAssistant(Employee e, TimeSheet t) {
        try {
            Supervisor s = supervisorBusinessLogic.getSupervisorByContract(t.getContract().getUuid());
            if (s != null) {
                String message = "Dear Supervisor, " + s.getPerson().getFirstName() + " " + s.getPerson().getLastName()
                        + ":"
                        + "\n\n \n\n" + "This is a reminder that you have remaining timesheets to sign for employee "
                        + e.getPerson().getFirstName() + " " + e.getPerson().getLastName() + " and "
                        + "end date of the timesheet is " + t.getEndDate()
                        + ". Please log into Time Sheet System(TSS) to review and process the timesheet."
                        + "\n\n If you have any question regarding this timesheet, please contact"
                        + " your employee " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                        + "\n\n \n\n Regards, \n Your System";

                String subject = "Reminder to sign the timesheet for your employee";
                String email = s.getPerson().getEmailAddress();
                sendReminder(email, subject, message);
            }
        } catch (Exception ex) {

        }

        try {
            assistants = assistantBusinessLogic.getAssistantsByContract(t.getContract().getUuid());
            if (assistants != null) {
                for (Assistant a : assistants) {
                    String message = "Dear Assistant, " + a.getPerson().getFirstName() + " " + a.getPerson().getLastName()
                            + ":"
                            + "\n\n \n\n " + "This is a reminder that you have remaining timesheets to sign for employee "
                            + e.getPerson().getFirstName() + " " + e.getPerson().getLastName() + " and "
                            + "end date of the timesheet is " + t.getEndDate()
                            + ". Please log into Time Sheet System(TSS) to review and process the timesheet."
                            + "\n\n If you have any question regarding this timesheet, please contact"
                            + " your employee " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                            + "\n\n \n\n Regards, \n Your System";

                    String subject = "Reminder to archive the timesheet of your employee";
                    String email = a.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
                }
            }
        } catch (Exception ex) {
        }

    }

    private void sendReminderToSecretaries(Employee e, TimeSheet t) {
        try {
            secretaries = secretaryBusinessLogic.getSecretariesByContract(t.getContract().getUuid());
            if (secretaries != null) {
                for (Secretary s : secretaries) {
                    System.out.println(s.getPerson().getEmailAddress());
                    String message = "Dear Secretary, " + s.getPerson().getFirstName() + " " + s.getPerson().getLastName()
                            + ":"
                            + "\n\n \n\n This is a reminder that you have remaining timesheets "
                            + "to archive for employee " + e.getPerson().getFirstName() + " " + e.getPerson().getLastName()
                            + " and the end date of the timesheet is " + t.getEndDate()
                            + " Please log into Time Sheet System(TSS) to review and process the timesheet."
                            + "\n\n If you have any question regarding this timesheet, please contact"
                            + " your Employee or Supervisor."
                            + "\n\n \n\n Regards, \n Your System";

                    String subject = "Reminder to archive the timesheet of your employee";
                    String email = s.getPerson().getEmailAddress();
                    sendReminder(email, subject, message);
                }
            }
        } catch (Exception ex) {
        }
    }
}
