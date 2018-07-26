package egerton.school.records.service;

import egerton.school.records.modal.Staff;
import egerton.school.records.modal.Student;

public interface SchoolRecordsService {
    boolean studentSaved(Student student);
    boolean staffSaved(Staff staff);
    Student findStudentRecord(Student student);
    Staff findStaffRecord(Staff staff);
}
