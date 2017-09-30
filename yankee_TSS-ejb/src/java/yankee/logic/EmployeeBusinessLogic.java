package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;

@Remote 
public interface EmployeeBusinessLogic {
    
    public List<Employee> getEmployeeList();
    
    public Employee createEmployee(String name , String personUUID);
    
    public Employee getEmployeeByContract(String contractUUID);
        
}
