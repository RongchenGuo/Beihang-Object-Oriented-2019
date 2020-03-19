import com.oocourse.specs3.models.RailwaySystem;
import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.PathIdNotFoundException;
import com.oocourse.specs3.models.PathNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import com.oocourse.specs3.models.NodeIdNotFoundException;

import java.util.HashMap;
import java.util.HashSet;

public class MyRailwaySystem extends MyGraph implements RailwaySystem {
    // TODO : IMPLEMENT

    // list of paths; list of ids of paths, respectively
    // private ArrayList<Path> dataset;
    // private ArrayList<Integer> id;
    // node; number of nodes.
    // private HashMap<Integer, Integer> nodeCounter;
    // node; neighbor nodes; appearance times.
    // private HashMap<Integer, HashMap<Integer, Integer>> edgeCounter;
    // node hash set
    private HashMap<Node, Integer> nodeSet;
    // node; neighbor nodes; unpleasant value
    private HashMap<Node, HashMap<Node, Integer>> edgeSet;
    // node; reachable nodes; shortest distance.
    // private HashMap<Integer, HashMap<Integer, Integer>> distance;
    // node; reachable nodes; least price.
    private HashMap<Node, HashMap<Node, Integer>> price;
    // node; reachable nodes; least transfer.
    private HashMap<Node, HashMap<Node, Integer>> transfer;
    // node; reachable nodes; least unpleasant.
    private HashMap<Node, HashMap<Node, Integer>> unpleasant;
    // private static int counter = 0;
    private static Integer inf = Integer.MAX_VALUE;

    public MyRailwaySystem() {
        nodeSet = new HashMap<>();
        edgeSet = new HashMap<>();
        price = new HashMap<>();
        transfer = new HashMap<>();
        unpleasant = new HashMap<>();
    }

    private void addToNewGraph(Path path, int id) {
        int preNodeId = 0;
        for (int i = 0; i < path.size(); i++) {
            int nodeId = path.getNode(i);
            Node tmpNode = new Node(nodeId, id);
            // add to nodeset
            if (nodeSet.containsKey(tmpNode)) {
                nodeSet.replace(tmpNode, nodeSet.get(tmpNode) + 1);
            } else { nodeSet.put(tmpNode, 1); }
            Node starter = new Node(nodeId, -1);
            if (nodeSet.containsKey(starter)) {
                nodeSet.replace(starter, nodeSet.get(starter) + 1);
            } else { nodeSet.put(starter, 1); }
            Node stopper = new Node(nodeId, -2);
            if (nodeSet.containsKey(stopper)) {
                nodeSet.replace(stopper, nodeSet.get(stopper) + 1);
            } else { nodeSet.put(stopper, 1); }
            // add to edgeset (different path id)
            HashMap<Node, Integer> oos = new HashMap<>();
            HashMap<Node, Integer> oot = new HashMap<>();
            if (edgeSet.containsKey(starter)) {
                oos = edgeSet.get(starter);
                oot = edgeSet.get(stopper);
            }
            oos.put(tmpNode, 0);
            edgeSet.put(starter, oos);
            HashMap<Node, Integer> b = new HashMap<>();
            if (edgeSet.containsKey(tmpNode)) {
                b = edgeSet.get(tmpNode);
            }
            b.put(stopper, 0);
            edgeSet.put(tmpNode, b);
            oot.put(starter, 32);
            edgeSet.put(stopper, oot);
            // add to edgeset (same path id)
            if (i != 0) {
                Node tmpPreNode = new Node(preNodeId, id);
                int tmpUnpleasant = Math.max(tmpNode.getUnpleasant(),
                        tmpPreNode.getUnpleasant());
                HashMap<Node, Integer> t = edgeSet.get(tmpNode);
                t.put(tmpPreNode, tmpUnpleasant);
                edgeSet.replace(tmpNode, t);  // forward
                HashMap<Node, Integer> s = edgeSet.get(tmpPreNode);
                s.put(tmpNode, tmpUnpleasant);
                edgeSet.replace(tmpPreNode, s);  // backward
            }
            preNodeId = nodeId;
        }
    }

    private void removeFromNewGraph(Path path, int id) {
        for (int i = 0; i < path.size(); i++) {
            int nodeId = path.getNode(i);
            Node tmpNode = new Node(nodeId, id);
            // add to nodeset
            int tmp = nodeSet.get(tmpNode) - 1;
            if (tmp != 0) { nodeSet.replace(tmpNode, tmp); }
            else { nodeSet.remove(tmpNode); }
            Node starter = new Node(nodeId, -1);
            tmp = nodeSet.get(starter) - 1;
            if (tmp != 0) { nodeSet.replace(starter, tmp); }
            else { nodeSet.remove(starter); }
            Node stopper = new Node(nodeId, -2);
            tmp = nodeSet.get(stopper) - 1;
            if (tmp != 0) { nodeSet.replace(stopper, tmp); }
            else { nodeSet.remove(stopper); }
            HashMap<Node, Integer> oos = edgeSet.get(starter);
            if (!nodeSet.containsKey(tmpNode) && oos.size() == 1) {
                edgeSet.remove(starter);
                edgeSet.remove(stopper);
                edgeSet.remove(tmpNode);
            } else if (!nodeSet.containsKey(tmpNode)) {
                oos.remove(tmpNode);
                edgeSet.replace(starter, oos);
                edgeSet.remove(tmpNode);
            }
        }
    }

    @Override
    public int addPath(Path path) {
        if (path != null && path.isValid()) {
            int counter = super.addPath(path);
            addToNewGraph(path, counter);
            price.clear();
            transfer.clear();
            unpleasant.clear();
            return counter;
        } else {
            return 0;
        }
    }

    @Override
    public int removePath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid() && containsPath(path)) {
            int tmp = super.removePath(path);
            removeFromNewGraph(path, tmp);
            price.clear();
            transfer.clear();
            unpleasant.clear();
            return tmp;
        } else {
            throw new PathNotFoundException(path);
        }
    }

    @Override
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            Path tmp = super.getPathById(pathId);
            super.removePathById(pathId);
            removeFromNewGraph(tmp, pathId);
            price.clear();
            transfer.clear();
            unpleasant.clear();
        } else {
            // System.out.println("sdfh");
            throw new PathIdNotFoundException(pathId);
        }
    }

    private Integer updatePrice(Node fromNode, Node toNode) {
        HashMap<Node, Integer> cost = new HashMap<>();
        HashSet<Node> unprocessed = new HashSet<>();
        HashMap<Node, Integer> straight = edgeSet.get(fromNode);
        for (Node key : edgeSet.keySet()) {
            if (straight.containsKey(key)) {
                cost.put(key, 0);
            } else {
                cost.put(key, inf);
            }
            unprocessed.add(key);
        }
        cost.put(fromNode, 0);
        unprocessed.remove(fromNode);
        while (unprocessed.size() > 0) {
            Integer lowest = inf;
            Node lowestNode = null;
            for (Node n : unprocessed) {
                Integer val = cost.get(n);
                if (val < lowest) {
                    lowest = val;
                    lowestNode = n;
                }
            }
            if (lowestNode == null) {
                break;
            }
            unprocessed.remove(lowestNode);
            Integer nodeCost = cost.get(lowestNode);
            HashMap<Node, Integer> neighbors = edgeSet.get(lowestNode);
            for (Node neighbor : neighbors.keySet()) {
                Integer stepCost;
                if (neighbor.getId() == -2) {
                    stepCost = 0;
                } else if (neighbor.getId() == -1) {
                    stepCost = 2;
                } else if (lowestNode.getId() == -1) {
                    stepCost = 0;
                } else {
                    stepCost = 1;
                }
                Integer newCost = nodeCost + stepCost;
                if (newCost < cost.get(neighbor)) {
                    cost.put(neighbor, newCost);
                }
            }
        }
        price.put(fromNode, cost);
        return cost.get(toNode);
    }

    public int getLeastTicketPrice(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        Node starter = new Node(fromNodeId, -1);
        Node stopper = new Node(toNodeId, -2);
        if (!nodeSet.containsKey(starter)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!nodeSet.containsKey(stopper)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else {
            if (fromNodeId == toNodeId) {
                return 0;
            }
            if (!price.containsKey(starter)) {
                Integer tmp = updatePrice(starter, stopper);
                if (tmp.equals(inf)) {
                    throw new NodeNotConnectedException(fromNodeId, toNodeId);
                } else {
                    return tmp;
                }
            }
            Integer tmp = price.get(starter).get(stopper);
            if (tmp.equals(inf)) {
                throw new NodeNotConnectedException(fromNodeId, toNodeId);
            } else {
                return tmp;
            }
        }
    }

    private Integer updateTransfer(Node fromNode, Node toNode) {
        HashMap<Node, Integer> cost = new HashMap<>();
        HashSet<Node> unprocessed = new HashSet<>();
        HashMap<Node, Integer> straight = edgeSet.get(fromNode);
        for (Node key : edgeSet.keySet()) {
            if (straight.containsKey(key)) {
                cost.put(key, 0);
            } else {
                cost.put(key, inf);
            }
            unprocessed.add(key);
        }
        cost.put(fromNode, 0);
        unprocessed.remove(fromNode);
        while (unprocessed.size() > 0) {
            Integer lowest = inf;
            Node lowestNode = null;
            for (Node n : unprocessed) {
                Integer val = cost.get(n);
                if (val < lowest) {
                    lowest = val;
                    lowestNode = n;
                }
            }
            if (lowestNode == null) {
                break;
            }
            unprocessed.remove(lowestNode);
            Integer nodeCost = cost.get(lowestNode);
            HashMap<Node, Integer> neighbors = edgeSet.get(lowestNode);
            for (Node neighbor : neighbors.keySet()) {
                Integer stepCost;
                if (neighbor.getId() == -2) {
                    stepCost = 0;
                } else if (neighbor.getId() == -1) {
                    stepCost = 1;
                } else if (lowestNode.getId() == -1) {
                    stepCost = 0;
                } else {
                    stepCost = 0;
                }
                Integer newCost = nodeCost + stepCost;
                if (newCost < cost.get(neighbor)) {
                    cost.put(neighbor, newCost);
                }
            }
        }
        transfer.put(fromNode, cost);
        return cost.get(toNode);
    }

    public int getLeastTransferCount(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        Node starter = new Node(fromNodeId, -1);
        Node stopper = new Node(toNodeId, -2);
        if (!nodeSet.containsKey(starter)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!nodeSet.containsKey(stopper)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else {
            if (fromNodeId == toNodeId) {
                return 0;
            }
            if (!transfer.containsKey(starter)) {
                Integer tmp = updateTransfer(starter, stopper);
                if (tmp.equals(inf)) {
                    throw new NodeNotConnectedException(fromNodeId, toNodeId);
                } else {
                    return tmp;
                }
            }
            Integer tmp = transfer.get(starter).get(stopper);
            if (tmp.equals(inf)) {
                throw new NodeNotConnectedException(fromNodeId, toNodeId);
            } else {
                return tmp;
            }
        }
    }

    // @ requires !unpleasant.containsKey(fromNode)
    private Integer updateUnpleasant(Node fromNode, Node toNode) {
        HashMap<Node, Integer> cost = new HashMap<>();
        HashSet<Node> unprocessed = new HashSet<>();
        HashMap<Node, Integer> straight = edgeSet.get(fromNode);
        for (Node key : edgeSet.keySet()) {
            if (straight.containsKey(key)) {
                cost.put(key, straight.get(key));
            } else {
                cost.put(key, inf);
            }
            unprocessed.add(key);
        }
        cost.put(fromNode, 0);
        unprocessed.remove(fromNode);
        while (unprocessed.size() > 0) {
            Integer lowest = inf;
            Node lowestNode = null;
            for (Node n : unprocessed) {
                Integer val = cost.get(n);
                if (val < lowest) {
                    lowest = val;
                    lowestNode = n;
                }
            }
            if (lowestNode == null) {
                break;
            }
            unprocessed.remove(lowestNode);
            Integer nodeCost = cost.get(lowestNode);
            HashMap<Node, Integer> neighbors = edgeSet.get(lowestNode);
            for (Node neighbor : neighbors.keySet()) {
                Integer stepCost = neighbors.get(neighbor);
                Integer newCost = nodeCost + stepCost;
                if (newCost < cost.get(neighbor)) {
                    cost.put(neighbor, newCost);
                }
            }
        }
        unpleasant.put(fromNode, cost);
        return cost.get(toNode);
    }

    public int getLeastUnpleasantValue(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        Node starter = new Node(fromNodeId, -1);
        Node stopper = new Node(toNodeId, -2);
        if (!nodeSet.containsKey(starter)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!nodeSet.containsKey(stopper)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else {
            if (fromNodeId == toNodeId) {
                return 0;
            }
            if (!unpleasant.containsKey(starter)) {
                Integer tmp = updateUnpleasant(starter, stopper);
                if (tmp.equals(inf)) {
                    throw new NodeNotConnectedException(fromNodeId, toNodeId);
                } else {
                    return tmp;
                }
            }
            Integer tmp = unpleasant.get(starter).get(stopper);
            if (tmp.equals(inf)) {
                throw new NodeNotConnectedException(fromNodeId, toNodeId);
            } else {
                return tmp;
            }
        }
    }

    public int getConnectedBlockCount() {
        return super.calculateBlock();
    }

    public int getUnpleasantValue(Path path, int fromIndex, int toIndex) {
        int fromId = path.getNode(fromIndex);
        int toId = path.getNode(toIndex);
        try {
            return getLeastUnpleasantValue(fromId, toId);
        } catch (NodeIdNotFoundException | NodeNotConnectedException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
