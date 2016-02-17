//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

//Display the registered guest who has contributed most comments.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Q5
{
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";

    // Database credentials
    static final String USERNAME = "mkejriw1";
    static final String PASSWORD = "13nov1989";

    public static void main(String[] args)
    {

        Q5 jdbcSelectRecords = new Q5();
        jdbcSelectRecords.getInformation();
    

    }
    

    public void getInformation()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            /*
             * Register the JDBC driver in DriverManager
             */

            Class.forName(JDBC_DRIVER);

            /*
             * Establish connection to the Database using DriverManager
             */

            connection = DriverManager
                    .getConnection(DB_URL, USERNAME, PASSWORD);

            String sql ="SELECT CUSTOMER.NAME, COUNT(REVIEW_DISCUSSION.MEMBER_ID) FROM (CUSTOMER JOIN MEMBER ON CUSTOMER.CUSTOMER_ID=MEMBER.MEMBER_ID JOIN REVIEW_DISCUSSION ON MEMBER.MEMBER_ID=REVIEW_DISCUSSION.MEMBER_ID ) GROUP BY CUSTOMER.NAME HAVING COUNT(REVIEW_DISCUSSION.MEMBER_ID) >= ALL(SELECT COUNT(REVIEW_DISCUSSION.MEMBER_ID) FROM (CUSTOMER JOIN MEMBER ON CUSTOMER.CUSTOMER_ID=MEMBER.MEMBER_ID JOIN REVIEW_DISCUSSION ON MEMBER.MEMBER_ID=REVIEW_DISCUSSION.MEMBER_ID ) GROUP BY CUSTOMER.NAME)";

            /*
             * Execute the query
             */
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.print("Customer Names: "+"\n");
            int i=0;
            while (rs.next())
            {
                    i=i+1;           
              
                String name = rs.getString(1);
       
                          
                /*
                 * Display values
                 */
                
                System.out.println(i+". "+name);
          
               
            }

            rs.close();

        }
        catch (SQLException se)
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            /*
             * Handle errors for Class.forName
             */
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            try
            {
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }

    }
}
