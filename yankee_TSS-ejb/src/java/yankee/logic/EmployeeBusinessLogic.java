package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;

@Remote 
public interface EmployeeBusinessLogic {
    /**
     * Returns a list of all {@link Person}s.
     *
     * @return the person list, sorted by name
     */
    public List<Employee> getEmployeeList();
    
    public Employee createEmployee(String name , String personUUID);
    
    public Employee getEmployeeByContract(String contractUUID);
        
}
