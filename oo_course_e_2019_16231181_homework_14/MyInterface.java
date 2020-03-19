import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

public class MyInterface {
    private HashMap<String, UmlInterface> invertFaceUml;
    private HashMap<String, ArrayList<String>> realization;
    private HashMap<String, List<String>> faceInfo;

    public MyInterface() {
        invertFaceUml = new HashMap<>();
        realization = new HashMap<>();
        faceInfo = new HashMap<>();
    }

    public void updateInterface(UmlElement e) {
        UmlInterface ff = (UmlInterface) e;
        // System.out.println("I:"+ff.getId()+ff.getName());
        invertFaceUml.put(ff.getId(), ff);
    }

    public void updateRealization(UmlElement e) {
        UmlInterfaceRealization gg = (UmlInterfaceRealization) e;
        // System.out.println("realize:"+gg.getSource()+gg.getTarget());
        ArrayList<String> tmo;
        if (realization.containsKey(gg.getSource())) {
            tmo = realization.get(gg.getSource());
        } else { tmo = new ArrayList<>(); }
        tmo.add(gg.getTarget());
        realization.put(gg.getSource(), tmo);
    }

    private HashSet<String> mergeList(
            HashSet<String> l1, HashSet<String> l2) {
        HashSet<String> ans = new HashSet<>();
        if (l1.size() != 0) {
            for (String a : l1) { ans.add(a); }
        }
        if (l2.size() != 0) {
            for (String a : l2) { ans.add(a); }
        }
        return ans;
    }

    private ArrayList<String> mergeList3(
            ArrayList<String> l1, ArrayList<String> l2) {
        ArrayList<String> ans = new ArrayList<>();
        if (l1.size() != 0) {
            for (String a : l1) { ans.add(a); }
        }
        if (l2.size() != 0) {
            for (String a : l2) { ans.add(a); }
        }
        return ans;
    }

    private HashSet<String> updateMyFace(
            String classId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        HashSet<String> ans = new HashSet<>();
        if (realization.size() != 0) {
            for (String id : realization.keySet()) {
                if (id.equals(classId)) {
                    for (String str: realization.get(id)) {
                        ans = mergeList(ans,
                                getFatherFace(str, generalizationUml)); } } } }
        return ans;
    }

    private HashSet<String> getFatherFace(
            String faceId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        LinkedList<String> q = new LinkedList<>();
        q.addLast(faceId);
        HashSet<String> ans = new HashSet<>();
        ans.add(faceId);
        while (q.size() != 0) {
            String tmpClassId = q.removeFirst();
            if (generalizationUml.containsKey(tmpClassId)) {
                ArrayList<String> tmp = generalizationUml.get(tmpClassId);
                for (String s: tmp) {
                    ans.add(s);
                    q.addLast(s); } } }
        return ans;
    }

    public List<String> getImplementInterfaceList(
            String classId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        if (!faceInfo.containsKey(classId)) {
            String tmpClassId = classId;
            HashSet<String> ans = updateMyFace(classId, generalizationUml);
            while (generalizationUml.containsKey(tmpClassId)) {
                tmpClassId = generalizationUml.get(tmpClassId).get(0);
                ans = mergeList(ans, updateMyFace(tmpClassId,
                        generalizationUml)); }
            List<String> names = new ArrayList<>();
            for (String face: ans) {
                if (invertFaceUml.containsKey(face)) {
                    names.add(invertFaceUml.get(face).getName()); } }
            faceInfo.put(classId, names); }
        return faceInfo.get(classId);
    }

    private boolean checkFatherFace2(
            String faceId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        LinkedList<String> q = new LinkedList<>();
        q.addLast(faceId);
        HashSet<String> ans = new HashSet<>();
        ans.add(faceId);
        while (q.size() != 0) {
            String tmpClassId = q.removeFirst();
            if (generalizationUml.containsKey(tmpClassId)) {
                ArrayList<String> tmp = generalizationUml.get(tmpClassId);
                for (String s: tmp) {
                    if (!ans.contains(s)) {
                        ans.add(s);
                        q.addLast(s);
                    } else {
                        if (s.equals(faceId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<UmlClassOrInterface> checkRule2(HashMap<String,
            ArrayList<String>> generalizationUml) {
        ArrayList<UmlClassOrInterface> ans = new ArrayList<>();
        if (invertFaceUml.size() != 0) {
            for (String faceId: invertFaceUml.keySet()) {
                boolean tmp = checkFatherFace2(faceId, generalizationUml);
                if (tmp) {
                    ans.add(invertFaceUml.get(faceId));
                }
            }
        }
        return ans;
    }

    private ArrayList<String> checkFatherFace3(
            String faceId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        ArrayList<String> rlist = new ArrayList<>();
        rlist.add(faceId);
        LinkedList<String> q = new LinkedList<>();
        q.addLast(faceId);
        HashSet<String> ans = new HashSet<>();
        ans.add(faceId);
        while (q.size() != 0) {
            String tmpClassId = q.removeFirst();
            if (generalizationUml.containsKey(tmpClassId)) {
                ArrayList<String> tmp = generalizationUml.get(tmpClassId);
                for (String s: tmp) {
                    if (!ans.contains(s)) {
                        rlist.add(s);
                        ans.add(s);
                        q.addLast(s);
                    } else {
                        rlist.add(s);
                    }
                }
            }
        }
        return rlist;
    }

    public ArrayList<String> checkImplementInterfaceList3(
            String classId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        ArrayList<String> rlist = new ArrayList<>();
        if (!faceInfo.containsKey(classId)) {
            String tmpClassId = classId;
            rlist = checkMyFace3(classId, generalizationUml);
            while (generalizationUml.containsKey(tmpClassId)) {
                tmpClassId = generalizationUml.get(tmpClassId).get(0);
                rlist = mergeList3(rlist, checkMyFace3(
                        tmpClassId, generalizationUml));
            }
        }
        return rlist;
    }

    private ArrayList<String> checkMyFace3(
            String classId, HashMap<String,
            ArrayList<String>> generalizationUml) {
        ArrayList<String> rlist = new ArrayList<>();
        if (realization.size() != 0) {
            for (String id : realization.keySet()) {
                if (id.equals(classId)) {
                    for (String str: realization.get(id)) {
                        ArrayList<String> tmp =
                                checkFatherFace3(str, generalizationUml);
                        rlist = mergeList3(tmp, rlist);
                    } } } }
        return rlist;
    }

    public ArrayList<UmlClassOrInterface> checkRule3(HashMap<String,
            ArrayList<String>> generalizationUml) {
        ArrayList<UmlClassOrInterface> ans = new ArrayList<>();
        if (invertFaceUml.size() != 0) {
            for (String faceId: invertFaceUml.keySet()) {
                ArrayList<String> tmp =
                        checkFatherFace3(faceId, generalizationUml);
                HashSet<String> tmp2 = new HashSet<>();
                for (String s: tmp) { tmp2.add(s); }
                if (tmp2.size() != tmp.size()) {
                    ans.add(invertFaceUml.get(faceId));
                }
            }
        }
        return ans;
    }
}
