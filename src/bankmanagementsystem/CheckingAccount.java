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
public class CheckingAccount extends BankAccount
{
    private static double FEE = .15; // Checking fee

   /**
      The constructor
      @param name The account name.
      @param begBal The amount of the beginning balance.
   */

   public CheckingAccount(String name, double begBal)
   {
      // Call the superclass constructor
      super(name, begBal);

      // Append the checking account extension to
      // the account number.
      setAccountNumber(getAccountNumber() + "-10");
   }

   /**
      The withdraw method overrides the superclass
      withdraw method. This method adds the checking
      fee to the amount that will be withdrawn.
      @param amount The amount to withdraw.
      @return True if there was sufficient funds to
              complete the transaction, false otherwise.
   */
   
   
   @Override
   public boolean withdraw(double amount)
   {
      // Add the checking fee to the amount that is
      // to be withdrawn from the checking account.
      amount = amount + FEE;

      // Call the superclass method, returning true
      // if the transaction was successful, or false
      // otherwise.
      return super.withdraw(amount);
   }
}
