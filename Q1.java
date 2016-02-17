


//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640
//Answer 1 queries 
//Display the 3 most recent discussions/comments from a specific discussion thread
//user will be asked for input of movie - and 3 recent comments from that particular movie will be displayed 
// delete this line after reading - in review discussion table comment I have renamed to MCOMMENT
import java.io.*;

import java.sql.*;

public class Q1 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		Q1 mem = new Q1();
		mem.getcomments();
	}

	public void getcomments() {
		Connection connection = null;
		PreparedStatement preparedStatementmtitle = null;
		PreparedStatement preparedStatementcomment = null;
		PreparedStatement preparedStatementshowdata = null;
		PreparedStatement preparedStatementcomment2 = null;
		String moviename = " ", movieid = " ", userId = " ", Comment = " ", date = "" , pid = "" ,spid=" ";
		String titles=" ";

		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			System.out.println("Following movies are in our database at present");
			String sqlshowdata = " Select TITLE from Movie";
			preparedStatementshowdata  = connection.prepareStatement(sqlshowdata);
			ResultSet rs3 = preparedStatementshowdata.executeQuery();
			while (rs3.next()){
				titles = rs3.getString(1);
				System.out.println(titles);
				
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Welcome !! Enter the title of movie");
			moviename = br.readLine();

			/* sql statement */
			String sql = "SELECT MOVIE_ID FROM MOVIE WHERE TITLE = ?";
			/*
			 * Execute the query
			 */
			preparedStatementmtitle = connection.prepareStatement(sql);
			preparedStatementmtitle.setString(1, moviename);
			// making a result set rs
			ResultSet rs = preparedStatementmtitle.executeQuery();
			if (rs.next()) {
				movieid = rs.getString(1);
				System.out.println("Movie id :" + movieid);

				String sql1 = "SELECT review_date,USER_ID, MCOMMENT ,PARENT_REVIEW_ID from REVIEW_DISCUSSION where movie_id=? and rownum < 4 order by review_date ";
				preparedStatementcomment = connection.prepareStatement(sql1);
				preparedStatementcomment.setString(1, movieid);
				ResultSet rs2 = preparedStatementcomment.executeQuery();
				System.out.println("The 3 recent discussions/comments for the movie " + moviename + " are below");
				
				while (rs2.next()) {
					date = rs2.getString(1);
					userId = rs2.getString(2);
					Comment = rs2.getString(3);
					pid = rs2.getString(4);
				
					System.out.println("Parent Review Id = " +pid);
					System.out.println(date + "    " + userId + "  " + Comment);
					System.out.println("--------------------------------------------------------------------------------------------------");
				}
				rs.close();
				System.out.println("Select the Parent review Id for which you want to see three recent comments/discussion thread");
				spid = br.readLine();
				String sql5= "SELECT MCOMMENT,REVIEW_DATE,USER_ID FROM REVIEW_DISCUSSION WHERE PARENT_REVIEW_ID = ? and REVIEW_TYPE = 'child' AND ROWNUM <= 3 ORDER BY REVIEW_DATE ";
				preparedStatementcomment2 = connection.prepareStatement(sql5);
				preparedStatementcomment2.setString(1, spid);
				ResultSet rs4 = preparedStatementcomment2.executeQuery();
				while (rs4.next()){
					Comment = rs4.getString(1);
					date = rs4.getString(2);
					userId = rs4.getString(3);
					System.out.println(date+ "     "+userId+ "   "+Comment);
							
					
				}
				
				
				
			}

			else {
				System.out.println("This movie is not in our database");
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
				if (preparedStatementmtitle != null) {
					preparedStatementmtitle.close();
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
