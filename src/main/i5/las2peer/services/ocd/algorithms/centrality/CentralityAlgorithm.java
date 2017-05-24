package i5.las2peer.services.ocd.algorithms.centrality;

import java.util.Set;

import i5.las2peer.services.ocd.graphs.CentralityCreationMethod;
import i5.las2peer.services.ocd.graphs.CentralityCreationType;
import i5.las2peer.services.ocd.graphs.CentralityMap;
import i5.las2peer.services.ocd.graphs.CustomGraph;
import i5.las2peer.services.ocd.graphs.GraphType;

public interface CentralityAlgorithm extends CentralityCreationMethod {
	
	/**
	 * Calculates the centrality values for all the nodes in the graph
	 * @param graph The graph on which the centrality values are calculated
	 * @return An array containing the centrality values
	 */
	public CentralityMap getValues(CustomGraph graph);
	
	/**
	 * Returns a log representing the concrete algorithm execution.
	 * @return The log.
	 */
	public CentralityCreationType getAlgorithmType();
	
	/**
	 * Returns all graph types the algorithm is compatible with.
	 * @return The compatible graph types.
	 * An empty set if the algorithm is not compatible with any type.
	 */
	public Set<GraphType> compatibleGraphTypes();
}