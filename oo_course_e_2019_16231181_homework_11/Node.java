public class Node {
    private int number;
    private int pathId;
    private int unpleasant;

    public Node(int number, int pathId) {
        this.number = number;
        this.pathId = pathId;
        this.unpleasant = (int)(Math.pow(4, (number % 5 + 5) % 5));
    }

    public int getNumber() {
        return this.number;
    }

    public int getId() {
        return this.pathId;
    }

    public int getUnpleasant() {
        return unpleasant;
    }

    @Override
    public int hashCode() {
        return (this.number + this.pathId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return (node.number == this.number && node.pathId == this.pathId);
        }
        return false;
    }
}
