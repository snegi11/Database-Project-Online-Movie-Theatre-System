//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

//Queries - 3 (least popular and most popular discussion threads)
import java.io.*;
import java.sql.*;

public class LeastViewed {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";
	
	static public void main(String[] args) throws IOException {		
		LeastViewed view = new LeastViewed();
		//System.out.println("Welcome to our discussion forum");
		view.viewLeastReview();
		view.viewMostReview();	
	}
	
	public void viewLeastReview() {
		Connection connection = null;
		PreparedStatement preparedStatementfind = null;		
		String review="", title="", theatre="";
		int reviewid = 0;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			
			Statement stmt = connection.createStatement();
			String sqlleast = "SELECT PARENT_REVIEW_ID, REVIEW_DISCUSSION.REVIEW_ID, MCOMMENT, COUNT(PARENT_REVIEW_ID) AS CNT, COUNT(VOTE.REVIEW_ID) AS CNT2 "+
			                  "FROM REVIEW_DISCUSSION, VOTE "+"WHERE VOTE.REVIEW_ID = REVIEW_DISCUSSION.REVIEW_ID "+
					          "GROUP BY PARENT_REVIEW_ID, REVIEW_DISCUSSION.REVIEW_ID, MCOMMENT ORDER BY CNT";
			ResultSet rs = stmt.executeQuery(sqlleast);
			if(rs.next()) {
				reviewid = rs.getInt(2);
				review = rs.getString(3);
				String sqlfind = "SELECT TITLE, THEATRE_NAME, REVIEW_DISCUSSION.MCOMMENT FROM MOVIE, REVIEW_DISCUSSION, THEATRE "+
				                 "WHERE (REVIEW_DISCUSSION.MOVIE_ID = MOVIE.MOVIE_ID "+
						         "AND THEATRE.THEATRE_ID = REVIEW_DISCUSSION.THEATRE_ID "+
				                 "AND REVIEW_DISCUSSION.REVIEW_ID = ?)";
				preparedStatementfind = connection.prepareStatement(sqlfind);
				preparedStatementfind.setInt(1, reviewid);
				ResultSet rsfind = preparedStatementfind.executeQuery();
				if(rsfind.next()) {
					title = rsfind.getString(1);
					theatre = rsfind.getString(2);
					
					System.out.println("The review which is least popular in this discussion forum is:");				
					System.out.println("--------------------------------------------------------------");
					//System.out.println();
					System.out.println("reviewid: "+reviewid+"   comment: '"+review+"' for the movie '"+title+"' watched at '"+theatre+"'");
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
			if (preparedStatementfind != null) {
				preparedStatementfind.close();
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
	
	public void viewMostReview() {	
      
		Connection connection = null;
		PreparedStatement preparedStatementfind = null;		
		String review="", title="", theatre="";
		int reviewid = 0;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			
			Statement stmt = connection.createStatement();
			String sqlleast = "SELECT PARENT_REVIEW_ID, REVIEW_DISCUSSION.REVIEW_ID, MCOMMENT, COUNT(PARENT_REVIEW_ID) AS CNT, COUNT(VOTE.REVIEW_ID) AS CNT2 "+
			                  "FROM REVIEW_DISCUSSION, VOTE "+"WHERE VOTE.REVIEW_ID = REVIEW_DISCUSSION.REVIEW_ID "+
					          "GROUP BY PARENT_REVIEW_ID, REVIEW_DISCUSSION.REVIEW_ID, MCOMMENT ORDER BY CNT DESC";
			ResultSet rs = stmt.executeQuery(sqlleast);
			if(rs.next()) {
				reviewid = rs.getInt(2);
				review = rs.getString(3);
				String sqlfind = "SELECT TITLE, THEATRE_NAME, REVIEW_DISCUSSION.MCOMMENT FROM MOVIE, REVIEW_DISCUSSION, THEATRE "+
				                 "WHERE (REVIEW_DISCUSSION.MOVIE_ID = MOVIE.MOVIE_ID "+
						         "AND THEATRE.THEATRE_ID = REVIEW_DISCUSSION.THEATRE_ID "+
				                 "AND REVIEW_DISCUSSION.REVIEW_ID = ?)";
				preparedStatementfind = connection.prepareStatement(sqlfind);
				preparedStatementfind.setInt(1, reviewid);
				ResultSet rsfind = preparedStatementfind.executeQuery();
				if(rsfind.next()) {
					title = rsfind.getString(1);
					theatre = rsfind.getString(2);
					System.out.println();
					System.out.println("The review which is most popular in this discussion forum is:");				
					System.out.println("-------------------------------------------------------------");	
					//System.out.println();
					System.out.println("reviewid: "+reviewid+"   comment: '"+review+"' for the movie '"+title+"' watched at '"+theatre+"'");
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
			if (preparedStatementfind != null) {
				preparedStatementfind.close();
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
