//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640
import java.io.*;
import java.sql.*;

public class updateType {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) {
		updateType star = new updateType();
		star.addTypeInfo();
	}

	public void addTypeInfo() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String typedesc = " ";
		int typeid = 901;
	
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true){
			System.out.println("Welcome manager ! Enter the type description  to be entered in the database or *** to exit");
			typedesc = br.readLine();
			if (typedesc.equals("***")){
				break;
			}else
			{
			if (typedesc.equals("")) {
				System.out.println("Please enter a description to continue");
				typedesc = br.readLine();
			}
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select MAX(TYPE_ID) as maxtypeid from movie_TYPE");
			if (rs.next()) {
				typeid = rs.getInt("maxtypeid");
				typeid  = typeid + 1;
			}
			System.out.println(typeid);
			String sql1 = "insert into movie_type values (?,?) ";
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, typeid);
			preparedStatement.setString(2, typedesc);
			int updated = preparedStatement.executeUpdate();
			
			System.out.println("New typeid entered: " +typeid);
			System.out.println("New star id is : "  +typedesc);

			System.out.println("Number of rows updated=" + updated);

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
