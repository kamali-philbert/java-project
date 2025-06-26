package view1;

import model1.Patient;
import dao1.PatientDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PatientForm extends JFrame {
    private JTextField txtPatientId, txtName, txtEmail, txtPhone, txtGender;
    private JTable table;
    private DefaultTableModel tableModel;
    private PatientDao patientDao = new PatientDao();

    public PatientForm() {
        setTitle("🧑‍⚕️ Patient Management");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(102, 204, 255), 0, getHeight(), new Color(0, 102, 204)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("🧑‍⚕️ Patient Management");
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        lblTitle.setForeground(Color.green);
        lblTitle.setBounds(250, 10, 450, 40);
        panel.add(lblTitle);

        JLabel lblPatientId = new JLabel("🆔 ID:");
        JLabel lblName = new JLabel("🧑 Name:");
        JLabel lblEmail = new JLabel("✉️ Email:");
        JLabel lblPhone = new JLabel("📞 Phone:");
        JLabel lblGender = new JLabel("⚧️ Gender:");

        JLabel[] labels = {lblPatientId, lblName, lblEmail, lblPhone, lblGender};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            lbl.setForeground(Color.WHITE);
        }

        txtPatientId = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtGender = new JTextField();

        int x1 = 30, x2 = 160, w = 160, h = 25;
        lblPatientId.setBounds(x1, 70, 120, h);
        txtPatientId.setBounds(x2, 70, w, h);
        lblName.setBounds(x1, 110, 120, h);
        txtName.setBounds(x2, 110, w, h);
        lblEmail.setBounds(x1, 150, 120, h);
        txtEmail.setBounds(x2, 150, w, h);
        lblPhone.setBounds(x1, 190, 120, h);
        txtPhone.setBounds(x2, 190, w, h);
        lblGender.setBounds(x1, 230, 120, h);
        txtGender.setBounds(x2, 230, w, h);

        panel.add(lblPatientId); panel.add(txtPatientId);
        panel.add(lblName); panel.add(txtName);
        panel.add(lblEmail); panel.add(txtEmail);
        panel.add(lblPhone); panel.add(txtPhone);
        panel.add(lblGender); panel.add(txtGender);

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
            b.setForeground(Color.green);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setFocusPainted(false);
            b.setBounds(bx + (i % 3) * 160, by + (i / 3) * gap, bw, bh);
            panel.add(b);
        }

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Gender"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 290, 780, 180);
        panel.add(scrollPane);

        loadPatients();

        btnAdd.addActionListener(e -> addPatient());
        btnUpdate.addActionListener(e -> updatePatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnClear.addActionListener(e -> clearForm());
        btnBack.addActionListener(e -> backToHome());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtPatientId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtEmail.setText(tableModel.getValueAt(row, 2).toString());
                txtPhone.setText(tableModel.getValueAt(row, 3).toString());
                txtGender.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        add(panel);
    }

    private void loadPatients() {
        tableModel.setRowCount(0);
        List<Patient> patients = patientDao.getAllPatients();
        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getName(),
                    p.getEmail(),
                    p.getPhone(),
                    p.getGender()
            });
        }
    }

    private void addPatient() {
        try {
            Patient patient = new Patient(
                    Integer.parseInt(txtPatientId.getText()),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtGender.getText()
            );
            int result = patientDao.addPatient(patient);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "✅ Patient added!");
                loadPatients();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Could not add patient.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void updatePatient() {
        try {
            Patient patient = new Patient(
                    Integer.parseInt(txtPatientId.getText()),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtGender.getText()
            );
            boolean result = patientDao.updatePatient(patient);
            if (result) {
                JOptionPane.showMessageDialog(this, "✅ Patient updated!");
                loadPatients();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void deletePatient() {
        try {
            int id = Integer.parseInt(txtPatientId.getText());
            boolean result = patientDao.deletePatient(id);
            if (result) {
                JOptionPane.showMessageDialog(this, "🗑️ Patient deleted.");
                loadPatients();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Could not delete patient.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid ID.");
        }
    }

    private void clearForm() {
        txtPatientId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtGender.setText("");
    }

    private void backToHome() {
        
         new HomePage().setVisible(true); 
         dispose();
// Uncomment when HomePage is available
    }

    //public static void main(String[] args) {
     //   SwingUtilities.invokeLater(() -> new PatientForm().setVisible(true));
   // }
}
