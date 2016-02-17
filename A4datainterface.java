//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

import java.io.*;
import java.sql.*;

public class A4datainterface {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	public static void main(String[] args) {
		A4datainterface face = new A4datainterface();
		face.showoptions();

	}

	public void showoptions() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println(
						"Welcome! Please select the corresponding number to the table for which you want to see data or type *** to exit");
				System.out.println("1.Theater - Lists the name of theatres and corresponding information");
				System.out.println("2.Screen - Lists the screens at particular theatres and corresponding information");
				System.out.println("3.Staff - Lists the information about a staff working at different theatres");
				System.out.println(
						"4.Staff Schedule - Lists information about the staff schedule working at different locations");
				System.out.println(
						"5.Staff Address - Lists the personl information of staff working under theatre group ");
				System.out.println(
						"6.Staff Privileges - This data is specific to admin, managers and owners with their credentials to update database");
				System.out.println(
						"7.Movie - Lists all the movies currently in the database and their corresponding info");
				System.out.println("8.Star - Lists the name of stars and their corresponding unique star_id");
				System.out.println("9.Movie Cast - Lists the name of all the stars in a the movies sorted accordingly");
				System.out.println("10.Movie Type - Lists the type and their description pertinent to movies");
				System.out.println("11.Movie Genre- Lists the movie genre(s) in which the movie falls ");
				System.out.println("12.Movie Schedule - Lists the show run times and corresponding information");
				System.out.println("13.Customer - What we will be without customers! Hit this to see customer info");
				System.out.println(
						"14.Buys - Lists the customer who boughts tickets to particular showtimes and corresponding information");
				System.out.println("15.Member - Lists the permamnent members of the theatre group");
				System.out.println("16.Credit card info - lists the credit card info of the members ");
				System.out.println(
						"17.Discounts - Lists the credit points which a member can earn on a partiular activity");
				System.out.println(
						"18.Member Status - Lists the credit points required to be platinum / silver / gold member");
				System.out.println(
						"19.Review Discussions - Lists the reviews / comments / discussions on movies and theatres");
				System.out.println("20.Vote - Lists the votes given to movies grouped accordinginly");
				String choice = br.readLine();
				if (choice.equals("***")) {
					break;
				}
				switch (choice) {
				case ("1"):
					System.out.println("Priting Theatre table!");
					viewtheatre();
					break;

				case ("2"):
					System.out.println("Priting Screens table!");
					viewscreen();
					break;

				case ("3"):
					System.out.println("Priting Staff table!");
					viewsstaff();
					break;

				case ("4"):
					System.out.println("Priting Staff Schedule table!");
					viewsstaffschedule();
					break;

				case ("5"):
					System.out.println("Priting Staff Address table!");
					viewstaffadd();
					break;

				case ("6"):
					System.out.println("Priting Staff Privileges table!");
					viewstaffpri();
					break;

				case ("7"):
					System.out.println("Priting Movies table!");
					viewmovie();
					break;

				case ("8"):
					System.out.println("Priting Star table!");
					viewstar();
					break;

				case ("9"):
					System.out.println("Priting Movie Cast table!");
					viewcast();
					break;

				case ("10"):
					System.out.println("Priting Movie type table!");
					viewtype();
					break;

				case ("11"):
					System.out.println("Priting Movie Genre table!");
					viewgenre();
					break;

				case ("12"):
					System.out.println("Priting Movie Schedule table!");
					viewmschedule();
					break;

				case ("13"):
					System.out.println("Priting customer table!");
					viewcustomer();
					break;

				case ("14"):
					System.out.println("Priting buys table!");
					viewbuys();
					break;

				case ("15"):
					System.out.println("Priting Member table!");
					viewmember();
					break;

				case ("16"):
					System.out.println("Priting Credit Card Info  table!");
					viewccard();
					break;

				case ("17"):
					System.out.println("Priting Discounts table!");
					viewdiscount();
					break;

				case ("18"):
					System.out.println("Priting Member Status table!");
					viewstatus();
					break;

				case ("19"):
					System.out.println("Priting Review Discussions table!");
					viewrd();
					break;

				case ("20"):
					System.out.println("Priting Vote table!");
					viewvote();
					break;
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

	public void viewtheatre() {
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM THEATRE";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String tid = rs.getString(1);
				String tname = rs.getString(2);
				String addr1 = rs.getString(3);
				String addr2 = rs.getString(4);
				int zip = rs.getInt(5);
				String city = rs.getString(6);
				String state = rs.getString(7);

				System.out.println("Thaetre Id:" + tid);
				System.out.println("Theatre Name:" + tname);
				System.out.println("Address Line 1: " + addr1);
				System.out.println("Address Line 2: " + addr2);
				System.out.println("City: " + city);
				System.out.println("State: " + state);
				System.out.println("ZIP: " + zip);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewscreen() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "Select * from SCREEN ORDER BY THEATRE_ID";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String screen_id = rs.getString(1);
				String tid = rs.getString(2);
				int capacity = rs.getInt(3);
				System.out.println("Screen ID: " + screen_id);
				System.out.println("Theatre ID: " + tid);
				System.out.println("Capacity: " + capacity);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewsstaff() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM STAFF ORDER BY STAFF_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String tid = rs.getString(1);
				int ssn = rs.getInt(2);
				String staffname = rs.getString(3);
				String phone = rs.getString(4);
				String role = rs.getString(5);
				int staff_id = rs.getInt(6);
				System.out.println("Theatre_ID:" + tid);
				System.out.println("SSN:" + ssn);
				System.out.println("Staff Name: " + staffname);
				System.out.println("Phone: " + phone);
				System.out.println("Role: " + role);
				System.out.println("Staff_ID: " + staff_id);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewsstaffschedule() {
		// date problem

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM STAFF_SCHEDULE ORDER BY STAFF_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String shift_day = rs.getString(1);
				String shift_from = rs.getString(2);
				String shift_to = rs.getString(3);
				String staffid = rs.getString(4);
				Date shift_date = rs.getDate(5);
				System.out.println("Staff ID: " + staffid);
				System.out.println("Shift Day:" + shift_day);
				System.out.println("Shift From: "+shift_from );
				System.out.println("Shift To: " +shift_to);
				System.out.println("Shift Date: " + shift_date);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewstaffadd() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT STAFF_ID,ADDR_LINE1,ADDR_LINE2,ZIP,CITY,STATE,STAFF_ID FROM STAFF_ADDRESS";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int staff_id = rs.getInt(1);
				String add1 = rs.getString(2);
				String add2 = rs.getString(3);
				int zip = rs.getInt(4);
				String city = rs.getString(5);
				String state = rs.getString(6);

				System.out.println("Staff ID: " + staff_id);
				System.out.println("Address Line 1:" + add1);
				System.out.println("Address Line 2: " + add2);
				System.out.println("ZIP: " + zip);
				System.out.println("City: " + city);
				System.out.println("State: " + state);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewstaffpri() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM STAFF_PRIVILEGES ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int staff_id = rs.getInt(1);
				String stafflogin = rs.getString(2);
				String password = rs.getString(3);

				System.out.println("Staff ID: " + staff_id);
				System.out.println("Staff Login ID:" + stafflogin);
				System.out.println("Password: " + password);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewmovie() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MOVIE ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String movid = rs.getString(1);
				String title = rs.getString(2);
				String des = rs.getString(3);
				String direc = rs.getString(4);
				System.out.println("Movie Id:" + movid);
				System.out.println("Title:" + title);
				System.out.println("Description: " + des);
				System.out.println("Director: " + direc);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewstar() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM STAR";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String stid = rs.getString(1);
				String name = rs.getString(2);
				System.out.println("Star Id:" + stid);
				System.out.println("Star Name:" + name);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewcast() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MOVIE_CAST ORDER BY MOVIE_ID";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String mid = rs.getString(1);
				int sid = rs.getInt(2);

				System.out.println("Movie Id:" + mid);
				System.out.println("Star Id:" + sid);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");
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

	public void viewtype() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MOVIE_TYPE ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String tid = rs.getString(1);
				String des = rs.getString(2);

				System.out.println("Type Id:" + tid);
				System.out.println("Description:" + des);
				System.out.println("--------------------------------------------------------------------------");

			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewgenre() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MOVIE_GENRE ORDER BY MOVIE_ID";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String mid = rs.getString(1);
				String tid = rs.getString(2);

				System.out.println("Movie Id:" + mid);
				System.out.println("Type Id:" + tid);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewmschedule() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MOVIE_SCHEDULE ORDER BY SCHEDULE_ID";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				
				String sid = rs.getString(1);
				String movieid = rs.getString(2);
				String screenid = rs.getString(3);
				Date date = rs.getDate(4);
				Float price =rs.getFloat(5);
				String stime = rs.getString(6);
				String end = rs.getString(7);
				int seatsavailable = rs.getInt(8);
				int revenue = rs.getInt(9);
				System.out.println("Schedule ID:" + sid);
				System.out.println("Movie ID:" +movieid);
				System.out.println("Screen ID: " +screenid);
				System.out.println("Date: " +date);
				System.out.println("Price: "+price);
				System.out.println("Start Time:"+stime);
				System.out.println("End Time:" +end);
				System.out.println("Seats Available: " +seatsavailable);
				System.out.println("Seats Revenue: " +revenue);
				System.out.println("--------------------------------------------------------------------------");

			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewcustomer() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM CUSTOMER ORDER BY CUSTOMER_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String cid = rs.getString(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String emailid = rs.getString(4);
				System.out.println("Customer ID:" + cid);
				System.out.println("Name:" + name);
				System.out.println("Phone: " +phone);
				System.out.println("Email ID: " +emailid);
				System.out.println("--------------------------------------------------------------------------");

			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");
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

	public void viewbuys() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM BUYS ORDER BY TICKET_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String cid = rs.getString(1);
				int TID = rs.getInt(2);
				String SID = rs.getString(3);
				Date date = rs.getDate(4);
				System.out.println("Customer ID:" + cid);
				System.out.println("Ticket ID:" +TID);
				System.out.println("Schedule ID: " +SID);
				System.out.println("Date: " +date);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewmember() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = " SELECT * FROM MEMBER ORDER BY MEMBER_ID";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				String userid = rs.getString(1);
				String pass = rs.getString(2);
				int cpoints = rs.getInt(3);
				String status = rs.getString(4);
				int memberid = rs.getInt(5);
				System.out.println("Member ID: " +memberid);
				System.out.println("User ID:" + userid);
				System.out.println("Password:" +pass);
				System.out.println("Credit Points: " +cpoints);
				System.out.println("Membership Status: "+status);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewccard() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM CREDIT_CARD ORDER BY MEMBER_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int memberid = rs.getInt(1);
				String cardno = rs.getString(2);
				String exp = rs.getString(3);
				String cardtype = rs.getString(4);
				Float balance = rs.getFloat(5);
				
				System.out.println("Member ID: " +memberid);
				System.out.println("Card Number:" + cardno);
				System.out.println("Expiry Date:" +exp);
				System.out.println("Card Type: " +cardtype);
				System.out.println("Balance: "+balance);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewdiscount() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM DISCOUNTS";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				
				String Activity = rs.getString(1);
				int cpoint = rs.getInt(2);
				System.out.println("Activity: " +Activity);
				System.out.println("Credit points earned:" + cpoint);
				System.out.println("--------------------------------------------------------------------------");
		
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewstatus() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM MEMBER_STATUS ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int cpoint = rs.getInt(1);
				String status = rs.getString(2);
				System.out.println("Total Credit Points: " +cpoint);
				System.out.println("Status Reached:" +status);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println(" ");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewrd() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			String sql = "SELECT * FROM REVIEW_DISCUSSION ORDER BY REVIEW_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int memberid = rs.getInt(1);
				String theatreid = rs.getString(2);
				String movieid = rs.getString(3);
				String mcomment = rs.getString(4);
				String pid = rs.getString(5);
				int rid = rs.getInt(6);
				String userid = rs.getString(7);
				Date date = rs.getDate(8);
				String rtype = rs.getString(9);
				String rfor = rs.getString(10);
				
				System.out.println("Review Id:" + rid);
				System.out.println("Member ID: "+memberid);
				System.out.println("Theatre ID:" +theatreid);
				System.out.println("Movie ID: " +movieid);
				System.out.println("Comment/Discussion: "  +mcomment);
				System.out.println("Parent Review ID:"+pid);
				System.out.println("User ID:" +userid);
				System.out.println("Review Date: " +date);
				System.out.println("Review Type: " +rtype);
				System.out.println("Review For: " +rfor);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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

	public void viewvote() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			String sql = "SELECT * FROM VOTE ORDER BY REVIEW_ID ";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				int rid = rs.getInt(1);
				int mid = rs.getInt(2);
				String value = rs.getString(3);
				
				
				
				System.out.println("Review ID: " +rid);
				System.out.println("Member ID:" +mid);
				System.out.println("Vote Value:" +value);
				System.out.println("--------------------------------------------------------------------------");
			}
			rs.close();
			System.out.println();
			System.out.println();
			System.out.println(
					"********************************************************************************************************");

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
