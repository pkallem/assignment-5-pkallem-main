package a5;

public class EdgeImpl implements Edge {
    /* You will include the implementations for any edge methods you need in this file. */

    /*Hint: Make sure you update the Edge interface in Edge.java when you add a new method implementation
    in EdgeImpl.java, and vice-versa.  getName() in Node.java and NodeImpl.java is an example.  Also, files in
    previous homeworks (e.g., BST.java and BSTImpl.java in homework 3) are good examples of
    interfaces and their implementations.
     */

    /*Also, any edge fields you want to add for the object should go in this file.  */



    Node srcNode;
    Node destNode;
    double weight;

    public EdgeImpl(Node startNode, Node endNode, double weight) {
        this.srcNode = startNode;
        this.destNode = endNode;
        this.weight = weight;
    }

    @Override
    public Node getSrcNode() {
        return this.srcNode;
    }

    @Override
    public Node getDestNode() {
        return this.destNode;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    @Override
    public String toString() {
        return "Edge: { " + this.srcNode.getName() + " -> " + this.destNode.getName() +
                ", Weight: " + this.weight + " }";
    }
}
