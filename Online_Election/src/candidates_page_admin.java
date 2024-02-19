import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class candidates_page_admin extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblName;
    private JLabel lblid;
    private JTextField txtName;
    private JTextField idName;
    private JLabel lblGender;
    private JComboBox<String> comboBoxGender;
    private JLabel lblPosition;
    private JComboBox<String> comboBoxPosition;
    private JLabel lblCandidateId;
    private JTextField txtCandidateId;
    private JButton btnBack;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnAdd;
    private JButton btnCheckWinner; // New button added

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM candidates");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("candidate_id"));
                v2.add(Rs.getString("name"));
                v2.add(Rs.getString("gender"));
                v2.add(Rs.getString("position"));
                v2.add(Rs.getString("election_id"));
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public candidates_page_admin() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here you can manage candidates");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(750, 10, 100, 30);
        btnBack.setBackground(Color.BLUE);
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                admin_page admin_ = new admin_page();
                admin_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("Candidate Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblid = new JLabel("Id:");
        lblid.setBounds(10, 40, 100, 20);
        formPanel.add(lblid);

        idName = new JTextField();
        idName.setBounds(120, 40, 150, 20);
        formPanel.add(idName);
        idName.setColumns(10);

        lblName = new JLabel("Name:");
        lblName.setBounds(10, 70, 100, 20);
        formPanel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 70, 150, 20);
        formPanel.add(txtName);
        txtName.setColumns(10);

        lblGender = new JLabel("Gender:");
        lblGender.setBounds(10, 100, 100, 20);
        formPanel.add(lblGender);

        comboBoxGender = new JComboBox<>();
        comboBoxGender.setBounds(120, 100, 150, 20);
        comboBoxGender.addItem("Male");
        comboBoxGender.addItem("Female");
        formPanel.add(comboBoxGender);

        lblPosition = new JLabel("Position:");
        lblPosition.setBounds(10, 130, 100, 20);
        formPanel.add(lblPosition);

        comboBoxPosition = new JComboBox<>();
        comboBoxPosition.setBounds(120, 130, 150, 20);
        comboBoxPosition.addItem("President");
        comboBoxPosition.addItem("Vice President");
        comboBoxPosition.addItem("CEO");
        comboBoxPosition.addItem("Vice CEO");
        comboBoxPosition.addItem("Manager");
        comboBoxPosition.addItem("Chairman");
        comboBoxPosition.addItem("Vice Chairman");
        formPanel.add(comboBoxPosition);

        lblCandidateId = new JLabel("Candidate ID:");
        lblCandidateId.setBounds(10, 160, 100, 20);
        formPanel.add(lblCandidateId);

        txtCandidateId = new JTextField();
        txtCandidateId.setBounds(120, 160, 150, 20);
        formPanel.add(txtCandidateId);
        txtCandidateId.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 190, 80, 30);
        btnAdd.setBackground(Color.BLUE);
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve candidate details from the form
                String name = txtName.getText();
                String gender = (String) comboBoxGender.getSelectedItem();
                String position = (String) comboBoxPosition.getSelectedItem();
                String vicePosition = txtCandidateId.getText();
                try {
                    // Check if user_id already exists
                    PreparedStatement checkUserStmt = con.prepareStatement("SELECT election_id FROM candidates WHERE election_id = ?");
                    checkUserStmt.setString(1, vicePosition);
                    ResultSet userResult = checkUserStmt.executeQuery();
                    if (userResult.next()) {
                        JOptionPane.showMessageDialog(null, "Election ID exists use new one");
                        return; // Exit the method if user ID already exists
                    }

                    // Check if admin exists

                    // Insert the new user or admin
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO candidates (name, position, election_id, gender) VALUES (?, ?, ?, ?)");
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, position);
                    insertStmt.setString(3, vicePosition);
                    insertStmt.setString(4, gender);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Candidate added");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding Candidate", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 190, 80, 30);
        btnDelete.setBackground(Color.RED);
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve candidate ID
                String id = idName.getText();
                try {
                    // Delete candidate
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM candidates WHERE election_id = ?");
                    deleteStmt.setString(1, id);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Candidate deleted");
                        showDataTable1();
                    } else {
                        JOptionPane.showMessageDialog(null, "Candidate with ID " + id + " not found");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting Candidate", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 190, 80, 30);
        btnUpdate.setBackground(Color.GREEN);
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve candidate details from the form
                String name = txtName.getText();
                String id = idName.getText();
                String gender = (String) comboBoxGender.getSelectedItem();
                String position = (String) comboBoxPosition.getSelectedItem();
                String vicePosition = txtCandidateId.getText();
                try {
                    // Check if user_id already exists
                     

                    // Check if admin exists

                    // Update the candidate
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE candidates SET name=?, position=?, election_id=?, gender=? WHERE election_id =?");
                    updateStmt.setString(1, name);
                    updateStmt.setString(2, position);
                    updateStmt.setString(3, vicePosition);
                    updateStmt.setString(4, gender);
                    updateStmt.setString(5, id);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Candidate updated");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating Candidate", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {
                {null, null, null, null,null},
         };
        String[] columnHeaders = {"id","Names", "Gender", "Position", "Election id"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        // Populate form fields with data from the selected row
                        idName.setText((String) table1.getValueAt(selectedRow, 0));
                        txtName.setText((String) table1.getValueAt(selectedRow, 1));
                        comboBoxGender.setSelectedItem((String) table1.getValueAt(selectedRow, 2));
                        comboBoxPosition.setSelectedItem((String) table1.getValueAt(selectedRow, 3));
                        txtCandidateId.setText((String) table1.getValueAt(selectedRow, 4));
                    }
                }
            }
        });

        // Button to check current election winner
        btnCheckWinner = new JButton("Check the Current Election Winner");
        btnCheckWinner.setBounds(500, 50, 300, 30);
        btnCheckWinner.setBackground(Color.BLUE);
        contentPane.add(btnCheckWinner);
        btnCheckWinner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkCurrentElectionWinner();
            }
        });
    }

    private void checkCurrentElectionWinner() {
        try {
            // Query the database to get the candidate with the most votes
            PreparedStatement stmt = con.prepareStatement("SELECT candidate_id, COUNT(*) as vote_count FROM votes GROUP BY candidate_id ORDER BY vote_count DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String winnerCandidateId = rs.getString("candidate_id");
                JOptionPane.showMessageDialog(frame, "Candidate with ID " + winnerCandidateId + " is the current election winner!", "Election Winner", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No votes have been cast yet!", "Election Winner", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while checking the current election winner.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    candidates_page_admin frame = new candidates_page_admin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
