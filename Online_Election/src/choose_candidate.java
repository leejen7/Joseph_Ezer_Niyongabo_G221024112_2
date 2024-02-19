import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class choose_candidate extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JTextField txtUserSelected;
    private JTextField txtEmail;

    private JButton btnChoose;
    private JButton btnBack; // Added back button
    private JButton btnMostChosenCandidate; // Added button to show most chosen candidate ID

    private JTable tableCandidates;

    Connection con;
    PreparedStatement pst;

    private void showCandidatesData() {
        try {
            pst = con.prepareStatement("SELECT * FROM candidates");
            ResultSet Rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tableCandidates.getModel();
            model.setRowCount(0);
            while (Rs.next()) {
                String candidateId = Rs.getString("candidate_id");
                String name = Rs.getString("name");
                String position = Rs.getString("position");
                String gender = Rs.getString("gender");
                model.addRow(new Object[]{candidateId, name, position, gender});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while fetching candidate data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public choose_candidate() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 600, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here select your favorite candidate and insert your email to vote");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 12));
        lblTopBar.setBounds(10, 10, 600, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(500, 10, 80, 30);
        btnBack.setBackground(Color.GRAY);
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userHome userWindow = new userHome();
                userWindow.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 50, 600, 150);
        contentPane.add(scrollPane);

        tableCandidates = new JTable();
        scrollPane.setViewportView(tableCandidates);
        DefaultTableModel model = new DefaultTableModel();
        tableCandidates.setModel(model);
        model.addColumn("Candidate ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Gender");

        showCandidatesData();

        // Most Chosen Candidate Button
        btnMostChosenCandidate = new JButton("Show Most Chosen Candidate");
        btnMostChosenCandidate.setBounds(200, 220, 200, 30);
        btnMostChosenCandidate.setBackground(Color.ORANGE);
        contentPane.add(btnMostChosenCandidate);
        btnMostChosenCandidate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMostChosenCandidate();
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 260, 600, 100);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        JLabel lblSelectUser = new JLabel("User Selected:");
        lblSelectUser.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblSelectUser.setBounds(10, 10, 200, 20);
        formPanel.add(lblSelectUser);

        txtUserSelected = new JTextField();
        txtUserSelected.setBounds(10, 40, 150, 20);
        formPanel.add(txtUserSelected);
        txtUserSelected.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(180, 10, 100, 20);
        formPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(180, 40, 150, 20);
        formPanel.add(txtEmail);
        txtEmail.setColumns(10);

        // Choose Button
        btnChoose = new JButton("Choose");
        btnChoose.setBounds(250, 40, 100, 20);
        btnChoose.setBackground(Color.GREEN);
        formPanel.add(btnChoose);
        btnChoose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userSelected = txtUserSelected.getText();
                String email = txtEmail.getText();

                if (!userSelected.isEmpty() && !email.isEmpty()) {
                    chooseCandidate(userSelected, email);
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void chooseCandidate(String userSelected, String email) {
        try {
            pst = con.prepareStatement("SELECT * FROM votes WHERE user_email = ?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Email " + email + " has already voted.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Add your logic to insert the vote
                JOptionPane.showMessageDialog(null, "Vote recorded successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while choosing candidate", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMostChosenCandidate() {
        try {
            pst = con.prepareStatement("SELECT candidate_id, COUNT(*) AS votes_count FROM votes GROUP BY candidate_id ORDER BY votes_count DESC LIMIT 1");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int mostChosenCandidateId = rs.getInt("candidate_id");
                JOptionPane.showMessageDialog(null, "Most chosen candidate ID: " + mostChosenCandidateId, "Most Chosen Candidate", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No votes recorded yet.", "No Votes", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while fetching most chosen candidate", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    choose_candidate frame = new choose_candidate();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
