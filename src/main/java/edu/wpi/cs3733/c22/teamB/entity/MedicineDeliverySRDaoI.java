package edu.wpi.cs3733.c22.teamB.entity;

import edu.wpi.cs3733.c22.teamB.entity.inheritance.AbstractSR;
import edu.wpi.cs3733.c22.teamB.entity.inheritance.IDatabase;
import edu.wpi.cs3733.c22.teamB.entity.objects.Employee;
import edu.wpi.cs3733.c22.teamB.entity.objects.Location;
import edu.wpi.cs3733.c22.teamB.entity.objects.services.MedicineDeliverySR;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicineDeliverySRDaoI implements IDatabase<MedicineDeliverySR> {

    public MedicineDeliverySRDaoI() {
    }


    @Override
    public void addValue(MedicineDeliverySR object) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO MEDICINEDELIVERYSR (srID, medicineID, patientID) VALUES(?, ?, ?)");
            pstmt.setString(1, object.getSrID());
            pstmt.setString(2, object.getMedicineID());
            pstmt.setString(3, object.getPatientID());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Insert Into MEDICINEDELIVERYSR Table: Failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteValue(String objectID) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM MEDICINEDELIVERYSR WHERE srID = ?");
            pstmt.setString(1, objectID);
            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Delete From MEDICINEDELIVERYSR Table Using SR ID: Failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void updateValue(MedicineDeliverySR object) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "UPDATE MEDICINEDELIVERYSR SET MEDICINEID = ?, patientID = ?  WHERE srID = ?");

            pstmt.setString(1, object.getMedicineID());
            pstmt.setString(2, object.getPatientID());
            pstmt.setString(3, object.getSrID());

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Update MEDICINEDELIVERYSR Node: Failed!");
            e.printStackTrace();
            return;
        }
    }

    @Override
    public MedicineDeliverySR getValue(String objectID) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        MedicineDeliverySR medicineDeliverySR = new MedicineDeliverySR();
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT * FROM MEDICINEDELIVERYSR WHERE srID = ?");
            pstmt.setString(1, objectID);
            ResultSet rset = pstmt.executeQuery();

            rset.next();

            String medicineID = rset.getString("medicineID");
            String patientID = rset.getString("patientID");

            MainSRDaoI mainSRDaoI = new MainSRDaoI();
            AbstractSR mainSR = mainSRDaoI.getValue(objectID);

            String status = mainSR.getStatus();
            Location location = mainSR.getLocation();
            Employee requestor = mainSR.getRequestor();
            Employee assignedEmployee = mainSR.getAssignedEmployee();
            LocalDate dateRequested = mainSR.getDateRequested();
            String notes = mainSR.getNotes();

            medicineDeliverySR = new MedicineDeliverySR(objectID, status, location, requestor, assignedEmployee, dateRequested, notes, medicineID, patientID);

        } catch (SQLException e) {
            System.out.println("Get MEDICINEDELIVERYSR Node Failed");
            e.printStackTrace();
        }
        return medicineDeliverySR;
    }

    @Override
    public List<MedicineDeliverySR> getAllValues() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<MedicineDeliverySR> medicineDeliverySRList = new ArrayList<>();
        try{
            PreparedStatement pstmt =
                    conn.prepareStatement("SELECT SRID FROM MEDICINEDELIVERYSR ");
            ResultSet rset = pstmt.executeQuery();


            while(rset.next()){
                medicineDeliverySRList.add(getValue(rset.getString("SRID")));
            }
        } catch (SQLException e) {
            System.out.println("Get MEDICINEDELIVERYSR Node Failed");
            e.printStackTrace();
        }
        return medicineDeliverySRList;
    }


    @Override
    public void createTable() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rset = dbmd.getTables(null, null, "MEDICINEDELIVERYSR", null);

            if (rset.next() && rset.getString(3).equals("MEDICINEDELIVERYSR")){
                // table exists
            } else {
                // Create table
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE MEDICINEDELIVERYSR ( "
                        + "srID VARCHAR(50) , "
                        + "medicineID VARCHAR(50), "
                        + "patientID VARCHAR(50), "
                        + "PRIMARY KEY (srID),"
                        + "CONSTRAINT FK_MEDICINEDELIVERYSR_MainSR FOREIGN KEY (srID) REFERENCES MainSR (srID) )");
            }
        } catch (SQLException e) {
            System.out.println("Create MEDICINEDELIVERYSR Table: Failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE MEDICINEDELIVERYSR");
        } catch (SQLException e) {
            System.out.println("Drop MEDICINEDELIVERYSR Table: Failed!");
        }

    }

    @Override
    public void restoreTable(List<MedicineDeliverySR> list) {

        createTable();

        for (MedicineDeliverySR medicineDeliverySR : list) {
            addValue(medicineDeliverySR);
        }

    }
}
