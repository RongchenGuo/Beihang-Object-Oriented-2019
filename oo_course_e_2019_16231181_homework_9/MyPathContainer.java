import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import com.oocourse.specs1.models.PathIdNotFoundException;
import com.oocourse.specs1.models.PathNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;

public class MyPathContainer implements PathContainer {
    // TODO : IMPLEMENT
    private ArrayList<Path> dataset;
    private ArrayList<Integer> id;
    private static int counter = 0;

    public MyPathContainer() {
        dataset = new ArrayList<>();
        id = new ArrayList<>();
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
        } else {
            throw new PathIdNotFoundException(pathId);
        }
    }

    public /*@pure@*/int getDistinctNodeCount() {
        HashSet<Integer> nodeCount = new HashSet<>();
        for (Path p : dataset) {
            for (int i = 0; i < p.size(); i++) {
                nodeCount.add(p.getNode(i));
            }
        }
        return nodeCount.size();
    }
}
