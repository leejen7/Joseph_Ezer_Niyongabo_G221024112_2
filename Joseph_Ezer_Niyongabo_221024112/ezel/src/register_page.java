 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import mikeTest.LogIn;

public class register_page {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					register_page window = new register_page();
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
	public register_page() {
		initialize();
		connection();
	}
 Connection con;
 PreparedStatement pst;
 
 void connection() {
	 try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		 con =  DriverManager.getConnection("jdbc:mysql://localhost/votting_system","root", "");
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
		frame.getContentPane().setFont(new Font("Verdana", Font.BOLD, 21));
		frame.setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 883, 694);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTER");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 22));
		lblNewLabel.setBounds(327, 11, 202, 64);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("FULL NAME:");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 22));
		lblNewLabel_1.setBounds(62, 105, 202, 64);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.BOLD, 21));
		textField.setBounds(327, 100, 228, 69);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		

		JLabel lblNewLabel_2 = new JLabel("USER_IDs:");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 22));
		lblNewLabel_2.setBounds(62, 217, 202, 64);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.BOLD, 21));
		textField_1.setColumns(10);
		textField_1.setBounds(327, 212, 228, 69);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_3 = new JLabel("PASSWORD:");
		lblNewLabel_3.setForeground(new Color(0, 0, 0));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Verdana", Font.BOLD, 22));
		lblNewLabel_3.setBounds(62, 330, 202, 64);
		frame.getContentPane().add(lblNewLabel_3);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Verdana", Font.BOLD, 21));
		passwordField.setBounds(327, 326, 228, 64);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_4 = new JLabel("USER TYPE:");
		lblNewLabel_4.setForeground(new Color(0, 0, 0));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Verdana", Font.BOLD, 22));
		lblNewLabel_4.setBounds(62, 439, 202, 64);
		frame.getContentPane().add(lblNewLabel_4);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Verdana", Font.BOLD, 21));
		comboBox.setBounds(327, 439, 228, 60);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("SELECT");
		comboBox.addItem("Admin");
		comboBox.addItem("Users");
		
		JButton btnNewButton = new JButton("SIGN UP");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String fullName = textField.getText();
		        String email = textField_1.getText();
		        String password = passwordField.getText();
		        String userType = (String) comboBox.getSelectedItem();

		        try {
		            // Check if user_id already exists
		            PreparedStatement checkUserStmt = con.prepareStatement("SELECT user_id FROM users WHERE user_id = ?");
		            checkUserStmt.setString(1, textField_1.getText());
		            ResultSet userResult = checkUserStmt.executeQuery();
		            if (userResult.next()) {
		                JOptionPane.showMessageDialog(null, "User ID already exists.");
		                return; // Exit the method if user ID already exists
		            }

		            // Check if admin exists
		            if (userType.equals("Admin")) {
		                PreparedStatement checkAdminStmt = con.prepareStatement("SELECT user_id FROM users WHERE role = 'Admin'");
		                ResultSet adminResult = checkAdminStmt.executeQuery();
		                if (adminResult.next()) {
		                    JOptionPane.showMessageDialog(null, "An admin already exists(only one admin is allowed.");
		                    return; // Exit the method if admin exists
		                }
		            }

		            // Insert the new user or admin
		            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)");
		            insertStmt.setString(1, fullName);
		            insertStmt.setString(2, password);
		            insertStmt.setString(3, email);
		            insertStmt.setString(4, userType);
		            insertStmt.executeUpdate();
		            JOptionPane.showMessageDialog(null, "You are now registered. You can log in.");
		            frame.dispose();
		            LogIn loginWindow = new LogIn();
		            loginWindow.frame.setVisible(true);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		btnNewButton.setBackground(new Color(0, 128, 192));
		btnNewButton.setForeground(new Color(0, 0, 0));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 22));
		btnNewButton.setBounds(648, 100, 159, 52);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("Already have an account?");
		lblNewLabel_5.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(626, 246, 216, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JButton btnLogIn = new JButton("LOG IN");
		
		   btnLogIn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Create an instance of the Login class
	            	register_page loginWindow = new register_page();
	                loginWindow.frame.setVisible(true); // Make the login window visible
	            }
	        });
		
		
		btnLogIn.setForeground(Color.BLACK);
		btnLogIn.setFont(new Font("Verdana", Font.BOLD, 22));
		btnLogIn.setBackground(new Color(0, 128, 192));
		btnLogIn.setBounds(648, 356, 159, 52);
		frame.getContentPane().add(btnLogIn);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(64, 128, 128));
		panel.setBounds(0, 0, 869, 77);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setForeground(new Color(0, 0, 0));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 77, 869, 580);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		

		
	
		
	
	}
}
