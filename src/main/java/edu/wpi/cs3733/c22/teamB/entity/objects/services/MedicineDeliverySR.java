package edu.wpi.cs3733.c22.teamB.entity.objects.services;

import edu.wpi.cs3733.c22.teamB.entity.inheritance.AbstractSR;
import edu.wpi.cs3733.c22.teamB.entity.objects.Employee;
import edu.wpi.cs3733.c22.teamB.entity.objects.Location;

import java.time.LocalDate;

public class MedicineDeliverySR extends AbstractSR {
    
    private String medicineID;
    private String patientID;

    public MedicineDeliverySR() {
        super(null, "MedicineDeliverySR", null, null, null, null, null, null);
        this.medicineID = null;
        this.patientID = null;
    }

    public MedicineDeliverySR(String srID, String status, Location location, Employee requestor, Employee assignedEmployee, LocalDate dateRequested, String notes, String medicineID, String patientID) {
        super(srID, "MedicineDeliverySR", status, location, requestor, assignedEmployee, dateRequested, notes);
        this.medicineID = medicineID;
        this.patientID = patientID;
    }

    public MedicineDeliverySR(AbstractSR csr, String medicineID, String patientID){
        super(csr);
        this.setSrType("MedicineDeliverySR");
        this.medicineID = medicineID;
        this.patientID = patientID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    @Override
    public String toString() {
        return "MedicineDeliverySR{" +
                "srID= " + getSrID() + '\'' +
                "srType= " + getSrType() + '\'' +
                "status= " + getStatus() + '\'' +
                "location= " + getLocation() + '\'' +
                "requestor= " + getRequestor() + '\'' +
                "assignedEmployee= " + getAssignedEmployee() + '\'' +
                "dateRequested= " + getDateRequested() + '\'' +
                "notes= " + getNotes() + '\'' +
                "medicineID='" + medicineID + '\'' +
                ", patientID='" + patientID + '\'' +
                '}';
    }

    public String toStringFields() {
        return getSrID()
                + ","
                + medicineID
                + ","
                + patientID;
    }

    public static String toStringHeader() {
        return "srID"
                + ","
                + "medicineID"
                + ","
                + "patientID";
    }


}
