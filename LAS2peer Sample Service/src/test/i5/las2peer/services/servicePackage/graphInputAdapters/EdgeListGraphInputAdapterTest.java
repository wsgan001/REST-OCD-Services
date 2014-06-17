package i5.las2peer.services.servicePackage.graphInputAdapters;

import static org.junit.Assert.*;
import i5.las2peer.services.servicePackage.graph.CustomGraph;
import i5.las2peer.services.servicePackage.testsUtil.OcdTestConstants;

import org.junit.Test;

public class EdgeListGraphInputAdapterTest {
	
	@Test
	public void test() {
		GraphInputAdapter inputAdapter =
				new EdgeListGraphInputAdapter(OcdTestConstants.sawmillEdgeListInputPath);
		CustomGraph graph = inputAdapter.readGraph();
		assertEquals(graph.nodeCount(), 36);
		assertEquals(graph.edgeCount(), 62);
	}

}
