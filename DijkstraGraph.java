// --== CS400 Fall 2023 File Header Information ==--
// Name: Mitchell Young
// Email: myoung37@wisc.edu
// Group: F32
// TA: Manas Trivedi
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
		implements GraphADT<NodeType, EdgeType> {

	/**
	 * While searching for the shortest path between two nodes, a SearchNode
	 * contains data about one specific path between the start node and another node
	 * in the graph. The final node in this path is stored in its node field. The
	 * total cost of this path is stored in its cost field. And the predecessor
	 * SearchNode within this path is referened by the predecessor field (this field
	 * is null within the SearchNode containing the starting node in its node
	 * field).
	 *
	 * SearchNodes are Comparable and are sorted by cost so that the lowest cost
	 * SearchNode has the highest priority within a java.util.PriorityQueue.
	 */
	protected class SearchNode implements Comparable<SearchNode> {
		public Node node;
		public double cost;
		public SearchNode predecessor;

		public SearchNode(Node node, double cost, SearchNode predecessor) {
			this.node = node;
			this.cost = cost;
			this.predecessor = predecessor;
		}

		public int compareTo(SearchNode other) {
			if (cost > other.cost)
				return +1;
			if (cost < other.cost)
				return -1;
			return 0;
		}
	}

	/**
	 * Constructor that sets the map that the graph uses.
	 * 
	 * @param map the map that the graph uses to map a data object to the node
	 *            object it is stored in
	 */
	public DijkstraGraph(MapADT<NodeType, Node> map) {
		super(map);
	}

	/**
	 * This helper method creates a network of SearchNodes while computing the
	 * shortest path between the provided start and end locations. The SearchNode
	 * that is returned by this method is represents the end of the shortest path
	 * that is found: it's cost is the cost of that shortest path, and the nodes
	 * linked together through predecessor references represent all of the nodes
	 * along that shortest path (ordered from end to start).
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return SearchNode for the final end node within the shortest path
	 * @throws NoSuchElementException when no path from start to end is found or
	 *                                when either start or end data do not
	 *                                correspond to a graph node
	 */
	protected SearchNode computeShortestPath(NodeType start, NodeType end) {

		// Create a new priority queue to greedily explore shorter path possibilities
		// before longer ones within your computeShortestPath definition. (From
		// write-up)
		PriorityQueue<SearchNode> queue = new PriorityQueue<>();

		// Creating the PlaceholderMap so that we can tell how many of the nodes we have
		// visited
		PlaceholderMap<NodeType, Node> visitedNodes = new PlaceholderMap<>();

		Node startingNode = nodes.get(start);
		SearchNode startNode = new SearchNode(startingNode, 0, null);

		// Adds the start SearchNode to the queue
		queue.add(startNode);

		// Starts the loop that keeps going while the queue is not empty
		while (!queue.isEmpty()) {

			// Sets currentNode to the first value in the priority queue
			SearchNode currentNode = queue.poll(); // queue.poll removes the head of the queue

			// Checks to see if the currentNode is the end node. If so, return currentNode
			if (currentNode.node != null && currentNode.node.data.equals(end)) {
				return currentNode;
			}

			// If the currentNode is not equal to the end, we still have to look for the end
			// point. We first must check if the visitedNodes map holds the currentNode
			// data.
			if (!visitedNodes.containsKey(currentNode.node.data)) {
				// If the visitedNodes map doesn't contain the key we must continue the search

				// Adds the value of the currentNode into the map
				visitedNodes.put(currentNode.node.data, currentNode.node);

				// Must check all of the edges
				for (Edge edge : currentNode.node.edgesLeaving) {

					// Gets the successor of the current edge (The node at the end of the edge)
					Node succNode = edge.successor;

					// Adds the cost of the edge to the total cost
					double totalCost = currentNode.cost + edge.data.doubleValue();

					// New SearchNode using the updated values (the successor node, the new cost,
					// and the predecessor)
					SearchNode succSearch = new SearchNode(succNode, totalCost, currentNode);
					queue.add(succSearch);
				}
			}
		}
		// If the code complete without finding a path this exception is thrown
		throw new NoSuchElementException();
	}

	/**
	 * Returns the list of data values from nodes along the shortest path from the
	 * node with the provided start value through the node with the provided end
	 * value. This list of data values starts with the start value, ends with the
	 * end value, and contains intermediary values in the order they are encountered
	 * while traversing this shorteset path. This method uses Dijkstra's shortest
	 * path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return list of data item from node along this shortest path
	 */
	public List<NodeType> shortestPathData(NodeType start, NodeType end) {

		// Call the helper method
		SearchNode search = computeShortestPath(start, end);

		// Create a an arrayList to hold the values
		List<NodeType> shortestPath = new ArrayList<>();

		// Sets the current node to the created search node
		SearchNode currentNode = search;

		// While currentNode is not null it keeps adding the currentNodes value
		while (currentNode != null) {
			shortestPath.add(currentNode.node.data);

			// Updates the currentNode to the predecessor (next node)
			currentNode = currentNode.predecessor;
		}

		// Reverses the arrayList so that its start to end
		Collections.reverse(shortestPath);

		// Returns the list of data items from the node along the shortest path
		return shortestPath;
	}

	/**
	 * Returns the cost of the path (sum over edge weights) of the shortest path
	 * freom the node containing the start data to the node containing the end data.
	 * This method uses Dijkstra's shortest path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return the cost of the shortest path between these nodes
	 */
	public double shortestPathCost(NodeType start, NodeType end) {
		SearchNode search = computeShortestPath(start, end);
		return search.cost;
	}

	/**
	 * This test creates a shortest path problem (from Week09 Lecture Notes) then
	 * tests to see if the computed shortest path is correct.
	 */
	@Test
	public void lectureTest() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

		// Creating the nodes
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");

		// Inserting the edges between the nodes
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "C", 2);

		graph.insertEdge("B", "F", 6);

		graph.insertEdge("C", "D", 2);

		graph.insertEdge("D", "E", 2);

		graph.insertEdge("E", "F", 2);

		// Assertions
		Assertions.assertEquals(7, graph.shortestPathCost("A", "F"));
		Assertions.assertEquals("[A, B, F]", graph.shortestPathData("A", "F").toString());
	}

	/**
	 * This test creates a shortest path problem (from Week09 Lecture Notes) but
	 * uses different starting and ending points. Then it tests to see if the
	 * computed shortest path is correct.
	 */
	@Test
	public void lectureTestDiffStartAndEnd() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

		// Creating the nodes
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");

		// Inserting the edges between the nodes
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "C", 2);

		graph.insertEdge("B", "F", 6);

		graph.insertEdge("C", "D", 2);

		graph.insertEdge("D", "E", 2);

		graph.insertEdge("E", "F", 2);

		// Assertions
		Assertions.assertEquals(4, graph.shortestPathCost("C", "E"));
		Assertions.assertEquals("[C, D, E]", graph.shortestPathData("C", "E").toString());

	}

	/**
	 * This test creates a shortest path problem (from Week09 Lecture Notes) but
	 * tries to find the shortest path of two nodes that don't have connections.
	 * This test should catch a NoSuchElementException because that is what the
	 * shortest path method throws when it cannot find a path.
	 */
	@Test
	public void testNoPath() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

		// Creating the nodes
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");

		// Inserting the edges between the nodes
		graph.insertEdge("A", "B", 1);
		graph.insertEdge("A", "C", 2);

		graph.insertEdge("B", "F", 6);

		graph.insertEdge("C", "D", 2);

		graph.insertEdge("D", "E", 2);

		graph.insertEdge("E", "F", 2);

		// Assertions

		// This checks that is no path is found a NoSuchElementException is thrown. It
		// then tries to calculate a shortest path between 2 nodes that do not connect.
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			graph.shortestPathCost("D", "A");
		});
	}

	/**
	 * Simple test to make sure that the right edges are being taken into account.
	 */
	@Test
	public void simpleTest() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");

		graph.insertEdge("A", "B", 20);
		graph.insertEdge("A", "C", 1);
		graph.insertEdge("C", "B", 1);

		Assertions.assertEquals(2, graph.shortestPathCost("A", "B"));
		Assertions.assertEquals("[A, C, B]", graph.shortestPathData("A", "B").toString());

	}

	/**
	 * This test makes sure that if you try and find the shortest path between a
	 * node and itself that the cost should be 0 because you're not going anywhere
	 */
	@Test
	public void pathBackToItself() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");

		graph.insertEdge("A", "B", 20);
		graph.insertEdge("A", "C", 1);
		graph.insertEdge("C", "B", 1);

		Assertions.assertEquals(0, graph.shortestPathCost("A", "A"));
		Assertions.assertEquals("[A]", graph.shortestPathData("A", "A").toString());
	}

}
