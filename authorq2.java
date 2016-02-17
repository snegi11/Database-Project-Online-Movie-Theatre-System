//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.*;
import java.sql.*;

public class authorq2 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws IOException {
	    authorq2 mem= new authorq2();	    
	    mem.Login();
	}
	public void Login(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		try {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
		connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please provide us with your Role from the following:");
		System.out.println("1.Owner");
		System.out.println("2.Web Admin");
		System.out.println("3.Member");
		System.out.println("4.Other Role");
		int choice = Integer.parseInt(br.readLine());
		if(choice == 1) {
			System.out.println("Hello Owner!");
			viewOwAd();
		}
        if(choice == 2) {
        	System.out.println("Hello Admin!");
        	viewOwAd();
		}
        if(choice == 3) {
			viewGuest();
		}
        if(choice == 4) {
			System.out.println("Sorry! You do not have the privilege to see the Guest Infromation.");;
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
	
	public void viewOwAd(){
		Connection connection = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement1 = null;
		String  username="",pass="",customername="",status="",phone="",email="";
		int customerID=0,points=0;
		try{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		  // get inputs from the userID
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userID="", password="";
		System.out.println("Please Enter your Credentials.");
		System.out.println("Enter the User Name");
		userID = br.readLine();
		System.out.println("Enter your Password");
		password = br.readLine();
		
		String sql= "SELECT * FROM STAFF_PRIVILEGES WHERE STAFF_LOGIN=? and STAFF_PASSWORD=?";
		preparedStatement1 = connection.prepareStatement(sql);
		preparedStatement1.setString(1, userID);
		preparedStatement1.setString(2, password);
		int i=0;
		
		ResultSet rs = preparedStatement1.executeQuery();
		if(rs.next()){
			String sql2= " SELECT CUSTOMER_ID,NAME,CREDIT_POINTS,MEMBERSHIP_STATUS,"
					+ "PHONE,EMAIL_ID,USER_ID,PASSWORD "
					+ "FROM CUSTOMER,MEMBER WHERE MEMBER.MEMBER_ID=CUSTOMER.CUSTOMER_ID";
			preparedStatement4 = connection.prepareStatement(sql2);
			
			ResultSet rs2 = preparedStatement4.executeQuery();
			System.out.println("\n"+"Guest's account details are as follows:"+"\n");
			while(rs2.next()){
			customerID = rs2.getInt(1);
			customername = rs2.getString(2);
			points= rs2.getInt(3);
			status = rs2.getString(4);
			phone = rs2.getString(5);
			email= rs2.getString(6);
			username=rs2.getString(7);
			pass=rs2.getString(8);
			i=i+1;
			System.out.println("Record Number: "+i);
			System.out.println("Name:"+customername);
			System.out.println("User Name:"+username);
			System.out.println("User Password:"+pass);
			System.out.println("Customer Id:"+customerID);
			System.out.println("Credit points:"+points);
			System.out.println("Membership Status:"+status);
			System.out.println("Phone:"+phone);
			System.out.println("Email Id:"+email);
			
			System.out.println();}
		}else {
				System.out.println("Oops!! The login information you provided does not match our records");
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
		

	public void viewGuest(){
		Connection connection = null;
		PreparedStatement preparedStatement3 = null;
		
		String  customername="",status="",phone="",email="";
		int customerID=0,points=0;
	
		try{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		  // get inputs from the userID
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userID2="", password2="";
		System.out.println("Please Enter your Credentials.");
		System.out.println("Enter User name");
		userID2 = br.readLine();
		System.out.println("Enter Password");
		password2 = br.readLine();
		String sql = "Select customer_id,NAME,credit_points,membership_status, phone, email_id"
				+ " from MEMBER, CUSTOMER WHERE "
				+ "MEMBER.MEMBER_ID=CUSTOMER.CUSTOMER_ID "
				+ "AND USER_ID=? "
				+ "AND PASSWORD=?";
		
		preparedStatement3 = connection.prepareStatement(sql);
		preparedStatement3.setString(1, userID2);
		preparedStatement3.setString(2, password2);			
		
		ResultSet rs = preparedStatement3.executeQuery();
		
		if(rs.next()){
			
			customerID = rs.getInt(1);
			customername = rs.getString(2);
			points= rs.getInt(3);
			status = rs.getString(4);
			phone = rs.getString(5);
			email= rs.getString(6);
			
			System.out.println("\n"+"Your account details are as follows:");
			System.out.println("1.Name:"+customername);
			System.out.println("2.Customer Id:"+customerID);
			System.out.println("3.Credit points:"+points);
			System.out.println("4.Membership Status:"+status);
			System.out.println("5.Phone:"+phone);
			System.out.println("6.Email Id:"+email);
		}
		else {
				System.out.println("Oops!! The login information you provided does not match our records");
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
					if (preparedStatement3 != null) {
						preparedStatement3.close();
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

