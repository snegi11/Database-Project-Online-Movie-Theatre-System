//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.*;
import java.sql.*;
public class q4 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	public static void main(String[] args) {
		q4 mem = new q4();
		mem.showalerts();

	}
public void showalerts(){
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Connection connection = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	String userid=" ",password=" ";
	int memberid;
	try {
		// Register driver manager
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

		// connect to database
		connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		
		System.out.println("Welcome Member!  enter your userID to begin with ");
		userid = br.readLine();
		System.out.println("Please enter your password to proove your identity");
		password = br.readLine();
		String sql = "Select MEMBER_ID FROM MEMBER WHERE USER_ID = ? AND PASSWORD = ?";
		preparedStatement1 = connection.prepareStatement(sql);
		preparedStatement1.setString(1, userid);
		preparedStatement1.setString(2, password);
		ResultSet rs = preparedStatement1.executeQuery();
		
		if (rs.next()) {
			memberid = rs.getInt(1);
			System.out.println("Lets see if you have any new alerts !! ");
			String sql2 = " SELECT ALERT FROM ALERTS WHERE MEMBER_ID = ? AND ALERT = 'UNREAD'";
			preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, memberid);
			ResultSet rs1 = preparedStatement2.executeQuery();
			if (rs1.next())
			{
				String al = rs1.getString(1);
				String sql3  = " SELECT CREDIT_POINTS,MEMBERSHIP_STATUS FROM MEMBER WHERE MEMBER_ID = ?";
				preparedStatement3 = connection.prepareStatement(sql3);
				preparedStatement3.setInt(1, memberid);
				ResultSet rs2 = preparedStatement3.executeQuery();
				if (rs2.next());
				String status = rs2.getString(2);
				int points = rs2.getInt(1);
				System.out.println("Wow!! you have a new alert!!");
				System.out.println("Your membership status is: "+status);
				System.out.println("Your current credit points are: "+points);
				
				String sql4 = "DELETE ALERTS WHERE MEMBER_ID = ? ";
				preparedStatement4 = connection.prepareStatement(sql4);
				preparedStatement4.setInt(1, memberid);
				preparedStatement4.executeUpdate();
				
				
				
			}
			else {
				
				System.out.println("No new alerts for you!! GO BACK SIMON or buy some tickets !!  Maybe start with writing a review ");
				
			}
					
					
					
					
					
		}
		else {
			System.out.println("Wrong combination of User ID or Password");
		}
		
		
		
		
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
			if (preparedStatement1 != null) {
				preparedStatement1.close();
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


