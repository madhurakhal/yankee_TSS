package yankee.logic;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

@Remote 
public interface ContractBusinessLogic {

    public List<Contract> getContractList();

    public Contract createContract(String contractName, Person supervisor, Person assistant, Person secretary, Person employee, Date startDate , Date endDate , TimesheetFrequencyEnum timesheetFrequency, double hoursPerWeek, int workingDaysPerWeek, int vacationDaysPerYear);
    
    public Contract editContract(String contractUUID , Person supervisorPerson , List<Person> secretaries , boolean secretariesChanged, List<Person> assistants , boolean assistantsChanged, Date startDate, Date endDate, TimesheetFrequencyEnum timesheetFrequency , int workingDaysPerWeek , int vacationDaysPerYear , double hoursPerWeek);
    
    public Contract startContract(String contractUUID);
    
    public Contract getContractByUUID(String contractUUID);
    
    public void updateContractStatistics(String contractUUID);
    
    public void deleteContract(String contractUUID);
    
    public void terminateContract(String contractUUID);
    
    public List<Contract> getContractsByPerson(String personUUID);
    
}
