/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankmanagementsystem;

import java.sql.*;
/**
 *
 * @author Russell LaCour
 */
public class BankDatabase 
{
    
    
   public static void main(String[] args)
    {
     final String DB_URL = "jdbc:derby:BankDB;create=true";
      
      try
      {
         // Create a connection to the database.
         Connection conn =
                DriverManager.getConnection(DB_URL);
					 
			// If the DB already exists, drop the tables.
			dropTables(conn);
			
			// Build the Customer table.
			buildCustomerTable(conn);
         // Close the connection.
         conn.close();
      }
      catch (Exception ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
   }
	
	/**
	 * The dropTables method drops any existing 
	 * in case the database already exists.
	 */

   public static void dropTables(Connection conn)
	{
		System.out.println("Checking for existing tables.");
		
		try
		{
			// Get a Statement object.
			Statement stmt  = conn.createStatement();

			try
			{
	         // Drop the Customer table.
	         stmt.execute("DROP TABLE Customer");
				System.out.println("Customer table dropped.");				
			}
			catch(SQLException ex)
			{
				// No need to report an error.
                                // The table simply did not exist.
			}

		}
  		catch(SQLException ex)
		{
	      System.out.println("ERROR: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void buildCustomerTable(Connection conn)
	{
      try
      {
         // Get a Statement object.
         Statement stmt = conn.createStatement();
         
         // Create the table.
         stmt.execute("CREATE TABLE Customer" +
            "( PinNumber CHAR(10) NOT NULL PRIMARY KEY, " +
            "  Name CHAR(25),"    +
            "  Address CHAR(30)," +
            "  City CHAR(14),"    +
            "  State CHAR(2),"    +
            "  Zip CHAR(5)," +
            "  CBalance DOUBLE, " +
            "  SBalance DOUBLE)");

         // Add some rows to the new table.
         stmt.executeUpdate("INSERT INTO Customer VALUES" +
               "('7783', 'Russell LaCour', '10 Elayne Court'," +
               " 'Smithtown', 'NY', '11787', 1.0, 10.0)");
         
         stmt.executeUpdate("INSERT INTO Customer VALUES" +
               "('5584', 'Johnny Galaxy'," +
               " '100 Universal City Plaza'," +
               " 'Universal City', 'CA', '91608', 2.5, 10.0)");
 
         stmt.executeUpdate("INSERT INTO Customer VALUES" +
               "('2162', 'Killian Darkwater', '25 Darkwater Path'," +
               " 'Junktown', 'CA', '96282', 3.0, 7.0)");
         
         stmt.executeUpdate("INSERT INTO Customer VALUES"
                 + "('9094', 'Mike Daniels', '3498 Kaz Place', "
                 + "'Gary', 'IN', '23423', 4.5, 8.0)");
         
         stmt.executeUpdate("INSERT INTO Customer VALUES"
                 + "('6486', 'Onix Bobe', '398 W.191st Street', "
                 + "'New York', 'NY', '10040', 2.0, 10.0)");
					
			System.out.println("Customer table created.");
		}
		catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
    }
	
}
