/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankmanagementsystem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author Russell LaCour
 */
public class BankManagementSystem extends JFrame implements ActionListener
{
    private final int WIDTH = 400;
    private final int HEIGHT = 200;
    private CheckingAccount ca;
    private SavingsAccount sa;
    private JMenuBar menuBar;
    private JMenu mainMenu;
    private JTextField nameField;
    private JPasswordField pinField;
    private final String DB_URL;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet resultSet = null; 
    
    //Menu Items
    private JMenuItem exitItem; 
    
    
    public BankManagementSystem()
    {
        DB_URL = "jdbc:derby:BankDB";
        try
        {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("Select * from Customer");
            setTitle("First National Bank for Broke People");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(WIDTH, HEIGHT);
            buildMenuBar();
        Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(4,1));
            JPanel welcomePanel = new JPanel();
            welcomePanel.setBackground(Color.LIGHT_GRAY);
            JLabel welcomeLabel = new JLabel("Welcome to the First National " +
                    "Bank for Broke People!");
            welcomePanel.add(welcomeLabel);
            JPanel namePanel = new JPanel();
            namePanel.setBackground(Color.LIGHT_GRAY);
            JLabel nameLabel = new JLabel("Enter your name:");
            namePanel.add(nameLabel);
            nameField = new JTextField(10);
            nameField.setText(null);
            namePanel.add(nameField);
            JPanel pinPanel = new JPanel();
            pinPanel.setBackground(Color.LIGHT_GRAY);
            JLabel pinLabel = new JLabel("Enter your PIN Number:");
            pinPanel.add(pinLabel);
            pinField = new JPasswordField(4);
            pinField.setText(null);
            pinPanel.add(pinField);
            contentPane.add(welcomePanel);
            contentPane.add(namePanel);
            contentPane.add(pinPanel);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.DARK_GRAY);
            JButton enterButton = new JButton("Enter");
            enterButton.addActionListener(this);
            buttonPanel.add(enterButton);
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(this);
            buttonPanel.add(clearButton);
            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(this);
            buttonPanel.add(exitButton);
            contentPane.add(buttonPanel);
            
            
            
            
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Enter"))
        {
            try 
            {               
                String user = nameField.getText();
                String pin = pinField.getText();
                
                
                if (user != null && pin != null) 
                {
                    String sql = "Select * from Customer Where Name='" + user + "' and pinNumber='" + pin + "'";
                    resultSet = stmt.executeQuery(sql);
                    if (resultSet.next()) 
                    {
                        ca = new CheckingAccount(resultSet.getString("Name"), resultSet.getDouble("CBalance"));
                        sa = new SavingsAccount(resultSet.getString("Name"), resultSet.getDouble("SBalance"));
                        AccountWindow aw = new AccountWindow(ca, sa);
               
                        
                    }
                
                    else // Default
                    {
                        nameField.setText("Invalid Data");
                    }
                }
                   
            }
                
            catch(Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        else if(actionCommand.equals("Clear"))
        {
            nameField.setText("");
            pinField.setText("");
        }
        else if (actionCommand.equals("Exit"))
        {
            int p=JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to Exit?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(p==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
		}
        }
        else
            nameField.setText("Error in GUI interface");
    }
    
    private void buildMenuBar()
    {
        buildFileMenu();
        
        menuBar = new JMenuBar();
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);
    }
    
    private void buildFileMenu()
    {
        mainMenu = new JMenu("Main Menu");
        mainMenu.setMnemonic(KeyEvent.VK_F);
        
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(new ExitListener());
       
        
 
       
        mainMenu.add(exitItem);
    }
    
    private class ExitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int p=JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to Exit?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(p==JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[] args) 
    {
            BankManagementSystem bms = new BankManagementSystem();
        
        
    }
    
}
