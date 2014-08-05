package i5.las2peer.services.servicePackage.graph;

public class CoverId {

    private long id;
    
    private CustomGraphId graph;
 
    public CoverId(int id, CustomGraphId graph) {
        this.id = id;
        this.graph = graph;
    }
 
    public boolean equals(Object object) {
        if (object instanceof CoverId) {
        	CoverId pk = (CoverId)object;
            return graph.equals(graph) && id == pk.id;
        } else {
            return false;
        }
    }
 
    public int hashCode() {
        return (int)(id + graph.hashCode());
    }
	
}