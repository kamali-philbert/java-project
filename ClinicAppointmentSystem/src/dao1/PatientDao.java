package dao1;

import model1.Patient;

import java.sql.*;
import java.util.*;

public class PatientDao {

    // Method to add a patient
    public int addPatient(Patient patient) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO patients (patient_id,name, email, phone, gender) VALUES (?, ?, ?, ?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,patient.getPatientId());
            pst.setString(2, patient.getName());
            pst.setString(3, patient.getEmail());
            pst.setString(4, patient.getPhone());
            pst.setString(5, patient.getGender());

            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method to retrieve all patients
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM patients";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("gender")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method to delete a patient by ID
    public boolean deletePatient(int patientId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM patients WHERE patient_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, patientId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update patient information
    public boolean updatePatient(Patient patient) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE patients SET name = ?, email = ?, phone = ?, gender = ? WHERE patient_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, patient.getName());
            pst.setString(2, patient.getEmail());
            pst.setString(3, patient.getPhone());
            pst.setString(4, patient.getGender());
            pst.setInt(5, patient.getPatientId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
