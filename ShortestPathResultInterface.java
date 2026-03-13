import java.util.List;

public interface ShortestPathResultInterface<NodeType, EdgeType extends Number> {

	/**
	 * gets the path (stored as a list of buildings along the path)
	 * 
	 * @return a list of buildings along the path
	 */
	List<NodeType> getPath();

	/**
	 * gets the list of the walking times of the path segments (the time it takes to
	 * walk from one building to the next)
	 * 
	 * @return a list of walking times
	 */
	List<EdgeType> getPathSegmentTimes();

	/**
	 * gets the total path cost as the estimated time it takes to walk from the
	 * start to the destination building
	 * 
	 * @return the total path cost
	 */
	double getTotalPathCost();
}
