//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A2generalprocesses {
	public static void main(String args[]) throws NumberFormatException, IOException{
		String exit = "Y";
		while(exit.equalsIgnoreCase("Y")) 
		{
	System.out.println("Please select one from the following:");
	
	System.out.println("1. Demonstrate the process of a guest registers for the first time and purchasing a ticket.");
	System.out.println("\n"+"2. Demonstrate the process of a registered guest logs into his account, adds a comment on an existing thread and starts a new thread.");
	System.out.println("\n"+"3. Show the change in reward points of the above registered guest.");
	System.out.println("\n"+"4. Demonstrate the process of a general public visiting the web site and reads a comment on the thread just created,");
			System.out.println(		"but he cannot enter a comment.");
	System.out.println("\n"+"5. Demonstrate the process of the general public buys a ticket without registering.");
	System.out.println("\n"+"6. Demonstrate the process of the manager making the schedule of an employee on a particular day.");
	System.out.println("Your program will not allow the manager to assign the employee to two different theatres, nor assign the employee the same job at the same time,");
	System.out.println("nor assign the employee a job that he/she cannot do.");
	
	System.out.println("\n"+"Enter your choice: ");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int choice = Integer.parseInt(br.readLine());
		if(choice==1){
			gp1 mem = new gp1();
			mem.addMemberInfo();
		}
		if(choice==2){
			Reviews review = new Reviews();	    
		    review.LoginUser();
		}
		if(choice==3){
	
			System.out.println("The change in reward points are being displayed in the programs itself.");	
			
				}
		if(choice==4){
			GuestReview guest = new GuestReview();
			guest.executeGuest();
			
		}
		if(choice==5){
			gp5 mem = new gp5();
			mem.addMemberInfo();
			
		}
		if(choice==6){
			
			ManagerAlert manage = new ManagerAlert();
	    	manage.displayAlert();
			
		}
			
		    System.out.println("Do you want to try again? (Y/N)");
			exit = br.readLine(); 
		}
	 }
		
	}
	
		
