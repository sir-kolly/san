package egerton.hospital.dao.login.impl;

import egerton.hospital.dao.login.LoginDAO;
import egerton.hospital.model.employe.Employee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
@Repository
public class LoginDAOImpl implements LoginDAO {
    @Inject
    private SessionFactory sessionFactory;
    @Override
    public Employee employeeInfo(Employee employee) {
        return this.getSessionFactory().getCurrentSession().createNamedQuery("employeeInfo",Employee.class)
                .setParameter("email",employee.getEmail())
                .setParameter("pwd",employee.getPassword())
                .stream().findFirst().orElse(null);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
