package kadi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class notification extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblMessage;
    private JTextField txtMessage;
    private JButton btnBack;
    private JButton btnAdd;

    Connection con;
    PreparedStatement pst;

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/skolbmp_db", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public notification() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600); // Larger frame size
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        topBarPanel.setBounds(0, 0, 1000, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("GIVE NOTIFICATION");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(900, 10, 80, 30); // Adjust bounds as needed
        btnBack.setBackground(Color.GRAY); // Adjust background color as needed
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	skolAdmin sales_page_ = new skolAdmin();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("NOTIFICATION INFORMATION");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblMessage = new JLabel("Message:");
        lblMessage.setBounds(10, 40, 150, 20);
        formPanel.add(lblMessage);

        txtMessage = new JTextField();
        txtMessage.setBounds(170, 40, 200, 20);
        formPanel.add(txtMessage);
        txtMessage.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 70, 80, 30);
        btnAdd.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve feedback message from the form
                String message = txtMessage.getText();
                try {
                    // Insert the feedback into the database
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO feedback (message) VALUES (?)");
                    insertStmt.setString(1, message);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Feedback added successfully");
                    // Optionally, you can clear the text field after adding feedback
                    txtMessage.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding feedback", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    notification frame = new notification();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}