package yankee.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.primefaces.model.UploadedFile;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.PublicHolidaysBusinessLogic;

@ManagedBean
public class PublicHolidaysBean {

    @EJB
    private PublicHolidaysBusinessLogic publicHolidaysBusinessLogic;

    private UploadedFile jsonFile;
    private boolean databaseEmpty;

    public boolean isDatabaseEmpty() {
        databaseEmpty = publicHolidaysBusinessLogic.databaseEmpty();
        return databaseEmpty;
    }

    public void setDatabaseEmpty(boolean databaseEmpty) {
        this.databaseEmpty = databaseEmpty;
    }

    public UploadedFile getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(UploadedFile jsonFile) {
        this.jsonFile = jsonFile;
    }

    public void upload() {
        FacesMessage msg = new FacesMessage("Succesful", jsonFile.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        try {
            System.out.println("fileName: " + jsonFile.getFileName());
            InputStream is;
            try (FileOutputStream fos = new FileOutputStream(new File(jsonFile.getFileName()))) {
                is = jsonFile.getInputstream();
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int a;
                while (true) {
                    a = is.read(buffer);
                    if (a < 0) {
                        break;
                    }
                    fos.write(buffer, 0, a);
                    fos.flush();
                }
            }
            is.close();
        } catch (IOException e) {
        }
    }

    public void test() {
        JSONParser parser = new JSONParser();
        final String relativePath = "resources/germanyholidays/";
        try {
            for (GermanyStatesEnum state : GermanyStatesEnum.values()) {
                Object obj = parser.parse(new FileReader(FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath) + "/" + state + ".json"));
                JSONObject genreJsonObject = (JSONObject) obj;
                JSONArray test = (JSONArray) genreJsonObject.get(state.name());

                Iterator<JSONObject> iterator = test.iterator();
                while (iterator.hasNext()) {
                    JSONObject updated = iterator.next();
                    JSONObject date = (JSONObject) updated.get("date");
                    Long day = (Long) date.get("day");
                    Long month = (Long) date.get("month");
                    Long year = (Long) date.get("year");
                    Long dayOfWeek = (Long) date.get("dayOfWeek");
                    String localName = (String) updated.get("localName");
                    String englishName = (String) updated.get("englishName");
                    publicHolidaysBusinessLogic.createPublicHolidays(state.name(), state, day.intValue(), month.intValue(), year.intValue(), dayOfWeek.intValue(), localName, englishName);
                }
            }
        } catch (IOException | ParseException e) {
        }
    }
}
