package egerton.hospital.message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public static void message(String msg, FacesMessage.Severity severity){
        FacesMessage message=new FacesMessage(msg);
        message.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null,message);
    }
}
