package i5.las2peer.services.ocd.simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import i5.las2peer.services.ocd.graphs.CustomGraph;
import i5.las2peer.services.ocd.graphs.GraphType;
import y.base.Node;
import y.base.NodeCursor;

/**
 * Implementation of a susceptible-infected-recovered process with a single source node
 * @author Tobias
 *
 */
public class SirSimulation implements GraphSimulation {
	/*
	 * PARAMETER NAMES
	 */
	protected static final String INFECTION_PROBABILITY_NAME = "Infection Probability";
	protected static final String RECOVERY_PROBABILITY_NAME = "Recovery Probability";
	
	Set<Node> infectedNodes;
	Set<Node> recoveredNodes;
	Map<Node, InfectionState> infectionMap;
	Node sourceNode;
	int sourceNodeId = 0;
	double infectionProbability = 0.25;
	double recoveryProbability = 1.0;
	
	/**
	 * Runs the SIR-simulation on the graph starting with a single infected node
	 * @param graph The graph on which the simulation is run
	 * @param sourceNode The node that is initially infected
	 * @return The spreading influence of the source node according to the SIR model
	 * @throws InterruptedException
	 */
	public int runSimulation(CustomGraph graph, Node sourceNode) throws InterruptedException {
		infectedNodes = new HashSet<Node>();
		recoveredNodes = new HashSet<Node>();
		infectionMap = new HashMap<Node, InfectionState>();
		//sourceNode = graph.getNodeArray()[sourceNodeId];
		this.sourceNode = sourceNode;
		infectedNodes.add(sourceNode);
		
		NodeCursor nc = graph.nodes();
		while(nc.ok()) {
			infectionMap.put(nc.node(), InfectionState.SUSCEPTIBLE);
			nc.next();
		}
		for(Node infected : infectedNodes) {
			if(graph.contains(infected)) {
				infectionMap.put(infected, InfectionState.INFECTED);
			}
		}
		
		while(!infectedNodes.isEmpty()) {
			if(Thread.interrupted()) {
				throw new InterruptedException();
			}
			Set<Node> nodesAtRisk = new HashSet<Node>();
			// Determine which nodes are at risk of being infected
			for(Iterator<Node> iterator = infectedNodes.iterator(); iterator.hasNext();) {
				Node infectedNode = iterator.next();
				for(Node neighbor : graph.getNeighbours(infectedNode)) {
					if(infectionMap.get(neighbor) == InfectionState.SUSCEPTIBLE)
						nodesAtRisk.add(neighbor);
				}	
				// Each infected node recovers with a certain probability
				double random = Math.random();
				if(random <= recoveryProbability) {
					infectionMap.put(infectedNode, InfectionState.RECOVERED);
					iterator.remove();
					recoveredNodes.add(infectedNode);
				}
			}
			// Each at risk node is infected with a certain probability
			for(Node nodeAtRisk : nodesAtRisk) {
				double random = Math.random();
				if(random <= infectionProbability) {
					infectionMap.put(nodeAtRisk, InfectionState.INFECTED);
					infectedNodes.add(nodeAtRisk);
				}
			}
		}

		return recoveredNodes.size();
	}

	@Override
	public Set<GraphType> compatibleGraphTypes() {
		Set<GraphType> compatibleTypes = new HashSet<GraphType>();
		compatibleTypes.add(GraphType.DIRECTED);
		return compatibleTypes;
	}
	
	@Override
	public SimulationType getSimulationType() {
		return SimulationType.SIR;
	}
	
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(INFECTION_PROBABILITY_NAME, Double.toString(infectionProbability));
		parameters.put(RECOVERY_PROBABILITY_NAME, Double.toString(recoveryProbability));
		return parameters;
	}
	
	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException {
		if(parameters.containsKey(INFECTION_PROBABILITY_NAME)) {
			infectionProbability = Double.parseDouble(parameters.get(INFECTION_PROBABILITY_NAME));
			parameters.remove(INFECTION_PROBABILITY_NAME);
		}
		if(parameters.containsKey(RECOVERY_PROBABILITY_NAME)) {
			recoveryProbability = Double.parseDouble(parameters.get(RECOVERY_PROBABILITY_NAME));
			parameters.remove(RECOVERY_PROBABILITY_NAME);
		}
		if(parameters.size() > 0) {
			throw new IllegalArgumentException();
		}
	}
}