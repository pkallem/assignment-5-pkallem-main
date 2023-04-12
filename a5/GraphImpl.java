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
    public boolean addEdge(String source, String destination, double weight) {
        if (!nodes.containsKey(source) || !nodes.containsKey(destination) || weight < 0.0) {
            return false;
        }

        if (nodes.get(source).isAdjacent(nodes.get(destination))) {
            return false;
        }

        nodes.get(source).addEdge(nodes.get(destination), weight);
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
    public boolean deleteEdge(String source, String destination) {
        if (!nodes.containsKey(source) || !nodes.containsKey(destination)) {
            return false;
        }

        if (!nodes.get(source).isAdjacent(nodes.get(destination))) {
            return false;
        }
        nodes.get(source).deleteEdge(nodes.get(destination));
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
            edges[0] += value.getNumEdges();
        });
        return edges[0];
    }

    @Override
    public Stack<String> topoSort() {
        Stack<String> sorted = new Stack<>();
        Stack<String> sort = new Stack<>();
        int count = nodes.size();

        nodes.forEach((key, value) -> {
            if(value.getNumEdges() == 0) {
                sorted.push(key);
                sort.push(key);
            }
        });

        while(!sort.empty()) {
            String node = sort.pop();

            for(Edge edge : nodes.get(node).getAdjacentEdges()) {
                Node destinationNode = edge.getDestinationNode();
                destinationNode.decrementNumEdges();

                if(destinationNode.getNumEdges() == 0) {
                    sorted.push(destinationNode.getName());
                    sort.push(destinationNode.getName());
                }
            }
        }

        if(sorted.size() != count) {
            return new Stack<>();
        }

        nodes.forEach((key, value) -> {
            for(Edge edge : value.getAdjacentEdges()) {
                Node destinationNode = edge.getDestinationNode();
                destinationNode.incrementNumEdges();
            }
        });

        return sorted;
    }
}
