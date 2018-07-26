package egerton.school.records.dao;

import egerton.school.records.modal.Staff;
import egerton.school.records.modal.Student;

public interface SchoolRecordsDAO {
    boolean studentSaved(Student student);
    boolean staffSaved(Staff staff);
    Student findStudentRecord(Student student);
    Staff findStaffRecord(Staff staff);

}
