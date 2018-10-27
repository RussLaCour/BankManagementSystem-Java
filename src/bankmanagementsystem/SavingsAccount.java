/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankmanagementsystem;

/**
 *
 * @author Russell LaCour
 */
public class SavingsAccount extends BankAccount
{
    private double rate = .025;   // Annual interest rate
   private int savingsNumber = 0;// For additional accounts
   private String accountNumber; // Savings account number

   /**
      The constructor
      @param name The account name.
      @param begBal The amount of the beginning balance.
   */

   public SavingsAccount(String name, double begBal)
   {
      // Call the superclass constructor
      super(name, begBal);

      // Get the account number from the superclass,
      // and append the savings account extension to
      // the account number.
      accountNumber = super.getAccountNumber() +
                      "-" + savingsNumber;
   }

   /**
      The copy constructor
      @param oldAccount An existing savings account.
      @param begBal The amount of the beginning balance.
   */

   public SavingsAccount(SavingsAccount oldAccount, double begBal)
   {
      // Call the superclass copy constructor.
      super(oldAccount, begBal);

      // Increment the savings number for the new account.
      savingsNumber = oldAccount.savingsNumber + 1;

      // Get the account number from the superclass and
      // append the new savings number to the existing
      // account number.
      accountNumber = super.getAccountNumber() + "-" +
                      savingsNumber;
   }

   /**
      The postInterest method add the amount of
      one month's interest to the account balance.
   */

   public void postInterest()
   {
      // Rate is an annual rate, and we only want one month's
      // worth of interest.
      double newBalance = getBalance() * (1 + rate / 12);
      setBalance(newBalance);
   }
   
   /**
      The getAccountNumber method overrides the superclass
      getAccountNumber method.
      @Return The savings account number.
   */

   @Override
   public String getAccountNumber()
   {
      return accountNumber;
   }
}
