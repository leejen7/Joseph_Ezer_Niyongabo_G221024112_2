package kadi;

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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
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
   		 con =  DriverManager.getConnection("jdbc:mysql://localhost/skolbmp_db","root", "");
            System.out.print("Connected succesfully");
   	} catch (ClassNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	} catch (SQLException e) {
   		// TODO Auto-generated catch block
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

        JLabel lblNewLabel = new JLabel("Please fill your information here");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        lblNewLabel.setBounds(184, 11, 457, 64);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Verdana", Font.BOLD, 22));
        lblEmail.setBounds(71, 156, 202, 64);
        frame.getContentPane().add(lblEmail);

        textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.BOLD, 21));
        textField.setBounds(327, 156, 202, 64);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("Verdana", Font.BOLD, 22));
        lblPassword.setBounds(71, 305, 202, 64);
        frame.getContentPane().add(lblPassword);

        JLabel lblUserType = new JLabel("User Type:");
        lblUserType.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserType.setFont(new Font("Verdana", Font.BOLD, 22));
        lblUserType.setBounds(71, 450, 202, 64);
        frame.getContentPane().add(lblUserType);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Verdana", Font.BOLD, 21));
        passwordField.setBounds(327, 310, 202, 59);
        frame.getContentPane().add(passwordField);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Verdana", Font.BOLD, 21));
        comboBox.setBounds(327, 450, 202, 64);
        comboBox.addItem("SELECT");
        comboBox.addItem("Admin");
        comboBox.addItem("Distributer");
        frame.getContentPane().add(comboBox);

        JButton loginButton = new JButton("LOG IN"); // Declaration as a JButton
        loginButton.setFont(new Font("Verdana", Font.BOLD, 21));
        loginButton.setBounds(659, 312, 144, 50);
        frame.getContentPane().add(loginButton);

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
                             String usernameFromDatabase = rs.getString("names");
                             String roleFromDatabase = rs.getString("role");

                             session_user_data loggedInUser = new session_user_data(usernameFromDatabase, email, roleFromDatabase);
                            // Session.setUser(loggedInUser);
                             // Set the user object in the session
                         //    Session.setUser(loggedInUser);
                             if (userType =="Distributer") {
                            	 user_skol jobSeekersWindow = new user_skol();
                             jobSeekersWindow.frame.setVisible(true);
                             frame.dispose(); // Close the login window
                             }else {
                            	 skolAdmin jobSeekersWindow = new skolAdmin();
                                 jobSeekersWindow.frame.setVisible(true);
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


        JPanel panel = new JPanel();
        panel.setBackground(new Color(64, 128, 128));
        panel.setBounds(0, 0, 869, 77);
        frame.getContentPane().add(panel);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(0, 76, 869, 581);
        frame.getContentPane().add(panel_1);
    }

}

