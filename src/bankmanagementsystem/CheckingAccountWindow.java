/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Russell LaCour
 * 12/11/2017
 */
package bankmanagementsystem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
/**
 *
 * @author Russell LaCour
 */
public class CheckingAccountWindow extends JFrame implements ActionListener
{
    private final int WIDTH = 600;
    private final int HEIGHT = 300;
    private DecimalFormat df;
    private CheckingAccount ca;
    private String URL_DB;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet result = null;
    public CheckingAccountWindow(CheckingAccount check)
    {
        try
        {
            URL_DB = "jdbc:derby:BankDB";
            conn = DriverManager.getConnection(URL_DB);
            stmt = conn.createStatement();
            result = stmt.executeQuery("Select Name, CBalance from Customer");
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setSize(WIDTH, HEIGHT);
            setTitle("Checking Account");

            df = new DecimalFormat("0.00");
            ca = check;
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(3,1));
            JPanel checkPanel = new JPanel();
            JLabel accountNo = new JLabel("Account No. " + ca.getAccountNumber());
            checkPanel.add(accountNo);
            contentPane.add(checkPanel);

            JPanel checkBalancePanel = new JPanel();
            JLabel checkingBalance = new JLabel("Balance: $" + df.format(ca.getBalance()));
            checkBalancePanel.add(checkingBalance);
            contentPane.add(checkBalancePanel);

            JPanel buttonPanel = new JPanel();
            JButton deposit = new JButton("Deposit");
            deposit.addActionListener(this);
            JButton withdrawal = new JButton("Withdraw");
            withdrawal.addActionListener(this);
            JButton exit = new JButton("Exit");
            exit.addActionListener(this);
            buttonPanel.add(deposit);
            buttonPanel.add(withdrawal);
            buttonPanel.add(exit);
            contentPane.add(buttonPanel);
            setVisible(true);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Deposit"))
        {
            Deposit deposit = new Deposit(ca);
            deposit.setVisible(true);
        }
        else if (actionCommand.equals("Withdraw"))
        {
           Withdraw withdraw = new Withdraw(ca);
           withdraw.setVisible(true);
        }
        
        else if (actionCommand.equals("Exit"))
        {
            setVisible(false);
        }
        
      
    }
    
    private class Deposit extends JFrame implements ActionListener
    {
        public final int WIDTH = 400;
        public final int HEIGHT = 200;
        public JTextField depositText; 
        public Deposit(CheckingAccount c)
        {
           setSize(WIDTH, HEIGHT);
           setTitle("Deposit");
           ca = c;
           Container contentPane = getContentPane();
           contentPane.setLayout(new GridLayout(2,1));
           JPanel depositPanel = new JPanel();
           JLabel depositLabel = new JLabel("Enter in the amount you"
                   + " want to deposit: $");
           depositText = new JTextField(10);
           depositText.setText(null);
           depositPanel.add(depositLabel);
           depositPanel.add(depositText);
           contentPane.add(depositPanel);
           JPanel buttonPanel = new JPanel();
           JButton depositButton = new JButton("Deposit");
           depositButton.addActionListener(this);
           JButton clearButton = new JButton("Clear");
           clearButton.addActionListener(this);
           JButton exitButton = new JButton("Exit");
           exitButton.addActionListener(this);
           buttonPanel.add(depositButton);
           buttonPanel.add(clearButton);
           buttonPanel.add(exitButton);
           contentPane.add(buttonPanel);
           
        }
       
        public void actionPerformed(ActionEvent e)
        {
            String actionCommand = e.getActionCommand();
          try
          {
            if(actionCommand.equals("Deposit"))
            {
                String depositString = depositText.getText();
                if(depositString.length() > 0)
                {
                    
                    double deposit = Double.parseDouble(depositString);
                    ca.deposit(deposit);
                    ca.getBalance();
                    setVisible(false);    
                }
            }
            else if(actionCommand.equals("Clear"))
            {
                depositText.setText("");
            }
            else if(actionCommand.equals("Exit"))
            {
                setVisible(false);
            }
            else
            {
                depositText.setText("Error in GUI interface");
            }
        }
        catch(Exception exe)
        {
            System.out.println("ERROR: " + exe.getMessage());
        }
      }
    }
    private class Withdraw extends JFrame implements ActionListener
    {
        public final int WIDTH = 400;
        public final int HEIGHT = 200;
        private JTextField withdrawText;
        public Withdraw(CheckingAccount check)
        {
            setSize(WIDTH, HEIGHT);
            setTitle("Withdraw");
            ca = check;
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(3, 1));
            JPanel withdrawPanel = new JPanel();
            JLabel withdrawLabel = new JLabel("Enter the amount you want to "
                    + "withdraw: $");
            withdrawText = new JTextField(10);
            withdrawText.setText(null);
            withdrawPanel.add(withdrawLabel);
            withdrawPanel.add(withdrawText);
            contentPane.add(withdrawPanel);
            
            JPanel buttonPanel = new JPanel();
            JButton withdrawButton = new JButton("Withdraw");
            withdrawButton.addActionListener(this);
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(this);
            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(this);
            buttonPanel.add(withdrawButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(exitButton);
            contentPane.add(buttonPanel);
        }
        
       
        
        public void actionPerformed(ActionEvent e)
        {
            String actionCommand = e.getActionCommand();
            if(actionCommand.equals("Withdraw"))
            {
                try
                {
                    String withdrawString = withdrawText.getText();
                    if(withdrawString.length() > 0)
                    {
                        double withdrawFunds = Double.parseDouble(withdrawString);
                        if(withdrawFunds > ca.getBalance())
                        {
                            withdrawText.setText("Insufficient Funds");
                        }
                        ca.withdraw(withdrawFunds);
                        setVisible(false);
                    }
                }
                catch(Exception ex)
                {
                    System.out.println("ERROR: " + ex.getMessage());
                }
            }
            else if(actionCommand.equals("Clear"))
            {
                withdrawText.setText("");
            }
            else if(actionCommand.equals("Exit"))
            {
                setVisible(false);
            }
            else
            {
                withdrawText.setText("Error in GUI interface");
            }
            
        }
    }
}