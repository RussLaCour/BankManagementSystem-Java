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
import java.text.DecimalFormat;
/**
 *
 * @author Russell LaCour
 */
public class SavingsAccountWindow extends JFrame implements ActionListener
{
    private final int WIDTH = 600;
    private final int HEIGHT = 300;
    private DecimalFormat df;
    private SavingsAccount sa;
    private  String DB_URL;
    private Statement stmt = null;
    private Connection conn = null;
    private ResultSet result = null;
    public SavingsAccountWindow(SavingsAccount save)
    {
       try
       {     
        DB_URL = "jdbc:derby:BankDB";
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement();
        result = stmt.executeQuery("SELECT SBalance, Name from Customer");

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Savings Account");
        sa = save;
        df = new DecimalFormat("0.00");

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(4,1));
        JPanel accountPanel = new JPanel();
        JPanel savingPanel = new JPanel();
        JLabel accountNumber = new JLabel("Account Number: " + sa.getAccountNumber());
        JLabel saveBalance = new JLabel("Balance: $" + df.format(sa.getBalance()));
        accountPanel.add(accountNumber);
        contentPane.add(accountPanel);
        savingPanel.add(saveBalance);
        contentPane.add(savingPanel);
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
       catch(Exception e)
       {
           System.out.println("Error: " + e.getMessage());
       }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Deposit"))
        {
            {
                Deposit d = new Deposit(sa);
                d.setVisible(true);
            }
        }
        else if (actionCommand.equals("Withdraw"))
        {
           Withdraw w = new Withdraw(sa);
           w.setVisible(true);
        }
        
        else if (actionCommand.equals("Exit"))
        {
            setVisible(false);
        }
        else
        {
            System.out.println("Error in GUI Interface");
        }
    }
    private class Deposit extends JFrame implements ActionListener
    {
        public final int WIDTH = 400;
        public final int HEIGHT = 200;
        public JTextField depositText;
        public Deposit(SavingsAccount s)
        {
           setSize(WIDTH, HEIGHT);
           setTitle("Deposit");
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
            
            if(actionCommand.equals("Deposit"))
            {
                try
                {
                  
                   
                    String depositString = depositText.getText();
                    if(depositString.length() > 0)
                     {
                         double deposit = Double.parseDouble(depositString);
                         sa.deposit(deposit);
                         sa.getBalance();
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
    }
    
    private class Withdraw extends JFrame implements ActionListener
    {
        public final int WIDTH = 400;
        public final int HEIGHT = 200;
        public JTextField withdrawText;
        public Withdraw(SavingsAccount save)
        {
            sa = save;
            setSize(WIDTH, HEIGHT);
            setTitle("Withdraw");
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(3,1));
            JPanel withdrawPanel = new JPanel();
            JLabel withdrawlabel = new JLabel("Enter the amount you want to "
                    + "withdraw: $");
            withdrawText = new JTextField(10);
            withdrawText.setText(null);
            withdrawPanel.add(withdrawlabel);
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
                String withdrawString = withdrawText.getText();
                if(withdrawString.length() > 0)
                {
                    double withdrawFunds = Double.parseDouble(withdrawString);
                    if(withdrawFunds > sa.getBalance())
                    {
                        withdrawText.setText("Insufficient Funds");
                    }
                   sa.withdraw(withdrawFunds);
                   setVisible(false);
                   
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
                withdrawText.setText("Error in GUI inerface.");
            }
        }       
    }
   
            
}
