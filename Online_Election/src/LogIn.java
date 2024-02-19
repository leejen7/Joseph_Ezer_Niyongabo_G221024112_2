import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LogIn {

    public JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JComboBox<String> comboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LogIn window = new LogIn();
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
    public LogIn() {
        initialize();
        connection();
    }

    Connection con;
    PreparedStatement pst;

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected succesfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 883, 694);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204)); // Set header background color
        headerPanel.setBounds(0, 0, 883, 77);
        frame.getContentPane().add(headerPanel);
        headerPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Welcome to Online Voting System");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE); // Set text color
        lblTitle.setBounds(145, 13, 593, 51);
        headerPanel.add(lblTitle);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        contentPanel.setBackground(Color.WHITE); // Set content background color
        contentPanel.setBounds(0, 77, 883, 617);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Please enter your information to login");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        lblNewLabel.setBounds(184, 11, 457, 64);
        contentPanel.add(lblNewLabel);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Verdana", Font.BOLD, 22));
        lblEmail.setBounds(71, 156, 202, 64);
        contentPanel.add(lblEmail);

        textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.BOLD, 21));
        textField.setBounds(327, 156, 202, 64);
        contentPanel.add(textField);
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("Verdana", Font.BOLD, 22));
        lblPassword.setBounds(71, 305, 202, 64);
        contentPanel.add(lblPassword);

        JLabel lblUserType = new JLabel("User Type:");
        lblUserType.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserType.setFont(new Font("Verdana", Font.BOLD, 22));
        lblUserType.setBounds(71, 450, 202, 64);
        contentPanel.add(lblUserType);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Verdana", Font.BOLD, 21));
        passwordField.setBounds(327, 310, 202, 59);
        contentPanel.add(passwordField);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Verdana", Font.BOLD, 21));
        comboBox.setBounds(327, 450, 202, 64);
        comboBox.addItem("SELECT");
        comboBox.addItem("Admin");
        comboBox.addItem("User");
        contentPanel.add(comboBox);

        JButton loginButton = new JButton("LOG IN");
        loginButton.setFont(new Font("Verdana", Font.BOLD, 21));
        loginButton.setBounds(659, 312, 144, 50);
        contentPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textField.getText();
                String password = new String(passwordField.getPassword());
                String userType = (String) comboBox.getSelectedItem();

                // Check if the email and password fields are not empty
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your email and password");
                    return;
                }

                try {
                    // Query the database to check if the user exists
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
                    pst.setString(1, email);
                    pst.setString(2, password);
                    ResultSet rs = pst.executeQuery();

                    // If user exists, display a message and create a session
                    if (rs.next()) {
                        String dbUserType = rs.getString("role");
                        JOptionPane.showMessageDialog(frame, "Login successful as " + userType);
                        // Create session here
                        // Assuming you have retrieved username, email, and role from the database
                        String usernameFromDatabase = rs.getString("username");
                        String roleFromDatabase = rs.getString("role");

                        // Set the user object in the session
             //           session_user_data loggedInUser = new session_user_data(usernameFromDatabase, email, roleFromDatabase);
             //           Session.setUser(loggedInUser);

                        // Navigate to appropriate home page
                        if (userType.equals("User")) {
                            userHome userWindow = new userHome();
                            userWindow.frame.setVisible(true);
                            frame.dispose(); // Close the login window
                        } else if (userType.equals("Admin")) {
                        	admin_page adminWindow = new admin_page();
                            adminWindow.frame.setVisible(true);
                            frame.dispose(); // Close the login window
                        }

                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid email or password");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error occurred while logging in");
                }
            }
        });
    }
}
