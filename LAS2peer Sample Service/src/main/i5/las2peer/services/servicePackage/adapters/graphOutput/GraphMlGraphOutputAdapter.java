package i5.las2peer.services.servicePackage.adapters.graphOutput;

import i5.las2peer.services.servicePackage.adapters.AdapterException;
import i5.las2peer.services.servicePackage.graph.CustomGraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import y.base.Edge;
import y.base.EdgeCursor;
import y.base.EdgeMap;
import y.base.Node;
import y.base.NodeCursor;
import y.base.NodeMap;
import y.io.GraphMLIOHandler;
import y.io.graphml.GraphMLHandler;
import y.io.graphml.KeyScope;
import y.io.graphml.KeyType;

public class GraphMlGraphOutputAdapter extends AbstractGraphOutputAdapter {
	
	@Override
	public void writeGraph(CustomGraph graph) throws AdapterException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		NodeMap nodeNames = graph.createNodeMap();
		NodeCursor nodes = graph.nodes();
		Node node;
		while(nodes.ok()) {
			node = nodes.node();
			nodeNames.set(node, graph.getNodeName(node));
			nodes.next();
		}
		EdgeMap edgeWeights = graph.createEdgeMap();
		EdgeCursor edges = graph.edges();
		Edge edge;
		while(edges.ok()) {
			edge = edges.edge();
			edgeWeights.set(edge, graph.getEdgeWeight(edge));
			edges.next();
		}
		GraphMLIOHandler ioh = new GraphMLIOHandler();
		GraphMLHandler core = ioh.getGraphMLHandler();
		core.addOutputDataProvider("name", nodeNames, KeyScope.NODE, KeyType.STRING);
		core.addOutputDataProvider("weight", edgeWeights, KeyScope.EDGE, KeyType.DOUBLE);
		try {
			ioh.write(graph, outStream);
			String outString = outStream.toString();
			writer.write(outString);
		}
		catch(IOException e) {
			throw new AdapterException(e);
		} finally {
			try {
				outStream.close();
			}
			catch(IOException e) {
			}
			try {
				writer.close();
			}
			catch(IOException e) {
			}
			graph.disposeNodeMap(nodeNames);
			graph.disposeEdgeMap(edgeWeights);
		}
	}

}