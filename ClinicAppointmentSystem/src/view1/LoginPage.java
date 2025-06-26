package view1;

import util.OTPManager;
import util.SessionManager;
import service0.OTPService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.mail.MessagingException;

public class LoginPage extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JCheckBox chkShowPassword;

    public LoginPage() {
        setTitle("🔐 Login - Clinic Appointment System");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 153, 102), 0, getHeight(), new Color(255, 94, 98)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Header with logo and title
        JLabel lblLogo = new JLabel("🏥", JLabel.CENTER);
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblLogo.setBounds(0, 20, 420, 60);
        panel.add(lblLogo);

        JLabel lblTitle = new JLabel("Clinic Appointment System", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.green);
        lblTitle.setBounds(0, 80, 420, 30);
        panel.add(lblTitle);

        // Username field
        JLabel lblUsername = new JLabel("👤 Username:");
        lblUsername.setBounds(50, 130, 120, 30);
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUsername.setForeground(Color.WHITE);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(170, 130, 180, 30);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(txtUsername);

        // Password field
        JLabel lblPassword = new JLabel("🔑 Password:");
        lblPassword.setBounds(50, 180, 120, 30);
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPassword.setForeground(Color.WHITE);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(170, 180, 180, 30);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(txtPassword);

        // Show password checkbox
        chkShowPassword = new JCheckBox("👁️ Show Password");
        chkShowPassword.setBounds(170, 215, 180, 25);
        chkShowPassword.setForeground(Color.WHITE);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkShowPassword.addActionListener(e -> {
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•');
        });
        panel.add(chkShowPassword);

        // Role selection
        JLabel lblRole = new JLabel("🧑‍💼 Role:");
        lblRole.setBounds(50, 250, 120, 30);
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRole.setForeground(Color.WHITE);
        panel.add(lblRole);

        cmbRole = new JComboBox<>(new String[]{"Admin", "Receptionist"});
        cmbRole.setBounds(170, 250, 180, 30);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(cmbRole);

        // Login button
JButton btnLogin = new JButton("🔓 Login");
btnLogin.setBounds(130, 300, 160, 40);
btnLogin.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
btnLogin.setBackground(new Color(0, 123, 255)); // Better visibility
btnLogin.setForeground(Color.green);
btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
btnLogin.setFocusPainted(false);
btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

// Add hover effect to login button
btnLogin.addMouseListener(new MouseAdapter() {
    public void mouseEntered(MouseEvent e) {
        btnLogin.setBackground(new Color(0, 102, 204)); // Darker on hover
    }
    public void mouseExited(MouseEvent e) {
        btnLogin.setBackground(new Color(0, 123, 255));
    }
});

        panel.add(btnLogin);

        // Forgot password link
        JLabel lblForgot = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        lblForgot.setBounds(145, 350, 150, 20);
        lblForgot.setHorizontalAlignment(JLabel.CENTER);
        lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblForgot.setForeground(Color.WHITE);
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginPage.this,
                        "Please contact the system administrator to reset your password.",
                        "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(lblForgot);

        // Footer
        JLabel lblFooter = new JLabel("© 2025 ClinicCare Systems | Contact administrator for assistance", JLabel.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setBounds(0, 460, 420, 20);
        panel.add(lblFooter);

        // Login action
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String role = cmbRole.getSelectedItem().toString();

            boolean isValidUser = (username.equalsIgnoreCase("philbertkamali5@gmail.com") && password.equals("admin123") && role.equals("Admin"))
                    || (username.equalsIgnoreCase("reception") && password.equals("reception123") && role.equals("Receptionist"));

            if (isValidUser) {
                try {
                    String otp = OTPService.generateOTP();
                    OTPService.sendOTPEmail(username, otp);
                    System.out.println("OTP for " + username + ": " + otp); // For testing

                    OTPVerificationDialog otpDialog = new OTPVerificationDialog(this, username);
                    otpDialog.setVisible(true);

                    if (otpDialog.isVerified()) {
                        SessionManager.startSession(username, role);
                        JOptionPane.showMessageDialog(this, "✅ Login successful with OTP!");
                        new HomePage().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "❌ OTP verification failed", 
                            "Login Failed", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to send OTP: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Invalid username, password, or role!", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new LoginPage().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

class OTPVerificationDialog extends JDialog {
    private JTextField txtOTP;
    private boolean verified = false;
    private String email;

    public OTPVerificationDialog(JFrame parent, String email) {
        super(parent, "OTP Verification", true);
        this.email = email;
        setSize(350, 200);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblMessage = new JLabel("Enter OTP sent to " + email);
        panel.add(lblMessage, BorderLayout.NORTH);
        
        txtOTP = new JTextField(6);
        txtOTP.setFont(new Font("Monospaced", Font.BOLD, 18));
        txtOTP.setHorizontalAlignment(JTextField.CENTER);
        panel.add(txtOTP, BorderLayout.CENTER);
        
        JButton btnVerify = new JButton("Verify");
        btnVerify.addActionListener(e -> {
            if (OTPService.verifyOTP(email, txtOTP.getText())) {
                verified = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid OTP. Please try again.", 
                    "Verification Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnVerify);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    public boolean isVerified() {
        return verified;
    }
}