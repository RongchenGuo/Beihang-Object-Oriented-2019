import com.oocourse.specs1.models.Path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MyPath implements Path {
    // TODO : IMPLEMENT
    private ArrayList<Integer> trajectory;
    private HashSet<Integer> set;
    // private String id;

    public MyPath(int... nodeList) {
        trajectory = new ArrayList<>();
        set = new HashSet<>();
        // StringBuilder s = new StringBuilder();
        for (int e : nodeList) {
            trajectory.add(e);
            set.add(e);
            // s.append(e);
        }
        // id = s.toString();
        // System.out.println(trajectory);
        // System.out.println(set);
        // System.out.print(id);
    }

    private ArrayList<Integer> getTrajectory() {
        return this.trajectory;
    }

    public /*@pure@*/int size() {
        return trajectory.size();
    }

    public /*@pure@*/ int getNode(int index) {
        if (index >= 0 && index < size()) {
            return trajectory.get(index);
        }
        return -1;
    }

    public /*@pure@*/ boolean containsNode(int node) {
        return set.contains(node);
    }

    public /*pure*/ int getDistinctNodeCount() {
        return set.size();
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Path) {
            if (((Path) obj).size() != this.size()) {
                return false;
            } else {
                return trajectory.equals(((MyPath) obj).getTrajectory());
            }
        } else {
            return false;
        }
    }

    public /*@pure@*/ boolean isValid() {
        return (size() >= 2);
    }

    @Override
    public Iterator<Integer> iterator() {
        return trajectory.iterator();
    }

    public int compareTo(Path o) {
        // when this.value < o.value, return < 0
        // when this.value == o.value, return == 0
        // when this.value > o.value, return > 0
        int len1 = this.size();
        int len2 = o.size();
        if (len1 <= len2) {
            int i;
            for (i = 0; i < len1; i++) {
                int p1 = trajectory.get(i);
                int p2 = o.getNode(i);
                if (p1 < p2) {
                    return -1;
                } else if (p1 > p2) {
                    return 1;
                }
            }
            if (len1 == len2) {
                return 0;
            } else {
                return -1;
            }
        } else {
            int i;
            for (i = 0; i < len2; i++) {
                int p1 = trajectory.get(i);
                int p2 = o.getNode(i);
                if (p1 < p2) {
                    return -1;
                } else if (p1 > p2) {
                    return 1;
                }
            }
            return 1;
        }
    }
}
