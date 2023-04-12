package a5;

import java.util.*;

public class GraphImpl implements Graph {
    Map<String, Node> nodes; //Do not delete.  Use this field to store your nodes.
                             // key: name of node. value: a5.Node object associated with name

    public GraphImpl() {
        nodes = new HashMap<>();
    }

    @Override
    public boolean addNode(String name) {
        if (name == null || nodes.containsKey(name)) {
            return false;
        }
        NodeImpl node = new NodeImpl(name);
        nodes.put(name, node);
        return true;
    }

    @Override
    public boolean addEdge(String src, String dest, double weight) {
        if (!nodes.containsKey(src) || !nodes.containsKey(dest) || weight < 0.0) {
            return false;
        }

        if (nodes.get(src).isAdjacent(nodes.get(dest))) {
            return false;
        }

        nodes.get(src).addEdge(nodes.get(dest), weight);
        return true;
    }

    @Override
    public boolean deleteNode(String name) {
        if(!nodes.containsKey(name)) {
            return false;
        }
        Node delNode = nodes.get(name);
        nodes.forEach((key, value) -> {
            if(delNode.isAdjacent(value)) {
                delNode.deleteEdge(value);
            }

            if(value.isAdjacent(delNode)) {
                value.deleteEdge(delNode);
            }
        });

        nodes.remove(name);
        return true;
    }

    @Override
    public boolean deleteEdge(String src, String dest) {
        if (!nodes.containsKey(src) || !nodes.containsKey(dest)) {
            return false;
        }

        if (!nodes.get(src).isAdjacent(nodes.get(dest))) {
            return false;
        }
        nodes.get(src).deleteEdge(nodes.get(dest));
        return true;
    }

    @Override
    public int numNodes() {
        return nodes.size();
    }

    @Override
    public int numEdges() {
        int[] edges = new int[1];
        nodes.forEach((key, value) -> {
            edges[0] += value.getInDegree();
        });
        return edges[0];
    }

    @Override
    public Stack<String> topoSort() {
        Stack<String> returnSort = new Stack<>();
        Stack<String> sort = new Stack<>();
        int nodeCount = nodes.size();

        nodes.forEach((key, value) -> {
            if(value.getInDegree() == 0) {
                returnSort.push(key);
                sort.push(key);
            }
        });

        while(!sort.empty()) {
            String node = sort.pop();

            for(Edge edge : nodes.get(node).getAdjacentEdges()) {
                Node destNode = edge.getDestNode();
                destNode.decrementInDegree();

                if(destNode.getInDegree() == 0) {
                    returnSort.push(destNode.getName());
                    sort.push(destNode.getName());
                }
            }
        }

        if(returnSort.size() != nodeCount) {
            return new Stack<>();
        }

        nodes.forEach((key, value) -> {
            for(Edge edge : value.getAdjacentEdges()) {
                Node destNode = edge.getDestNode();
                destNode.incrementInDegree();
            }
        });

        return returnSort;
    }
}
