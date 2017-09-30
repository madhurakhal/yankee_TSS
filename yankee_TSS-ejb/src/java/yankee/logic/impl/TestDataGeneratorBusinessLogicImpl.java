package yankee.logic.impl;

import java.time.LocalDate;
import java.time.Month;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;

import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SupervisorEntity;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;

import yankee.logic.TestDataGeneratorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SupervisorAccess;


@Stateless
public class TestDataGeneratorBusinessLogicImpl implements TestDataGeneratorBusinessLogic{

    
    @EJB
    PersonAccess personAccess;
        
    @EJB
    EmployeeAccess employeAccess;
    
    @EJB
    SupervisorAccess supervisorAccess;
       
    @EJB
    TimeSheetBusinessLogic timesheetService;
    
    @EJB 
    ContractAccess contractAccess;
    
    
    
    @Override
    public void generateTestData() {
              
       System.out.println("creating a person");
       
       
        PersonEntity p = personAccess.createEntity("Shriharsh");
        p.setLastName("Ambhore");
        p.setFirstName("Shriharsh");
        p.setEmailAddress("ashriharsh@gmail.com");
        System.out.println("uuid ::"+p.getUuid());
        
        PersonEntity p1 = personAccess.createEntity("Sabin");
        p1.setLastName("Bhattarai");
        p1.setFirstName("Sabin");
        p1.setEmailAddress("sbhattarai@gmail.com");

        System.out.println("uuid ::"+p1.getUuid());
        
        PersonEntity p2 = personAccess.createEntity("Bob");
        p2.setLastName("marley");
        p2.setFirstName("bob");
        p2.setEmailAddress("bob@gmail.com");

        System.out.println("uuid ::"+p2.getUuid());
        
        
        PersonEntity p3 = personAccess.createEntity("alice");
        p3.setLastName("cooper");
        p3.setFirstName("alice");
        p3.setEmailAddress("alicec@gmail.com");

        System.out.println("uuid ::"+p3.getUuid());
        
               
        EmployeeEntity employee= employeAccess.createEntity(p.getFirstName());
        employee.setName(p.getFirstName());
        employee.setPerson(p);
        employee.setRollType(RoleTypeEnum.EMPLOYEE);
        
        
        SupervisorEntity se= supervisorAccess.createEntity(p1.getFirstName());
        se.setName(p1.getFirstName());
        se.setRollType(RoleTypeEnum.SUPERVISOR);
        se.setPerson(p1);
        
        
        SupervisorEntity se2= supervisorAccess.createEntity(p3.getFirstName());
        se2.setName(p3.getFirstName());
        se2.setRollType(RoleTypeEnum.SUPERVISOR);
        se2.setPerson(p3);
        
        EmployeeEntity employee2= employeAccess.createEntity(p2.getFirstName());
        employee2.setName(p2.getFirstName());
        employee2.setPerson(p2);
        employee2.setRollType(RoleTypeEnum.EMPLOYEE);
        
        
        ContractEntity contract=contractAccess.createEntity("JAVA EE Grading support");
        contract.setFrequency(TimesheetFrequencyEnum.WEEKLY);
        final LocalDate startDate=LocalDate.of(2017, Month.JANUARY, 1);
        final LocalDate endDate=LocalDate.of(2017, Month.MARCH, 31);
        contract.setEndDate(endDate);
        contract.setStartDate(startDate);
        contract.setHoursPerWeek(Double.parseDouble("20"));
        contract.setStatus(ContractStatusEnum.STARTED);
        contract.setTerminationDate(null);
        contract.setName("NTDS EE Grading support");
        contract.setWorkingDaysPerWeek(5);
        
        contract.setSupervisor(se);
        contract.setEmployee(employee);
       
        
        ContractEntity contract2=contractAccess.createEntity("Intro to web EE Grading support");
        contract2.setFrequency(TimesheetFrequencyEnum.MONTHLY);
        
        final LocalDate startDate2=LocalDate.of(2017, Month.JULY, 1);
        final LocalDate endDate2=LocalDate.of(2017, Month.SEPTEMBER, 30);
        contract2.setEndDate(endDate2);
        contract2.setStartDate(startDate2);
        contract2.setHoursPerWeek(Double.parseDouble("4"));
        contract2.setStatus(ContractStatusEnum.STARTED);
        contract2.setTerminationDate(null);
        contract2.setName("Intro to web Grading support");
        contract2.setWorkingDaysPerWeek(5);
        
        contract2.setSupervisor(se2);
        contract2.setEmployee(employee2);
    }
}