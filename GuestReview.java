//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

//general processes 4 (Guest visits a website and views reviews but cannot write a review)
import java.io.*;
import java.sql.*;

public class GuestReview {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";
	
	static public void main(String[] args) throws IOException {
		
		GuestReview guest = new GuestReview();
		guest.executeGuest();	    
	}
	
	public void executeGuest() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to our website... Please select from below as how you want to login:");
		System.out.println("1. Guest mode");
		System.out.println("2. As a member");
		int choice = Integer.parseInt(br.readLine());
		if(choice == 1) {
			System.out.println("You have selected to use guest mode...");
			System.out.println("If you login with guest mode you will only be able to view the reviews for movies/theatres but cannot write a new review or reply to any review..");
			System.out.println("Do you want to continue logging in as guest? (Y/N)");
			String exit = br.readLine();
			if(exit.equalsIgnoreCase("Y")) {
				viewReviews();
			} else {
				System.out.println("Do you want to register yourself with us as a member? Its free and you can get rewards and discounts for your review contributions as well!! (Y/N)");
			    String mem = br.readLine();
			    if(mem.equalsIgnoreCase("Y")) {
			    	gp1 reg = new gp1();
			    	reg.addMemberInfo();
			    } else {
			    	System.out.println("Thank you for visting us!!");
			    }
			} 
		} else if(choice == 2) {
			Reviews review = new Reviews();	    
		    review.LoginUser();
		}
	}
	
	public void viewReviews() {

		Connection connection = null;
		PreparedStatement preparedStatementmovie = null;
		PreparedStatement preparedStatementreview = null;	
		PreparedStatement preparedStatementtheatre = null;
		String moviename="", movieid="", review="", userid="", type="", rfor="", theatrename="", theatreid="";
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			// get inputs from the userID
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String exit = "Y";
			while(exit.equalsIgnoreCase("Y")) {
				System.out.println("What do you want to do?... 1) View Movie reviews 2) View Theatre reviews");  
				int choiceRev = Integer.parseInt(br.readLine());
				if(choiceRev == 1) {
					rfor = "movie";
					System.out.println("Please enter the movie name for which you want to view the reviews");
					moviename = br.readLine();
					String sqlmovie = "Select MOVIE_ID from MOVIE where (TITLE=?)";
					preparedStatementmovie = connection.prepareStatement(sqlmovie);
					preparedStatementmovie.setString(1, moviename);
					ResultSet rsmovie = preparedStatementmovie.executeQuery();
					if(rsmovie.next()) {
						movieid = rsmovie.getString(1);
						String sqlreviews = "SELECT PARENT_REVIEW_ID, REVIEW_TYPE, USER_ID, MCOMMENT "
		    		            +"FROM REVIEW_DISCUSSION WHERE (MOVIE_ID = ? AND REVIEW_FOR = ?) "
		    		            +"GROUP BY PARENT_REVIEW_ID, REVIEW_TYPE, USER_ID, MCOMMENT "
		    		            +"ORDER BY PARENT_REVIEW_ID, REVIEW_TYPE DESC";
		                preparedStatementreview = connection.prepareStatement(sqlreviews);
		                preparedStatementreview.setString(1, movieid);
		                preparedStatementreview.setString(2, rfor);
		                ResultSet rsreviews = preparedStatementreview.executeQuery();
		                int count = 0;
		    
		                System.out.println("    "+"userid"+"      "+"Comment  ");
		                System.out.println("    "+"------"+"      "+"-------");
		     
		                while(rsreviews.next()) {	
		                	type = rsreviews.getString(2); 
					    	userid = rsreviews.getString(3);
					    	review = rsreviews.getString(4);
					    	if(type.equalsIgnoreCase("parent")) {
					    		System.out.println();
					    		System.out.println("    "+ userid+"      "+review);	
					    	} else
					    		System.out.println("      "+ userid+"        "+review);	
					    	count++;
		                 }
		                
		                 if(count == 0) {
		    	            System.out.println("Sorry we currently do not have any reviews for this movie");
		    	            System.out.println();
			 				System.out.println("Do you want to view reviews for another movie? (Y/N)");
			 				exit = br.readLine();
		                 } 
		                 
		                 System.out.println();
		 				 System.out.println("Do you want to view reviews for another movie? (Y/N)");
		 				 exit = br.readLine();
					     
					} else {
						System.out.println("Sorry!! The movie name you entered does not match our records.. Please try again!!");
						System.out.println("Do you want to try again? (Y/N)");
						exit = br.readLine();
					}
				} else if(choiceRev == 2) {
					rfor = "theatre";
					System.out.println("Please enter the theatre name for which you want to view the reviews");
					theatrename = br.readLine();
					String sqltheatre = "Select THEATRE_ID from THEATRE where (THEATRE_NAME=?)";
					preparedStatementtheatre = connection.prepareStatement(sqltheatre);
					preparedStatementtheatre.setString(1, theatrename);
					ResultSet rstheatre = preparedStatementtheatre.executeQuery();
					if(rstheatre.next()){
						theatreid = rstheatre.getString(1);
						String sqlreviews = "SELECT PARENT_REVIEW_ID, REVIEW_TYPE, USER_ID, MCOMMENT "
		    		            +"FROM REVIEW_DISCUSSION WHERE (THEATRE_ID = ? AND REVIEW_FOR = ?) "
		    		            +"GROUP BY PARENT_REVIEW_ID, REVIEW_TYPE, USER_ID, MCOMMENT "
		    		            +"ORDER BY PARENT_REVIEW_ID, REVIEW_TYPE DESC";
		                preparedStatementreview = connection.prepareStatement(sqlreviews);
		                preparedStatementreview.setString(1, theatreid);
		                preparedStatementreview.setString(2, rfor);
		                ResultSet rsreviews = preparedStatementreview.executeQuery();
		                int count = 0;
		    
		                System.out.println("    "+"userid"+"      "+"Comment  ");
		                System.out.println("    "+"------"+"      "+"-------");
		     
		                while(rsreviews.next()) {	
		                	type = rsreviews.getString(2); 
					    	userid = rsreviews.getString(3);
					    	review = rsreviews.getString(4);
					    	if(type.equalsIgnoreCase("parent")) {
					    		System.out.println();
					    		System.out.println("    "+ userid+"      "+review);	
					    	} else
					    		System.out.println("      "+ userid+"        "+review);	
					    	count++;
		                 }
		                
		                 if(count == 0) {
		    	            System.out.println("Sorry we currently do not have any reviews for this theatre");
		                 } 	
		                 
		                 System.out.println();
		 				 System.out.println("Do you want to view reviews for another theatre? (Y/N)");
		 				 exit = br.readLine();	
					     
					} else {
						System.out.println("Sorry!! The theatre name you entered does not match our records.. Please try again!!");
						System.out.println("Do you want to try again? (Y/N)");
						exit = br.readLine();
					}
				} else {
					System.out.println("Invalid choice.. please try again!!");
				}
			
		}	
			System.out.println("Thankyou for visiting us... do visit again!!");	
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
			if (preparedStatementmovie != null) {
				preparedStatementmovie.close();
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
