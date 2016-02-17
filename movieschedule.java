//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640



	import java.io.BufferedReader;

	import java.io.InputStreamReader;
	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.text.SimpleDateFormat;

	public class movieschedule {
		public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
		public static final String DBUSER = "mkejriw1";
		public static final String DBPASS = "13nov1989";

		static public void main(String[] args) {
			movieschedule screeninsert = new movieschedule();
			screeninsert.addschedule();
			
		}
		String sch_date= null ;
		String screenid = "SN 07/01" ;
		
		
		public void addschedule() {
			Connection connection = null;
			PreparedStatement preparedStatement1 = null;
			PreparedStatement preparedStatement2 = null;
			PreparedStatement preparedStatement3 = null;
			String movieid = " ", title = " ",movieid1=" ",latest = " " , schid = " " ,starttime = " ", endtime=" ";
			
			int capacity = 40;
			int cost = 0, seatrevenue = 0;
			java.util.Date from, to;
			Object sfrom, sto;
			
				try {
					// Register driver manager
					DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

					// connect to database
					connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String sql1 = "Select movie_id , title from MOVIE order by movie_id";
					preparedStatement1 = connection.prepareStatement(sql1);
					ResultSet rs = preparedStatement1.executeQuery();
					while(rs.next()){
						movieid = rs.getString(1);
						title = rs.getString(2);
						System.out.println(movieid + "  " + title);
						}
					while(true){
						
						System.out.println("Enter the movie ID of the selected movie you want this screen to show or *** to exit ");
						movieid1 = br.readLine();
						System.out.println(movieid1);
						if (movieid1.equalsIgnoreCase("***")){
							break;
						}
					System.out.println("Enter the movie ID of the selected movie you want this screen to show");
					movieid1 = br.readLine();
					System.out.println(movieid1);
					String sql2 = " SELECT SCHEDULE_ID  FROM MOVIE_SCHEDULE  WHERE rownum = 1  order by SCHEDULE_ID DESC";
					preparedStatement2 = connection.prepareStatement(sql2);
					ResultSet rs1 = preparedStatement2.executeQuery();
					if (rs1.next())
					{
						 latest = rs1.getString(1);
						System.out.println("Please enter the schedule id in format (SC-- ),the last schedule id entered was "+latest);
						schid = br.readLine();
						if (schid.equals("")) {
							System.out.println("Please enter a valid schedule id  to continue");
							schid = br.readLine();
						}
						
						System.out.println("Please enter the price you want to set" );
						cost = Integer.parseInt(br.readLine());
						if (cost == 0 ) {
							System.out.println("Please enter a valid price to continue");
							cost = Integer.parseInt(br.readLine());
						} 
						System.out.println("Please enter the Schedule Date in format MM-dd-yyyy ");
						sch_date = br.readLine();
						SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy"); 
						
						java.util.Date date = sdf.parse(sch_date);
						
						
						SimpleDateFormat sdftime = new SimpleDateFormat("hh:mm:ss");   
		                java.sql.Date sqlDate = new Date(date.getTime());
						
						seatrevenue = 0;
						
						System.out.println("Please enter the show start time (HH:MI:SS) 24hr format");
						starttime =br.readLine();
						if(starttime.equals(" "))
								{
			    			  System.out.println("Please enter a valid start time to continue");
			    			  starttime = br.readLine();
			    		  }
						
						 from = sdftime.parse(starttime);
						 sfrom = new java.sql.Timestamp(from.getTime());   
						 
						 System.out.println("Please enter the show end time (HH:MI:SS) 24hr format");
			    		  endtime = br.readLine(); 
			    		
			    		  if(endtime.equals("")) {
			    			  System.out.println("Please enter a valid end time to continue");
			    			  endtime = br.readLine();
			    		  }
			    		  
			    		  to = sdftime.parse(endtime);	  
			    		  sto = new java.sql.Timestamp(to.getTime());
			    		  
			    		  
			    	
			    			  String sql3 = "insert into MOVIE_SCHEDULE values (?,?,?,?,?,?,?,?,?)";
			      			  preparedStatement3 = connection.prepareStatement(sql3);
			      		      preparedStatement3.setString(1, schid);
			      			  preparedStatement3.setString(2, movieid1);
			      			  preparedStatement3.setString(3, screenid);
			      			  preparedStatement3.setDate(4, sqlDate);
			      			  preparedStatement3.setInt(5, cost);
			      			  preparedStatement3.setObject(6, sfrom);
			      			  preparedStatement3.setObject(7, sto);
			      			  preparedStatement3.setInt(8, capacity);
			      			  preparedStatement3.setInt(9, seatrevenue);
			      			
			      		   	  int intoSchedule = preparedStatement3.executeUpdate();
			      		   	  if(intoSchedule > 0)
			      			    System.out.println("Schedule for "+screenid+" has been added succesfully");
			      		   	  else
			      		   		  System.out.println("Something went wrong.. Please try again"); 	
						
					}		
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

}
