package mikeTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class admin_page {

    public JFrame frame;
    private JButton btnProfile;
    private JButton btnViewProposal;
    private JButton btnLogout;
    private JButton btnVote;
    private JButton btnViewCandidates;
    private JButton btnViewUpcomingElections;
    private JButton btnChangePassword;
    private JButton btnViewPastElections;
    private JButton btnFeedback;

    private JPanel tablePanel;
    private JPanel formPanel;
    private JLabel lblWelcome;

    private JTable table1;
    private JTable table12;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	admin_page window = new admin_page();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public admin_page() {
        initialize();
        connection();
        table1 = new JTable();

    }
    Connection con;
    PreparedStatement pst;
    private void showDataTable1() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM feedback");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("id"));
                v2.add(Rs.getString("names"));
                v2.add(Rs.getString("messages"));
                v2.add(Rs.getString("date_"));
                
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showDataTable2() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM users");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table12.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("user_id"));
                v2.add(Rs.getString("username"));
                v2.add(Rs.getString("email"));
                v2.add(Rs.getString("role"));
                
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    void connection() {
   	 try {
   		Class.forName("com.mysql.cj.jdbc.Driver");
   		 con =  DriverManager.getConnection("jdbc:mysql://localhost/votting_system","root", "");
            System.out.print("Connected well");
   	} catch (ClassNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	} catch (SQLException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	 
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("You are Welcome to, ONLINE VOTTING SYSTEM");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(64, 128, 128));
        sidebarPanel.setBounds(0, 50, 200, 700);
        frame.getContentPane().add(sidebarPanel);
        sidebarPanel.setLayout(null);

        JLabel lblHomepage = new JLabel("HOMEPAGE");
        lblHomepage.setForeground(Color.WHITE);
        lblHomepage.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomepage.setFont(new Font("Verdana", Font.BOLD, 19));
        lblHomepage.setBounds(0, 0, 200, 28);
        sidebarPanel.add(lblHomepage);

        btnProfile = new JButton("PROFILE");
        btnProfile.setFont(new Font("Verdana", Font.BOLD, 14));
        btnProfile.setBounds(0, 50, 200, 40);
        sidebarPanel.add(btnProfile);

        btnViewProposal = new JButton("Add New Annoucements");
        btnViewProposal.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewProposal.setBounds(0, 100, 200, 40);
        sidebarPanel.add(btnViewProposal);

        btnVote = new JButton("Candidates");
        btnVote.setFont(new Font("Verdana", Font.BOLD, 14));
        btnVote.setBounds(0, 150, 200, 40);
        sidebarPanel.add(btnVote);

        btnViewCandidates = new JButton("New Events");
        btnViewCandidates.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewCandidates.setBounds(0, 200, 200, 40);
        sidebarPanel.add(btnViewCandidates);

        btnViewUpcomingElections = new JButton("Elections");
        btnViewUpcomingElections.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewUpcomingElections.setBounds(0, 250, 200, 40);
        sidebarPanel.add(btnViewUpcomingElections);

        btnChangePassword = new JButton("Change password");
        btnChangePassword.setFont(new Font("Verdana", Font.BOLD, 14));
        btnChangePassword.setBounds(0, 300, 200, 40);
        sidebarPanel.add(btnChangePassword);

        btnViewPastElections = new JButton("Users");
        btnViewPastElections.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewPastElections.setBounds(0, 350, 200, 40);
        sidebarPanel.add(btnViewPastElections);

        btnFeedback = new JButton("Feedback/Contact Admin");
        btnFeedback.setFont(new Font("Verdana", Font.BOLD, 14));
        btnFeedback.setBounds(0, 400, 200, 40);
        sidebarPanel.add(btnFeedback);

        tablePanel = new JPanel();
        tablePanel.setBounds(200, 50, 850, 700);
        frame.getContentPane().add(tablePanel);
        tablePanel.setLayout(null);

        formPanel = new JPanel();
        formPanel.setBounds(200, 50, 850, 700);
        frame.getContentPane().add(formPanel);
        formPanel.setLayout(null);

        btnViewProposal.addActionListener(new ActionListener() {
            
            
            public void actionPerformed(ActionEvent e) {
                // Hide form panel
                formPanel.setVisible(false);
                // Show table panel
                tablePanel.setVisible(true);
                // Show the appropriate table (table1, table2, etc.) based on your requirement
                addAnnouncement();
            }
                });

                btnVote.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        candidate jobSeekersWindow = new candidate();
                        jobSeekersWindow.frame.setVisible(true);
                        frame.dispose(); // Close the login window
                    }
                });
                btnViewCandidates.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        candidate jobSeekersWindow = new candidate();
                        jobSeekersWindow.frame.setVisible(true);
                        frame.dispose(); // Close the login window

                    }
                });
                btnViewUpcomingElections.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                            // Hide form panel
                            elections jobSeekersWindow = new elections();
                            jobSeekersWindow.frame.setVisible(true);
                            frame.dispose(); // Close the login window

                                           }
                });
                btnChangePassword.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showChangePasswordForm();
                    }
                });
                btnViewPastElections.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showVoteTable4();
                    }
                });
                btnFeedback.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showVoteTable2();
                    }
                });

                // Initially show the welcome message with the default username
                showWelcomeMessage("Username");
            }

            private void showWelcomeMessage(String username) {
                lblWelcome.setText("You are Welcome to, ONLINE VOTTING SYSTEM");
            }
            private void addAnnouncement() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create a label for the announcement message
                JLabel lblAnnouncementMessage = new JLabel("Announcement Message:");
                lblAnnouncementMessage.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblAnnouncementMessage.setBounds(50, 50, 200, 25);

                // Create a text area for the announcement message
                JTextArea txtAnnouncementMessage = new JTextArea();
                txtAnnouncementMessage.setBounds(50, 80, 300, 150);

                // Create a button to submit the announcement
                JButton btnSubmitAnnouncement = new JButton("Submit");
                btnSubmitAnnouncement.setFont(new Font("Verdana", Font.BOLD, 14));
                btnSubmitAnnouncement.setBounds(150, 250, 100, 30);

                // Add action listener to the submit button
                btnSubmitAnnouncement.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Retrieve the announcement message from the text area
                        String announcementMessage = txtAnnouncementMessage.getText();

                        try {
                            // Add the announcement to the database
                            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO annoucement (annoucement_message) VALUES (?)");
                            insertStmt.setString(1, announcementMessage);
                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Announcement added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error while adding announcement", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Add components to the form panel
                formPanel.add(lblAnnouncementMessage);
                formPanel.add(txtAnnouncementMessage);
                formPanel.add(btnSubmitAnnouncement);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true); // Make sure to set visibility to true
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }

            

            // Method to show the vote table
            private void showVoteTable() {
                // Sample data for the table
                Object[][] data = {
                        {null, null},
                        {null, null},
                };
                // Column headers
                String[] columnHeaders = {"Candidate", "Party"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Create the table
                JTable table2 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table2);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 20, 850, 680);

                // Add a title label above the table
                JLabel titleLabel = new JLabel("Candidates for Voting");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 20);

                // Clear any existing components in the table panel
                tablePanel.removeAll();

                // Add the title label and the scroll pane to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);
                showDataTable1();
                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }

           private void showVoteTable2() {
                // Sample data for the table
                Object[][] data = {
                        {null, null, null},
                };
                // Column headers
                String[] columnHeaders = {"ID", "Name", "Message", "date"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Initialize the member variable instead of re-declaring it
                table1 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table1);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 80, 850, 560);
                showDataTable1();

                // Add a mouse listener to the table
                


                // Add a title label above the table
                JLabel titleLabel = new JLabel("User's feedback");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 40);

                // Create buttons for different actions
     


                // Clear any existing components in the table panel
                //  tablePanel.removeAll();

                // Add components to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }

 
            private void showVoteTable4() {
                // Sample data for the table
                Object[][] data = {
                        {null, null, null,null},
                };
                // Column headers
                String[] columnHeaders = {"ID", "User Name", "Email", "Role"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Initialize the member variable instead of re-declaring it
                table12 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table12);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 80, 850, 560);
                showDataTable2();

                // Add a mouse listener to the table
                


                // Add a title label above the table
                JLabel titleLabel = new JLabel("Users");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 40);

                // Create buttons for different actions
     


                // Clear any existing components in the table panel
                //  tablePanel.removeAll();

                // Add components to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }
            private void feedBackForm() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create labels for current password and new password
                JLabel lblCurrentPassword = new JLabel("Current Password:");
                lblCurrentPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblCurrentPassword.setBounds(50, 50, 150, 25);

                JLabel lblNewPassword = new JLabel("New Password:");
                lblNewPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblNewPassword.setBounds(50, 100, 150, 25);

                // Create text fields for current password and new password
                JTextField txtCurrentPassword = new JTextField();
                txtCurrentPassword.setBounds(200, 50, 200, 25);

                JTextField txtNewPassword = new JTextField();
                txtNewPassword.setBounds(200, 100, 200, 25);

                // Create an update button
                JButton btnUpdate = new JButton("Update");
                btnUpdate.setFont(new Font("Verdana", Font.BOLD, 14));
                btnUpdate.setBounds(200, 150, 100, 30);

                // Add action listener to the update button
                btnUpdate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Perform action to update the password
                        String currentPassword = txtCurrentPassword.getText();
                        String newPassword = txtNewPassword.getText();

                        // Add code here to update the password in the database or perform any other action

                        // Display a message indicating success or failure
                        JOptionPane.showMessageDialog(frame, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                // Add components to the form panel
                formPanel.add(lblCurrentPassword);
                formPanel.add(lblNewPassword);
                formPanel.add(txtCurrentPassword);
                formPanel.add(txtNewPassword);
                formPanel.add(btnUpdate);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }
            private void showChangePasswordForm() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create labels for current password, new password, and confirm password
                JLabel lblCurrentPassword = new JLabel("Current Password:");
                lblCurrentPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblCurrentPassword.setBounds(50, 50, 150, 25);

                JLabel lblNewPassword = new JLabel("New Password:");
                lblNewPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblNewPassword.setBounds(50, 100, 150, 25);

                JLabel lblConfirmPassword = new JLabel("Confirm Password:");
                lblConfirmPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblConfirmPassword.setBounds(50, 150, 150, 25);

                // Create text fields for current password, new password, and confirm password
                JPasswordField txtCurrentPassword = new JPasswordField();
                txtCurrentPassword.setBounds(200, 50, 200, 25);

                JPasswordField txtNewPassword = new JPasswordField();
                txtNewPassword.setBounds(200, 100, 200, 25);

                JPasswordField txtConfirmPassword = new JPasswordField();
                txtConfirmPassword.setBounds(200, 150, 200, 25);

                // Create an update button
                JButton btnUpdate = new JButton("Update");
                btnUpdate.setFont(new Font("Verdana", Font.BOLD, 14));
                btnUpdate.setBounds(200, 200, 100, 30);

                // Add action listener to the update button
                btnUpdate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Get the text from the password fields
                        String currentPassword = new String(txtCurrentPassword.getPassword());
                        String newPassword = new String(txtNewPassword.getPassword());
                        String confirmPassword = new String(txtConfirmPassword.getPassword());

                        // Check if new password and confirm password match
                        if (!newPassword.equals(confirmPassword)) {
                            // If not equal, show an error message
                            JOptionPane.showMessageDialog(frame, "New password and confirm password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Add code here to update the password in the database or perform any other action
                            // Display a message indicating success or failure
                            JOptionPane.showMessageDialog(frame, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                // Add components to the form panel
                formPanel.add(lblCurrentPassword);
                formPanel.add(lblNewPassword);
                formPanel.add(lblConfirmPassword);
                formPanel.add(txtCurrentPassword);
                formPanel.add(txtNewPassword);
                formPanel.add(txtConfirmPassword);
                formPanel.add(btnUpdate);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }

        }
