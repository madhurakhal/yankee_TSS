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
import yankee.logic.ENUM.TimesheetStatusEnum;
import static yankee.logic.ENUM.TimesheetStatusEnum.IN_PROGRESS;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.to.Employee;

/**
 *
 * @author pradipgiri
 */
@Singleton
public class ReminderService {

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;
    private List<Employee> employees;
    
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
            System.out.println("Exception Occured!!");
        }

        if (employees != null) {
            for (Employee e : employees) {
                String contractId = e.getContract().getUuid();
                System.out.println("Employee Id" + contractId);
            }
        }

        TimesheetStatusEnum timesheetstatus = IN_PROGRESS;

        switch (timesheetstatus) {
            case IN_PROGRESS:
                sendReminder();
                break;
            case SIGNED_BY_EMPLOYEE:
                break;

            case SIGNED_BY_SUPERVISOR:
                break;
        }
    }

    private void sendReminder() {
        
        /* String message = "Your account settings have been changed",
                    "Dear user,\n\n"
                    + "Your account settings have been changed to:\n"
                    + "- firstname: " + person.getFirstname() "\n"
                    + "- lastname: " + person.getLastname() + "\n"
                    + "- date of birth: " + (person.getDateOfBirth() == null ? "(not specified)" : person.getDateOfBirth()) + "\n\n"
                    + "Regards: \n\n Your TEAM SYSTEM\n" */
        
        try {
            Reminder reminder = new Reminder();
            reminder.sendMail("mepradipjee@gmail.com", "hello", "Test Email");
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
