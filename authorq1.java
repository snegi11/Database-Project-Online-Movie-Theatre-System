//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

import java.io.*;
import java.sql.*;


public class authorq1 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws IOException {
	    authorq1 mem= new authorq1();	    
	    mem.LoginUser();
	}
	
	
	public void LoginUser() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String userID = "", password = "", status="",phone="",customername="",email="",choice="";
		int points,customerID=0;
		try {
			
			// Register driver manager
						DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

						// connect to database
						connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

						// get inputs from the userID
						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						System.out.println("Welcome member, please provide your used ID to begin");
						userID = br.readLine();
						System.out.println("Please enter your password to login");
						password = br.readLine();
						String sql = "Select customer_id,NAME,credit_points,membership_status, phone, email_id"
								+ " from MEMBER, CUSTOMER WHERE "
								+ "MEMBER.MEMBER_ID=CUSTOMER.CUSTOMER_ID "
								+ "AND USER_ID=? "
								+ "AND PASSWORD=?";
						
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, userID);
						preparedStatement.setString(2, password);			
						
						ResultSet rs = preparedStatement.executeQuery();
						
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
							
							System.out.println("\n"+"Do you want to change some details?");
							System.out.println("Enter Y/N");
							choice = br.readLine();
							if(choice.equalsIgnoreCase("Y")){
								update(userID ,customername , phone, email,customerID, points,status);	
							}
							else if(choice.equalsIgnoreCase("N")){
								System.out.println("Have a nice day!");
								//update(userID ,name , phone, email,customerID, points,status);	
							}
						    else {
							System.out.println("Please provide a valid choice");
						    }
		                 }
						else {
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


public void update(String userID,String customername, String phone, String email, int customerID, int points, String status)
{
	Connection connection = null;
	PreparedStatement preparedStatement1 = null;
	PreparedStatement preparedStatement2 = null;
	PreparedStatement preparedStatement3 = null;
	PreparedStatement preparedStatement4 = null;
	PreparedStatement preparedStatement5 = null;
	PreparedStatement preparedStatement6 = null;
	String cname="";
	
	try {
		// Register driver manager
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

		// connect to database
		connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

	  // get inputs from the userID
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String exit = "Y";
		while(exit.equalsIgnoreCase("Y")) {
		System.out.println("\n"+"Please enter the choice for the value you want to change:"); 
		int value = Integer.parseInt(br.readLine());
	
		if(value==1){
		
		String sql="SELECT NAME FROM CUSTOMER WHERE CUSTOMER_ID=?";
		preparedStatement2 = connection.prepareStatement(sql);
	    
		preparedStatement2.setInt(1, customerID);
		//System.out.println(customerID);
        
		ResultSet rs = preparedStatement2.executeQuery();
        
		if(rs.next()) {	    		  
  		
		customername = rs.getString(1);	
		
		System.out.println("\n"+"Enter a new entry for NAME");
		cname=br.readLine();
		String sqlupdate="UPDATE CUSTOMER SET NAME=? WHERE CUSTOMER_ID=?";	
		
		preparedStatement1 = connection.prepareStatement(sqlupdate);
		preparedStatement1.setString(1, cname);
		preparedStatement1.setInt(2, customerID);
	
		
		int intoCustomer = preparedStatement1.executeUpdate();
		
			if(intoCustomer > 0)
			       {
				    
				    System.out.println("Name has been updated successfully from "+customername+" to "+cname);
				    customername=cname;
				    System.out.println("Your updated account details are as follows:");
					System.out.println("1.Name:"+cname);
					System.out.println("2.Customer Id:"+customerID);
					System.out.println("3.Credit points:"+points);
					System.out.println("4.Membership Status:"+status);
					System.out.println("5.Phone:"+phone);
					System.out.println("6.Email Id:"+email);
			       
			       }else
				System.out.println("Something went wrong.. Please try again");
  }		   } 		

		
	if(value==2){
		System.out.println("Sorry! You can not change your Customer ID.");	
			
		}
		
	if(value==3){
		
		System.out.println("Sorry! You can not change your Credit Points.");	
		
	}
	
	if(value==4){
		
		System.out.println("Sorry! You can not change your Membership Status.");
		
	}
	
	if(value==5){
		String sql2="SELECT PHONE FROM CUSTOMER WHERE CUSTOMER_ID=?";
		preparedStatement3 = connection.prepareStatement(sql2);
		preparedStatement3.setInt(1, customerID);
		  
		ResultSet rs2 = preparedStatement3.executeQuery();
		if(rs2.next()) {	    		  
	  		
			phone = rs2.getString(1);	
		
			System.out.println("\n"+"Enter a new entry for PHONE");
			String phone1=br.readLine();
			String sqlupdate="UPDATE CUSTOMER SET PHONE=? WHERE CUSTOMER_ID=?";	
			
			preparedStatement4 = connection.prepareStatement(sqlupdate);
			preparedStatement4.setString(1, phone1);
			preparedStatement4.setInt(2, customerID);
		
			
			int intoCustomer = preparedStatement4.executeUpdate();
			
				if(intoCustomer > 0)
				       {
					  
					   System.out.println("Phone Number has been updated successfully from "+phone+" to "+phone1);
					    phone=phone1;
						System.out.println("Your updated account details are as follows:");
						System.out.println("1.Name:"+customername);
						System.out.println("2.Customer Id:"+customerID);
						System.out.println("3.Credit points:"+points);
						System.out.println("4.Membership Status:"+status);
						System.out.println("5.Phone:"+phone1);
						System.out.println("6.Email Id:"+email);
				       
				       
				       }
				       
				else
					System.out.println("Something went wrong.. Please try again");
	  }		   } 		
		
	
	
	if(value==6){
		String sql3="SELECT EMAIL_ID FROM CUSTOMER WHERE CUSTOMER_ID=?";
		preparedStatement5 = connection.prepareStatement(sql3);
		preparedStatement5.setInt(1, customerID);
		  
		ResultSet rs3 = preparedStatement5.executeQuery();
		if(rs3.next()) {	    		  
	  		
			email = rs3.getString(1);	
		
			System.out.println("Enter a new entry for EMAIL ID");
			String email1=br.readLine();
			String sqlupdate="UPDATE CUSTOMER SET EMAIL_ID=? WHERE CUSTOMER_ID=?";	
			
			preparedStatement6 = connection.prepareStatement(sqlupdate);
			preparedStatement6.setString(1, email1);
			preparedStatement6.setInt(2, customerID);
		
			
			int intoCustomer = preparedStatement6.executeUpdate();
			
				if(intoCustomer > 0)
				{		System.out.println("\n"+"Email ID has been updated successfully from "+email+" to "+email1);
				email=email1;
				System.out.println("\n"+"Your updated account details are as follows:");
				System.out.println("1.Name:"+customername);
				System.out.println("2.Customer Id:"+customerID);
				System.out.println("3.Credit points:"+points);
				System.out.println("4.Membership Status:"+status);
				System.out.println("5.Phone:"+phone);
				System.out.println("6.Email Id:"+email1);
				
		}
				else
					System.out.println("Something went wrong.. Please try again");
	      }
		}
	System.out.println("\n"+"Do you still want to make any more changes?(Y/N)");
	exit = br.readLine(); 
	
	}
		System.out.println("Thankyou for visiting us... do visit again!!"); 
    		

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
				if (preparedStatement2 != null) {
					preparedStatement2.close();
				}
				if (preparedStatement3 != null) {
					preparedStatement3.close();}
				
				if (preparedStatement4 != null) {
					preparedStatement4.close();}
				if (preparedStatement5 != null) {
					preparedStatement5.close();}
				if (preparedStatement6 != null) {
					preparedStatement6.close();}

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
