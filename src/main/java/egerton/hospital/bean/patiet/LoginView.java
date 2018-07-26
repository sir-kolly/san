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
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public String login(){
        try {
            System.out.println(employee.getEmployeeNumber());
            System.out.println(employee.getPassword());

            Employee em=this.getLoginService().employeeInfo(employee);
            if(!em.equals(null)){
                employee=em;
                switch (employee.getRole().toLowerCase()){
                    case "doctor":
                        this.getSession().setAttribute("doc",employee);
                        return "doctor-board";
                    case "nurse":
                        this.getSession().setAttribute("nurse",employee);
                        return "nurse-board";
                    case "receptionist":
                        this.getSession().setAttribute("reception",employee);
                        return "reception-board";
                    case "lab-doctor":
                        this.getSession().setAttribute("lab-doc",employee);
                        return "lab-board";
                }
            }
            else {
                return "login";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "login";
    }

    private HttpSession getSession(){
        return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    private HttpServletRequest getRequest(){
        return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
