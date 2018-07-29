package egerton.hospital.bean.patiet;

import egerton.hospital.message.Message;
import egerton.hospital.model.admit.Admission;
import egerton.hospital.model.employe.Employee;
import egerton.hospital.model.illness.Illness;
import egerton.hospital.model.lab.Lab;
import egerton.hospital.model.patient.Patient;
import egerton.hospital.model.room.Bed;
import egerton.hospital.model.room.Room;
import egerton.hospital.model.test.Test;
import egerton.hospital.model.medication.Medication;
import egerton.hospital.model.triage.Triage;
import egerton.hospital.model.visit.Visit;
import egerton.hospital.service.admission.AdmissionService;
import egerton.hospital.service.lab.LabService;
import egerton.hospital.service.medication.MedicationService;
import egerton.hospital.service.patient.ReceptionService;
import egerton.hospital.service.room.RoomService;
import egerton.hospital.service.triage.TriageService;
import egerton.hospital.service.visit.VisitService;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.*;
@Named
@ViewScoped
public class DoctorView {
    private Medication medication;
    private Illness illness;
    private Patient patient;
    private Admission admission;
    private Visit visit;
    private Triage triage;
    private Lab lab;
    private Employee employee;
    private Room room;
    private Bed bed;
    private Test test;
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

    private List<Illness>illnesses;
    private List<Medication>medications;
    private List<Visit>visits;
    private Set<Admission>admissions;
    private Set<String>numbers;
    private List<Lab>labs,prevLabs;
    private Set<Triage>triageSet;
    private Set<String>freeRooms,freeBeds;
    private Set<String>suspectedIllness;

    private boolean patientRecordAvailable,consultationInitiated;

    private FacesContext context=FacesContext.getCurrentInstance();

    public DoctorView(){
        admission =new Admission();
        bed=new Bed();
        employee=new Employee();
        illness=new Illness();
        lab=new Lab();
        medication=new Medication();
        patient=new Patient();
        room=new Room();
        test=new Test();
        triage=new Triage();
        visit=new Visit();
    }

    public String saveSuggestedIllness(){
        try {
            illness=new Illness(generateRandomNumber(),getIllness().getIllness(),getPatient());
            if (this.getMedicationService().checkIfIllnessIsSavedAlready(illness)){
                if (this.getMedicationService().saveIllness(illness)){
                    Message.message("Illness Submitted",FacesMessage.SEVERITY_INFO);
                    context.getExternalContext().getFlash().setKeepMessages(true);

                    return ("/faces/doctor/illness.xhtml?faces-redirect=true");
                }
            }else  Message.message("Illness Submitted already",FacesMessage.SEVERITY_INFO);


        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public String submitMedications(){
        try {
            this.setEmployee((Employee)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("doc"));
            medication=new Medication(generateRandomNumber(),medication.getProductName(),medication.getPrescription(),medication.getStrength(),medication.getRouteOfAdministration(),medication.getComment(),new Date(),getPatient(),getEmployee());
            if (this.getMedicationService().checkIfMedicationIsSavedAlready(medication)){
                if (this.getMedicationService().saveTreatmentRecord(medication)){
                    Message.message("Medication Submitted",FacesMessage.SEVERITY_INFO);
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    return ("/faces/doctor/medication.xhtml?faces-redirect=true");
                }
            }
            else Message.message("Medication Submitted already",FacesMessage.SEVERITY_INFO);
        }catch (Exception e){
            Message.message(""+e,FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    public String submitTest(){
        try {
            test=new Test(generateRandomNumber(),test.getTest(),this.getPatient(),false,new Date());
            if(!this.getLabService().checkIfTestIsAlreadySubmitted(test))
                if(getLabService().submitTestToBeDone(test)){
                     Message.message("Test Submitted",FacesMessage.SEVERITY_INFO);
                    return ("/faces/doctor/test-to-be-done.xhtml?faces-redirect=true");
            }
            else Message.message("Test Submitted",FacesMessage.SEVERITY_INFO);
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
            admission =new Admission(generateRandomNumber(),getAdmission().getSection(),new Date(),new Date(),getRoom(),getBed(),getPatient(),getEmployee());
            if (this.getAdmissionService().checkIfAdmittedAlready(admission)){
                if (this.getAdmissionService().admitPatient(admission)){
                    if(this.getRoomService().updateRoom(room) && this.getRoomService().updateBed(bed)){
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

    public List<Illness>illnesses(){
        try {
            illnesses=this.getMedicationService().getPatientIllnesses(patient);
            if(!illnesses.isEmpty())
                return illnesses;
        }catch (Exception e){}
        return new ArrayList<>();
    }
    public List<Illness>medications(){
        try {
            medications=this.getMedicationService().getPatientMedications(patient);
            if(!illnesses.isEmpty())
                return illnesses;
        }catch (Exception e){}
        return new ArrayList<>();
    }

    public Patient patientInfo(){
        if(!visits.isEmpty()){
            for (int i=0;i<visits.size();i++){
                String number=visits.get(i).getPatient().getPatientNumber();
                String num=patient.getPatientNumber();
                if(number.equalsIgnoreCase(num)){
                    patient=visits.get(i).getPatient();
                }
            }
            if(patient!=null){
                this.setPatientRecordAvailable(true);
                this.treatments(patient);
                this.illnesses(patient);
                this.tests(patient);
                this.previousLabReports();
                this.admissions(patient);
                return patient;
            }
        }
        return null;
    }
    public Set<String> visitorsToday(){
        numbers=new HashSet<>();
        try {
            visits=getVisitService().visiting(new Date());
            if(!visits.isEmpty()){
                for (int i=0;i<visits.size();i++){
                    numbers.add(visits.get(i).getPatient().getPatientNumber());
                }
                return numbers;
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
    private void illnesses(Patient patient){
        try {
            illnesses=this.getMedicationService().getPatientIllnesses(patient);
            if(!illnesses.isEmpty()){

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
    private void admissions(Patient patient){
        try {
            admissions=this.getAdmissionService().admissionDetails(patient);
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
    public void labReport(){
        try{
            labs=this.getLabService().todayLabReport(new Date(),patient);
            if (!labs.isEmpty())
                return;
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
    public Set<String>suspectedIllnesses(){
        try{
            suspectedIllness=new HashSet<>();
            illnesses=this.getMedicationService().getTodayPatientIllnesses(patient);
            if (!illnesses.isEmpty()){
                for (Illness illness:illnesses)
                    suspectedIllness.add(illness.getIllness());
                return suspectedIllness;
            }
        }catch (Exception e){
        }
        return null;
    }
    public Illness illnessInfo(){
        try{
            illness=this.getMedicationService().illnessInfo(illness);
        }catch (Exception e){
            Message.message(e.toString(),FacesMessage.SEVERITY_ERROR);
        }
        return illness;
    }
    private String generateRandomNumber(){
        return UUID.randomUUID().toString().replace("-","")
                .substring(1,16).toUpperCase();
    }
    public String refresh(){
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String admissionUrl(){
        if(isConsultationInitiated())
            return ("/faces/doctor/admission.xhtml?faces-redirect=true");

        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String testToBeDoneUrl(){
        if(this.patient!=(null))
          return ("/faces/doctor/test-to-be-done.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String triageReportUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/triage-report.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }

    public String illnessUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/illness.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String medicationUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/medication.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String consultationUrl(){
        this.setConsultationInitiated(true);
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String labResultUrl(){
        if (isConsultationInitiated())
            return ("/faces/doctor/lab-result.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }
    public String toPatientAdmissionRecord(){
        if (isConsultationInitiated())
            return ("/faces/doctor/admission-info.xhtml?faces-redirect=true");
        return ("/faces/doctor/consultation.xhtml?faces-redirect=true");
    }


    public String date(){
        return new SimpleDateFormat("dd/MM/yyy").format(new Date());
    }
    public String time(){
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public Set<Triage> getTriageSet() {
        return triageSet;
    }

    public void setTriageSet(Set<Triage> triageSet) {
        this.triageSet = triageSet;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Illness getIllness() {
        return illness;
    }

    public void setIllness(Illness illness) {
        this.illness = illness;
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

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
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

    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    public List<Medication> getMedications() {
        return medications;
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

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
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

    public List<Lab> getPrevLabs() {
        return prevLabs;
    }

    public void setPrevLabs(List<Lab> prevLabs) {
        this.prevLabs = prevLabs;
    }

    public void setAdmissions(Set<Admission> admissions) {
        this.admissions = admissions;
    }

    public Set<String> getSuspectedIllness() {
        return suspectedIllness;
    }

    public void setSuspectedIllness(Set<String> suspectedIllness) {
        this.suspectedIllness = suspectedIllness;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public boolean isConsultationInitiated() {
        return consultationInitiated;
    }

    public void setConsultationInitiated(boolean consultationInitiated) {
        this.consultationInitiated = consultationInitiated;
    }
}
