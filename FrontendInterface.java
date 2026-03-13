/*
 * This Frontend Interface provides methods for interacting with
 * the user. The user will be able to enter different inputs which 
 * will either load a file, get the statistics about the graph, find
 * the shortest path between two buildings, or exit the app.
 */
public interface FrontendInterface {

	/*
	 * This method will start a command loop that will run infinitely 
	 * until the user decides for it to quit
	 */
	public void startCommandLoop();
	
	/*
	 * This method will display the main menu and wait for user input
	 * @return whether or not the display menu should be displayed 
	 */
	public boolean  outputMainMenu();
	
	/*
	 * This method will load the data file specified by the user
	 */
	public void loadDataFileSubMenu();
	
	/*
	 * This method gets the statistics of the given graph
	 */
	public void getGraphStatistics();
	
	/*
	 * This method gets the starting and destination building from the user 
	 * and outputs the path that connects them
	 */
	public void getPath();
	
	/*
	 * This method will output an error message to the user
	 */
	public void invalidInputMessage();
	
	/*
	 * This method closes the application 
	 */
	public void exitSubMenu();
	
}
