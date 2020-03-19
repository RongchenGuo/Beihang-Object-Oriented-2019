import com.oocourse.specs2.models.Graph;
import com.oocourse.specs2.models.Path;
import com.oocourse.specs2.models.PathIdNotFoundException;
import com.oocourse.specs2.models.PathNotFoundException;
import com.oocourse.specs2.models.NodeNotConnectedException;
import com.oocourse.specs2.models.NodeIdNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MyGraph implements Graph {
    // TODO : IMPLEMENT

    // list of paths; list of ids of paths, respectively
    private ArrayList<Path> dataset;
    private ArrayList<Integer> id;
    // node; number of nodes.
    private HashMap<Integer, Integer> nodeCounter;
    // node; neighbor nodes; weight.
    private HashMap<Integer, HashMap<Integer, Integer>> edgeCounter;
    // node; reachable nodes; distance.
    private HashMap<Integer, HashMap<Integer, Integer>> distance;
    // path id generator
    private static int counter = 0;

    public MyGraph() {
        dataset = new ArrayList<>();
        id = new ArrayList<>();
        nodeCounter = new HashMap<>();
        edgeCounter = new HashMap<>();
        distance = new HashMap<>();
    }

    private void insertEdge(int fromNode, int toNode) {
        if (edgeCounter.containsKey(fromNode)) {
            HashMap<Integer, Integer> tmp1 = edgeCounter.get(fromNode);
            if (tmp1.containsKey(toNode)) {
                int tmp2 = tmp1.get(toNode);
                tmp1.replace(toNode, tmp2 + 1);
                edgeCounter.replace(fromNode, tmp1);
            } else {
                tmp1.put(toNode, 1);
                edgeCounter.replace(fromNode, tmp1);
            }
        } else {
            HashMap<Integer, Integer> tmp = new HashMap<>();
            tmp.put(toNode, 1);
            edgeCounter.put(fromNode, tmp);
        }
    }

    private void removeEdge(int fromNode, int toNode) {
        HashMap<Integer, Integer> tmp = edgeCounter.get(fromNode);
        int tmp2 = tmp.get(toNode);
        tmp2 = tmp2 - 1;
        if (tmp2 != 0) {
            tmp.replace(toNode, tmp2);
            edgeCounter.replace(fromNode, tmp);
        } else {
            tmp.remove(toNode);
            edgeCounter.replace(fromNode, tmp);
        }
    }

    private void addToCounter(Path path) {
        int preNodeId = 0;
        for (int i = 0; i < path.size(); i++) {
            int nodeId = path.getNode(i);
            if (nodeCounter.containsKey(nodeId)) {
                int tmp = nodeCounter.get(nodeId);
                nodeCounter.replace(nodeId, tmp + 1);
            } else {
                nodeCounter.put(nodeId, 1);
            }
            if (i != 0) {
                insertEdge(nodeId, preNodeId);
                insertEdge(preNodeId, nodeId);
            }
            preNodeId = nodeId;
        }
    }

    private void removeFromCounter(Path path) {
        int preNodeId = 0;
        for (int i = 0; i < path.size(); i++) {
            int nodeId = path.getNode(i);
            int tmp = nodeCounter.get(nodeId);
            tmp = tmp - 1;
            if (tmp != 0) {
                nodeCounter.replace(nodeId, tmp);
            } else {
                nodeCounter.remove(nodeId);
            }
            if (i != 0) {
                removeEdge(nodeId, preNodeId);
                removeEdge(preNodeId, nodeId);
            }
            preNodeId = nodeId;
        }
    }

    // @ requires distance.containsKey(fromNode)
    private int traverseDistance(int fromNode, int toNode) {
        HashMap<Integer, Integer> tmp = distance.get(fromNode);
        return tmp.getOrDefault(toNode, -1);
    }

    // @ requires !distance.containsKey(fromNode)
    private int updateDistance(int fromNode, int toNode) {
        LinkedList<Integer> q = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        q.addLast(fromNode);
        map.put(fromNode, 0);
        while (q.size() != 0) {
            int node = q.removeFirst();
            int d = map.get(node) + 1;
            HashMap<Integer, Integer> graph = edgeCounter.get(node);
            for (Integer c : graph.keySet()) {
                if (!map.containsKey(c)) {
                    map.put(c, d);
                    q.addLast(c);
                }
            }
        }
        distance.put(fromNode, map);
        return map.getOrDefault(toNode, -1);
    }

    public int size() {
        return dataset.size();
    }

    public boolean containsPath(Path path) {
        if (path == null) {
            return false;
        }
        return dataset.contains(path);
    }

    public boolean containsPathId(int pathId) {
        return id.contains(pathId);
    }

    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            return dataset.get(id.indexOf(pathId));
        } else {
            throw new PathIdNotFoundException(pathId);
        }
    }

    public int getPathId(Path path) throws PathNotFoundException {
        if (path != null && path.isValid() && containsPath(path)) {
            return id.get(dataset.indexOf(path));
        } else {
            throw new PathNotFoundException(path);
        }
    }

    public int addPath(Path path) {
        if (path != null && path.isValid()) {
            if (containsPath(path)) {
                try {
                    return getPathId(path);
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                    return 0;
                }
            } else {
                counter += 1;
                dataset.add(path);
                id.add(counter);
                addToCounter(path);
                distance.clear();
                return counter;
            }
        } else {
            return 0;
        }
    }

    public int removePath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid() && containsPath(path)) {
            int tmp = getPathId(path);
            id.remove((Integer) tmp);
            dataset.remove(path);
            removeFromCounter(path);
            distance.clear();
            return tmp;
        } else {
            throw new PathNotFoundException(path);
        }
    }

    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            Path tmp = getPathById(pathId);
            dataset.remove(tmp);
            id.remove((Integer) pathId);
            removeFromCounter(tmp);
            distance.clear();
        } else {
            throw new PathIdNotFoundException(pathId);
        }
    }

    public int getDistinctNodeCount() {
        return nodeCounter.size();
    }

    public boolean containsNode(int nodeId) {
        return nodeCounter.containsKey(nodeId);
    }

    public boolean containsEdge(int fromNodeId, int toNodeId) {
        if (edgeCounter.containsKey(fromNodeId)) {
            HashMap<Integer, Integer> tmp = edgeCounter.get(fromNodeId);
            return tmp.containsKey(toNodeId);
        }
        return false;
    }

    public boolean isConnected(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {
        if (!nodeCounter.containsKey(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!nodeCounter.containsKey(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else {
            if (fromNodeId == toNodeId) {
                return true;
            }
            if (distance.containsKey(fromNodeId)) {
                int tmp = distance.get(fromNodeId).getOrDefault(toNodeId, -1);
                return (tmp != -1);
            } else if (distance.containsKey(toNodeId)) {
                int tmp = distance.get(toNodeId).getOrDefault(fromNodeId, -1);
                return (tmp != -1);
            } else {
                int tmp = updateDistance(fromNodeId, toNodeId);
                return (tmp != -1);
            }
        }
    }

    public int getShortestPathLength(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        if (!nodeCounter.containsKey(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!nodeCounter.containsKey(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else {
            if (fromNodeId == toNodeId) {
                return 0;
            }
            int tmp;
            if (distance.containsKey(fromNodeId)) {
                tmp = distance.get(fromNodeId).getOrDefault(toNodeId, -1);
            } else if (distance.containsKey(toNodeId)) {
                tmp = distance.get(toNodeId).getOrDefault(fromNodeId, -1);
            } else {
                tmp = updateDistance(fromNodeId, toNodeId);
            }
            if (tmp == -1) {
                throw new NodeNotConnectedException(fromNodeId, toNodeId);
            } else {
                return tmp;
            }
        }
    }

}
