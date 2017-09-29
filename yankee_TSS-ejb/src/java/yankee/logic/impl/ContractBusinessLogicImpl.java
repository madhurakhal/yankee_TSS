package yankee.logic.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.ContractEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SecretaryEntity;
import yankee.entities.SupervisorEntity;
import yankee.entities.TimesheetEntity;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;
import yankee.utilities.UTILNumericSupport;

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
    public List<Contract> getContractList() {
        List<ContractEntity> lce = contractAccess.getContractList();
        if (lce == null) {
            return null;
        }
        List<Contract> result = new ArrayList<>();
        for (ContractEntity ce : lce) {
            Contract c = new Contract(ce.getUuid(), ce.getName());
            c.setStartDate(ce.getStartDate());
            c.setEndDate(ce.getEndDate());
            c.setFrequency(ce.getFrequency());
            c.setHoursPerWeek(ce.getHoursPerWeek());
            c.setWorkingDaysPerWeek(ce.getWorkingDaysPerWeek());
            c.setVacationDaysPerYear(ce.getVacationDaysPerYear());
            c.setStatus(ce.getStatus());
            c.setTerminationDate(ce.getTerminationDate());
            c.setVacationHours(ce.getVacationHours());
            c.setHoursDue(ce.getHoursDue());
            c.setArchiveDuration(ce.getArchiveDuration());
            result.add(c);
        }
        return result;

    }

    @RolesAllowed("STAFF")
    @Override
    public void createContract(String contractName, Person supervisor, Person assistant, Person secretary, Person employee, Date startDate, Date endDate, TimesheetFrequencyEnum timesheetFrequency, double hoursPerWeek, int workingDaysPerWeek, int vacationDaysPerYear, int archiveDuration) {

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
        if (timesheetFrequency != null) {
            ce.setFrequency(timesheetFrequency);
        }
        ce.setHoursPerWeek(hoursPerWeek);
        ce.setVacationDaysPerYear(vacationDaysPerYear);
        ce.setWorkingDaysPerWeek(workingDaysPerWeek);
        ce.setArchiveDuration(archiveDuration);

        try {
            //2.
            EmployeeEntity ee = employeeAccess.createEntity(employee.getName());
            ee.setPerson(personAccess.getByUuid(employee.getUuid()));
            ee.setContract(ce);
            ce.setEmployee(ee);

            //3.
            SupervisorEntity sve = supervisorAccess.createEntity(supervisor.getName());
            sve.setPerson(personAccess.getByUuid(supervisor.getUuid()));
            sve.setContract(ce);
            ce.setSupervisor(sve);

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
    }

    @RolesAllowed("STAFF")
    @Override
    public void editContract(String contractUUID, Person supervisorPerson, List<Person> secretaries, boolean secretariesChanged, List<Person> assistants, boolean assistantsChanged, Date startDate, Date endDate, TimesheetFrequencyEnum timesheetFrequency, int workingDaysPerWeek, int vacationDaysPerYear, double hoursPerWeek, int archiveDuration) {
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
        ce.setFrequency(timesheetFrequency);
        ce.setWorkingDaysPerWeek(workingDaysPerWeek);
        ce.setVacationDaysPerYear(vacationDaysPerYear);
        ce.setHoursPerWeek(hoursPerWeek);
        ce.setArchiveDuration(archiveDuration);

        // 1. 
        SupervisorEntity svePrev = supervisorAccess.getSupervisorByContract(contractAccess.getByUuid(contractUUID));
        // This means the current selected supervisor is different from one before
        if (!supervisorPerson.getUuid().equals(svePrev.getPerson().getUuid())) {
            // delete old supervisor for this contract

            contractAccess.getByUuid(contractUUID).setSupervisor(null);
            supervisorAccess.deleteEntity(svePrev);

            // create new supervisor with this contract id
            SupervisorEntity sve = supervisorAccess.createEntity(supervisorPerson.getName());
            sve.setPerson(personAccess.getByUuid(supervisorPerson.getUuid()));
            sve.setContract(ce);
            contractAccess.getByUuid(contractUUID).setSupervisor(sve);
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
    }

    @RolesAllowed("STAFF")
    @Override
    public Contract startContract(String contractUUID) {

        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        ce.setStatus(ContractStatusEnum.STARTED);
        // Create timesheets 
        timeSheetBusinessLogic.createTimeSheet(contractUUID);

        // Now updating the total hours due for contract. Subject to change when timesheet entries changes.
        _updateContractStatistics(contractUUID);
        return new Contract(ce.getUuid(), ce.getName());
    }

    @RolesAllowed("STAFF")
    @Override
    public void terminateContract(String contractUUID) {

        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        ce.setStatus(ContractStatusEnum.TERMINATED);
        ce.setTerminationDate(LocalDate.now());
        // Delete Timesheets in progress status        
        timeSheetBusinessLogic.deleteTimeSheet(contractUUID, Boolean.TRUE);
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
        c.setVacationHours(ce.getVacationHours());
        c.setHoursDue(ce.getHoursDue());
        c.setArchiveDuration(ce.getArchiveDuration());

        // Set Supervisor also. Note to also do for employee
        Supervisor s = new Supervisor(ce.getSupervisor().getUuid(), ce.getSupervisor().getName());
        PersonEntity person = ce.getSupervisor().getPerson();
        Person pS = new Person(person.getUuid(), person.getName());
        pS.setFirstName(person.getFirstName());
        pS.setLastName(person.getLastName());
        pS.setDateOfBirth(person.getDateOfBirth());
        pS.setEmailAddress(person.getEmailAddress());
        pS.setUserRoleRealm(person.getUserRoleRealm());
        pS.setPreferredLanguage(person.getPreferredLanguage());
        s.setPerson(pS);
        c.setSupervisor(s);

        Employee e = new Employee(ce.getEmployee().getUuid(), ce.getEmployee().getName());
        PersonEntity personforE = ce.getEmployee().getPerson();
        Person pE = new Person(personforE.getUuid(), personforE.getName());
        // To fill rest of person
        e.setPerson(pE);
        c.setEmployee(e);
        return c;
    }

    @Override
    public void calledForContractArchive(String contractUUID) {
        List<TimesheetEntity> lts = timeSheetAccess.getTimeSheetsForContract(contractUUID);
        if (lts.stream().filter(p -> p.getStatus() != TimesheetStatusEnum.ARCHIVED).collect(Collectors.toList()).isEmpty()) {
            // This means all timesheet for this contract are set to archived.
            ContractEntity ce = contractAccess.getByUuid(contractUUID);
            ce.setStatus(ContractStatusEnum.ARCHIVED);
        }
    }

    @Override
    public void _updateContractStatistics(String contractUUID) {
        ContractEntity ce = contractAccess.getByUuid(contractUUID);

        // Calculating vacation hours.
        LocalDate date1 = ce.getStartDate();
        LocalDate date2 = ce.getEndDate();
        double durationOfContract = (double) ChronoUnit.MONTHS.between(date1, date2) + 1;
        double vacationHours = (double) ce.getVacationDaysPerYear() * (durationOfContract / 12) * (ce.getHoursPerWeek() / ce.getWorkingDaysPerWeek());
        ce.setVacationHours(UTILNumericSupport.round(vacationHours, 2));

        // Calculating Total hours due.
        // load each timesheet for this contract. and sum each timesheets hoursdue.
        List<TimesheetEntity> lte = timeSheetAccess.getTimeSheetsForContract(contractUUID);
        double totalhoursDue = 0.0;
        for (TimesheetEntity te : lte) {
            totalhoursDue += te.getHoursDue();
        }
        ce.setHoursDue(totalhoursDue);
    }

    @RolesAllowed("STAFF")
    @Override
    public void deleteContract(String contractUUID) {
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        ce.setEmployee(null);
        ce.setSupervisor(null);

        SupervisorEntity svePrev = supervisorAccess.getSupervisorByContract(ce);
        supervisorAccess.deleteEntity(svePrev);

        List<SecretaryEntity> toDeleteSecretaries = secretaryAccess.getSecretariesByContract(ce);
        for (SecretaryEntity delSecretary : toDeleteSecretaries) {
            secretaryAccess.deleteEntity(delSecretary);
        }

        EmployeeEntity toDeleteEmployee = employeeAccess.getEmployeeByContract(ce);
        employeeAccess.deleteEntity(toDeleteEmployee);

        List<AssistantEntity> toDeleteAssistants = assistantAccess.getAssistantsByContract(ce);
        for (AssistantEntity delEmployee : toDeleteAssistants) {
            assistantAccess.deleteEntity(delEmployee);
        }

        contractAccess.deleteEntity(contractAccess.getContractEntity(contractUUID));
    }

    @Override
    public List<Contract> getContractsByPerson(String personUUID) {
        //Because all users logged in doesnot have to be as employee
        List<ContractEntity> lce = contractAccess.getContractsByPerson(personAccess.getByUuid(personUUID));

        if (lce == null) {
            return null;
        }
        List<Contract> result = new ArrayList<>();
        for (ContractEntity ce : lce) {
            Contract c = new Contract(ce.getUuid(), ce.getName());
            c.setStartDate(ce.getStartDate());
            c.setEndDate(ce.getEndDate());
            c.setFrequency(ce.getFrequency());
            c.setHoursPerWeek(ce.getHoursPerWeek());
            c.setWorkingDaysPerWeek(ce.getWorkingDaysPerWeek());
            c.setVacationDaysPerYear(ce.getVacationDaysPerYear());
            c.setStatus(ce.getStatus());
            c.setTerminationDate(ce.getTerminationDate());
            c.setVacationHours(ce.getVacationHours());
            c.setHoursDue(ce.getHoursDue());
            c.setArchiveDuration(ce.getArchiveDuration());

            // Set Supervisor
            Supervisor s = new Supervisor(ce.getSupervisor().getUuid(), ce.getSupervisor().getName());
            PersonEntity person = ce.getSupervisor().getPerson();
            Person pS = new Person(person.getUuid(), person.getName());
            pS.setFirstName(person.getFirstName());
            pS.setLastName(person.getLastName());
            pS.setDateOfBirth(person.getDateOfBirth());
            pS.setEmailAddress(person.getEmailAddress());
            pS.setUserRoleRealm(person.getUserRoleRealm());
            pS.setPreferredLanguage(person.getPreferredLanguage());
            s.setPerson(pS);
            c.setSupervisor(s);

            // Set Employee
            Employee e = new Employee(ce.getEmployee().getUuid(), ce.getEmployee().getName());
            PersonEntity personforE = ce.getEmployee().getPerson();
            Person pE = new Person(personforE.getUuid(), personforE.getName());
            // To fill rest of person
            e.setPerson(pE);
            c.setEmployee(e);

            result.add(c);
        }
        return result;
    }

    @Override
    public void updateTotalHoursDue(String contractUUID, double hoursToReduce) {
        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        double updateHours = ce.getHoursDue() - hoursToReduce;
        if (updateHours >= 0.0) {
            ce.setHoursDue(updateHours);
        }
    }

}
