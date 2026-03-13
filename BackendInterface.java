import java.io.IOException;

public interface BackendInterface {

	/**
	 * possible constructor
	 * 
	 * public class Backend<NodeType, EdgeType extends Number> implements
	 * BackendInterface<NodeType, EdgeType> {
	 * 
	 * private GraphADT<NodeType, EdgeType> graph; public Backend(GraphADT<NodeType,
	 * EdgeType> graph) { this.graph = graph; }
	 * 
	 * @Override methods down here
	 * 
	 *           }
	 */

	/**
	 * read data from a file and populate the graph with nodes and edges
	 * 
	 * @param filePath the path to the data file
	 * @throws IOException if there is an error reading the file
	 */
	void readDataFromFile(String filePath) throws IOException;

	/**
	 * gets the shortest path from a start destination building in the dataset
	 * 
	 * @param startBuilding the starting building
	 * @param destination   the destination building
	 * @return an string representing the shortest path
	 */
	String getShortestPath(String startBuilding, String destination);

	/**
	 * get a string with statistics about the dataset that includes the number of
	 * nodes (buildings), the number of edges, and the total walking time (sum of
	 * weights) for all edges in the graph
	 * 
	 * @return a string with dataset statistics
	 */
	String getDatasetStatistics();

}
