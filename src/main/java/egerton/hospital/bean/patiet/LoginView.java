package egerton.hospital.bean.patiet;

import egerton.hospital.model.employe.Employee;
import egerton.hospital.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
@SessionScoped
@Named("login")
public class LoginView implements Serializable {
    private Employee employee;
    @Autowired
    private LoginService loginService;

    public LoginView(){
        employee=new Employee();
    }

    public String logout(){
        return ("/faces/login.xhtml?faces-redirect=true");
    }

    public String login(){
        try {
            Employee em=this.getLoginService().employeeInfo(employee);
            if(!em.equals(null))
                employee=em;
            if(!employee.equals(null)){
                switch (employee.getRole().toLowerCase()){
                    case "doctor":
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("doc",employee);
                        return ("/faces/doctor/doctor-board.xhtml?faces-redirect=true");
                    case "nurse":
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nurse",employee);
                        return ("/faces/nurse/triage-nurse-board.xhtml?faces-redirect=true");
                    case "receptionist":
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reception",employee);
                        return ("/faces/reception/reception-board.xhtml?faces-redirect=true");
                    case "lab-doctor":
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lab",employee);
                        return ("/faces/lab/lab-board.xhtml?faces-redirect=true");
                }
            }
            else {
                return ("/faces/login.xhtml?faces-redirect=true");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return ("/faces/login.xhtml?faces-redirect=true");
    }
    public String validateSession(){
        if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsValue(employee))
             return ("/faces/login.xhtml?faces-redirect=true");
        return null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
