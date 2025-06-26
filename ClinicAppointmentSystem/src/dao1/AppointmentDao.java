package dao1;

import java.sql.*;
import java.util.*;
import model1.Appointment;

public class AppointmentDao {

    public int bookAppointment(Appointment appointment) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO appointments (Appointment_id,patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, ?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, appointment.getAppointmentId());
            pst.setInt(2, appointment.getPatientId());
            pst.setInt(3, appointment.getDoctorId());
            pst.setDate(4, appointment.getAppointmentDate());
            pst.setString(5, appointment.getStatus());

            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM appointments";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getDate("appointment_date"),
                    rs.getString("status")
                );
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean cancelAppointment(int appointmentId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM appointments WHERE appointment_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, appointmentId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAppointment(Appointment appointment) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE appointments SET Appointment_id = ?,patient_id = ?, doctor_id = ?, appointment_date = ?, status = ? WHERE appointment_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, appointment.getAppointmentId());
            pst.setInt(2, appointment.getPatientId());
            pst.setInt(3, appointment.getDoctorId());
            pst.setDate(4, appointment.getAppointmentDate());
            pst.setString(5, appointment.getStatus());
            pst.setInt(6, appointment.getAppointmentId());

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAppointment(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
