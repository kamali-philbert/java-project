package view1;

import model1.Doctor;
import dao1.DoctorDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DoctorForm extends JFrame {
    private JTextField txtDoctorId, txtName, txtEmail, txtSpecialty, txtStatus;
    private JTable table;
    private DefaultTableModel tableModel;
    private DoctorDao doctorDao = new DoctorDao();

    public DoctorForm() {
        setTitle("👩‍⚕️ Doctor Management");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 153, 102), 0, getHeight(), new Color(255, 94, 98)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("👩‍⚕️ Doctor Management");
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        lblTitle.setForeground(Color.green);
        lblTitle.setBounds(250, 10, 450, 40);
        panel.add(lblTitle);

        // Labels for input fields
        JLabel lblDoctorId = new JLabel("🆔 ID:");
        JLabel lblName = new JLabel("👩‍⚕️ Name:");
        JLabel lblEmail = new JLabel("✉️ Email:");
        JLabel lblSpecialty = new JLabel("💼 Specialty:");
        JLabel lblStatus = new JLabel("🔴 Availability:");

        JLabel[] labels = {lblDoctorId, lblName, lblEmail, lblSpecialty, lblStatus};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            lbl.setForeground(Color.green);
        }

        txtDoctorId = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtSpecialty = new JTextField();
        txtStatus = new JTextField();

        int x1 = 30, x2 = 160, w = 160, h = 25;
        lblDoctorId.setBounds(x1, 70, 120, h);
        txtDoctorId.setBounds(x2, 70, w, h);
        lblName.setBounds(x1, 110, 120, h);
        txtName.setBounds(x2, 110, w, h);
        lblEmail.setBounds(x1, 150, 120, h);
        txtEmail.setBounds(x2, 150, w, h);
        lblSpecialty.setBounds(x1, 190, 120, h);
        txtSpecialty.setBounds(x2, 190, w, h);
        lblStatus.setBounds(x1, 230, 120, h);
        txtStatus.setBounds(x2, 230, w, h);

        panel.add(lblDoctorId); panel.add(txtDoctorId);
        panel.add(lblName); panel.add(txtName);
        panel.add(lblEmail); panel.add(txtEmail);
        panel.add(lblSpecialty); panel.add(txtSpecialty);
        panel.add(lblStatus); panel.add(txtStatus);

        // Buttons
        JButton btnAdd = new JButton("➕ Add");
        JButton btnUpdate = new JButton("✏️ Update");
        JButton btnDelete = new JButton("🗑️ Delete");
        JButton btnClear = new JButton("🧹 Clear");
        JButton btnBack = new JButton("⬅️ Back to Home");

        JButton[] buttons = {btnAdd, btnUpdate, btnDelete, btnClear, btnBack};
        int bx = 350, by = 70, bw = 140, bh = 30, gap = 40;

        for (int i = 0; i < buttons.length; i++) {
            JButton b = buttons[i];
            b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
            b.setBackground(new Color(0, 123, 255));
            b.setForeground(Color.white);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setFocusPainted(false);
            b.setBounds(bx + (i % 3) * 160, by + (i / 3) * gap, bw, bh);
            panel.add(b);
        }

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Specialty", "Availability"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 290, 780, 180);
        panel.add(scrollPane);

        loadDoctors();

        // Action Listeners
        btnAdd.addActionListener(e -> addDoctor());
        btnUpdate.addActionListener(e -> updateDoctor());
        btnDelete.addActionListener(e -> deleteDoctor());
        btnClear.addActionListener(e -> clearForm());
        btnBack.addActionListener(e -> backToHome());

        // Table row selection
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtDoctorId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtEmail.setText(tableModel.getValueAt(row, 2).toString());
                txtSpecialty.setText(tableModel.getValueAt(row, 3).toString());
                txtStatus.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        add(panel);
    }

    private void loadDoctors() {
        tableModel.setRowCount(0);
        List<Doctor> doctors = doctorDao.getAllDoctors();
        for (Doctor d : doctors) {
            tableModel.addRow(new Object[]{
                    d.getDoctorId(),
                    d.getName(),
                    d.getEmail(),
                    d.getSpecialization(),
                    d.getAvailabilityStatus()
            });
        }
    }

    private void addDoctor() {
        if (!isValidInput()) return;
        try {
            Doctor doctor = new Doctor(
                    0,
                    txtName.getText(),
                    txtEmail.getText(),
                    txtSpecialty.getText(),
                    txtStatus.getText()
            );
            boolean result = doctorDao.addDoctor(doctor);
            if (result) {
                JOptionPane.showMessageDialog(this, "✅ Doctor added!");
                loadDoctors();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Could not add doctor.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void updateDoctor() {
        if (!isValidInput()) return;
        try {
            Doctor doctor = new Doctor(
                    Integer.parseInt(txtDoctorId.getText()),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtSpecialty.getText(),
                    txtStatus.getText()
            );
            boolean result = doctorDao.updateDoctor(doctor);
            if (result) {
                JOptionPane.showMessageDialog(this, "✅ Doctor updated!");
                loadDoctors();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void deleteDoctor() {
        try {
            int id = Integer.parseInt(txtDoctorId.getText());
            boolean result = doctorDao.deleteDoctor(id);
            if (result) {
                JOptionPane.showMessageDialog(this, "🗑️ Doctor deleted.");
                loadDoctors();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Could not delete doctor.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid ID.");
        }
    }

    private void clearForm() {
        txtDoctorId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtSpecialty.setText("");
        txtStatus.setText("");
    }

    private void backToHome() {
        new HomePage().setVisible(true);
        dispose();
    }

    private boolean isValidInput() {
        if (txtDoctorId.getText().trim().isEmpty() || !txtDoctorId.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a valid Doctor ID (only numbers).");
            return false;
        }
        if (txtName.getText().trim().isEmpty() || !txtName.getText().matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a valid name.");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a valid email address.");
            return false;
        }
        if (txtSpecialty.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a valid specialty.");
            return false;
        }
        if (txtStatus.getText().trim().isEmpty() || !txtStatus.getText().matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a valid status.");
            return false;
        }
        return true;
    }

  // public static void main(String[] args) {
   //    SwingUtilities.invokeLater(() -> new DoctorForm().setVisible(true));
  // }
}
