//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

import java.io.*;
import java.sql.*;
import java.util.Calendar;
public class ManagerAlert {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";
	
    static public void main(String[] args) {
    	ManagerAlert manage = new ManagerAlert();
    	manage.displayAlert();
    }
    public void displayAlert() {
    	Connection connection = null;
		PreparedStatement preparedStatementlogin = null;
		PreparedStatement preparedStatementJob = null;
		PreparedStatement preparedStatementlocation = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String login = "", password = "", location="";
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("This is the portal for Manager/owner/admin login...");
			System.out.println("Please provide your login id to log in to the portal");
			login = br.readLine();
			System.out.println("Please provide your password");
			password = br.readLine();
			
			String sqllogin = "Select STAFF_ID from STAFF_PRIVILEGES where (STAFF_LOGIN = ? and STAFF_PASSWORD = ?)";
			preparedStatementlogin = connection.prepareStatement(sqllogin);
			preparedStatementlogin.setString(1, login);
			preparedStatementlogin.setString(2, password);
			
			ResultSet rslogin = preparedStatementlogin.executeQuery();
			if(rslogin.next()) {
				System.out.println("You have successfully logged in");
				int staffid = rslogin.getInt(1); 
				String sqlLocation = "Select THEATRE_ID from STAFF where STAFF_ID = ?";
				preparedStatementlocation = connection.prepareStatement(sqlLocation);
				preparedStatementlocation.setInt(1, staffid);
				
				ResultSet rslocation = preparedStatementlocation.executeQuery();
				
				if(rslocation.next())
				   location = rslocation.getString(1);
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());			    			    
			    
			    long milliInADay = 1000 * 60 * 60 * 24;
			    today.setTime(today.getTime() + milliInADay);
			    //System.out.println("today: "+today);
			   
			    String sqlJob = "SELECT STAFF_SCHEDULE.STAFF_ID FROM STAFF_SCHEDULE, STAFF "+
			                    "WHERE (STAFF_SCHEDULE.STAFF_ID = STAFF.STAFF_ID "+"AND STAFF.ROLE = 'security' "+
			    		        "AND STAFF.THEATRE_ID = ? "+"AND TO_CHAR(STAFF_SCHEDULE.SHIFT_DATE, 'DD-MON-YYYY') = ?)"; 
			    preparedStatementJob = connection.prepareStatement(sqlJob);
			    preparedStatementJob.setString(1, location);
			    preparedStatementJob.setDate(2, today);
			    
			    ResultSet rsJob = preparedStatementJob.executeQuery();
			    if(rsJob.next()) {
			    	System.out.println("please choose from the option below what you want to do...");
			    	System.out.println("1) add details of a new employee to the database");
			    	System.out.println("2) add schedule for an existing employee");
			    	int choice = Integer.parseInt(br.readLine());
			    	if(choice == 1) {
			    		staffmodify jdbcInsertRecord = new staffmodify();
			            jdbcInsertRecord.addInformation();
			    	} else if(choice == 2) {
			    		modifySchedule stm = new modifySchedule();			
					    stm.addSchedule();
			    	} else {
			    		System.out.println("Invalid choice... please try again");
			    	}
			    } else {
			    	System.out.println("ALERT: Oops!! seems like there is no security working on duty tomorrow in your location");
			    	System.out.println("Please assign  the shift to the appropriate person");
			    	modifySchedule stm = new modifySchedule();			
				    stm.addSchedule();	
			    }
			    
			} else {
				System.out.println("The credentials you entered seems to be invalid please try again");
			}
    } catch (SQLException se) {
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
			if (preparedStatementlogin != null) {
				preparedStatementlogin.close();
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
