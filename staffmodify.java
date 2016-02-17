//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.*;
import java.sql.*;

public class staffmodify {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";

    // Database credentials
    static final String USERNAME = "mkejriw1";
    static final String PASSWORD = "13nov1989";

    public static void main(String[] args)
    {

    	staffmodify jdbcInsertRecord = new staffmodify();
        jdbcInsertRecord.addInformation();

    }

    public void addInformation()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 =null;    	
        String theatreid="", addr1="",
        addr2="",zip="",city="",state="",staffname="", phone="", role="", exit = "y";;
        int ssn=0;
        int staffid=500;        
        
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
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//System.out.println("Welcome!"+"\n"+"Enter Staff Id"+" " +"Eg : S**, ** is the digit");
		
            
           while(exit.equalsIgnoreCase("y")) { 
            System.out.println("Enter a Staff name");
			staffname = br.readLine();
			if(staffname.equals("")) {
				System.out.println("Please enter a value");
				staffname = br.readLine();
			}
		
			System.out.println("Enter a Theatre Id(FORMAT T__) eg. T01, T02...");
			theatreid = br.readLine();
			if(theatreid.equals("")) {
				System.out.println("Please enter a value");
				theatreid = br.readLine();
			}
			
			System.out.println("Enter SSN");
            ssn = Integer.parseInt(br.readLine());
            if(ssn==0) {
				System.out.println("Please enter a value");
				ssn = Integer.parseInt(br.readLine());
			}
           
			
			System.out.println("Phone");
				phone = br.readLine();
				if(phone.equals("")) {
					System.out.println("Please enter a value");
					phone = br.readLine();
				}
			System.out.println("Role");
				role = br.readLine();
				if(role.equals("")) {
					System.out.println("Please enter a value");
					role = br.readLine();
				}
			
			System.out.println("Address line 1");
				addr1 = br.readLine();
				if(addr1.equals("")) {
					System.out.println("Please enter a value");
					addr1 = br.readLine();
				}
				
				System.out.println("Address line 2");
				addr2 = br.readLine();
				if(addr2.equals("")) {
					System.out.println("Please enter a value");
					addr2 = br.readLine();
				}
				
				System.out.println("Zip");
				zip = br.readLine();
				if(zip.equals("")) {
					System.out.println("Please enter a value");
					zip = br.readLine();
				}
				
				System.out.println("City");
				city = br.readLine();
				if(city.equals("")) {
					System.out.println("Please enter a value");
					city = br.readLine();
				}
				
				System.out.println("State");
				state = br.readLine();
				if(state.equals("")) {
					System.out.println("Please enter a value");
					state = br.readLine();
				}
				
		
	    		  
	    		  Statement stmt = connection.createStatement();
	    		  ResultSet rs = stmt.executeQuery("Select MAX(staff_id) as staid from staff");
	    		  if (rs.next()) {
	  				staffid = rs.getInt("staid");
	  				staffid = staffid + 1;
	  			}
	    		
	    		  System.out.println("Staff ID is: " + staffid);
	    		  
	    		  
            String sql = "insert into STAFF values  (?,?,?,?,?,?)";
          
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,theatreid);
            preparedStatement.setInt(2,ssn);
            preparedStatement.setString(3,staffname );
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, role);
            preparedStatement.setInt(6,staffid );

            preparedStatement.executeUpdate();
            
            String sql1 = "insert into STAFF_ADDRESS values  (?,?,?,?,?,?)";
            
            preparedStatement2 = connection.prepareStatement(sql1);
            
            preparedStatement2.setString(1,addr1);
            preparedStatement2.setString(2,addr2 );
            preparedStatement2.setString(3, zip);
            preparedStatement2.setString(4, city);
            preparedStatement2.setString(5, state);
            preparedStatement2.setInt(6,staffid);

            preparedStatement2.executeUpdate();     
            
            System.out.println("do you want to add a schedule for this staff as well? (y/n)");
            String choice = br.readLine();
            if(choice.equalsIgnoreCase("y")) {
            	modifySchedule modify = new modifySchedule();
            	modify.addSchedule();
            } else {
            	System.out.println("The new staff has been added to the database successfully");
            }
            
            System.out.println("Do you want to add another employee? (y/n)");
            exit = br.readLine();
           }
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
