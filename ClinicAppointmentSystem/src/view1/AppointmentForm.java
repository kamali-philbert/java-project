package view1;

import model1.Appointment;
import dao1.AppointmentDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class AppointmentForm extends JFrame {

    private JTextField txtAppointmentId, txtPatientId, txtDoctorId, txtDate, txtStatus;
    private JTable table;
    private DefaultTableModel model;
    private AppointmentDao appointmentDao;

    public AppointmentForm() {
        appointmentDao = new AppointmentDao();

        setTitle("📅 Appointment Management");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Gradient Panel (Updated to match PatientForm)
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 153, 102), 0, getHeight(), new Color(255, 94, 98)));
               
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("📅 Appointment Management");
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        lblTitle.setForeground(Color.green);
        lblTitle.setBounds(250, 10, 450, 40);
        panel.add(lblTitle);

        // Labels
        JLabel lblAppointmentId = new JLabel("🆔 Appointment ID:");
        JLabel lblPatientId = new JLabel("👤 Patient ID:");
        JLabel lblDoctorId = new JLabel("🧑‍⚕️ Doctor ID:");
        JLabel lblDate = new JLabel("📅 Date (YYYY-MM-DD):");
        JLabel lblStatus = new JLabel("📌 Status:");

        JLabel[] labels = {lblAppointmentId, lblPatientId, lblDoctorId, lblDate, lblStatus};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            lbl.setForeground(Color.WHITE);
        }

        // Inputs
        txtAppointmentId = new JTextField();
        txtAppointmentId.setEditable(false);
        txtAppointmentId.setBackground(Color.LIGHT_GRAY);

        txtPatientId = new JTextField();
        txtDoctorId = new JTextField();
        txtDate = new JTextField();
        txtStatus = new JTextField();

        int x1 = 30, x2 = 160, w = 160, h = 25;
        lblAppointmentId.setBounds(x1, 70, 160, h);
        txtAppointmentId.setBounds(x2, 70, w, h);
        lblPatientId.setBounds(x1, 110, 160, h);
        txtPatientId.setBounds(x2, 110, w, h);
        lblDoctorId.setBounds(x1, 150, 160, h);
        txtDoctorId.setBounds(x2, 150, w, h);
        lblDate.setBounds(x1, 190, 160, h);
        txtDate.setBounds(x2, 190, w, h);
        lblStatus.setBounds(x1, 230, 160, h);
        txtStatus.setBounds(x2, 230, w, h);

        panel.add(lblAppointmentId); panel.add(txtAppointmentId);
        panel.add(lblPatientId); panel.add(txtPatientId);
        panel.add(lblDoctorId); panel.add(txtDoctorId);
        panel.add(lblDate); panel.add(txtDate);
        panel.add(lblStatus); panel.add(txtStatus);

        // Buttons
        JButton btnBook = new JButton("➕ Book");
        JButton btnUpdate = new JButton("✏️ Update");
        JButton btnCancel = new JButton("🗑️ Cancel");
        JButton btnViewAll = new JButton("👀 View All");
        JButton btnHome = new JButton("⬅️ Back to Home");

        JButton[] buttons = {btnBook, btnUpdate, btnCancel, btnViewAll, btnHome};
        int bx = 350, by = 70, bw = 140, bh = 30, gap = 40;

        for (int i = 0; i < buttons.length; i++) {
            JButton b = buttons[i];
            b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
            b.setBackground(new Color(0, 123, 255));
            b.setForeground(Color.WHITE);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setFocusPainted(false);
            b.setBounds(bx + (i % 3) * 160, by + (i / 3) * gap, bw, bh);
            panel.add(b);
        }

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Patient ID", "Doctor ID", "Date", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 290, 780, 180);
        panel.add(scrollPane);

        // Load appointments
        loadAppointments();

        // Button actions
        btnBook.addActionListener(e -> bookAppointment());
        btnUpdate.addActionListener(e -> updateAppointment());
        btnCancel.addActionListener(e -> cancelAppointment());
        btnViewAll.addActionListener(e -> loadAppointments());
        btnHome.addActionListener(e -> backToHome());

        // Table row click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtAppointmentId.setText(model.getValueAt(row, 0).toString());
                txtPatientId.setText(model.getValueAt(row, 1).toString());
                txtDoctorId.setText(model.getValueAt(row, 2).toString());
                txtDate.setText(model.getValueAt(row, 3).toString());
                txtStatus.setText(model.getValueAt(row, 4).toString());
            }
        });

        // Input validations
        txtPatientId.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        txtDoctorId.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        add(panel);
    }

    private void loadAppointments() {
        model.setRowCount(0);
        List<Appointment> appointments = appointmentDao.getAllAppointments();
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getDoctorId(),
                    a.getAppointmentDate(),
                    a.getStatus()
            });
        }
    }

    private void bookAppointment() {
        try {
            if (txtPatientId.getText().isEmpty() || txtDoctorId.getText().isEmpty() || txtDate.getText().isEmpty() || txtStatus.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill in all fields.");
                return;
            }

            int patientId = Integer.parseInt(txtPatientId.getText());
            int doctorId = Integer.parseInt(txtDoctorId.getText());
            Date date = Date.valueOf(txtDate.getText());
            String status = txtStatus.getText();

            Appointment appointment = new Appointment();
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(date);
            appointment.setStatus(status);

            int result = appointmentDao.bookAppointment(appointment);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "✅ Appointment booked!");
                loadAppointments();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Booking failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid input! Ensure IDs are numbers and date is in YYYY-MM-DD.");
        }
    }

    private void updateAppointment() {
        try {
            if (txtAppointmentId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select an appointment to update.");
                return;
            }

            int id = Integer.parseInt(txtAppointmentId.getText());
            int patientId = Integer.parseInt(txtPatientId.getText());
            int doctorId = Integer.parseInt(txtDoctorId.getText());
            Date date = Date.valueOf(txtDate.getText());
            String status = txtStatus.getText();

            Appointment appointment = new Appointment(id, patientId, doctorId, date, status);
            boolean success = appointmentDao.updateAppointment(appointment);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Updated successfully!");
                loadAppointments();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid input!");
        }
    }

    private void cancelAppointment() {
        try {
            if (txtAppointmentId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select an appointment to cancel.");
                return;
            }

            int id = Integer.parseInt(txtAppointmentId.getText());
            boolean success = appointmentDao.cancelAppointment(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "🗑️ Appointment cancelled!");
                loadAppointments();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Cancellation failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid Appointment ID!");
        }
    }

    private void clearForm() {
        txtAppointmentId.setText("");
        txtPatientId.setText("");
        txtDoctorId.setText("");
        txtDate.setText("");
        txtStatus.setText("");
    }

    private void backToHome() {
        new HomePage().setVisible(true);
        dispose();
    }
}
