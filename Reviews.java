//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640

//General Processes - 2
import java.io.*;
import java.sql.*;
import java.util.Calendar;

public class Reviews {
	public static final String DBURL = "jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	public static final String DBUSER = "mkejriw1";
	public static final String DBPASS = "13nov1989";

	static public void main(String[] args) throws IOException {
	    Reviews review = new Reviews();	    
	    review.LoginUser();
	}
	
	public void LoginUser() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String userID = "", password = "", status="";
		int points, customerID = 0;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			// get inputs from the userID
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Welcome member, please provide your used ID to begin");
			userID = br.readLine();
			System.out.println("Please enter your password to login");
			password = br.readLine();
			
			String sql = "Select MEMBER_ID, CREDIT_POINTS, MEMBERSHIP_STATUS from MEMBER where (USER_ID=? AND PASSWORD=?)";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userID);
			preparedStatement.setString(2, password);			
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				customerID = rs.getInt(1);
				points = rs.getInt(2);
				status = rs.getString(3);
				System.out.println("Welcome to the discussion forum... A lot has happened over here since you last visited");
				System.out.println("What do you want to do?");
				System.out.println("1. Write a new review");
				System.out.println("2. View reviews for a particular movie/theatre");
				System.out.println("3. View the least popular review comment");
				System.out.println("4. View the most popular review comment");
				int choice = Integer.parseInt(br.readLine());
				if(choice == 1) {
					writeReview(userID, customerID, points, status);
				} else if(choice == 2) {
					viewReviews(userID, customerID, points, status);
				} else if(choice == 3) {
					LeastViewed view = new LeastViewed();
					view.viewLeastReview();
				} else if(choice == 4) {
					LeastViewed view = new LeastViewed();
					view.viewMostReview();
				} else {
					System.out.println("Please provide a valid choice");
				}
			} else {
				System.out.println("Oops!! The login information you provided does not match our records");
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
	
	public void writeReview(String userID, int customerID, int points, String status) {
		Connection connection = null;
		PreparedStatement preparedStatementmovie = null;
		PreparedStatement preparedStatementtheatre = null;
		PreparedStatement preparedStatementcomment = null;
		String comment = "", theatreid = "", theatrename = "", movieid = "", moviename = "", rfor = "", type = "parent";
		int parentID = 101, reviewid = 101;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		  // get inputs from the userID
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  String exit = "Y";
		  while(exit.equalsIgnoreCase("Y")) {
			System.out.println("Please specify what you want to review... 1) Movie 2) Theatre");  
			int choice = Integer.parseInt(br.readLine());
			if(choice == 1) {
				rfor = "movie";
				System.out.println("Please fill in the details below to review the movie");
			} else if(choice == 2) {
				rfor = "theatre";
				System.out.println("Please fill in the details below to review the theatre");
			} else {
				System.out.println("Invalid choice.. please try again!!");
			}
			System.out.println("Please enter the movie name that you watched");
			moviename = br.readLine();
			String sqlmovie = "Select MOVIE_ID from MOVIE where (TITLE=?)";
			preparedStatementmovie = connection.prepareStatement(sqlmovie);
			preparedStatementmovie.setString(1, moviename);
			ResultSet rsmovie = preparedStatementmovie.executeQuery();
			if(rsmovie.next()){
				movieid = rsmovie.getString(1);
				System.out.println("Please specify the theatre where you watched this movie");				
				theatrename = br.readLine();
				String sqltheatre = "Select THEATRE_ID from THEATRE where (THEATRE_NAME=?)";
				preparedStatementtheatre = connection.prepareStatement(sqltheatre);
				preparedStatementtheatre.setString(1, theatrename);
				ResultSet rstheatre = preparedStatementtheatre.executeQuery();
				if(rstheatre.next()) {
					theatreid = rstheatre.getString(1);
					System.out.println("Please enter your review comment");
					comment = br.readLine();
					
					//assigning new review id to this review comment
					Statement stmtrid = connection.createStatement();
					ResultSet rsrid = stmtrid.executeQuery("Select MAX(REVIEW_ID) as MAXRID from REVIEW_DISCUSSION");
					if(rsrid.next()) {
						reviewid = rsrid.getInt("MAXRID");
						reviewid = reviewid+1;							
					}
					
					//assigning new parent review id to this review comment
					Statement stmtpid = connection.createStatement();
					ResultSet rspid = stmtpid.executeQuery("Select MAX(PARENT_REVIEW_ID) as MAXPID from REVIEW_DISCUSSION");
					if(rspid.next()) {
						parentID = rspid.getInt("MAXPID");
						//System.out.println("Max parent id selected: "+parentID);
						parentID = parentID+1;	
						//System.out.println("New parent id: "+parentID);
					}					
					
					Calendar calendar = Calendar.getInstance();
				    java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());				   
					
					String sqlcomment = "Insert into REVIEW_DISCUSSION values (?,?,?,?,?,?,?,?,?,?)";
					preparedStatementcomment = connection.prepareStatement(sqlcomment);
					preparedStatementcomment.setInt(1, customerID);
					preparedStatementcomment.setString(2, theatreid);
					preparedStatementcomment.setString(3, movieid);
					preparedStatementcomment.setString(4, comment);					
					preparedStatementcomment.setInt(5, parentID);
					preparedStatementcomment.setInt(6, reviewid);
					preparedStatementcomment.setString(7, userID);
					preparedStatementcomment.setDate(8, today);
					preparedStatementcomment.setString(9, type);
					preparedStatementcomment.setString(10, rfor);
					
					int update = preparedStatementcomment.executeUpdate();
					if(update > 0) {
						System.out.println("Thank you for submitting your review");
						points = calculatePoints(customerID, points, status, "review");
						System.out.println("Do you want to write a review for another movie/theatre? (Y/N)");
						exit = br.readLine();
					} else {
						System.out.println("Oops!! Something went wrong please try again");
					}
					
				} else {
					System.out.println("Sorry!! We do not recognize the theatre name you entered.. Please try again!!");
					System.out.println("Do you want to try again? (Y/N)");
					exit = br.readLine();
				}
			} else {
				System.out.println("Sorry!! The movie name you entered does not match our records.. Please try again!!");
				System.out.println("Do you want to try again? (Y/N)");
				exit = br.readLine();
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
			if (preparedStatementtheatre != null) {
				preparedStatementtheatre.close();
			}			

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		try {
			if (preparedStatementcomment != null) {
				preparedStatementcomment.close();
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
  
	public void viewReviews(String userID, int customerID, int points, String status) {
		Connection connection = null;
		PreparedStatement preparedStatementmovie = null;
		PreparedStatement preparedStatementreview = null;
		PreparedStatement preparedStatementcomment = null;
		PreparedStatement preparedStatementtheatre = null;
		String comment="", moviename="", movieid="", theatrename="", theatreid="", review="", ucomment="", rfor="", type="child";
		int reviewID = 101, parentID = 101;
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
					if(rsmovie.next()){
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
		                	  parentID = rsreviews.getInt(1);
		    	              type = rsreviews.getString(2); 
		    	              ucomment = rsreviews.getString(3);
		    	              review = rsreviews.getString(4);
		    	              if(type.equalsIgnoreCase("parent")) {
		    		              System.out.println();
		    		              System.out.println(parentID+"    "+ ucomment+"      "+review);	
		    	              } else
		    		              System.out.println("          "+ ucomment+"        "+review);	
		    	              count++;
		                 }
		                
		                 if(count == 0) {
		    	            System.out.println("Sorry we currently do not have any reviews for this movie");
		                 } else {
		    	            System.out.println();
					    	System.out.println("Do you like to reply to any of the comments displayed above? Type(Y/N)");
					    	String choice = br.readLine();
					    	if(choice.equalsIgnoreCase("Y")) {
					    		System.out.println("Please enter the ID of the review for which you wish to reply");
					    		parentID = Integer.parseInt(br.readLine());
					    		System.out.println("Please specify the theatre where you watched this movie");				
								theatrename = br.readLine();
								String sqltheatre = "Select THEATRE_ID from THEATRE where (THEATRE_NAME=?)";
								preparedStatementtheatre = connection.prepareStatement(sqltheatre);
								preparedStatementtheatre.setString(1, theatrename);
								ResultSet rstheatre = preparedStatementtheatre.executeQuery();
								if(rstheatre.next()) {
									theatreid = rstheatre.getString(1);
									System.out.println("Please enter your review comment");
									comment = br.readLine();
									
									//assigning new review id to this review comment
									Statement stmtrid = connection.createStatement();
									ResultSet rsrid = stmtrid.executeQuery("Select MAX(REVIEW_ID) as MAXRID from REVIEW_DISCUSSION");
									if(rsrid.next()) {
										reviewID = rsrid.getInt("MAXRID");
										reviewID = reviewID+1;							
									}																	
									
									Calendar calendar = Calendar.getInstance();
								    java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());				    		   
									
									String sqlcomment = "Insert into REVIEW_DISCUSSION values (?,?,?,?,?,?,?,?,?,?)";
									preparedStatementcomment = connection.prepareStatement(sqlcomment);
									preparedStatementcomment.setInt(1, customerID);
									preparedStatementcomment.setString(2, theatreid);
									preparedStatementcomment.setString(3, movieid);
									preparedStatementcomment.setString(4, comment);					
									preparedStatementcomment.setInt(5, parentID);
									preparedStatementcomment.setInt(6, reviewID);
									preparedStatementcomment.setString(7, userID);
									preparedStatementcomment.setDate(8, today);
									preparedStatementcomment.setString(9, "child");
									preparedStatementcomment.setString(10, rfor);
									
									int update = preparedStatementcomment.executeUpdate();
									if(update > 0) {
										System.out.println("Thank you for submitting your review");
										points = calculatePoints(customerID, points, status, "comment");
										System.out.println("Do you want to view reviews for another movie? (Y/N)");
										exit = br.readLine();
									} else {
										System.out.println("Oops!! Something went wrong please try again");
									}
									
								} else {
									System.out.println("Sorry!! We do not recognize the theatre name you entered.. Please try again!!");
									System.out.println("Do you want to try again? (Y/N)");
									exit = br.readLine();
								}
					    	} else {
					    		System.out.println("Do you want to view reviews for another movie/theatre ? (Y/N)");
								exit = br.readLine();
					    	}
					    }			     
					     
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
		                	  parentID = rsreviews.getInt(1);
		    	              type = rsreviews.getString(2); 
		    	              ucomment = rsreviews.getString(3);
		    	              review = rsreviews.getString(4);
		    	              if(type.equalsIgnoreCase("parent")) {
		    		              System.out.println();
		    		              System.out.println(parentID+"    "+ ucomment+"      "+review);	
		    	              } else
		    		              System.out.println("          "+ ucomment+"        "+review);	
		    	              count++;
		                 }
		                
		                 if(count == 0) {
		    	            System.out.println("Sorry we currently do not have any reviews for this theatre");
		                 } else {
		    	            System.out.println();
					    	System.out.println("Do you like to reply to any of the comments displayed above? Type(Y/N)");
					    	String choice = br.readLine();
					    	if(choice.equalsIgnoreCase("Y")) {
					    		System.out.println("Please enter the ID of the review for which you wish to reply");
					    		parentID = Integer.parseInt(br.readLine());
					    		System.out.println("Please specify the movie which you watched in this theatre");				
								moviename = br.readLine();
								String sqlmovie = "Select MOVIE_ID from MOVIE where (TITLE=?)";
								preparedStatementmovie = connection.prepareStatement(sqlmovie);
								preparedStatementmovie.setString(1, moviename);
								ResultSet rsmovie = preparedStatementmovie.executeQuery();
								if(rsmovie.next()) {
									movieid = rsmovie.getString(1);
									System.out.println("Please enter your review comment");
									comment = br.readLine();
									
									//assigning new review id to this review comment
									Statement stmtrid = connection.createStatement();
									ResultSet rsrid = stmtrid.executeQuery("Select MAX(REVIEW_ID) as MAXRID from REVIEW_DISCUSSION");
									if(rsrid.next()) {
										reviewID = rsrid.getInt("MAXRID");
										reviewID = reviewID+1;							
									}																	
									
									Calendar calendar = Calendar.getInstance();
								    java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());				    		   
									
									String sqlcomment = "Insert into REVIEW_DISCUSSION values (?,?,?,?,?,?,?,?,?,?)";
									preparedStatementcomment = connection.prepareStatement(sqlcomment);
									preparedStatementcomment.setInt(1, customerID);
									preparedStatementcomment.setString(2, theatreid);
									preparedStatementcomment.setString(3, movieid);
									preparedStatementcomment.setString(4, comment);					
									preparedStatementcomment.setInt(5, parentID);
									preparedStatementcomment.setInt(6, reviewID);
									preparedStatementcomment.setString(7, userID);
									preparedStatementcomment.setDate(8, today);
									preparedStatementcomment.setString(9, type);
									preparedStatementcomment.setString(10, rfor);
									
									int update = preparedStatementcomment.executeUpdate();
									if(update > 0) {
										System.out.println("Thank you for submitting your review");
										points = calculatePoints(customerID, points, status, "comment");
										System.out.println("Do you want to view reviews for another movie? (Y/N)");
										exit = br.readLine();
									} else {
										System.out.println("Oops!! Something went wrong please try again");
									}
									
								} else {
									System.out.println("Sorry!! We do not recognize the movie name you entered.. Please try again!!");
									System.out.println("Do you want to try again? (Y/N)");
									exit = br.readLine();
								}
					    	} else {
					    		System.out.println("Do you want to view reviews for another movie/theatre ? (Y/N)");
								exit = br.readLine();
					    	}
					    }			     
					     
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

	public int calculatePoints(int customerID, int points, String oldstatus, String type) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatementAlert = null;
        String newstatus = "";
		int currpoints = 0;
		try {
			// Register driver manager
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// connect to database
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

			// get inputs from the userID
			String sqldisc = "select CREDIT_POINT from DISCOUNTS where ACTIVITY=?"; 
			preparedStatement = connection.prepareStatement(sqldisc);
			preparedStatement.setString(1, type);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				    currpoints = rs.getInt(1);
				    
				    if (oldstatus.equals("standard")){
	            	  currpoints =1*currpoints;	
	            	}
	            	else if(oldstatus.equals("silver")){
	            		currpoints =2*currpoints;	
	            	}
	            	else if(oldstatus.equals("gold")){
	            		currpoints =5*currpoints;	
	            	}
	            	else if(oldstatus.equals("platinum")){
	            		currpoints =10*currpoints;	
	            	}			    
				
	                points = points + currpoints;
	                
	                if (points>=600){
	            	    newstatus = "Platinum";	
	            	}
	            	else if(points>=400){
	                	newstatus = "Gold";
	            	}
	            	else if(points>=200){
	                	newstatus = "Silver";
	            	}
	            	else newstatus = "Standard";
	                
	                System.out.println("Congratulations... you are being awarded "+currpoints+" points for the "+type+" you contributed!!");
	                System.out.println("You now have totally: "+points+" points");
	                String sql4 = "update member set credit_points=? ,membership_status=? where member_id=?";
	                preparedStatement4 = connection.prepareStatement(sql4);
	                preparedStatement4.setInt(1,points);
	                preparedStatement4.setString(2,newstatus);
	                preparedStatement4.setInt(3,customerID);
	                preparedStatement4.executeUpdate();
	                if(!oldstatus.equalsIgnoreCase(newstatus)) {
	                	System.out.println("Hurray!! your membership status has been upgraded from "+oldstatus+" to "+newstatus);
	                }
	                
	                String sqlAlert = " INSERT INTO ALERTS VALUES (?,?)";
					String ALERT1 = "UNREAD";
					preparedStatementAlert = connection.prepareStatement(sqlAlert);
					preparedStatementAlert.setInt(1,customerID);
					preparedStatementAlert.setString(2,ALERT1);
					preparedStatementAlert.executeUpdate(); 
					
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
			if (preparedStatement4 != null) {
				preparedStatement4.close();
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
		return points;
  }	 
}
