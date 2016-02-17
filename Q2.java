//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.sql.*;
import java.text.ParseException;

public class Q2 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws ParseException {
		Q2 review = new Q2();
		review.seeThreads();
	}

	public void seeThreads() {

		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;

		int pid = 0;
		String userid = " ", comment = " ", date = " ", moviename = " " ,pcomment=" ";
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			// string sql
			String sql = "SELECT PARENT_REVIEW_ID,TITLE,MCOMMENT FROM REVIEW_DISCUSSION JOIN MOVIE ON REVIEW_DISCUSSION.MOVIE_ID = MOVIE.MOVIE_ID WHERE REVIEW_TYPE ='parent'";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			while (rs.next()) {
				pid = rs.getInt(1);
				moviename = rs.getString(2);
				pcomment = rs.getString(3);
				String sql1 = "SELECT USER_ID,MCOMMENT,REVIEW_DATE FROM REVIEW_DISCUSSION WHERE PARENT_REVIEW_ID =? and REVIEW_TYPE ='child' and ROWNUM <= 3 order by review_date";
				preparedStatement2 = connection.prepareStatement(sql1);
				preparedStatement2.setInt(1, pid);
				ResultSet rs1 = preparedStatement2.executeQuery();
				System.out.println("The three recent discussion/comments for movie " + moviename
						+ " on parent review id  " + pid + " with parent comment "+pcomment+ " is: ");
				while (rs1.next()) {
					userid = rs1.getString(1);
					comment = rs1.getString(2);
					date = rs1.getString(3);
					System.out.println(date + "  " + userid + "  " + comment);

				}
				System.out.println();
				System.out.println();
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
			}

		}

		catch (Exception e) {
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
