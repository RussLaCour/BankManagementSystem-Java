/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankmanagementsystem;

/**
 *
 * @author Russell LaCour
 * 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class AccountWindow extends JFrame implements ActionListener
{
    private final int WIDTH = 600;
    private final int HEIGHT = 300;
    private CheckingAccount ca;
    private SavingsAccount sa;
    private final String DB_URL;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet resultSet;
    
    
    public AccountWindow(CheckingAccount check, SavingsAccount save)
    {
        DB_URL = "jdbc:derby:BankDB";
        try
        {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM Customer");
            
            setSize(WIDTH, HEIGHT);
            setTitle("First National Bank for Broke People");
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            ca = check;
            sa = save;
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(4,1));
            JPanel accountPanel = new JPanel();
            JLabel accountLabel = new JLabel("Hello. Please select which account you " +
                    "want to select.");
            accountPanel.add(accountLabel);

            accountPanel.setBackground(Color.LIGHT_GRAY);
            contentPane.add(accountPanel);
            JPanel checkAccount = new JPanel();
            JPanel saveAccount = new JPanel();
            JButton checks = new JButton("Checking Account");
            checks.addActionListener(this);
            JButton saves = new JButton("Savings Account");
            saves.addActionListener(this);
            checkAccount.setBackground(Color.LIGHT_GRAY);
            saveAccount.setBackground(Color.LIGHT_GRAY);
            JPanel buttonPanel = new JPanel();
            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(this);
            buttonPanel.add(exitButton);
            checkAccount.add(checks);
            saveAccount.add(saves);
            contentPane.add(checkAccount);
            contentPane.add(saveAccount);
            contentPane.add(buttonPanel);

            setVisible(true);
            
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
         String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Checking Account"))
        {
           
            CheckingAccountWindow caw = new CheckingAccountWindow(ca);
            
            
        }
        else if (actionCommand.equals("Savings Account"))
        {
            SavingsAccountWindow saw = new SavingsAccountWindow(sa);
            
            
        }
        else if(actionCommand.equals("Exit"))
        {
            int p=JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to Log out??", "Confirm", JOptionPane.YES_NO_OPTION);
            if(p==JOptionPane.YES_OPTION)
            {
		setVisible(false);
            }
        }
        else
        {
            System.out.println("Error in GUI interface");
        }
                
    }
}
