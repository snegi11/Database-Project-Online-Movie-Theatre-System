


//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640
//Answer 6 queries 
//Display the theatre that are showing most number of movies 
import java.sql.*;
public class Q6 {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";
	
	static public void main(String[] args) {
		Q6 disp = new Q6();
	     disp.populartheatre(); 
	}
	public void populartheatre()
	{
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		try {
			//Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//string sql 
			String sql = "SELECT theatre.theatre_name,COUNT(THEATRE.THEATRE_NAME) From ( theatre join screen on screen.theatre_id = theatre.theatre_id AND SCREEN.SCREEN_ID IN (select screen_id from movie_schedule)) GROUP BY THEATRE.THEATRE_name HAVING COUNT (THEATRE.THEATRE_NAME) >= ALL (SELECT COUNT(THEATRE.THEATRE_NAME) From ( theatre join screen on screen.theatre_id = theatre.theatre_id AND SCREEN.SCREEN_ID IN (select screen_id from movie_schedule)) GROUP BY THEATRE.THEATRE_name)";
			preparedStatement1 = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement1.executeQuery();
			System.out.println("The theatres which shows most number of movies are ");
			while(rs.next()){
				String theatreis = rs.getString(1);
				System.out.println(theatreis);
				}
			rs.close();
		}
		catch (SQLException se) {
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
                if (preparedStatement1 != null)
                {
                    preparedStatement1.close();
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






/*select theatre_name from theatre 
 * where theatre_id in (select theatre_id 
 * from (SELECT theatre_id,count(theatre_id) 
 * from screen where screen_id in (select screen_id from movie_schedule) 
 * group by theatre_id order by count(theatre_id) desc)temp 
 * where rownum  < 2);*/
 