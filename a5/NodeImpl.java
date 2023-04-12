package a5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeImpl implements Node {

    /* You will include the method signatures (return type, name, and arg types) for any node methods you
    need in this file. */

    /*Hint: Make sure you update the Node interface in Node.java when you add a new method implementation
    in NodeImpl.java, and vice-versa.  getName() in Node.java and NodeImpl.java is an example.  Also, files in
    previous homeworks (e.g., BST.java and BSTImpl.java in homework 3) are good examples of
    interfaces and their implementations.
     */

    /*Also, any node fields you want to add for the object should go in this file.  */

    String name;
    List<Edge> edges;
    int inDegree;
    int topNum;

    public NodeImpl(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        inDegree = 0;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public List<Edge> getAdjacentEdges() {
        return this.edges;
    }
    @Override
    public int getInDegree() {
        return this.inDegree;
    }
    @Override
    public void incrementInDegree() {
        this.inDegree++;
    }
    @Override
    public void decrementInDegree() {
        this.inDegree--;
    }

    @Override
    public boolean addEdge(Node otherNode, double weight) {
        if(otherNode == this) {
            return false;
        }
        Edge temp = new EdgeImpl(this, otherNode, weight);
        edges.add(temp);
        otherNode.incrementInDegree();
        return true;
    }

    @Override
    public boolean isAdjacent(Node otherNode) {
        for(Edge edge : edges) {
            if(edge.getDestinationNode() == otherNode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEdge(Node otherNode) {
        if(!isAdjacent(otherNode)) {
            return false;
        }
        otherNode.decrementInDegree();
        for(Edge edge : edges) {
            if(edge.getDestinationNode() == otherNode) {
                edges.remove(edge);
                break;
            }
        }
        return true;
    }

}
