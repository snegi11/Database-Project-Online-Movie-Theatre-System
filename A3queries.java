//Name: Archanaa R Sathyanarayana CWID: A20354423 Name: Mallika KejriwalCWID: A2036504 Name : Srishti Negi CWID : A20351640


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class A3queries {	
	public static void main(String args[]) throws NumberFormatException, IOException{
  
	String exit = "Y";
	while(exit.equalsIgnoreCase("Y")) 
	{
System.out.println("Please select one from the following:"+"\n");

System.out.println("1.Display the 3 most recent discussions/comments from a specific discussion thread.");
System.out.println("\n"+"2.Display the 3 most recent discussion/comments from all discussion threads.");
System.out.println("\n"+"3.Display the least popular discussion thread in terms of visits and comments.");
System.out.println("\n"+"4.Display an alert to a registered guest when his membership status has changed.");
System.out.println("\n"+"5.Display the registered guest who has contributed most comments.");
System.out.println("\n"+"6.Display the theatre(s) that are showing most number of movies.");
System.out.println("\n"+"7.Display the theatre (s) that has the most online ticket sales.");
System.out.println("\n"+"8.Display the list of all employees who are on duty on Monday on a specific theatre.");
System.out.println("Display also their jobs and time table.");
System.out.println("\n"+"9.Send an alert to the owner and manager if no employee with the job of security is scheduled to work tomorrow.");
System.out.println("\n"+"Enter your choice: ");
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int choice = Integer.parseInt(br.readLine());
if(choice==1){
	Q1 mem = new Q1();
	mem.getcomments();
}
if(choice==2){	
Q2 review = new Q2();
review.seeThreads();
	
}
if(choice==3){
	LeastViewed view = new LeastViewed();
	//System.out.println("Welcome to our discussion forum");
	view.viewLeastReview();
	view.viewMostReview();
	
}
if(choice==4){
	q4 mem = new q4();
	mem.showalerts();
	
}
if(choice==5){
    Q5 jdbcSelectRecords = new Q5();
    jdbcSelectRecords.getInformation();

	
}
if(choice==6){
	Q6 disp = new Q6();
    disp.populartheatre(); 
}
if(choice==7){
	Q7 disp = new Q7();
	disp.maxsales();
	
}
if(choice==8){
    Q8 mem= new Q8();	    
    mem.getInformation();
}
if(choice==9){
	ManagerAlert manage = new ManagerAlert();
	manage.displayAlert();
	
}

System.out.println("Do you want to try again? (Y/N)");
exit = br.readLine(); 

}
	System.out.println("Thanks for Visiting!");
}
}