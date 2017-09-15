package yankee.primefaces.convertor;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import yankee.logic.to.Person;

@FacesConverter(value = "personConverter")
public class PickListPersonConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
        DualListModel<Person> model = (DualListModel<Person>) ((PickList) comp).getValue();
        for (Person person : model.getSource()) {
            if (person.getUuid().equals(value)) {
                return person;
            }
        }
        for (Person person : model.getTarget()) {
            if (person.getUuid().equals(value)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
        return ((Person) value).getUuid();
    }
}