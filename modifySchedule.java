package fianlCodes;


import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
public class modifySchedule {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";	
	
	static public void main(String[] args) throws IOException{
		modifySchedule stm = new modifySchedule();			
	    stm.addSchedule();				
	}

	public void addSchedule() {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatementdATE = null;
	PreparedStatement preparedStatementTime = null;
	PreparedStatement preparedStatementInsert = null;
	String  staffname = " ",day= " ", date="";
	String  shift_from = " ",shift_to = " ", role="", theatreid="";
	int staff_id = 0;
	java.util.Date from, to, givenDate;
	java.sql.Date shiftDate = null;
	java.sql.Timestamp sfrom, sto;
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); 
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");    
	
    try {
	  //Register driver manager
	  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	
	  //connect to database 
	  connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
	  // get inputs from the userID
	  String exit = "y";
	  while(exit.equalsIgnoreCase("Y")) {
		 sdfDate = new SimpleDateFormat("yyyy-MM-dd"); 
	  System.out.println("Enter the name of staff for adding his/her schedule");
	  staffname = br.readLine();	  
	
	  if(staffname.equals("")) {
		  System.out.println("Please enter a name to continue");
		  staffname = br.readLine();
	  }
	
	  String sql = "select STAFF_ID, THEATRE_ID, ROLE from STAFF where STAFFNAME =?";
	  
	  preparedStatement = connection.prepareStatement(sql);
	  preparedStatement.setString(1, staffname);	
	  
	  ResultSet rs = preparedStatement.executeQuery();
	  if(rs.next()) {
		  staff_id = rs.getInt(1);		  
		  theatreid = rs.getString(2);		  
		  role = rs.getString(3);
		  
		 System.out.println("Please enter the date for which you want to assign the schedhule (yyyy-mm-dd)");
		 date = br.readLine();		 
		 givenDate = sdfDate.parse(date);
		 sdfDate.applyPattern("EEEE");
		 day = sdfDate.format(givenDate);		 		 
		 shiftDate = new java.sql.Date(givenDate.getTime());		 
		 
		 String sqlDate = "Select STAFF_ID from STAFF_SCHEDULE where (STAFF_ID = ? AND TO_CHAR(STAFF_SCHEDULE.SHIFT_DATE, 'yyyy-MM-dd') = ?)";
		 preparedStatementdATE = connection.prepareStatement(sqlDate);
		 preparedStatementdATE.setInt(1, staff_id);
		 preparedStatementdATE.setString(2, date);
		 
		 ResultSet rsDate = preparedStatementdATE.executeQuery();
		 if(rsDate.next()) {
			 System.out.println("The employee is already working on the date you entered... try for a different date or a different employee");
		 } else {
			 System.out.println();
			 System.out.println("Here are the details of the shift timing slots... Please eneter your choice from the one shown below: ");
			 System.out.println("1. Shift from - 09:00:00 AM and Shift to - 12:59:00 PM");
			 System.out.println("2. Shift from - 13:00:00 PM and Shift to - 16:59:00 PM");
			 System.out.println("3. Shift from - 17:00:00 PM and Shift to - 20:59:00 PM");
			 int choice = Integer.parseInt(br.readLine());
			 boolean defaultChoice = false;
			 switch(choice) {
			     case 1: shift_from = "09:00:00";
			             shift_to = "12:59:00";
			             break;
			     case 2: shift_from = "13:00:00";
	                     shift_to = "16:59:00";
	                     break;  
			     case 3: shift_from = "17:00:00";
	                     shift_to = "20:59:00";
	                     break;    
			     default: defaultChoice = true;
			 }
			 if(defaultChoice) {
				 System.out.println("You have specified invalid choice... Please specify the correct choice");	
				 System.out.println("Wanna try again? (y/n)");
				 exit = br.readLine();
			 } else {
				 from = sdf.parse(shift_from);   		  
	   		     sfrom = new java.sql.Timestamp(from.getTime());
	   		     to = sdf.parse(shift_to);   		  
			     sto = new java.sql.Timestamp(to.getTime());   		     
	   		     
	   		     String sqlTime = "Select STAFF_SCHEDULE.STAFF_ID, THEATRE_ID, ROLE, (TO_CHAR(SHIFT_FROM, 'HH24:MI:SS')) AS A, "
	   		     		          +"(TO_CHAR(SHIFT_TO, 'HH24:MI:SS')) AS B, (TO_CHAR(SHIFT_DATE, 'yyyy-MM-dd')) AS C  from STAFF_SCHEDULE, STAFF where "
	   		                      +"(STAFF.STAFF_ID = STAFF_SCHEDULE.STAFF_ID AND "
	   		    		          +"TO_CHAR(SHIFT_FROM, 'HH24:MI:SS') = ? AND TO_CHAR(SHIFT_TO, 'HH24:MI:SS') = ? "
	   		    		          +"AND TO_CHAR(SHIFT_DATE, 'yyyy-MM-dd') = ? "
	   		                      + "AND ROLE = ? AND THEATRE_ID = ?)";
	   		     preparedStatementTime = connection.prepareStatement(sqlTime);
	   		     preparedStatementTime.setString(1, shift_from);
	   		     preparedStatementTime.setString(2, shift_to);
	   		     preparedStatementTime.setString(3, date);
	   		     preparedStatementTime.setString(4, role);
	   		     preparedStatementTime.setString(5, theatreid);
	   	
	   		     //System.out.println("shiftfrom: "+shift_from+" shiftto: "+shift_to+" role: "+role+" date: "+date+" theatreid: "+theatreid);
	   		 
	   		     ResultSet rsTime = preparedStatementTime.executeQuery();
	   		     if(rsTime.next()) {		   		    	 
	   		    	 System.out.println("Oops!! seems like another person is already working in this location for the same job at the same time slot on this day");
	                 System.out.println("Do you wanna try again?");
	                 exit = br.readLine();
	   		     } else {
	   		    	 //System.out.println("Good job... continue this");
	   		    	String sql1 = "insert into staff_schedule values (?, ?, ?, ?, ?)";
	   		    	preparedStatementInsert = connection.prepareStatement(sql1);
	   		    	preparedStatementInsert.setString(1, day);
	   		    	preparedStatementInsert.setTimestamp(2, sfrom);
	   		    	preparedStatementInsert.setTimestamp(3, sto);
	   		    	preparedStatementInsert.setInt(4, staff_id);    		    	
	   		    	preparedStatementInsert.setDate(5, shiftDate);
	    			
	    		   	  int intoSchedule = preparedStatementInsert.executeUpdate();
	    		   	  if(intoSchedule > 0) {
	    			    System.out.println("Schedule for "+staffname+" has been added succesfully");
	    		   	    System.out.println("Do you want to add another schedule? (y/n)");
	    			    exit = br.readLine();
	    		   	  } else
	    		   		  System.out.println("Something went wrong.. Please try again"); 
	   		     }
			 }   		     
		 }
	  
	  }	else {
		  System.out.println("The name you entered does not match our records");
	  }
	  
     } 
	  System.out.println("You have chosen to exit... Bye bye!!");
   }	catch (SQLException se) {
	            /*
	             * Handle errors for JDBC
	             */
	            se.printStackTrace();
	        } 
	          catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            /*
	             * finally block used to close resources
	             */
	            try
	            {
	                if (preparedStatement != null)
	                {
	                    preparedStatement.close();
	                }
	                if (preparedStatementdATE != null)
	                {
	                	preparedStatementdATE.close();
	                }
	                if (preparedStatementTime != null)
	                {
	                	preparedStatementTime.close();
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
