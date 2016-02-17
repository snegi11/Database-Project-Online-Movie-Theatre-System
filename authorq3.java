//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class authorq3{
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws IOException {
	    authorq3 mem= new authorq3();	    
	    mem.Details();
	}
	public void Details(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatementPrivilege = null;
		String userID = "", password = "", addprivilege = "";
		int staffId= 0;
		
		try {
			
			// Register driver manager
						DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

						// connect to database
						connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

						// get inputs from the userID
						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						System.out.println("Welcome to the Online Movie Database!");
						System.out.println("Please enter your Credentials:- ");
						System.out.println("Enter User Name:");
						userID = br.readLine();
						System.out.println("Enter your password to login");
						password = br.readLine();
						String sql="Select * from STAFF,STAFF_PRIVILEGES"
								+ " WHERE STAFF.STAFF_ID=STAFF_PRIVILEGES.STAFF_ID"
								+ " AND STAFF.ROLE='owner' AND "
								+ "STAFF_LOGIN=? AND STAFF_PASSWORD=?";
						
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, userID);
						preparedStatement.setString(2, password);			
						
						ResultSet rs = preparedStatement.executeQuery();
						if(rs.next()){
							
							System.out.println("\n"+"Welcome Owner!");
							String sql2="Select STAFFNAME, STAFF_ID FROM STAFF WHERE ROLE='manager'";
							preparedStatement2 = connection.prepareStatement(sql2);
							ResultSet rs2 = preparedStatement2.executeQuery();
							System.out.println("Here is the List of all the Managers:");
							int i=0;
							while(rs2.next())
							{   
								i=i+1;								
								String name=rs2.getString(1);
								staffId = rs2.getInt(2);
								System.out.println(i+": "+staffId+"  "+name);
								
							}
							System.out.println("\n"+"Please Enter the Name"
									+ " of the Manager to whom you want"
									+ " to GRANT the Privileges!");
							String man=br.readLine();
							
							System.out.println("Enter the corresponding staffid of that manager");
							staffId = Integer.parseInt(br.readLine());
							
							System.out.println("what priviledge do you want to add?");
							System.out.println("1) Show Movie Schedule");
							System.out.println("2) Update Movie Schedule");
							int choiceAdd = Integer.parseInt(br.readLine());
							if(choiceAdd == 1) {
								addprivilege = "SHOW_MOVIE_DETAILS";
							} else if(choiceAdd == 2) {
								addprivilege = "UPDATE_MOVIE_DETAILS";
							}
							

							String sqlPrivilege = "update STAFF_PRIVILEGES set STAFF_ACCESS = ? where STAFF_ID = ?";
							preparedStatementPrivilege = connection.prepareStatement(sqlPrivilege);
							preparedStatementPrivilege.setString(1, addprivilege);
							preparedStatementPrivilege.setInt(2, staffId);
							
							preparedStatementPrivilege.executeUpdate();
							
							String sql3="SELECT STAFFNAME FROM STAFF WHERE ROLE='manager' and "
									+ "STAFFNAME=?";
							
							preparedStatement3 = connection.prepareStatement(sql3);
							preparedStatement3.setString(1, man);
							
							ResultSet rs3 = preparedStatement3.executeQuery();
							
							
								if(rs3.next()){
								  man=rs3.getString(1);
								  System.out.println("Helloo Manager!");
								  here();
							   } else{
								
								  System.out.println("Sorry! You have entered"
										+ " an Invalid name.");
								  
							   }
								
							}	
						else 
						{
						System.out.println("Oops!! "
								+ "The login information you provided does not match our records");
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
	public void here(){
		Connection connection = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		PreparedStatement preparedStatement6 = null;
		PreparedStatement preparedStatement7 = null;
		PreparedStatement preparedStatement8 = null;
		String start = "", end = "", inputDate="";
		java.sql.Date date;
		java.sql.Timestamp st, et;
		java.util.Date givenDate, givenStart, givenEnd;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
try {
			
			// Register driver manager
						DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

						// connect to database
						connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

						// get inputs from the userID
						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter your Credentials:- ");
		System.out.println("\n"+"Enter User Name:");
		String userm=br.readLine();
		System.out.println("Enter Password:");
		String pass=br.readLine();
		String sql4="Select * from STAFF_PRIVILEGES WHERE STAFF_LOGIN=? and STAFF_PASSWORD=?";		
		preparedStatement4 = connection.prepareStatement(sql4);
		preparedStatement4.setString(1, userm);
		preparedStatement4.setString(2, pass);			
		
		ResultSet rs4 = preparedStatement4.executeQuery();
		if(rs4.next()){
        //System.out.println("You can now see the Database");	 
        
       
       // System.out.println("1. Insert New Movie Data");
        System.out.println("\n"+"Do you Want to Update Existing Movie Data ");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int choice= Integer.parseInt(br.readLine());
       
        
        if(choice==1){
        	String sql5="Select * from "
        			+ "STAFF_PRIVILEGES WHERE "
        			+ "STAFF_ACCESS='UPDATE_MOVIE_DETAILS'"
        			+ "AND STAFF_LOGIN=?";
        	
        	preparedStatement5 = connection.prepareStatement(sql5);
    		preparedStatement5.setString(1, userm);

    		ResultSet rs5 = preparedStatement5.executeQuery();
    		if(rs5.next()){
    			System.out.println("You have the privilege to Update!");
    			System.out.println("\n"+"These are the Movies in the Database:");
    			System.out.println("Movie ID"+" || "+ "Movie Name");
    			System.out.println("---------------------------------------");
    			String sql6="Select MOVIE_ID, TITLE FROM MOVIE";
    			
    			preparedStatement6 = connection.prepareStatement(sql6);
        		ResultSet rs6=preparedStatement6.executeQuery();
                		while(rs6.next()){
                			String n=rs6.getString(2);
                			String i=rs6.getString(1);
                			//System.out.println("The list of MOVIE:");
                			System.out.println(i+" || "+n);
                			System.out.println("---------------------------------------");
                		}	
    			System.out.println("\n"+"Enter the Movie ID for the movie you want Re-Schedule:");
    			String i2=br.readLine();
    			String sql7="SELECT MOVIE_ID,SCHEDULE_ID,TO_CHAR(SCHEDULE_DATE,'YYYY-MM-DD') AS C,TO_CHAR(ST_TIME,'HH:MI:SS') AS A,"
    					+ "  TO_CHAR(END_TIME,'HH:MI:SS') AS B FROM MOVIE_SCHEDULE WHERE MOVIE_ID=?";
    			preparedStatement7 = connection.prepareStatement(sql7);
        		preparedStatement7.setString(1, i2);
        		
        		ResultSet rs7 = preparedStatement7.executeQuery();
        		
        		if(rs7.next()){
        			
        			String her=rs7.getString("MOVIE_ID");
        			String here= rs7.getString("SCHEDULE_ID");	
        			String here2= rs7.getString("C");
        			String here3= rs7.getString("A");
        			String here4= rs7.getString("B");
        			
        			System.out.println("Movie Schedule in Database for the Movie ID you entered:");
        			System.out.println("Schedule ID: "+here);
        			System.out.println("Movie ID: "+her);
        			System.out.println("Schedule Date: "+here2);
        			System.out.println("Movie Start Time: "+here3);
        			System.out.println("Movie End Time: "+here4);
        			
        			System.out.println("\n"+"Give the entries for:");
        			System.out.println("MOVIE ID:");
        			String id=br.readLine();
        			System.out.println("SCHEDULE DATE:(YYYY-MM-DD)");
        			inputDate=br.readLine();
        			System.out.println("MOVIE START TIME:");
        			start=br.readLine();
        			System.out.println("MOVIE END TIME:");
        			end=br.readLine();
        			
        			givenDate = sdfDate.parse(inputDate);
        			date = new java.sql.Date(givenDate.getTime());
        			
        			givenStart = sdf.parse(start);   		  
   	   		        st = new java.sql.Timestamp(givenStart.getTime());
   	   		        givenEnd = sdf.parse(end);   		  
   			        et = new java.sql.Timestamp(givenEnd.getTime());
        			
        			String sql8="UPDATE MOVIE_SCHEDULE SET MOVIE_ID=? "
        					+ ",SCHEDULE_DATE=?, ST_TIME=?, END_TIME=? WHERE MOVIE_ID=?  ";
        			preparedStatement8 = connection.prepareStatement(sql8);
        			
        			
            		preparedStatement8.setString(1, id);
            		preparedStatement8.setDate(2, date);
            		preparedStatement8.setTimestamp(3, st);
            		preparedStatement8.setTimestamp(4, et);            		
            		preparedStatement8.setString(5, her);
            		
            		int intoCustomer = preparedStatement8.executeUpdate();
            		if(intoCustomer > 0)
            		
            		{
            		System.out.println("Schedule has been updated successfully!");
					System.out.println("Updated Movie ID: "+id);
					System.out.println("Updated Schedule Date: "+date);
					System.out.println("Updated Start Time: "+st);
					System.out.println("Updated End Time: "+et);
			       
            			
            			
            		}
        		}
            		else
            			System.out.println("Sorry Something Went Wrong!");
        		
            	} else {
            		System.out.println("Sorry... you do not have the privileges for this!!");
            	}
        			
        		}  if(choice==2){
                	System.out.println("Thanks for Visiting!");
                }
    		}
    		else
    		{
    			
    			System.out.println("Sorry you do not have the privilege to Update the Movie Schedule!");
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
				if (preparedStatement4 != null) {
					preparedStatement4.close();
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