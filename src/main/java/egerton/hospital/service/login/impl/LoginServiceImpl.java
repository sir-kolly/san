package egerton.hospital.service.login.impl;

import egerton.hospital.dao.login.LoginDAO;
import egerton.hospital.model.employe.Employee;
import egerton.hospital.service.login.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
@Transactional
@Service
public class LoginServiceImpl implements LoginService {
    @Inject
    private LoginDAO loginDAO;
    @Override
    public Employee employeeInfo(Employee employee) {
        return this.getLoginDAO().employeeInfo(employee);
    }
    public LoginDAO getLoginDAO() {
        return loginDAO;
    }

    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }
}

