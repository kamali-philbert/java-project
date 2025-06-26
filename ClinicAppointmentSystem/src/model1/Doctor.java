package model1;

public class Doctor {
    private int doctorId;
    private String name;
    private String email;
    private String specialization;
    private String availabilityStatus;

    public Doctor(int doctorId, String name, String email, String specialization, String availabilityStatus) {
        this.doctorId = doctorId;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.availabilityStatus = availabilityStatus;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
