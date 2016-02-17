
//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640
//Answer 7 queries 
//Display the theatre that has most online ticket sales 
import java.sql.*;

public class Q7 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		Q7 disp = new Q7();
		disp.maxsales();
	}

	public void maxsales() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		try {
			//Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//string sql 
			String sql = "SELECT THEATRE.THEATRE_NAME, SUM(SEATS_REVENUE) FROM (MOVIE_SCHEDULE JOIN SCREEN ON SCREEN.SCREEN_ID = MOVIE_SCHEDULE.SCREEN_ID JOIN THEATRE ON THEATRE.THEATRE_ID = SCREEN.THEATRE_ID) GROUP BY THEATRE.THEATRE_NAME HAVING SUM(SEATS_REVENUE) >= ALL(SELECT SUM(SEATS_REVENUE) FROM (MOVIE_SCHEDULE JOIN SCREEN ON SCREEN.SCREEN_ID = MOVIE_SCHEDULE.SCREEN_ID JOIN THEATRE ON THEATRE.THEATRE_ID = SCREEN.THEATRE_ID) GROUP BY THEATRE.THEATRE_NAME)";

			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			System.out.println("The theatre(s) with maximum online sales are ");
			while(rs.next()){
				String theatreis = rs.getString(1);
				int revenue = rs.getInt(2);
				System.out.println(theatreis+ "  with a revenue of:  " +revenue+" dollars ");
				}
			rs.close();
			

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
