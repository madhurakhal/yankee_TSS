/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.primefaces.event.FileUploadEvent;
 
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadBean {
     
    private UploadedFile jsonFile;

    public UploadedFile getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(UploadedFile jsonFile) {
        this.jsonFile = jsonFile;
    } 
    
    //The file will be store in your domain1 .. for me payaradomain folder, in your glassfish folder
    public void upload() {        
        FacesMessage msg = new FacesMessage("Succesful", jsonFile.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);        
        try{
            System.out.println("fileName: " + jsonFile.getFileName());
            InputStream is;
            try (FileOutputStream fos = new FileOutputStream(new File(jsonFile.getFileName()))) {
                is = jsonFile.getInputstream();
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int a;
                while(true){
                    a = is.read(buffer);
                    if(a < 0) break;
                    fos.write(buffer, 0, a);
                    fos.flush();
                }
            }
            is.close();
        }catch(IOException e){
        }
    
        
        
        
//        if(file != null) {
//            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
    }
    
    public void test(){
        JSONParser parser = new JSONParser();
        System.out.println(new File(".").getAbsolutePath());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
        String relativePath = "resources/germanyholidays/";
        try {
            
            Object obj = parser.parse(new FileReader(
                    FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath) + "/BADENWURTTENMERG.json"));
 
                      
	    JSONObject genreJsonObject = (JSONObject) obj;
	    JSONArray test = (JSONArray) genreJsonObject.get("BADENWURTTENMERG");            
 
            Iterator<JSONObject> iterator = test.iterator();
            while (iterator.hasNext()) {
                JSONObject updated = iterator.next(); 
                JSONObject date = (JSONObject) updated.get("date");
                Long day = (Long) date.get("day");
	        String time = (String) updated.get("localName");
	        System.out.println(day);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}