import java.util.List;

public class ShortestPathResult<NodeType, EdgeType extends Number>
		implements ShortestPathResultInterface<NodeType, EdgeType> {

	private List<NodeType> path;
	private List<EdgeType> pathSegmentTimes;
	private double totalPathCost;

	@Override
	public List<NodeType> getPath() {
		return path;
	}

	@Override
	public List<EdgeType> getPathSegmentTimes() {
		return pathSegmentTimes;
	}

	@Override
	public double getTotalPathCost() {
		return totalPathCost;
	}

}
