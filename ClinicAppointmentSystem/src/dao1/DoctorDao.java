package dao1;

import model1.Doctor;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {

    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (name, email, specialization, availability_status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getEmail());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setString(4, doctor.getAvailabilityStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctors SET name = ?, email = ?, specialization = ?, availability_status = ? WHERE doctor_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getEmail());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setString(4, doctor.getAvailabilityStatus());
            stmt.setInt(5, doctor.getDoctorId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDoctor(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("specialization"),
                    rs.getString("availability_status")
                );
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
}
