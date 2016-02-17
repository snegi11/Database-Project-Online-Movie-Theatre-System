//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.*;
import java.sql.*;

public class Q8{
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws IOException {
	    Q8 mem= new Q8();	    
	    mem.getInformation();
	}
	

    public void getInformation()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        try
        {
        	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			// get inputs from the userID
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Theatre Names in the Online Movie Database: ");
			String sql2="SELECT THEATRE_NAME FROM THEATRE";
		     preparedStatement2 = connection.prepareStatement(sql2);
	      
	        ResultSet rs2 = preparedStatement2.executeQuery();
			while(rs2.next()){
				String name=rs2.getString(1);
				System.out.println(name);
				
			}
			System.out.println("\n"+"Please Enter a Theatre Name from the list above:");
			String tname= br.readLine();
			

            String sql = "select STAFFNAME,ROLE,SHIFT_DAY,TO_CHAR(SHIFT_DATE,'YYYY-MM-DD') AS A,"
            		+ "TO_CHAR(SHIFT_FROM,'HH:MI:SS') AS B,TO_CHAR(SHIFT_TO,'HH:MI:SS') AS C "
            		+ "from STAFF JOIN "
            		+ "STAFF_SCHEDULE ON "
            		+ "STAFF.staff_id=STAFF_SCHEDULE.staff_id "
            		+ "JOIN THEATRE ON THEATRE.theatre_id=STAFF.theatre_id "
            		+ "WHERE STAFF_SCHEDULE.shift_day='Monday' and "
            		+ "theatre_name=?" ;

            /*
             * Execute the query
             */
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tname);
            ResultSet rs = preparedStatement.executeQuery();
            int counter=0;
            while (rs.next())
            {   counter++;
                               
              
                String staffname = rs.getString("STAFFNAME");
                String role = rs.getString("ROLE");
                String day = rs.getString("SHIFT_DAY");
                String date=rs.getString("A");
                String shift_from = rs.getString("B");
                String shift_to = rs.getString("C");
                //String theatre_name = rs.getString(13);
                
           

                /*
                 * Display values
                 */
                
                //System.out.println("Theatre Name: " + theatre_name);
                System.out.println("Staff Name: " + staffname);
                System.out.println("Staff Role: " + role);
                System.out.println("Shift Day: " + day);
                System.out.println("Shift Date: "+ date);
                System.out.println("Shift From: " + shift_from);
                System.out.println("Shift To: " + shift_to);
               
            

            }
            if(counter==0)
            System.out.println("Sorry No staff working on Monday in this Theatre!");
            }

        
    	catch (SQLException se) {
			/*
			 * Handle errors for JDBC
			 */
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*
			 * finally block used to close resources
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}			

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
    }
}