//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A1authorization {
	public static void main(String args[]) throws NumberFormatException, IOException{
		String exit = "Y";
		while(exit.equalsIgnoreCase("Y")) 
		{
	System.out.println("Please select one from the following:");
	
	System.out.println("\n"+"1. Show that a registered guest can view his own information; "
	        + "change his phone number but not his membership status nor his reward points.");
	
	System.out.println("2. Show that only the guest himself, "
			+ "the owner and the web administrator can view the guest’s information.");
	
	System.out.println("3. Show that the owner can delegate the updating of "
			+ "movies to be shown and their show time to the manager of a theatre.");
	System.out.println("\n"+"Enter your choice: ");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int choice = Integer.parseInt(br.readLine());
	
	if(choice == 1) {
		 authorq1 mem= new authorq1();	    
		 mem.LoginUser();
	}
	if(choice == 2) {
		authorq2 mem= new authorq2();	    
	    mem.Login();
		
	}
	if(choice == 3) {
		 authorq3 mem= new authorq3();	    
		    mem.Details();
	}	
	
	    System.out.println("Do you want to try again? (Y/N)");
		exit = br.readLine(); 
	}
	
	
	System.out.println("Thanks for Visiting!");
	}
	
}