import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

public class Register {

    private JFrame frame;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JComboBox<String> comboBoxRole;

    private Connection con;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register window = new Register();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Register() {
        initialize();
        connection();
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error in SQL make sure you have added mysql-java-connector");

        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Register to ONLINE VOTTING SYSTEM");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        lblTitle.setBounds(45, 23, 360, 29);
        frame.getContentPane().add(lblTitle);

        JLabel lblNewLabel = new JLabel("Register Form");
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        lblNewLabel.setBounds(170, 60, 110, 18);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblUsername.setBounds(59, 100, 87, 23);
        frame.getContentPane().add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(145, 100, 214, 23);
        frame.getContentPane().add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblEmail.setBounds(59, 140, 87, 23);
        frame.getContentPane().add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(145, 140, 214, 23);
        frame.getContentPane().add(textFieldEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblPassword.setBounds(59, 180, 87, 23);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(145, 180, 214, 23);
        frame.getContentPane().add(passwordField);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblRole.setBounds(59, 220, 87, 23);
        frame.getContentPane().add(lblRole);

        comboBoxRole = new JComboBox<>();
        comboBoxRole.addItem("Admin");
        comboBoxRole.addItem("User");
        comboBoxRole.setBounds(145, 220, 214, 23);
        frame.getContentPane().add(comboBoxRole);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String email = textFieldEmail.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) comboBoxRole.getSelectedItem();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields");
                    return;
                }

                try {
                    PreparedStatement pst = con.prepareStatement("INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)");
                    pst.setString(1, username);
                    pst.setString(2, email);
                    pst.setString(3, password);
                    pst.setString(4, role);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Registration successful");
                        LogIn jobSeekersWindow = new LogIn();
                        jobSeekersWindow.frame.setVisible(true);
                        frame.dispose(); // Close the login window



                        // Clear fields after successful registration
                        textFieldUsername.setText("");
                        textFieldEmail.setText("");
                        passwordField.setText("");
                        comboBoxRole.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration failed");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error occurred while registering");
                }
            }
        });
        btnRegister.setBounds(156, 270, 89, 23);
        frame.getContentPane().add(btnRegister);
    }
}
