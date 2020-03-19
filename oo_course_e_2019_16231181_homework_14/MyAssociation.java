import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MyAssociation {
    private HashMap<String, UmlAssociation> invertAssoUml;
    private HashMap<String, UmlAssociationEnd> invertAssoEndUml;
    private HashMap<String, HashSet<String>> classAsso;
    private HashMap<String, Integer> assoEndCount;
    private HashMap<String, HashSet<UmlClass>> assoEndClasses;

    public MyAssociation() {
        invertAssoEndUml = new HashMap<>();
        invertAssoUml = new HashMap<>();
        classAsso = new HashMap<>();
        assoEndCount = new HashMap<>();
        assoEndClasses = new HashMap<>();
    }

    public void updateAsso(UmlElement e) {
        UmlAssociation hh = (UmlAssociation) e;
        invertAssoUml.put(hh.getId(), hh);
    }

    public void updateAssoId(UmlElement e) {
        UmlAssociationEnd ii = (UmlAssociationEnd) e;
        invertAssoEndUml.put(ii.getId(), ii);
        HashSet<String> tmpClassName;
        if (classAsso.containsKey(ii.getReference())) {
            tmpClassName = classAsso.get(ii.getReference()); }
        else { tmpClassName = new HashSet<>(); }
        tmpClassName.add(ii.getParentId());
        classAsso.put(ii.getReference(), tmpClassName);
    }

    private void updateAssoMid(String classId,
                            HashMap<String, UmlClass> invertClassUml,
                            HashMap<String,
                                    ArrayList<String>> generalizationUml) {
        int counter = 0;
        HashSet<UmlClass> names = new HashSet<>();
        String tmpClassId = classId;
        while (true) {
            HashSet<String> tmpSet;
            if (classAsso.containsKey(tmpClassId)) {
                tmpSet = classAsso.get(tmpClassId);
            } else { tmpSet = new HashSet<>(); }
            if (tmpSet.size() != 0) {
                for (String str : tmpSet) {
                    String end1 = invertAssoUml.get(str).getEnd1();
                    String end2 = invertAssoUml.get(str).getEnd2();
                    UmlAssociationEnd umlEnd1 = invertAssoEndUml.get(end1);
                    UmlAssociationEnd umlEnd2 = invertAssoEndUml.get(end2);
                    String ref1 = umlEnd1.getReference();
                    String ref2 = umlEnd2.getReference();
                    if (invertClassUml.containsKey(ref1)
                            && invertClassUml.containsKey(ref2)) {
                        if (ref1.equals(ref2)) {
                            counter += 2;
                            names.add(invertClassUml.get(tmpClassId));
                        } else {
                            counter += 1;
                            if (ref1.equals(tmpClassId)) {
                                names.add(invertClassUml.get(ref2));
                            } else {
                                names.add(invertClassUml.get(ref1));
                            }
                        }
                    }
                }
            }
            if (!generalizationUml.containsKey(tmpClassId)) { break; }
            tmpClassId = generalizationUml.get(tmpClassId).get(0); }
        assoEndCount.put(classId, counter);
        assoEndClasses.put(classId, names);
    }

    public int getClassAssociationCount(String classId,
                                        HashMap<String,
                                                UmlClass> invertClassUml,
                                        HashMap<String, ArrayList<String>>
                                                generalizationUml) {
        if (!assoEndCount.containsKey(classId)) {
            updateAssoMid(classId, invertClassUml, generalizationUml); }
        return assoEndCount.get(classId);
    }

    public List<String> getClassAssociatedClassList(String id,
                          HashMap<String, UmlClass> invertClassUml,
                          HashMap<String, ArrayList<String>>
                                                            generalizationUml) {
        if (!assoEndClasses.containsKey(id)) {
            updateAssoMid(id, invertClassUml, generalizationUml); }
        List<String> ans = new ArrayList<>();
        if (assoEndClasses.get(id).size() != 0) {
            for (UmlClass t: assoEndClasses.get(id)) {
                ans.add(t.getName()); } }
        return ans;
    }

    public ArrayList<String> checkClassAsso(String classId) {
        ArrayList<String> names = new ArrayList<>();
        if (classAsso.containsKey(classId)) {
            if (classAsso.get(classId).size() != 0) {
                for (String assoId: classAsso.get(classId)) {
                    String end1 = invertAssoUml.get(assoId).getEnd1();
                    String end2 = invertAssoUml.get(assoId).getEnd2();
                    UmlAssociationEnd umlEnd1 = invertAssoEndUml.get(end1);
                    UmlAssociationEnd umlEnd2 = invertAssoEndUml.get(end2);
                    String ref1 = umlEnd1.getReference();
                    String ref2 = umlEnd2.getReference();
                    if (!ref1.equals(ref2)) {
                        if (ref1.equals(classId)) {
                            if (umlEnd2.getName() == null) { continue; }
                            names.add(umlEnd2.getName());
                        } else if (ref2.equals(classId)) {
                            if (umlEnd1.getName() == null) { continue; }
                            names.add(umlEnd1.getName());
                        }
                    } else {
                        if (ref1.equals(classId)) {
                            if (umlEnd1.getName() != null) {
                                names.add(umlEnd1.getName());
                            }
                            if (umlEnd2.getName() != null) {
                                names.add(umlEnd2.getName());
                            }
                        }
                    }
                }
            }
        }
        return names;
    }

}
