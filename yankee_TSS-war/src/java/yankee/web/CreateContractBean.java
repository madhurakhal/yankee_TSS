package yankee.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;
import org.primefaces.event.RowEditEvent;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class CreateContractBean {

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private List<Person> persons;
    private List<Person> availableSupervisorList = new ArrayList<>();
    private Person supervisorForContract;
    private boolean personSelected  = true;

    public boolean isPersonSelected() {
        return personSelected;
    }

    public void setPersonSelected(boolean personSelected) {
        this.personSelected = personSelected;
    }
    
    private RoleTypeEnum roleType;  // CAN DELETEEEEEEEEE look at update PersonDetails method down.
    private Person contractTo;
    private Person changedSupervisorPerson;
    private RoleTypeEnum yourRoleType;
    private Date startDate;
    private Date endDate;
    private TimesheetFrequencyEnum timesheetFrequency;
    private Integer hoursPerWeek;
    private Integer workingDaysPerWeek;
    private Integer vacationDaysPerYear;

    public Integer getWorkingDaysPerWeek() {
        workingDaysPerWeek = 5;
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(Integer workingDaysPerWeek) {
        System.out.println("HEREEEEEE for workindDays");
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public Integer getVacationDaysPerYear() {
        vacationDaysPerYear = 20;
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(Integer vacationDaysPerYear) {
        System.out.println("HERE FOR vacation days");
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TimesheetFrequencyEnum getTimesheetFrequency() {
        return timesheetFrequency;
    }

    public void setTimesheetFrequency(TimesheetFrequencyEnum timesheetFrequency) {
        this.timesheetFrequency = timesheetFrequency;
    }

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }

    public Person getChangedSupervisorPerson() {
        changedSupervisorPerson = loginBean.getUser();
        return changedSupervisorPerson;
    }

    public void setChangedSupervisorPerson(Person changedSupervisorPerson) {
        this.changedSupervisorPerson = changedSupervisorPerson;
    }

    public RoleTypeEnum getYourRoleType() {
        return yourRoleType;
    }

    public void setYourRoleType(RoleTypeEnum yourRoleType) {
        this.yourRoleType = yourRoleType;
    }

    public Person getContractTo() {
        return contractTo;
    }

    public void setContractTo(Person contractTo) {
        this.contractTo = contractTo;
    }

    public List<Person> getPersons() {
        if (persons == null) {
            persons = personBusinessLogic.getPersonList();

            for (Iterator<Person> iter = persons.listIterator(); iter.hasNext();) {
                Person all = iter.next();

                // Get the supervisors for the person who is trying to create contract. He might have been supervisor for many contracts.
                List<Supervisor> ls = supervisorBusinessLogic.getSupervisorByPerson(loginBean.getUser().getUuid());
                if (all.getUuid() == null ? loginBean.getUser().getUuid() == null : all.getUuid().equals(loginBean.getUser().getUuid())) {
                    iter.remove();
                    continue;
                }
                for (Supervisor s : ls) {
                    Employee e = employeeBusinessLogic.getEmployeeByContract(s.getContract().getUuid());
                    if (e != null ) {
                        if (all.getUuid() == null ? e.getPerson().getUuid() == null : all.getUuid().equals(e.getPerson().getUuid())) {
                            if(s.getContract().getStatus()== ContractStatusEnum.TERMINATED){
                            iter.remove();}
                        }
                    }
                }
            }
        }
        return persons;
    }

    public void create() {
        // When create contract button pressed.
        // First if person clicks on Set supervisor box
        // select your role i.e assistant or secretary appears.
        // Make private var as  1. yourRoleType       allowing for Assistant or Secretary enum.
        //                      2. changedSupervisorPerson
        Person supervisor = null;
        System.out.println("Contract To is " + contractTo.getFirstName());
        Person employee = contractTo;
        Person assistant = null;
        Person secretary = null;

        if (changedSupervisorPerson == null){                        
            supervisor = loginBean.getUser();
        } else {
            assistant = loginBean.getUser();
            supervisor = changedSupervisorPerson;
        }
        System.out.println("Supervisor" + changedSupervisorPerson);
        
        contractBusinessLogic.createContract("contract" + employee.getName(), supervisor, assistant, secretary, employee, startDate, endDate, timesheetFrequency, (double) hoursPerWeek, workingDaysPerWeek, vacationDaysPerYear);

        FacesMessage msg = new FacesMessage("Contract Created");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // Test for supervisor 
    public List<Person> getAvailableSupervisorList() {
        if(contractTo == null){
            return availableSupervisorList;
        }        
        personSelected = false;
        if (availableSupervisorList.isEmpty()) {
            for (Person p : personBusinessLogic.getPersonList()) {
                boolean hashimAsSupervisor = false;
                if (p.getUserRoleRealm() != null && !contractTo.getUuid().equals(p.getUuid())) {
                    List<Supervisor> ls = supervisorBusinessLogic.getSupervisorByPerson(p.getUuid());
                    for (Supervisor s : ls) {
                        Employee e = employeeBusinessLogic.getEmployeeByContract(s.getContract().getUuid());
                        if (e != null) {
                            if (e.getPerson().getUuid().equals(contractTo.getUuid())) {
                                if (s.getContract().getStatus()== ContractStatusEnum.TERMINATED)
                                {hashimAsSupervisor = true;  }                              
                            }
                        }
                    };
                    if (!hashimAsSupervisor) {
                        availableSupervisorList.add(p);
                    }
                }
            }
        }
        return availableSupervisorList;
    }
    
    public void setAvailableSupervisorList(List<Person> availableSupervisorList) {
        this.availableSupervisorList = availableSupervisorList;
    }
    
    public void updateVal(){
        getAvailableSupervisorList();
    }
    
    // NOT USED IN OUR MAIN CODES
    // Look this up on test.xhtml it makes use of it.
    public void updatePersonDetails(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Person) event.getObject()).getUuid());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        String selectedPerson_uuid = ((Person) event.getObject()).getUuid();
        personBusinessLogic.updatePersonDetails(selectedPerson_uuid, roleType);
        System.out.println("Am I here to update?" + roleType);
    }
}
