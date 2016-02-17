//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class buytickets {

	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		buytickets mem = new buytickets();
		mem.bookTicket();
	}

	public void bookTicket() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection connection = null;
		PreparedStatement preparedStatementuser = null;
		PreparedStatement preparedStatementbalance = null;
		PreparedStatement preparedStatementupdate = null;
		PreparedStatement preparedStatementtheatre = null;
		PreparedStatement preparedStatementmovieid = null;
		PreparedStatement preparedStatementcheckmovie = null;
		PreparedStatement preparedStatementbuysupdate = null;
		PreparedStatement preparedstatementseatav = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatementsold = null;
		PreparedStatement preparedStatementupdatesold = null;
		PreparedStatement preparedStatementupdatenames = null;
		PreparedStatement preparedStatementshowtheatre = null;
		String movie = "", theatrename = " ", t_id = " ", m_id = " ", userID = " ",Mname = " ";
		int memberid, numtckts = 0, tickid = 100001, counter = 1;
		float balance = 0.0f, price = 0.0f, tcktcost = 0.0f, sold = 0.0f;
		Date sch_date = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			System.out.println("Please enter your user id to continue");
			userID = br.readLine();

			String sqluser = "Select MEMBER_ID from MEMBER where USER_ID = ?";
			preparedStatementuser = connection.prepareStatement(sqluser);
			preparedStatementuser.setString(1, userID);
			ResultSet rsuser = preparedStatementuser.executeQuery();
			if (rsuser.next()) {

				System.out.println("Select the theatre name where you want to watch ");
				memberid = rsuser.getInt(1);
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
					System.out.println(t_id);
					String sqlmname = "SELECT DISTINCT TITLE FROM MOVIE WHERE MOVIE_ID IN (SELECT MOVIE_ID FROM MOVIE_SCHEDULE WHERE SCREEN_ID IN (SELECT SCREEN_ID FROM SCREEN WHERE THEATRE_ID =?))";
					preparedStatementupdatenames = connection.prepareStatement(sqlmname);
					preparedStatementupdatenames.setString(1,t_id );
					ResultSet rs3 = preparedStatementupdatenames.executeQuery();
					System.out.println("Following movies show times available");
					while (rs3.next()){
						Mname = rs3.getString(1);
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
						System.out.println(m_id);
						// system.out.will start - i have m_id of the movie
						// which is
						// valid and no i will check if that movie is played at
						// the t_id which was returned
						String sqlcheckmovie = "select DISTINCT price,SCHEDULE_ID,SCHEDULE_DATE,ST_TIME,END_TIME from MOVIE_SCHEDULE,theatre where movie_id = ? and screen_id in (SELECT  SCREEN_ID FROM SCREEN WHERE THEATRE_ID = ?)";
						preparedStatementcheckmovie = connection.prepareStatement(sqlcheckmovie);
						preparedStatementcheckmovie.setString(1, m_id);
						preparedStatementcheckmovie.setString(2, t_id);
						ResultSet rscheckmovie = preparedStatementcheckmovie.executeQuery();
						// now you have a movie which is played at the theatre
						// name you selected
						while (rscheckmovie.next()) {
							price = rscheckmovie.getFloat(1);
							String schid = rscheckmovie.getString(2);
							sch_date = rscheckmovie.getDate(3);
							Timestamp sttime = rscheckmovie.getTimestamp(4);
							Timestamp endtime = rscheckmovie.getTimestamp(5);
							System.out.println(" Schedule Id is:" + schid + "   " + sch_date
									+ "   " + sttime + "   " + endtime+ "price is: " + price );
						}

						System.out.println("Select Schedule ID of the showtime you want to watch");
						String sch_id = br.readLine();

						System.out.println("Please enter the no. of tickets you want to book");
						numtckts = Integer.parseInt(br.readLine());
						String sqlseatavailable = "SELECT SEATS_AVAILABLE FROM MOVIE_SCHEDULE WHERE SCHEDULE_ID = ?";
						preparedstatementseatav = connection.prepareStatement(sqlseatavailable);
						preparedstatementseatav.setString(1, sch_id);
						ResultSet rsseat = preparedstatementseatav.executeQuery();
						if (rsseat.next()) {
							int seatavailable = rsseat.getInt(1);
							System.out.println(seatavailable);
							if (seatavailable > numtckts) {
								String sqlbalance = "Select BALANCE from CREDIT_CARD where MEMBER_ID = ?";
								preparedStatementbalance = connection.prepareStatement(sqlbalance);
								preparedStatementbalance.setInt(1, memberid);
								ResultSet rsbalance = preparedStatementbalance.executeQuery();
								if (rsbalance.next()) {
									balance = rsbalance.getFloat(1);
									tcktcost = price * numtckts;
									System.out.println("Your current card balance: " + balance);
									if (balance >= tcktcost) {
										balance = balance - tcktcost;
										String sqlupdate = "Update CREDIT_CARD set BALANCE = ? where MEMBER_ID = ?";
										preparedStatementupdate = connection.prepareStatement(sqlupdate);
										preparedStatementupdate.setFloat(1, balance);
										preparedStatementupdate.setInt(2, memberid);

										preparedStatementupdate.executeUpdate();
										System.out.println(
												"Congratulations!! Your tickets have been booked successfully");
										System.out.println("You ticket cost: " + tcktcost
												+ " Card balance after this transaction: " + balance);

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
											preparedStatementbuysupdate = connection.prepareStatement(sqlupdatebuys);
											preparedStatementbuysupdate.setInt(1, memberid);
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
										if (rssold.next()) {
											sold = rssold.getInt(1);

											sold = sold + tcktcost;
											String sqlupdatesold = "UPDATE MOVIE_SCHEDULE SET SEATS_REVENUE = ? WHERE SCHEDULE_ID = ?";
											preparedStatementupdatesold = connection.prepareStatement(sqlupdatesold);
											preparedStatementupdatesold.setFloat(1, sold);
											preparedStatementupdatesold.setString(2, sch_id);
											preparedStatementupdatesold.executeUpdate();

										}

										modifypoint(memberid, numtckts);

									} else {
										System.out.println(
												"Oops!! You do not have sufficient balance to make this transaction");
									}
								}
							} else {
								System.out.println("The movie is Housefull");

							}
						} else {
							System.out.println("Sorry.. The movie name " + movie
									+ " you entered is not screened at the selected theatre " + theatrename
									+ " currently");
						}
					} else {
						System.out.println("The movie entered is not screened anywhere");
					}
				} else {
					System.out.println("Sorry this theatre name is incorrect  ");
				}

			} else {
				System.out.println("The userID you provided does not match our records..");
			}

		} catch (SQLException se)

		{
			/*
			 * Handle errors for JDBC
			 */
			se.printStackTrace();
		} catch (

		Exception e)

		{
			e.printStackTrace();
		} finally

		{
			/*
			 * finally block used to close resources
			 */
			try {
				if (preparedStatementuser != null) {
					preparedStatementuser.close();
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

	public void modifypoint(int memberid, int numtckts) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		PreparedStatement preparedStatement6 = null;
		String status = "";
		int credit_points = 0;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			String sql1 = "SELECT credit_points,membership_status,member_id from member where member_id=?";
			preparedStatement2 = connection.prepareStatement(sql1);
			preparedStatement2.setInt(1, memberid);
			ResultSet rs2 = preparedStatement2.executeQuery();
			if (rs2.next()) {

				status = rs2.getString("membership_status");
				credit_points = rs2.getInt("credit_points");

				String sql3 = "SELECT CREDIT_POINT from discounts where activity=?";
				preparedStatement3 = connection.prepareStatement(sql3);
				preparedStatement3.setString(1, "purchase_ticket");
				ResultSet rs3 = preparedStatement3.executeQuery();
				System.out.println("The membership_status is:" + status);
				System.out.println("The credit points are:" + credit_points);
				if (rs3.next()) {

					int currpoints = rs3.getInt("CREDIT_POINT");

					if (status.equalsIgnoreCase("standard")) {
						currpoints = currpoints + numtckts * 5;
					} else if (status.equalsIgnoreCase("silver")) {
						currpoints = currpoints + numtckts * 8;
					} else if (status.equalsIgnoreCase("gold")) {
						currpoints = currpoints + numtckts * 10;
					} else if (status.equalsIgnoreCase("platinum")) {
						currpoints = currpoints + numtckts * 12;
					}

					credit_points = credit_points + currpoints;

					if (credit_points >= 600) {
						status = "Platinum";
					} else if (credit_points >= 400) {
						status = "Gold";
					} else if (credit_points >= 200) {
						status = "Silver";
					} else
						status = "Standard";
				}
				String sql4 = "update member set credit_points=? ,membership_status=? where member_id=?";
				preparedStatement4 = connection.prepareStatement(sql4);
				preparedStatement4.setInt(1, credit_points);
				preparedStatement4.setString(2, status);
				preparedStatement4.setInt(3, memberid);
				int updated = preparedStatement4.executeUpdate();
				System.out.println("New membership status:" + status);
				System.out.println("New credit point is:" + credit_points);

				System.out.println("Number of rows updated=" + updated);
				
				String sq2= " SELECT ALERT FROM ALERTS WHERE MEMBER_ID = ?";
				preparedStatement6 = connection.prepareStatement(sq2);
				preparedStatement6.setInt(1,memberid);
				preparedStatement6.executeQuery();
				ResultSet rs6 = preparedStatement6.executeQuery();				
				if (rs6.next()){
				}
				else{
				String sql5 = " INSERT INTO ALERTS VALUES (?,?)";
				String ALERT1 = "UNREAD";
				preparedStatement5 = connection.prepareStatement(sql5);
				preparedStatement5.setInt(1,memberid);
				preparedStatement5.setString(2,ALERT1);
				preparedStatement5.executeUpdate();
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