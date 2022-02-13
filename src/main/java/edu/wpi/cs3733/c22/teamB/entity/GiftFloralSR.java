package edu.wpi.cs3733.c22.teamB.entity;

import java.time.LocalDate;
import java.util.Objects;

public class GiftFloralSR extends AbstractSR {

    private String giftName;

    public GiftFloralSR(){
        super(null, "GiftFloralSR", null, null, null, null, null, null);
        this.giftName = null;
    }

    public GiftFloralSR(String srID, String status, Location location, Employee requestor, Employee assignedEmployee, LocalDate dateRequested, String notes,  String giftName) {
        super(srID, "GiftFloralSR", status, location, requestor, assignedEmployee, dateRequested, notes);
        this.giftName = giftName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    @Override
    public String toString() {
        return "GiftFloralSR{ " +
                "srID= " + getSrID() + '\'' +
                "srType= " + getSrType() + '\'' +
                "status= " + getStatus() + '\'' +
                "location= " + getLocation() + '\'' +
                "requestor= " + getRequestor() + '\'' +
                "assignedEmployee= " + getAssignedEmployee() + '\'' +
                "dateRequested= " + getDateRequested() + '\'' +
                "notes= " + getNotes() + '\'' +
                "giftName='" + giftName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftFloralSR that = (GiftFloralSR) o;
        return Objects.equals(giftName, that.giftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftName);
    }
}
