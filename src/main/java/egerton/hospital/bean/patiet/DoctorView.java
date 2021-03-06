package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.employe.Employee;
import egerton.hospital.model.illness.Disease;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.medication.Medicate;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;
import egerton.hospital.model.symptom.Symptom;
import egerton.hospital.model.test.Test;
import egerton.hospital.model.triage.Triage;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.admission.AdmissionService;
import egerton.hospital.service.lab.LabService;
import egerton.hospital.service.medication.MedicationService;
import egerton.hospital.service.reception.ReceptionService;
import egerton.hospital.service.room.RoomService;
import egerton.hospital.service.triage.TriageService;
import egerton.hospital.service.visit.VisitService;
import egerton.hospital.utill.Utill;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.*;
@Named
@ViewScoped
public class DoctorView {
    private Medicate medicate;
    private Disease disease;
    private Patient patient;
    private Admission admission;
    private Visit visit;
    private Triage triage;
    private Lab lab;
    private Employee employee;
    private Room room;
    private Bed bed;
    private Test test;
    private Symptom symptom;
    @Inject
    private MedicationService medicationService;
    @Inject
    private AdmissionService admissionService;
    @Inject
    private ReceptionService receptionService;
    @Inject
    private VisitService visitService;
    @Inject
    private TriageService triageService;
    @Inject
    private LabService labService;
    @Inject
    private RoomService roomService;

    private List<Disease>illnesses;
    private List<Medicate>medications;
    private List<Visit>visits;
    private List<Visit>unitA,unitB,unitC,unitD;
    private Set<Admission>admissions;
    private List<Lab>labs,prevLabs;
    private Set<Triage>triageSet;
    private Set<String>freeRooms,freeBeds;
    private Set<String>sicknesses;
    private boolean patientRecordAvailable,consultationInitiated,testResultAvailable;

    private FacesContext context=FacesContext.getCurrentInstance();


    public DoctorView(){
        admission =new Admission();
        bed=new Bed();
        employee=new Employee();
        disease=new Disease();
        lab=new Lab();
        medicate=new Medicate();
        patient=new Patient();
        room=new Room();
        symptom=new Symptom();
        test=new Test();
        triage=new Triage();
        visit=new Visit();
    }
    private String test1="",test2="",test3="",test4="",test5="";
    @PostConstruct
    private void init(){
        this.setConsultationInitiated(false);
    }

    public String saveSuggestedIllness(){
        try {
            disease=new Disease(generateRandomNumber(),disease.getIllness(),getPatient(),new Date());
            if (!this.getMedicationService().checkIfIllnessIsSavedAlready(disease)){
                if (this.getMedicationService().saveIllness(disease)){
                    Message.message("Illness Submitted",FacesMessage.SEVERITY_INFO);
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    disease=new Disease();
                    return ("/faces/doctor/illness.xhtml?faces-redirect=true");
                }
            }else{
                Message.message("Illness Submitted already",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public String submitMedications(){
        try {
            disease=this.illnessInfo();
            medicate=new Medicate(generateRandomNumber(),medicate.getMedName(),medicate.getDiagnosis(),medicate.getIllness(),medicate.getPrescription(),medicate.getStrength(),medicate.getMode(),medicate.getRecommendation(),new Date(),getPatient(),(Employee)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("doc"));
            if (!this.getMedicationService().checkIfMedicationIsSavedAlready(medicate)){
                if (this.getMedicationService().saveTreatmentRecord(medicate)){
                    Message.message("Medication Submitted",FacesMessage.SEVERITY_INFO);
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    return ("/faces/doctor/medication.xhtml?faces-redirect=true");
                }
            }
            else {
                context.getExternalContext().getFlash().setKeepMessages(true);
                Message.message("Medication Submitted already",FacesMessage.SEVERITY_INFO);
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public String submitTest(){
        try {
            List<String>tests=new ArrayList<>();
            tests.add(test1);
            for (String ts:tests){
                if(!ts.equalsIgnoreCase("")){
                    test=new Test(generateRandomNumber(),test.getTest(),this.getPatient(),false,new Date());
                    test.setTest(ts);
                    if(!this.getLabService().checkIfTestIsAlreadySubmitted(test)){
                        if (this.getLabService().submitTestToBeDone(test)){
                            visit.setInQueue(false);
                            visit.setSentToTheLab(true);
                            if (this.getVisitService().updateVisit(visit));
                        }
                    }
                }
            }
            {
                Message.message("Test Submitted",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    public String updateTestToBeDone(){
        try{
            if(this.getLabService().updateTestToBeDone(test)){
                Message.message("update successful",FacesMessage.SEVERITY_ERROR);
                return ("/faces/doctor/test-to-be-done.xhtml?faces-redirect=true");
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    private void tests(Patient patient){
        try {
            labs=this.getLabService().getLabReport(patient);
            if(!labs.isEmpty()){
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
    }

    public String saveAdmissionInfo(){
        try {
            bed.setRoom(room);
            setBed(this.bedInfo(bed));
            this.setEmployee((Employee)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("doc"));

            admission =new Admission(generateRandomNumber(),getAdmission().getSection(),getAdmission().getReason(),new Date(),new Date(),getRoom(),getBed(),getPatient(),getEmployee());
            if (!this.getAdmissionService().checkIfAdmittedAlready(admission)){
                if (this.getAdmissionService().admitPatient(admission)){
                    room.setSize(this.updatedRoomSize(room));
                    bed.setRoom(room);
                    bed.setOccupied(true);
                    if(this.getRoomService().updateRoom(room) && this.getRoomService().updateBed(bed)){
                        Utill.setNumber(admission.getAdmissionNumber());
                        Message.message("Admission Successful",FacesMessage.SEVERITY_INFO);
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        return ("/faces/doctor/admission.xhtml?faces-redirect=true");
                    }
                }
            }
            else Message.message("Admitted already",FacesMessage.SEVERITY_INFO);
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public void saveSymptoms(){
        try{
            symptom=new Symptom(new Date(),generateRandomNumber(),symptom.getSymptom(),patient);
            if (this.getMedicationService().saveSymptoms(symptom)){
                Message.message("saved Successful",FacesMessage.SEVERITY_INFO);
                context.getExternalContext().getFlash().setKeepMessages(true);
            }
        }catch (Exception e){}
    }
    public String updateAdmissionInfo(){
        try {
            admission.setAdmissionNumber(Utill.getNumber());
             if(this.getAdmissionService().update(admission)) {
                 Message.message("Admission Successful", FacesMessage.SEVERITY_INFO);
                 context.getExternalContext().getFlash().setKeepMessages(true);
                 return ("/faces/doctor/admission.xhtml?faces-redirect=true");
             }
             else Message.message("Admitted already",FacesMessage.SEVERITY_INFO);
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public Admission admissionInfo(){
        try{
            Admission adm=this.getAdmissionService().todayPatientAdmission(admission);
            if (adm!=null)
                admission=adm;

            disease=new Disease(patient,new Date());
            illnesses=this.getMedicationService().getTodayPatientIllnesses(disease);

            return adm;
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public List<Medicate>medications(){
        try {
            medications=this.getMedicationService().getPatientMedications(patient);
            if(!medications.isEmpty())
                return medications;
        }catch (Exception e){}
        return new ArrayList<>();
    }

    public Patient visitorToday(){
        try {
            Visit vs=getVisitService().visitorToday(new Date());
            if(!vs.equals(null)){
                visit=vs;
                patient=visit.getPatient();
                if(patient!=null){
                    this.setPatientRecordAvailable(true);
                    this.treatments(patient);
                    this.tests(patient);
                    this.previousLabReports();
                    this.admissions();
                    return patient;
                }
            }
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_WARN);
        }
        return null;
    }

    private void treatments(Patient patient){
        try {
            medications=this.getMedicationService().getPatientMedications(patient);
            if(!medications.isEmpty()){

            }
        }catch (Exception e){

        }
    }
    public Set<Triage> triageRecords(){
        triageSet=new HashSet<>();
        try {
            triage=new Triage(new Date(),patient);
            triage=this.getTriageService().getTriageResult(triage);
            if (!triage.equals(null)){
                triageSet.add(triage);
                return triageSet;
            }

        }catch (Exception e){

        }
        return null;
    }
    private void admissions(){
        try {
            admissions=this.getAdmissionService().patientPreviousAdmissionDetails(admission);
            if(!admissions.isEmpty()){
            }
        }catch (Exception e){

        }
    }
    private List<Lab>previousLabReports(){
        try{
            prevLabs=this.getLabService().previousReports(new Date(),patient);
            if (!prevLabs.isEmpty())
                return prevLabs;
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    private void labReport(){
        try{
            labs=this.getLabService().todayLabReport(new Date(),patient);
            if (!labs.isEmpty())
                setTestResultAvailable(true);
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
    }
    private void patientSymptoms(){
        try{
            symptom=this.getMedicationService().getTodaySymptom(new Date(),patient);
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
    }
    public Set<String>roomNumbers(){
        try{
            freeRooms=this.getRoomService().freeRooms();
            if (!freeRooms.isEmpty())
                return freeRooms;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public Set<String>bedNumbers(){
        try{
            freeBeds=this.getRoomService().freeBeds(getRoom());
            if (!freeBeds.isEmpty())
                return freeBeds;
        }catch (Exception e){}
        return new LinkedHashSet<>();
    }
    private Bed bedInfo(Bed bed){
        try {
            bed=this.getRoomService().bedInfo(bed);
            if (bed!=null)
                return bed;
        }catch (Exception e){}
        return null;
    }
//    public Set<String> suspectedIllnesses(){
//        sicknesses=new HashSet<>();
//        try{
//            disease=new Disease(patient,new Date());
//            illnesses=this.getMedicationService().getTodayPatientIllnesses(disease);
//            if (!illnesses.isEmpty()){
//                for (Disease il:illnesses){
//                    sicknesses.add(il.getIllness());
//                }
//                return sicknesses;
//            }
//        }catch (Exception e){
//            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
//        }
//        return null;
//    }

    public Disease illnessInfo(){
        try{
            disease=new Disease(disease.getIllness(),patient,new Date());
            disease=this.getMedicationService().patientIllness(disease);
            System.out.println(disease.getIllnessNumber());
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
        return disease;
    }

    private String generateRandomNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }

    private boolean updatePatientVisit() {
        try{
            visit.setTreated(true);
            if (this.getVisitService().updateVisit(visit))
                return true;
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
        return false;
    }

    public void patientsInTheQueue(){
        try{
            visits=this.getVisitService().visitsToday(new Date());
            if (!visits.isEmpty()){
                unitA=new ArrayList<>();
                unitB=new ArrayList<>();
                unitC=new ArrayList<>();
                unitD=new ArrayList<>();
                for (Visit visit:visits){
                    if (visit.getUnit().equalsIgnoreCase("A") && (visit.isInQueue())){
                        unitA.add(visit);
                    }else if (visit.getUnit().equalsIgnoreCase("B") && (visit.isInQueue())){
                        unitB.add(visit);
                    }else if (visit.getUnit().equalsIgnoreCase("C") && (visit.isInQueue())){
                        unitC.add(visit);
                    }else if (visit.getUnit().equalsIgnoreCase("D") && (visit.isInQueue())){
                        unitD.add(visit);
                    }
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public String selectedPatientRecords(){
        if(!visit.equals(null)){
            patient=visit.getPatient();
            setPatientRecordAvailable(true);
            return ("/faces/doctor/patient-record-manipulation.xhtml?faces-redirect=true");
        }
       return null;
    }
    public void patientMedicationViewElements(){
        try{
            this.patientSymptoms();
            this.labReport();
        }catch (Exception e){}
    }
    public void openSymptomsDlg(){
        PrimeFaces.current().executeScript("PF('symptomDlg').show()");
    }
    public void openTestDlg(){
        PrimeFaces.current().executeScript("PF('testsDlg').show()");
    }
    public void openDiagnoseDlg(){
        PrimeFaces.current().executeScript("PF('diagnoseDlg').show()");
    }

    ///ADMISSION URLS
    public String openConsultation(){
        Employee doc=(Employee)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("doc");
        if (doc.getUnit().equalsIgnoreCase("A")){
            return ("/faces/doctor/patient-to-attend-to-unit-A.xhtml?faces-redirect=true");
        }
        return null;
    }



    public String admissionUrls(){
        if(isConsultationInitiated())
            return ("/faces/doctor/admission-option.xhtml?faces-redirect=true");

        return ("/faces/doctor/init-consultation.xhtml?faces-redirect=true");
    }
    public String admissionUrl(){
        return ("/faces/doctor/admission.xhtml?faces-redirect=true");
    }
    public String admInfoUrl(){
        return ("/faces/doctor/admission-info.xhtml?faces-redirect=true");
    }
    public String backToAdmOption(){
        return ("/faces/doctor/admission-option.xhtml?faces-redirect=true");
    }
    ///LAB URLS
    public String labOptionUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/lab-option.xhtml?faces-redirect=true");
        return ("/faces/doctor/init-consultation.xhtml?faces-redirect=true");
    }
    public String testToBeDoneUrl(){
        return ("/faces/doctor/test-to-be-done.xhtml?faces-redirect=true");
    }
    public String labResultUrl(){
            return ("/faces/doctor/lab-result.xhtml?faces-redirect=true");
    }

    ///TRIAGE URLS
    public String triageReportUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/triage-report.xhtml?faces-redirect=true");
        return ("/faces/doctor/init-consultation.xhtml?faces-redirect=true");
    }

    ///ILLNESS URLS
    public String illnessUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/illness.xhtml?faces-redirect=true");
        return ("/faces/doctor/init-consultation.xhtml?faces-redirect=true");
    }

    ///MEDICATION URLS
    public String medicationUrl(){
        return ("/faces/doctor/medication.xhtml?faces-redirect=true");
    }
    ///INIT DOCTOR ACTIONS URLS
    public String initConsultationUrl(){
        return ("/faces/doctor/init-consultation.xhtml?faces-redirect=true");
    }
    public String backUrl(){
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String openConsultationUrl(){
        this.setConsultationInitiated(true);
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String endConsultationUrl(){
        if(updatePatientVisit()){
            clear();
            return ("/faces/doctor/int-consultation.xhtml?faces-redirect=true");
        }
        return null;
    }



    private String updatedRoomSize(Room room){
        try{
            room=this.getRoomService().roomInfo(room);
            if(room.getSize()=="0")
                return room.getSize();
            else {
                room.setSize(""+(Integer.parseInt(room.getSize())-(1)));
                return room.getSize();
            }
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    private void clear(){
        this.setConsultationInitiated(false);
        admission =new Admission();
        bed=new Bed();
        employee=new Employee();
        disease=new Disease();
        lab=new Lab();
        medicate=new Medicate();
        patient=new Patient();
        room=new Room();
        test=new Test();
        triage=new Triage();
        visit=new Visit();
    }
    public String date(){
        return new SimpleDateFormat("dd/MM/yyy").format(new Date());
    }
    public String time(){
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public boolean isTestResultAvailable() {
        return testResultAvailable;
    }

    public void setTestResultAvailable(boolean testResultAvailable) {
        this.testResultAvailable = testResultAvailable;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

    public String getTest5() {
        return test5;
    }

    public void setTest5(String test5) {
        this.test5 = test5;
    }

    public Set<Triage> getTriageSet() {
        return triageSet;
    }

    public void setTriageSet(Set<Triage> triageSet) {
        this.triageSet = triageSet;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Triage getTriage() {
        return triage;
    }

    public void setTriage(Triage triage) {
        this.triage = triage;
    }

    public MedicationService getMedicationService() {
        return medicationService;
    }

    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    public AdmissionService getAdmissionService() {
        return admissionService;
    }

    public void setAdmissionService(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    public ReceptionService getReceptionService() {
        return receptionService;
    }

    public void setReceptionService(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    public VisitService getVisitService() {
        return visitService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public LabService getLabService() {
        return labService;
    }

    public void setLabService(LabService labService) {
        this.labService = labService;
    }

    public List<Lab> getLabs() {
        return labs;
    }

    public void setLabs(List<Lab> labs) {
        this.labs = labs;
    }

    public TriageService getTriageService() {
        return triageService;
    }

    public void setTriageService(TriageService triageService) {
        this.triageService = triageService;
    }

    public List<Disease> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Disease> illnesses) {
        this.illnesses = illnesses;
    }

    public List<Medicate> getMedications() {
        return medications;
    }

    public void setMedications(List<Medicate> medications) {
        this.medications = medications;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Set<String> getFreeRooms() {
        return freeRooms;
    }

    public void setFreeRooms(Set<String> freeRooms) {
        this.freeRooms = freeRooms;
    }

    public Set<String> getFreeBeds() {
        return freeBeds;
    }

    public void setFreeBeds(Set<String> freeBeds) {
        this.freeBeds = freeBeds;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Set<Admission> getAdmissions() {
        return admissions;
    }

    public boolean isPatientRecordAvailable() {
        return patientRecordAvailable;
    }

    public void setPatientRecordAvailable(boolean patientRecordAvailable) {
        this.patientRecordAvailable = patientRecordAvailable;
    }

    public List<Visit> getUnitA() {
        return unitA;
    }

    public void setUnitA(List<Visit> unitA) {
        this.unitA = unitA;
    }

    public List<Visit> getUnitB() {
        return unitB;
    }

    public void setUnitB(List<Visit> unitB) {
        this.unitB = unitB;
    }

    public List<Visit> getUnitC() {
        return unitC;
    }

    public void setUnitC(List<Visit> unitC) {
        this.unitC = unitC;
    }

    public List<Visit> getUnitD() {
        return unitD;
    }

    public void setUnitD(List<Visit> unitD) {
        this.unitD = unitD;
    }

    public Medicate getMedicate() {
        return medicate;
    }

    public void setMedicate(Medicate medicate) {
        this.medicate = medicate;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public List<Lab> getPrevLabs() {
        return prevLabs;
    }

    public void setPrevLabs(List<Lab> prevLabs) {
        this.prevLabs = prevLabs;
    }

    public void setAdmissions(Set<Admission> admissions) {
        this.admissions = admissions;
    }

    public Set<String> getSicknesses() {
        return sicknesses;
    }

    public void setSicknesses(Set<String> sicknesses) {
        this.sicknesses = sicknesses;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public boolean isConsultationInitiated() {
        return consultationInitiated;
    }

    public void setConsultationInitiated(boolean consultationInitiated) {
        this.consultationInitiated = consultationInitiated;
    }
}
