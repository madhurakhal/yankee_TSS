/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.ContractEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.SecretaryEntity;
import yankee.entities.SupervisorEntity;
import yankee.entities.TimesheetEntity;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Stateless
public class ContractBusinessLogicImpl implements ContractBusinessLogic {

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private PersonAccess personAccess;

    @EJB
    private AssistantAccess assistantAccess;

    @EJB
    private EmployeeAccess employeeAccess;

    @EJB
    private SecretaryAccess secretaryAccess;

    @EJB
    private SupervisorAccess supervisorAccess;
    
    @EJB
    private TimeSheetAccess timeSheetAccess;
    
    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    @Override
    public List<Person> getContractList() {
//        List<PersonEntity> l = personAccess.getPersonList();
//        List<Person> result = new ArrayList<>(l.size());
//        for (PersonEntity pe : l) {
//            Person p = new Person(pe.getUuid(), pe.getName());
//            p.setFirstName(pe.getFirstName());
//            p.setLastName(pe.getLastName());
//            p.setDateOfBirth(pe.getDateOfBirth());
//            result.add(p);
//        }
        return null;
    }

    // This roleTypeUUID is either supervisor, secretary, assistant, employee uuid
    @Override
    public Contract createContract(String contractName, Person supervisor, Person assistant, Person secretary, Person employee, Date startDate, Date endDate, TimesheetFrequencyEnum timesheetFrequency , double hoursPerWeek, int workingDaysPerWeek, int vacationDaysPerYear) {

        // When creating contract.
        // 1. First create contract and get contract id Also set start end date along with timesheetFrequency detail.
        // 2. now create employee with person associated to it.
        // 3. now create supervisor with person associated to it
        // 4. now create secretary as of yet in this createContract.
        // 5. now create assistant as of yet in this createContract.
        
        //1.
        ContractEntity ce = contractAccess.createEntity(contractName);
        LocalDate lstartdate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lenddate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ce.setStartDate(lstartdate);
        ce.setEndDate(lenddate);
        if (timesheetFrequency != null){
        ce.setFrequency(timesheetFrequency);
    }
        ce.setHoursPerWeek(hoursPerWeek);
        
        ce.setVacationDaysPerYear(vacationDaysPerYear);
        ce.setWorkingDaysPerWeek(workingDaysPerWeek);
        
        System.out.println("Contract ko naam k ho ta" + ce.getName() + ce.getId());

        try {
            //2.
            EmployeeEntity ee = employeeAccess.createEntity(employee.getName());
            System.out.println("Employee ko naam k ho ta" + ee.getName() + ee.getId());
            ee.setPerson(personAccess.getByUuid(employee.getUuid()));
            ee.setContract(ce);

            //3.
            SupervisorEntity sve = supervisorAccess.createEntity(supervisor.getName());
            sve.setPerson(personAccess.getByUuid(supervisor.getUuid()));
            sve.setContract(ce);

            //4. Note not necessary that you will be provided with person secretary. So can be null
            // Usually if when creating contract assistant and secretary trying to create contract for supervisor.
            if (secretary != null) {
                SecretaryEntity se = secretaryAccess.createEntity(secretary.getName());
                se.setPerson(personAccess.getByUuid(secretary.getUuid()));
                se.setContract(ce);
            }

            //5. Note not necessary that you will be provided with person assistant. So can be null
            // Usually if when creating contract assistant and secretary trying to create contract for supervisor.
            if (assistant != null) {
                AssistantEntity ae = assistantAccess.createEntity(assistant.getName());
                ae.setPerson(personAccess.getByUuid(assistant.getUuid()));
                ae.setContract(ce);
                ae.setRollType(RoleTypeEnum.ASSISTANT);
            }

        } catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODOOOOOOOOOOOOOO have to think what to return
        // Note that all contract values like timesheets and start end date and so on gets added when updating contract.
        // initially the above is correct. 
        return new Contract(ce.getUuid(), ce.getName());
    }

    @Override
    public Contract editContract(String contractUUID, Person supervisorPerson, List<Person> secretaries, boolean secretariesChanged, List<Person> assistants, boolean assistantsChanged, Date startDate, Date endDate) {
        // When updating contract 
        // We will receive 
        // 1. new supervisorFor contract .. check for null if no change. Delete and create if not null and associate this contract id.
        // 2. list of secretaries updated. BE SMART or easy is delete existing secretaries and create new ones.
        //    2.1  Note maybe check for null. because no change = null is also good.
        // 3. Similar as 2. This time assistant
        // 4. Set start and end date.

        
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        if (startDate != null) {
            LocalDate lstartdate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ce.setStartDate(lstartdate);
        }
        if (startDate != null) {
            LocalDate lenddate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ce.setEndDate(lenddate);
        }
        // 1. 
        SupervisorEntity svePrev = supervisorAccess.getSupervisorByContract(contractAccess.getByUuid(contractUUID));
        // This means the current selected supervisor is different from one before
        if (!supervisorPerson.getUuid().equals(svePrev.getPerson().getUuid())) {
            // delete old supervisor for this contract
            supervisorAccess.deleteEntity(svePrev);

            // create new supervisor with this contract id
            System.out.println("Should not come here right?");
            SupervisorEntity sve = supervisorAccess.createEntity(supervisorPerson.getName());
            sve.setPerson(personAccess.getByUuid(supervisorPerson.getUuid()));
            sve.setContract(ce);
        }

        // 2.
        // delete all secretaries for that contract and add new assigned secretaries
        if (secretariesChanged) {
            List<SecretaryEntity> toDeleteSecretaries = secretaryAccess.getSecretariesByContract(ce);
            // For loop to delete
            for (SecretaryEntity delSecretary : toDeleteSecretaries) {
                secretaryAccess.deleteEntity(delSecretary);
            }
            // Now we create the secretaries given in the list
            if (!secretaries.isEmpty()) {
                for (Person p : secretaries) {
                    SecretaryEntity se = secretaryAccess.createEntity(p.getName());
                    se.setPerson(personAccess.getByUuid(p.getUuid()));
                    se.setContract(ce);
                }
            }
        }

        // 3.
        // delete all assistants for that contract
        if (assistantsChanged) {
            // Deleting previous assistants
            List<AssistantEntity> toDeleteAssistants = assistantAccess.getAssistantsByContract(ce);
            for (AssistantEntity delAssistant : toDeleteAssistants) {
                assistantAccess.deleteEntity(delAssistant);
            }

            // Now we create the secretaries given in the list
            if (!assistants.isEmpty()) {
                for (Person p : assistants) {
                    AssistantEntity ae = assistantAccess.createEntity(p.getName());
                    ae.setPerson(personAccess.getByUuid(p.getUuid()));
                    ae.setContract(ce);
                }
            }
        }
        //4. Change start and end date  IMPLEMENTED AT BEGINNING OF this function
        return new Contract(ce.getUuid(), ce.getName());
    }

    @Override
    public Contract startContract(String contractUUID) {
        
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        ce.setStatus(ContractStatusEnum.STARTED); 
        // Create timesheets 
        timeSheetBusinessLogic.createTimeSheet(contractUUID);
        
        return new Contract(ce.getUuid(), ce.getName());
    }

    @Override
    public Contract getContractByUUID(String contractUUID) {
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        Contract c = new Contract(ce.getUuid(), ce.getName());
        c.setStartDate(ce.getStartDate());
        c.setEndDate(ce.getEndDate());
        c.setFrequency(ce.getFrequency());
        c.setHoursPerWeek(ce.getHoursPerWeek());
        c.setWorkingDaysPerWeek(ce.getWorkingDaysPerWeek());
        c.setVacationDaysPerYear(ce.getVacationDaysPerYear());
        c.setStatus(ce.getStatus());
        c.setTerminationDate(ce.getTerminationDate());
        return c;

    }
    
    @Override 
    public Contract getUpdateContractStatistics(String contractUUID){
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        ce.setStatus(ContractStatusEnum.STARTED);
        
        // Calculating vacation hours.
        LocalDate date1 = ce.getStartDate();
        LocalDate date2 = ce.getEndDate();
        Period period = Period.between(date1, date2);        
        double durationOfContract = (double) period.getMonths() ;
        double vacationHours = ce.getVacationDaysPerYear()*(durationOfContract / 12) * (ce.getHoursPerWeek() / ce.getWorkingDaysPerWeek());
        ce.setVacationHours(vacationHours);
        
        // Calculating Total hours due.
        // load each timesheet for this contract. and sum each timesheets hoursdue.
        List<TimesheetEntity> lte = timeSheetAccess.getTimeSheetsForContract(Long.MIN_VALUE);
        double totalhoursDue = 0.0;
        for (TimesheetEntity te : lte){
            totalhoursDue += te.getHoursDue();
        }
        ce.setHoursDue(totalhoursDue);  
        
        
        // Fill All Contract details        
        Contract contract = new Contract(ce.getUuid() , ce.getName());
        contract.setStatus(ce.getStatus());
        // Fill allll
        return contract;
        
                
        
    }
    
}
