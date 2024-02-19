import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class view_candidate extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JButton btnBack;

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

    public view_candidate() {
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

        lblTopBar = new JLabel("HERE YOU CAN VIEW ALL CANDIDATES AVAILABLE IN THIS FEB");
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
                frame.dispose(); // Close the current window
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 50, 600, 350);
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
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    view_candidate frame = new view_candidate();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
