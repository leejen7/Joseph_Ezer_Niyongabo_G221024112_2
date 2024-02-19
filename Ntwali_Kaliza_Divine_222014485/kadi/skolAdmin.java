package kadi;
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

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class skolAdmin {

    public JFrame frame;
    private JButton btnProfile;
    private JButton btnViewProposal;
    private JButton btnLogout;
    private JButton Distributors;
    private JButton Sales;
    private JButton Products;
    private JButton Orders;
    private JButton Users;
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
                    skolAdmin window = new skolAdmin();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public skolAdmin() {
        initialize();
        connection();
        table1 = new JTable();
    }

    Connection con;
    PreparedStatement pst;

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/skolbmp_db", "root", "");
            System.out.print("Connected well");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.YELLOW); // Changed background color to yellow
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("SKOL BEER MANAGEMENT SYSTEM");
        lblWelcome.setForeground(Color.RED); // Changed text color to dark red
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        topMenuPanel.setBounds(0, 50, 1050, 50);
        frame.getContentPane().add(topMenuPanel);
        topMenuPanel.setLayout(new GridLayout(1, 7, 5, 5)); // Changed layout to GridLayout

        JLabel lblHomepage = new JLabel("ADMIN PAGE");
        lblHomepage.setForeground(Color.WHITE);
        lblHomepage.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomepage.setFont(new Font("Verdana", Font.BOLD, 19));
        topMenuPanel.add(lblHomepage);

        btnProfile = new JButton("PROFILE");
        btnProfile.setFont(new Font("Verdana", Font.BOLD, 14));
        btnProfile.setOpaque(true);
        btnProfile.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        btnProfile.setBorderPainted(false);
        btnProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        topMenuPanel.add(btnProfile);

        btnViewProposal = new JButton("Add New Announcements");
        btnViewProposal.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewProposal.setOpaque(true);
        btnViewProposal.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        btnViewProposal.setBorderPainted(false);
        btnViewProposal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notification sales_page_ = new notification();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        topMenuPanel.add(btnViewProposal);

        Distributors = new JButton("Distributors");
        Distributors.setFont(new Font("Verdana", Font.BOLD, 14));
        Distributors.setOpaque(true);
        Distributors.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        Distributors.setBorderPainted(false);
        Distributors.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	distributers_page sales_page_ = new distributers_page();
                sales_page_.frame1.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        topMenuPanel.add(Distributors);

        Sales = new JButton("Sales");
        Sales.setFont(new Font("Verdana", Font.BOLD, 14));
        Sales.setOpaque(true);
        Sales.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        Sales.setBorderPainted(false);
        Sales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sales_page sales_page_ = new sales_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        topMenuPanel.add(Sales);

        Products = new JButton("Products");
        Products.setFont(new Font("Verdana", Font.BOLD, 14));
        Products.setOpaque(true);
        Products.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        Products.setBorderPainted(false);
        Products.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                product_page sales_page_ = new product_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        topMenuPanel.add(Products);

        Orders = new JButton("Orders");
        Orders.setFont(new Font("Verdana", Font.BOLD, 14));
        Orders.setOpaque(true);
        Orders.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        Orders.setBorderPainted(false);
        Orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oders_page usersPage = new oders_page();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        topMenuPanel.add(Orders);

        Users = new JButton("Users");
        Users.setFont(new Font("Verdana", Font.BOLD, 14));
        Users.setOpaque(true);
        Users.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        Users.setBorderPainted(false);
        Users.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                users_page usersPage = new users_page();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        topMenuPanel.add(Users);

        btnFeedback = new JButton("Feedback/Contact Admin");
        btnFeedback.setFont(new Font("Verdana", Font.BOLD, 14));
        btnFeedback.setOpaque(true);
        btnFeedback.setBackground(new Color(153, 102, 0)); // Changed background color to dark yellow
        btnFeedback.setBorderPainted(false);
        btnFeedback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notification sales_page_ = new notification();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        topMenuPanel.add(btnFeedback);

        // Create a margin panel to add space between the top menu and the body holding the cards
        JPanel marginPanel = new JPanel();
        marginPanel.setBackground(Color.WHITE);
        marginPanel.setBounds(0, 100, 1050, 5);
        frame.getContentPane().add(marginPanel);

        // Create the cards in the middle bar
        createCards();

        // Initially show the welcome message with the default username
        showWelcomeMessage("Username");
    }

    // Function to create cards in the middle bar
    private void createCards() {
        // Define the card size and position
        int cardWidth = 200;
        int cardHeight = 150;
        int cardX = 250;
        int cardY = 120;

        // Card 1: Sales
        JPanel salesCard = createCard("Sales", "Total Sales", "11");
        salesCard.setBounds(cardX, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(salesCard);

        // Card 2: Products
        JPanel productsCard = createCard("Products", "Available Products", "23");
        productsCard.setBounds(cardX + cardWidth + 50, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(productsCard);

        // Card 3: Stock
        JPanel stockCard = createCard("Stock", "Total Stock", "3000");
        stockCard.setBounds(cardX + (cardWidth + 50) * 2, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(stockCard);
    }

    // Function to create a card with specified title, subtitle, and value
    private JPanel createCard(String title, String subtitle, String value) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(200, 150));
        cardPanel.setMaximumSize(new Dimension(200, 150));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        titleLabel.setBounds(10, 10, 150, 20);
        cardPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        subtitleLabel.setBounds(10, 40, 150, 20);
        cardPanel.add(subtitleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        valueLabel.setBounds(10, 70, 150, 50);
        cardPanel.add(valueLabel);

        // Round the borders of the card
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(roundedBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        return cardPanel;
    }

    private void showWelcomeMessage(String username) {
        lblWelcome.setText("SKOL BEER MANAGEMENT SYSTEM");
    }

	public Component getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}