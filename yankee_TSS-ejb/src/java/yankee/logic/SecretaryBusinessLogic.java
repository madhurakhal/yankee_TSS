package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;


@Remote 
public interface SecretaryBusinessLogic {
    
    public List<Secretary> getSecretaryList();
    
    public Secretary getSecretaryByUUID(String UUID);
    
    public Secretary createSecretary(String name , String personUUID);
    
    public List<Secretary> getSecretariesByContract(String contractUUID);
    
    public List<Person> getPersonsUnderSecretary(String secretaryPersonUUID);
}
