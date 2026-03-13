import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is the main backend class that implements the BackendInterface
 * 
 * @author Mitchell Young
 *
 */
public class Backend implements BackendInterface {

	// Creating a graph that stores Strings and Doubles
	private GraphADT<String, Double> graph;

	// Setting a variable for the total cost
	private double totalCost = 0.0;

	/**
	 * The constructor for the backend that takes a graph
	 * 
	 * @param graph that is passed into the constructor
	 */
	public Backend(GraphADT<String, Double> graph) {
		this.graph = graph;
	}

	@Override
	public void readDataFromFile(String filePath) throws IOException {

		try {

			Scanner scnr = new Scanner(new File(filePath));
			scnr.nextLine();

			while (scnr.hasNextLine()) {
				String currentLine = scnr.nextLine();

				// Checking if the whole file has been read
				if (currentLine.equals("}")) {
					// Breaks the loop once the whole file is read
					break;
				}

				// Splitting the .dot file into different parts so the data is easier to grab
				String[] parts = currentLine.split("\"");

				// Getting the start building
				String startBuilding = parts[1];

				// Getting the end building
				String endBuilding = parts[3];

				// A string that contains the cost of the path that is then parsed into a double
				String costString = parts[4].substring(parts[4].indexOf('=') + 1, parts[4].indexOf(']'));
				Double cost = Double.parseDouble(costString);

				// Checking to make sure that if the building has already been visited, it is
				// not added again
				if (!graph.containsNode(startBuilding)) {
					graph.insertNode(startBuilding);
				}
				
				// Checking to make sure that if the building has already been visited, it is
				// not added again
				if (!graph.containsNode(endBuilding)) {
					graph.insertNode(endBuilding);
				}

				// Inserting the edges into the graph
				graph.insertEdge(startBuilding, endBuilding, cost);

				// Incrementing the total cost by the cost of that specific edge
				totalCost += cost;
			}

			// This runs if the file cannot be found
		} catch (Exception e) {
			throw new FileNotFoundException("File could not be found!");

		}
	}

	@Override
	public String getDatasetStatistics() {
		// The string that returns all of the required information
		String stats = "Buildings: " + graph.getNodeCount() + ", " + "Edges: " + ", " + graph.getEdgeCount() + ", "
				+ "Distance: " + totalCost;
		return stats;
	}

	@Override
	public String getShortestPath(String startBuilding, String destination) {

		// Getting the shortest path data and storing it in a string
		String result = graph.shortestPathData(startBuilding, destination).toString();
		return result;
	}

    public static void main(String[] args) {
	MapADT map = new PlaceholderMap();
	DijkstraGraph<String, Double> graph = new DijkstraGraph<>(map);
	Backend backend = new Backend(graph);
	Scanner scnr = new Scanner(System.in);

	Frontend frontend = new Frontend(backend, scnr);
	frontend.startCommandLoop();

  }
}
