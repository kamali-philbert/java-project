package view1;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("🏥 Clinic Appointment Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Modern Hospital-Themed Gradient Background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(221, 245, 255); // Soft lavender-blue
                Color color2 = new Color(198, 255, 221); // Mint green
                Color color3 = new Color(204, 229, 255); // Sky blue tint

                GradientPaint gp1 = new GradientPaint(0, 0, color1, 0, getHeight() / 2, color2);
                GradientPaint gp2 = new GradientPaint(0, getHeight() / 2, color2, 0, getHeight(), color3);

                g2d.setPaint(gp1);
                g2d.fillRect(0, 0, getWidth(), getHeight() / 2);

                g2d.setPaint(gp2);
                g2d.fillRect(0, getHeight() / 2, getWidth(), getHeight());
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("🏥 Welcome to Clinic Appointment Management System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 77, 110)); // Deep muted blue
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        JButton btnPatients = new JButton("👨‍⚕️ Manage Patients");
        JButton btnDoctors = new JButton("🩺 Manage Doctors");
        JButton btnAppointments = new JButton("📅 Manage Appointments");
        JButton btnLogout = new JButton("🔙 Back to Login");

        styleButton(btnPatients);
        styleButton(btnDoctors);
        styleButton(btnAppointments);
        styleButton(btnLogout);

        btnPatients.addActionListener(e -> {
            new PatientForm().setVisible(true);
            dispose();
        });

        btnDoctors.addActionListener(e -> {
            new DoctorForm().setVisible(true);
            dispose();
        });

        btnAppointments.addActionListener(e -> {
            new AppointmentForm().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        panel.add(btnPatients);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnDoctors);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnAppointments);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnLogout);

        add(panel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        button.setBackground(new Color(34, 139, 34)); // Forest Green
        button.setForeground(Color.green);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(280, 45));
        button.setMinimumSize(new Dimension(280, 45));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
