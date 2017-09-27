package yankee.web;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.to.Person;

@RequestScoped
@Named
public class AccountBean implements Serializable {

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @Inject
    private LoginBean logInBean;

    private Person person;
    private Date dateOfBirth;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private StreamedContent chart;

    public StreamedContent getChart() {
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

    @PostConstruct
    public void init() {
        getPerson();
    }

    public Person getPerson() {
        if (person == null) {
            person = personBusinessLogic.getPersonByName(logInBean.getUser().getName());
            //System.out.println("IMAGESSSSSS" + Arrays.toString(person.getPhoto()));
            if (person.getPhoto() != null) {
                chart = new DefaultStreamedContent(new ByteArrayInputStream(person.getPhoto()));
            }
        }
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void update() {
        if (dateOfBirth != null) {
            person.setDateOfBirth(dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        personBusinessLogic.updateDetails(person);
        
        FacesMessage message = new FacesMessage("Your changes for preferred language will reflect on next Login");
        FacesContext.getCurrentInstance().addMessage(null, message);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        UploadedFile file = event.getFile();
        person.setPhoto(file.getContents());
        personBusinessLogic.updatePhoto(person);
    }
}
