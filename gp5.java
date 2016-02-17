//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640
//General Processes 
// Demonstrate the process of guest registers for the first time and purchasing a ticket 


import java.io.*;
import java.sql.*;

public class gp5 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		 gp5 mem = new gp5();
		mem.addMemberInfo();
	}

	public void addMemberInfo() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
			
	String name = "", phone = "", mail = "",cardNo = "";
	int customerID = 1001;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			// get inputs from the userID
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please enter your Name");
			name=br.readLine();
			if (name.equals("")) {	
			System.out.println("Please enter a Name to continue");
			name = br.readLine();
			}
			
			System.out.println("Please enter your Phone number");
			phone = br.readLine();
			if (phone.equals("")) {
				System.out.println("Please enter a valid phone number  to continue");
				phone = br.readLine();
			}
			System.out.println("Please enter your Email ID");
			mail = br.readLine();
			if (mail.equals("")) {
				System.out.println("Please enter a valid mail to continue");
				mail = br.readLine();
			}
			System.out.println("Please enter your Credit Card Number");
			cardNo = br.readLine();
			if (cardNo.equals("")) {
				System.out.println("Please enter a valid card number  to continue");
				cardNo = br.readLine();
			}
		
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select MAX(customer_id) as maxid from customer");
			if (rs.next()) {
				customerID = rs.getInt("maxid");
				customerID = customerID + 1;
			}

			 //sql to insert in Customer
			String sql1 = "insert into customer values (?, ?, ?, ?)";
			
			
			// insert into Customer
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, customerID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, mail);

			preparedStatement.executeUpdate();
			
			System.out.println("Your Customer id is: " + customerID);
			System.out.println("Do you want to buy tickets ? Y/N ");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			String input = br1.readLine();
			if (input.toLowerCase().equals("y")) {
			bookTicket(customerID);
			} else {
				System.out.println(
						"Either you not interested in boking tickets or you have pressed the wrong key ! Bye Bye ! ");
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
        public void bookTicket(int customerID) {
        
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection connection = null;
		PreparedStatement preparedStatementcust = null;
		PreparedStatement preparedStatementtheatre = null;
		PreparedStatement preparedStatementmovieid = null;
		PreparedStatement preparedStatementcheckmovie = null;
		PreparedStatement preparedstatementseatav = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatementbuysupdate =null;
		PreparedStatement preparedStatementsold = null;
		PreparedStatement preparedStatementupdatesold = null;
		
		PreparedStatement preparedStatementshowtheatre= null;
		PreparedStatement preparedStatementupdatenames = null;
		String movie = "", theatrename = " ", t_id = " ", m_id = " ";
		int  numtckts = 0, tickid = 100001, counter = 1;
		float price=0.0f, tcktcost = 0.0f , sold = 0.0f;
		Date sch_date = null;


		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			// get inputs from the userID

				System.out.println("Select the theatre name where you want to watch ");
				String sqlth = "SELECT THEATRE_NAME FROM THEATRE";
				preparedStatementshowtheatre = connection.prepareStatement(sqlth);
				ResultSet rsshow = preparedStatementshowtheatre.executeQuery();
				while(rsshow.next()){
					String y= rsshow.getString(1);
					System.out.println(y);
				}
				theatrename = br.readLine();
				String sqltheatre = "Select THEATRE_ID from THEATRE where THEATRE_NAME = ?";
				preparedStatementtheatre = connection.prepareStatement(sqltheatre);
				preparedStatementtheatre.setString(1, theatrename);
				ResultSet rstheatre = preparedStatementtheatre.executeQuery();
				if (rstheatre.next()) {
					t_id = rstheatre.getString(1);

					String sqlmname = "SELECT DISTINCT TITLE FROM MOVIE WHERE MOVIE_ID IN (SELECT MOVIE_ID FROM MOVIE_SCHEDULE WHERE SCREEN_ID IN (SELECT SCREEN_ID FROM SCREEN WHERE THEATRE_ID =?))";
					preparedStatementupdatenames = connection.prepareStatement(sqlmname);
					preparedStatementupdatenames.setString(1,t_id );
					ResultSet rs3 = preparedStatementupdatenames.executeQuery();
					System.out.println("Following movies have show times available");
					while (rs3.next()){
						String Mname = rs3.getString(1);
						System.out.println(Mname);
						
					}
					System.out.println("Please enter the movie for which you want to book ticktes");
					
					movie = br.readLine();
					
					String sqlmovieid = "Select MOVIE.MOVIE_ID from MOVIE WHERE TITLE = ?";
					preparedStatementmovieid = connection.prepareStatement(sqlmovieid);
					preparedStatementmovieid.setString(1, movie);
					ResultSet rsmovieid = preparedStatementmovieid.executeQuery();
					if (rsmovieid.next()) {
						m_id = rsmovieid.getString(1);
						System.out.println("Movie ID: "+m_id);
						
                     	String sqlcheckmovie = "select distinct price,SCHEDULE_ID,SCHEDULE_DATE,ST_TIME,END_TIME from MOVIE_SCHEDULE,theatre where movie_id = ? and screen_id in (SELECT  SCREEN_ID FROM SCREEN WHERE THEATRE_ID = ?)";
						preparedStatementcheckmovie = connection.prepareStatement(sqlcheckmovie);
						preparedStatementcheckmovie.setString(1, m_id);
						preparedStatementcheckmovie.setString(2, t_id);
						ResultSet rscheckmovie = preparedStatementcheckmovie.executeQuery();
						
							while (rscheckmovie.next()) {
							price = rscheckmovie.getFloat(1);
							String schid = rscheckmovie.getString(2);
							sch_date = rscheckmovie.getDate(3);
							Timestamp st_time= rscheckmovie.getTimestamp(4);
							Timestamp end_time= rscheckmovie.getTimestamp(5);
							
							System.out.println("Schedule Id:"+" "+schid+" "+"Start time:"+" "+st_time+" "+"End time:"+" "+end_time);
							
							}System.out.println("Select Schedule ID of the showtime you want to watch");
							String sch_id = br.readLine();
						
					
							System.out.println("Please enter the no. of tickets you want to book");
						
						    numtckts = Integer.parseInt(br.readLine());
				
							String sqlseatavailable = "SELECT SEATS_AVAILABLE FROM MOVIE_SCHEDULE WHERE SCHEDULE_ID = ?";
							preparedstatementseatav = connection.prepareStatement(sqlseatavailable);
							preparedstatementseatav.setString(1, sch_id);
							ResultSet rsseat = preparedstatementseatav.executeQuery();
							if (rsseat.next()) {
								int seatavailable = rsseat.getInt(1);
								System.out.println("No of Seats Available:"+seatavailable);
								if (seatavailable > numtckts) {
									
										tcktcost = price * numtckts;
										
							
											System.out.println(
													"Congratulations!! Your tickets have been booked successfully");
											     System.out.println("You ticket cost: " + tcktcost );

											seatavailable = seatavailable - numtckts;
											String sqlseat = "update MOVIE_SCHEDULE set seats_available =? where SCHEDULE_ID =?";
											preparedStatement4 = connection.prepareStatement(sqlseat);
											preparedStatement4.setInt(1, seatavailable);
											preparedStatement4.setString(2, sch_id);
											preparedStatement4.executeUpdate();

											while (counter <= numtckts) {
												Statement stmt = connection.createStatement();
												ResultSet rsticketid = stmt
														.executeQuery("Select MAX(ticket_id) as maxtid from buys");
												if (rsticketid.next()) {
													tickid = rsticketid.getInt("maxtid");
													tickid = tickid + 1;
												}

											String sqlupdatebuys = "insert into BUYS values (?, ?, ?, ?)";
											preparedStatementbuysupdate = connection
													.prepareStatement(sqlupdatebuys);
											preparedStatementbuysupdate.setInt(1, customerID);
											preparedStatementbuysupdate.setInt(2, tickid);
											preparedStatementbuysupdate.setString(3, sch_id);
								            preparedStatementbuysupdate.setDate(4, sch_date);
											preparedStatementbuysupdate.executeUpdate();
											counter = counter + 1;
											}
											String sqlsold = "SELECT SEATS_REVENUE FROM MOVIE_SCHEDULE WHERE SCHEDULE_ID = ?";
											preparedStatementsold = connection.prepareStatement(sqlsold);
											preparedStatementsold.setString(1, sch_id);
											ResultSet rssold = preparedStatementsold.executeQuery();
											while (rssold.next()) {
												sold = rssold.getInt(1);

												sold = sold + tcktcost;
												String sqlupdatesold = "UPDATE MOVIE_SCHEDULE SET SEATS_REVENUE = ? WHERE SCHEDULE_ID = ?";
												preparedStatementupdatesold = connection
														.prepareStatement(sqlupdatesold);
												preparedStatementupdatesold.setFloat(1, sold);
												preparedStatementupdatesold.setString(2, sch_id);
												preparedStatementupdatesold.executeUpdate();
											
											}	
										}
										
									 else {
										
										 System.out.println("The movie is Housefull");
									}
								
							       }
								
							 else {
								System.out.println("Sorry.. The movie name " + movie
										+ " you entered is not screened at the selected theatre " + theatrename
										+ " currently");

							        }
					                 }else
									{
										System.out.println("The movie entered is not screened anywhere");
									}
							
							
					}else {
						System.out.println("Sorry this theatre name is incorrect  ");
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
				if (preparedStatementcust != null) {
					preparedStatementcust.close();
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
        

