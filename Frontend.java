
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Frontend implements FrontendInterface {

	private Backend backend;
	private Scanner scan;
	
	public Frontend(Backend backend, Scanner scan) {
		this.backend = backend;
		this.scan = scan;
	}
	
	@Override
	public void startCommandLoop() {
	    boolean output = true;
		
		while (output) {
		    output = outputMainMenu();
		    
		}
	}

	@Override
	public boolean outputMainMenu() {

	 
		
	    System.out.println("Menu:");
	    System.out.println("1. Load Data File");
	    System.out.println("2. Get the Statistics of the Graph of Buildings");
	    System.out.println("3. Get Shortest Path Between Buildings");
	    System.out.println("4. Exit App");

	    // Capture the user's menu selection
	    String choice = scan.nextLine();

	    // Process the user's menu selection
	    switch (choice) {
	      case "1":
	        loadDataFileSubMenu();
	        break;
	      case "2":
	        getGraphStatistics();
	        break;
	      case "3":
	        getPath();
	        break;
	      case "4":
	        exitSubMenu();
	        return false;
	      default:
	        invalidInputMessage();
	        break;
	    }
	    return true;
	}
	

	@Override
	public void loadDataFileSubMenu() {
		System.out.println("Enter the path of the data file: ");
		String path = scan.nextLine();
		
		try {
			backend.readDataFromFile(path);
			System.out.println("File was successfully loaded!");
		} catch(IOException e) {
			System.out.println("File was not able to be loaded. Please try again.");
		}
	}

	@Override
	public void getGraphStatistics() {
		System.out.println("Here are the statistics of the graph of buildings:");
		System.out.println(backend.getDatasetStatistics());
	}

	@Override
	public void getPath() {
		System.out.println("Enter the name of the starting building: ");
		String start = scan.nextLine();
		System.out.println("Enter the name of the destination building: ");
		String end = scan.nextLine();
		
		String path = backend.getShortestPath(start, end);
		
		System.out.println("The shortest path to " + end + " from " + start + " is: " + path);
		
	}

	@Override
	public void invalidInputMessage() {
		System.out.println("The value entered does not correspond to a value in the menu. "
				+ "Please enter a new value.");
	}

	@Override
	public void exitSubMenu() {
		System.out.println("Goodbye!");
	}
	
}
