//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A5extras {
	public static void main(String args[]) throws NumberFormatException, IOException{
		String exit = "Y";
		while(exit.equalsIgnoreCase("Y")) {
			System.out.println("Please select one from the following:"+"\n");
			System.out.println("1.Add Screen details for the existing theatres. ");
			System.out.println("2.Existing members can Buy Tickets.");
			System.out.println("3.Member can see the reviews for particular movie or theatre and vote like or dislike.");
			System.out.println("4.Add a movie to database along with the stars and movie genre, also allows user to add the new movie to the movie schedule");
			System.out.println("5.Add a schedule to a movie present in the database");
			System.out.println("6.Add staff details along with staff address.");
			System.out.println("7.Add staff schedule");
			System.out.println("8.Add star details.");
			System.out.println("9.Add Movie Type information in the database");
		
			System.out.println("\n"+"Enter your choice: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int choice = Integer.parseInt(br.readLine());
			if(choice == 1) {		
			addScreen screeninsert = new addScreen();
			screeninsert.addscreens();}
			if(choice==2){
				buytickets mem = new buytickets();
				mem.bookTicket();
				
			}
			if(choice==3){
				LikeReviews review = new LikeReviews();	    
			    review.LoginUser();
			}
			if(choice==4){
				movieEntry movieinsert = new movieEntry();
				movieinsert.addmovie();
			}
			if(choice==5){
				movieschedule screeninsert = new movieschedule();
				screeninsert.addschedule();
				
			}
           if(choice==6){
			   staffmodify jdbcInsertRecord = new staffmodify();
               jdbcInsertRecord.addInformation();
			}
            if(choice==7){
            	
            	modifySchedule stm = new modifySchedule();			
        	    stm.addSchedule();	
	       }
            if(choice==8){
            	
            	updateStars star = new updateStars();
        		star.addStarInfo();
            }
            if(choice==9){
            	updateType star = new updateType();
        		star.addTypeInfo();
            	
            }        	
        	
        	    System.out.println("Do you want to try again? (Y/N)");
        		exit = br.readLine(); 
			
		  }
		System.out.println("Thanks for Visiting!");
		}
}
