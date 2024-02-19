package kadi;

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

public class sales_page extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblDistributerNames;
    private JLabel lblDistributerId;
    private JLabel lblProduct;
    private JLabel lblAmount;
    private JLabel lblBoxes; // New label for boxes
    private JTextField txtDistributerNames;
    private JTextField txtDistributerId;
    private JComboBox<String> comboBoxProduct;
    private JTextField txtAmount;
    private JTextField txtBoxes; // New text field for boxes
    private JButton btnBack;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnAdd;

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM sales");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("id"));
                v2.add(Rs.getString("distributer_names"));
                v2.add(Rs.getString("distributer_id"));
                v2.add(Rs.getString("date_"));
                v2.add(Rs.getString("product_"));
                v2.add(Rs.getString("amount"));
                v2.add(Rs.getString("boxes")); // Adding the new column to the table
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public sales_page() {
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
        topBarPanel.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("MANAGE SALES");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(800, 10, 80, 30); // Adjust bounds as needed
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

        lblFormTitle = new JLabel("Sales Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblDistributerNames = new JLabel("Distributer Names:");
        lblDistributerNames.setBounds(10, 40, 150, 20);
        formPanel.add(lblDistributerNames);

        txtDistributerNames = new JTextField();
        txtDistributerNames.setBounds(170, 40, 200, 20);
        formPanel.add(txtDistributerNames);
        txtDistributerNames.setColumns(10);

        lblDistributerId = new JLabel("Distributer ID:");
        lblDistributerId.setBounds(10, 70, 150, 20);
        formPanel.add(lblDistributerId);

        txtDistributerId = new JTextField();
        txtDistributerId.setBounds(170, 70, 200, 20);
        formPanel.add(txtDistributerId);
        txtDistributerId.setColumns(10);

        lblProduct = new JLabel("Product:");
        lblProduct.setBounds(10, 100, 150, 20);
        formPanel.add(lblProduct);

        comboBoxProduct = new JComboBox<>();
        comboBoxProduct.setBounds(170, 100, 200, 20);
        comboBoxProduct.addItem("Milk");
        comboBoxProduct.addItem("Water");
        comboBoxProduct.addItem("Juice");
        formPanel.add(comboBoxProduct);

        lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 130, 150, 20);
        formPanel.add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(170, 130, 200, 20);
        formPanel.add(txtAmount);
        txtAmount.setColumns(10);

        lblBoxes = new JLabel("Boxes:"); // New label for boxes
        lblBoxes.setBounds(10, 160, 150, 20);
        formPanel.add(lblBoxes);

        txtBoxes = new JTextField(); // New text field for boxes
        txtBoxes.setBounds(170, 160, 200, 20);
        formPanel.add(txtBoxes);
        txtBoxes.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 190, 80, 30);
        btnAdd.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve sale details from the form
                String distributerNames = txtDistributerNames.getText();
                String distributerId = txtDistributerId.getText();
                String product = (String) comboBoxProduct.getSelectedItem();
                String amount = txtAmount.getText();
                String boxes = txtBoxes.getText(); // Retrieve boxes value
                try {
                    // Check if distributor exists
                    PreparedStatement checkDistributorStmt = con.prepareStatement("SELECT * FROM users WHERE id = ?");
                    checkDistributorStmt.setString(1, distributerId);
                    ResultSet distributorResult = checkDistributorStmt.executeQuery();
                    if (!distributorResult.next()) {
                        JOptionPane.showMessageDialog(null, "Distributor with ID " + distributerId + " does not exist");
                        return; // Exit the method if distributor does not exist
                    }

                    // Insert the new sale
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO sales (distributer_names, distributer_id, date_, product_, amount, boxes) VALUES (?, ?, NOW(), ?, ?, ?)");
                    insertStmt.setString(1, distributerNames);
                    insertStmt.setString(2, distributerId);
                    insertStmt.setString(3, product);
                    insertStmt.setString(4, amount);
                    insertStmt.setString(5, boxes); // Set the value for boxes
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Sale added");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding Sale", "Error", JOptionPane.ERROR_MESSAGE);
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
                // Retrieve sale ID
                String id = txtDistributerId.getText(); // Assuming distributor ID is used for deletion
                try {
                    // Delete sale
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM sales WHERE distributer_id = ?");
                    deleteStmt.setString(1, id);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Sale deleted");
                        showDataTable1();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sale with Distributer ID " + id + " not found");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting Sale", "Error", JOptionPane.ERROR_MESSAGE);
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
                // Retrieve sale details from the form
                String distributerNames = txtDistributerNames.getText();
                String distributerId = txtDistributerId.getText();
                String product = (String) comboBoxProduct.getSelectedItem();
                String amount = txtAmount.getText();
                String boxes = txtBoxes.getText(); // Retrieve boxes value
                try {
                    // Update the sale
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE sales SET distributer_names=?, distributer_id=?, date_=NOW(), product_=?, amount=?, boxes=? WHERE distributer_id =?");
                    updateStmt.setString(1, distributerNames);
                    updateStmt.setString(2, distributerId);
                    updateStmt.setString(3, product);
                    updateStmt.setString(4, amount);
                    updateStmt.setString(5, boxes); // Set the value for boxes
                    updateStmt.setString(6, distributerId);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Sale updated");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating Sale", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null, null}};
        String[] columnHeaders = {"id", "Distributer Names", "Distributer ID", "Date", "Product", "Amount", "Boxes"}; // Added "Boxes" to column headers
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        // Populate form fields with data from the selected row
                        txtDistributerId.setText((String) table1.getValueAt(selectedRow, 2));
                        txtDistributerNames.setText((String) table1.getValueAt(selectedRow, 1));
                        comboBoxProduct.setSelectedItem((String) table1.getValueAt(selectedRow, 4));
                        txtAmount.setText((String) table1.getValueAt(selectedRow, 5));
                        txtBoxes.setText((String) table1.getValueAt(selectedRow, 6)); // Set boxes value
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    sales_page frame = new sales_page();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}