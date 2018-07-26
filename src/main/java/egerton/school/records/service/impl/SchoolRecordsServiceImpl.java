package egerton.school.records.service.impl;

import egerton.school.records.modal.Staff;
import egerton.school.records.modal.Student;
import egerton.school.records.dao.SchoolRecordsDAO;
import egerton.school.records.service.SchoolRecordsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
@Transactional
@Service
public class SchoolRecordsServiceImpl implements SchoolRecordsService {
    @Inject
    private SchoolRecordsDAO schoolRecord;
    @Override
    public boolean studentSaved(Student student) {
        return this.schoolRecord.studentSaved(student);
    }

    @Override
    public boolean staffSaved(Staff staff) {
        return this.schoolRecord.staffSaved(staff);
    }

    @Override
    public Student findStudentRecord(Student student) {
        return this.schoolRecord.findStudentRecord(student);
    }

    @Override
    public Staff findStaffRecord(Staff staff) {
        return this.schoolRecord.findStaffRecord(staff);
    }

    public SchoolRecordsDAO getSchoolRecord() {
        return schoolRecord;
    }

    public void setSchoolRecord(SchoolRecordsDAO schoolRecord) {
        this.schoolRecord = schoolRecord;
    }
}
