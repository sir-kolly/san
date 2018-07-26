package egerton.school.records.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import egerton.school.records.modal.Staff;
import egerton.school.records.modal.Student;
import egerton.school.records.dao.SchoolRecordsDAO;

import javax.inject.Inject;
@Repository
public class SchoolRecordsDAOImpl implements SchoolRecordsDAO {
    @Inject
    private SessionFactory sessionFactory;
    @Override
    public boolean studentSaved(Student student) {
        this.getSessionFactory().getCurrentSession().save(student);
        return true;
    }

    @Override
    public boolean staffSaved(Staff staff) {
        this.getSessionFactory().getCurrentSession().save(staff);
        return true;
    }

    @Override
    public Student findStudentRecord(Student student) {
        return this.getSessionFactory().getCurrentSession().find(Student.class,student.getRegNo());
    }

    @Override
    public Staff findStaffRecord(Staff staff) {
        return this.getSessionFactory().getCurrentSession().find(Staff.class, staff.getSsNo());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
