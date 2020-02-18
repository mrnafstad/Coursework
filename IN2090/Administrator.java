import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Administrator {

    private static String user = "halvona"; // Input your UiO-username
    private static String pwd = "Im3Vophae0"; // Input the password for the _priv-user you got in a mail
    // Connection details
    private static String connectionStr = 
        "user=" + user + "_priv&" + 
        "port=5432&" +  
        "password=" + pwd + "";
    private static String host = "jdbc:postgresql://dbpg-ifi-kurs.uio.no"; 

    public static void main(String[] agrs) {

        try {
            // Load driver for PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(host + "/" + user
                    + "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&" + connectionStr);

            int ch = 0;
            while (ch != 3) {
                System.out.println("-- ADMINISTRATOR --");
                System.out.println("Please choose an option:\n 1. Create bills\n 2. Insert new product\n 3. Exit");
                ch = getIntFromUser("Option: ", true);

                if (ch == 1) {
                    makeBills(connection);
                } else if (ch == 2) {
                    insertProduct(connection);
                }
            }
        } catch (SQLException|ClassNotFoundException ex) {
            System.err.println("Error encountered: " + ex.getMessage());
        }
    }

    private static void makeBills(Connection connection)  throws SQLException {
      	//Oppg 2
      	System.out.println(" -- BILLS --");
		String username = getStrFromUser("Username: ");
		PreparedStatement statement  = connection.prepareStatement("SELECT u.name, u.address, CAST(SUM(p.price*o.num)AS DECIMAL(16,2)) as total from ws.users as u INNER JOIN ws.orders as o USING (uid) inner join ws.products as p USING (pid) where u.username LIKE ? group by u.name, u.address ORDER BY total;");
		statement.setString(1, '%' + username + '%');
		ResultSet rows = statement.executeQuery();
		if (!rows.next()){
			System.out.println("Invalid username");	
		}
		do {
			System.out.println(" -- BILL -- \n " +
			"Name: " + rows.getString(1) + "\n " +
			"Address: " + rows.getString(2) + "\n " +
			"Total due: " + rows.getString(3) + "\n ");
			
		} while(rows.next());
    }


    private static void insertProduct(Connection connection) throws SQLException {
        //  Oppg 3
        System.out.println(" -- INSERT NEW PRODUCT --");
		String pName = getStrFromUser("Product name: ");
		Integer price = getIntFromUser("Price: ", true);
		String category = getStrFromUser("Category: ");
		String descr = getStrFromUser("Description: ");
		
		String insertItem = "INSERT INTO ws.products (name, price, cid, description) VALUES (?, ?, (SELECT cid FROM ws.categories WHERE name LIKE ?), ?);";
		
		PreparedStatement statement = connection.prepareStatement(insertItem);

		statement.setString(1, pName);
		statement.setInt(2, price);
		statement.setString(3, '%' +  category + '%');
		statement.setString(4, descr);
		statement.execute();	
		
		System.out.println("New product " + pName + " inserted");
    }

    /**
     * Utility method that gets an int as input from user
     * Prints the argument message before getting input
     * If second argument is true, the user does not need to give input and can leave
     * the field blank (resulting in a null)
     */
    private static Integer getIntFromUser(String message, boolean canBeBlank) {
        while (true) {
            String str = getStrFromUser(message);
            if (str.equals("") && canBeBlank) {
                return null;
            }
            try {
                return Integer.valueOf(str);
            } catch (NumberFormatException ex) {
                System.out.println("Please provide an integer or leave blank.");
            }
        }
    }

    /**
     * Utility method that gets a String as input from user
     * Prints the argument message before getting input
     */
    private static String getStrFromUser(String message) {
        Scanner s = new Scanner(System.in);
        System.out.print(message);
        return s.nextLine();
    }
}
