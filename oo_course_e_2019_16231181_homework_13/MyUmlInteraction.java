import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlInterface;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlParameter;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlClass;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class MyUmlInteraction implements UmlInteraction {
    // TODO : IMPLEMENT
    private HashMap<String, String> generalizationUml;
    private HashMap<String, Integer> classCounter;
    private HashMap<String, String> classUml;
    private HashMap<String, UmlClass> invertClassUml;
    private HashMap<String, HashMap<String, Integer>> attriCounter;
    private HashMap<String, HashMap<String, String>> attriUml;
    private HashMap<String, HashMap<String, UmlAttribute>> invertAttriUml;
    private HashMap<String, HashMap<String, Integer>> attriCounterF;
    private HashMap<String, HashMap<String, String>> attriUmlF;
    private HashMap<String, HashMap<String, UmlAttribute>> invertAttriUmlF;
    private HashMap<String, List<AttributeClassInformation>> classInfo;
    private HashMap<String, HashMap<String, UmlOperation>> invertOperUml;
    private HashMap<String, HashSet<String>> paraUml;
    private HashMap<String, UmlParameter> invertParaUml;
    private HashMap<String, HashMap<OperationQueryType, Integer>> operQuer;
    private HashMap<String, UmlAssociation> invertAssoUml;
    private HashMap<String, UmlAssociationEnd> invertAssoEndUml;
    private HashMap<String, HashSet<String>> classAsso;
    private HashMap<String, Integer> assoEndCount;
    private HashMap<String, HashSet<UmlClass>> assoEndClasses;
    private HashMap<String, UmlInterface> invertFaceUml;
    private HashMap<String, HashSet<String>> realization;
    private HashMap<String, List<String>> faceInfo;

    public MyUmlInteraction(UmlElement... elements) {
        generalizationUml = new HashMap<>();
        classCounter = new HashMap<>();
        classUml = new HashMap<>();
        invertClassUml = new HashMap<>();
        attriCounter = new HashMap<>();
        attriUml = new HashMap<>();
        invertAttriUml = new HashMap<>();
        attriCounterF = new HashMap<>();
        attriUmlF = new HashMap<>();
        invertAttriUmlF = new HashMap<>();
        invertOperUml = new HashMap<>();
        paraUml = new HashMap<>();
        invertParaUml = new HashMap<>();
        operQuer = new HashMap<>();
        invertAssoEndUml = new HashMap<>();
        invertAssoUml = new HashMap<>();
        classAsso = new HashMap<>();
        assoEndCount = new HashMap<>();
        assoEndClasses = new HashMap<>();
        classInfo = new HashMap<>();
        invertFaceUml = new HashMap<>();
        realization = new HashMap<>();
        faceInfo = new HashMap<>();
        for (UmlElement e : elements) {
            switch (e.getElementType()) {
                case UML_CLASS:
                    UmlClass aa = (UmlClass) e;
                    int tmp = classCounter.getOrDefault(aa.getName(), 0);
                    classCounter.put(aa.getName(), tmp + 1);
                    classUml.put(aa.getName(), aa.getId());
                    invertClassUml.put(aa.getId(), aa);
                    if (!invertOperUml.containsKey(aa.getId())) {
                        invertOperUml.put(aa.getId(), new HashMap<>()); }
                    if (!attriCounter.containsKey(aa.getId())) {
                        attriCounter.put(aa.getId(), new HashMap<>());
                        attriUml.put(aa.getId(), new HashMap<>());
                        invertAttriUml.put(aa.getId(), new HashMap<>()); }
                    break;
                case UML_OPERATION:
                    UmlOperation bb = (UmlOperation) e;
                    String tmpClassId = bb.getParentId();
                    if (!invertOperUml.containsKey(tmpClassId)) {
                        invertOperUml.put(tmpClassId, new HashMap<>()); }
                    HashMap<String, UmlOperation> tmp4 =
                            invertOperUml.get(tmpClassId);
                    tmp4.put(bb.getId(), bb);
                    invertOperUml.put(tmpClassId, tmp4);
                    break;
                case UML_ATTRIBUTE:
                    UmlAttribute cc = (UmlAttribute) e;
                    tmpClassId = cc.getParentId();
                    if (!attriCounter.containsKey(tmpClassId)) {
                        attriCounter.put(tmpClassId, new HashMap<>());
                        attriUml.put(tmpClassId, new HashMap<>());
                        invertAttriUml.put(tmpClassId, new HashMap<>()); }
                    HashMap<String, Integer> tmp1
                            = attriCounter.get(tmpClassId);
                    tmp = tmp1.getOrDefault(cc.getName(), 0);
                    tmp1.put(cc.getName(), tmp + 1);
                    attriCounter.put(tmpClassId, tmp1);
                    HashMap<String, String> tmp2 = attriUml.get(tmpClassId);
                    tmp2.put(cc.getName(), cc.getId());
                    attriUml.put(tmpClassId, tmp2);
                    HashMap<String, UmlAttribute> tmp3
                            = invertAttriUml.get(tmpClassId);
                    tmp3.put(cc.getId(), cc);
                    invertAttriUml.put(tmpClassId, tmp3);
                    break;
                case UML_PARAMETER:
                    UmlParameter dd = (UmlParameter) e;
                    invertParaUml.put(dd.getId(), dd);
                    HashSet<String> tmpParaSet;
                    if (paraUml.containsKey(dd.getParentId())) {
                        tmpParaSet = paraUml.get(dd.getParentId()); } else {
                        tmpParaSet = new HashSet<>(); }
                    tmpParaSet.add(dd.getId());
                    paraUml.put(dd.getParentId(), tmpParaSet);
                    break;
                case UML_GENERALIZATION:
                    UmlGeneralization ee = (UmlGeneralization) e;
                    generalizationUml.put(ee.getSource(), ee.getTarget());
                    break;
                case UML_INTERFACE:
                    UmlInterface ff = (UmlInterface) e;
                    invertFaceUml.put(ff.getId(), ff);
                    break;
                case UML_INTERFACE_REALIZATION:
                    UmlInterfaceRealization gg = (UmlInterfaceRealization) e;
                    HashSet<String> tmo;
                    if (realization.containsKey(gg.getSource())) {
                        tmo = realization.get(gg.getSource());
                    } else { tmo = new HashSet<>(); }
                    tmo.add(gg.getTarget());
                    realization.put(gg.getSource(), tmo);
                    break;
                case UML_ASSOCIATION:
                    UmlAssociation hh = (UmlAssociation) e;
                    invertAssoUml.put(hh.getId(), hh);
                    break;
                case UML_ASSOCIATION_END:
                    UmlAssociationEnd ii = (UmlAssociationEnd) e;
                    invertAssoEndUml.put(ii.getId(), ii);
                    HashSet<String> tmpClassName;
                    if (classAsso.containsKey(ii.getReference())) {
                        tmpClassName = classAsso.get(ii.getReference()); }
                    else { tmpClassName = new HashSet<>(); }
                    tmpClassName.add(ii.getParentId());
                    classAsso.put(ii.getReference(), tmpClassName);
                    break;
                default:
                    break; } } }

    public int getClassCount() { return invertClassUml.size(); }

    private void updateOperCount(String classId) {
        Set<String> operIds = invertOperUml.getOrDefault(
                classId, new HashMap<>()).keySet();
        int nonReturnOp = 0;
        int returnOp = 0;
        int nonParaOp = 0;
        int paraOp = 0;
        int allOp = 0;
        if (operIds.size() != 0) {
            for (String str : operIds) {
                int inPara = 0;
                int outPara = 0;
                int inoutPara = 0;
                int returnPara = 0;
                if (paraUml.containsKey(str)) {
                    HashSet<String> paraIds = paraUml.get(str);
                    for (String s : paraIds) {
                        switch (invertParaUml.get(s).getDirection()) {
                            case RETURN:
                                returnPara += 1;
                                break;
                            case IN:
                                inPara += 1;
                                break;
                            case OUT:
                                outPara += 1;
                                break;
                            case INOUT:
                                inoutPara += 1;
                                break;
                            default:
                                break; } } }
                if (returnPara == 0) { nonReturnOp += 1; }
                else { returnOp += 1; }
                if (inPara != 0 || inoutPara != 0 || outPara != 0) {
                    paraOp += 1; }
                else { nonParaOp += 1; }
                allOp += 1; } }
        HashMap<OperationQueryType, Integer> tmp = new HashMap<>();
        tmp.put(OperationQueryType.NON_RETURN, nonReturnOp);
        tmp.put(OperationQueryType.RETURN, returnOp);
        tmp.put(OperationQueryType.NON_PARAM, nonParaOp);
        tmp.put(OperationQueryType.PARAM, paraOp);
        tmp.put(OperationQueryType.ALL, allOp);
        operQuer.put(classId, tmp); }

    public int getClassOperationCount(
            String className, OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className);
        } else {
            String classId = classUml.get(className);
            if (operQuer.containsKey(classId)) {
                return operQuer.get(classId).get(queryType); }
            updateOperCount(classId);
            return operQuer.get(classId).get(queryType); } }

    public int getClassAttributeCount(
            String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String classId = classUml.get(className);
                if (queryType == AttributeQueryType.SELF_ONLY) {
                    return invertAttriUml.getOrDefault(
                            classId, new HashMap<>()).size(); } else {
                    String tmpClassId = classId;
                    int counter = invertAttriUml.getOrDefault(
                            classId, new HashMap<>()).size();
                    while (generalizationUml.containsKey(tmpClassId)) {
                        tmpClassId = generalizationUml.get(tmpClassId);
                        if (invertAttriUml.containsKey(tmpClassId)) {
                            counter += invertAttriUml.get(tmpClassId).size();
                        } }
                    return counter; } } } }

    private void updateAsso(String classId) {
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
                            names.add(invertClassUml.get(tmpClassId)); } else {
                            counter += 1;
                            if (ref1.equals(tmpClassId)) {
                                names.add(invertClassUml.get(ref2)); } else {
                                names.add(invertClassUml.get(ref1)); } } } } }
            if (!generalizationUml.containsKey(tmpClassId)) { break; }
            tmpClassId = generalizationUml.get(tmpClassId); }
        assoEndCount.put(classId, counter);
        assoEndClasses.put(classId, names); }

    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String classId = classUml.get(className);
                if (!assoEndCount.containsKey(classId)) { updateAsso(classId); }
                return assoEndCount.get(classId); } } }

    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String id = classUml.get(className);
                if (!assoEndClasses.containsKey(id)) { updateAsso(id); }
                List<String> ans = new ArrayList<>();
                if (assoEndClasses.get(id).size() != 0) {
                    for (UmlClass t: assoEndClasses.get(id)) {
                        ans.add(t.getName()); } }
                return ans; } } }

    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String classId = classUml.get(className);
                HashMap<String, UmlOperation> tmp =
                        invertOperUml.getOrDefault(classId, new HashMap<>());
                int pub = 0;
                int pri = 0;
                int pro = 0;
                int pac = 0;
                if (tmp.size() != 0) {
                    for (UmlOperation o : tmp.values()) {
                        if (o.getName().equals(operationName)) {
                            switch (o.getVisibility()) {
                                case PUBLIC:
                                    pub += 1;
                                    break;
                                case PRIVATE:
                                    pri += 1;
                                    break;
                                case PROTECTED:
                                    pro += 1;
                                    break;
                                case PACKAGE:
                                    pac += 1;
                                    break;
                                default:
                                    break; } } } }
                Map<Visibility, Integer> map = new HashMap<>();
                map.put(Visibility.PUBLIC, pub);
                map.put(Visibility.PRIVATE, pri);
                map.put(Visibility.PROTECTED, pro);
                map.put(Visibility.PACKAGE, pac);
                return map; } } }

    private void updateAttriF(String cid) {
        HashMap<String, Integer> tmp1 = attriCounter.get(cid);
        HashMap<String, String> tmp2 = attriUml.get(cid);
        HashMap<String, UmlAttribute> tmp3 = invertAttriUml.get(cid);
        List<AttributeClassInformation> tmp4 = new ArrayList<>();
        for (UmlAttribute at : tmp3.values()) {
            if (at.getVisibility() != Visibility.PRIVATE) {
                tmp4.add(new AttributeClassInformation(
                        at.getName(), invertClassUml.get(cid).getName())); } }
        HashMap<String, Integer> new1 = new HashMap<>();
        HashMap<String, String> new2 = new HashMap<>();
        HashMap<String, UmlAttribute> new3 = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry : tmp1.entrySet()) {
            new1.put(entry.getKey(), entry.getValue()); }
        for (HashMap.Entry<String, String> entry : tmp2.entrySet()) {
            new2.put(entry.getKey(), entry.getValue()); }
        for (HashMap.Entry<String, UmlAttribute> entry : tmp3.entrySet()) {
            new3.put(entry.getKey(), entry.getValue()); }
        String tmpCid = cid;
        while (generalizationUml.containsKey(tmpCid)) {
            tmpCid = generalizationUml.get(tmpCid);
            if (attriCounter.containsKey(tmpCid)) {
                HashMap<String, Integer> t1 = attriCounter.get(tmpCid);
                HashMap<String, String> t2 = attriUml.get(tmpCid);
                HashMap<String, UmlAttribute> t3
                        = invertAttriUml.get(tmpCid);
                for (String tt1 : t1.keySet()) {
                    int ttt1 = new1.getOrDefault(tt1, 0);
                    new1.put(tt1, ttt1 + 1); }
                for (HashMap.Entry<String, String> entry : t2.entrySet()) {
                    new2.put(entry.getKey(), entry.getValue()); }
                for (HashMap.Entry<String, UmlAttribute> entry :
                        t3.entrySet()) {
                    new3.put(entry.getKey(), entry.getValue()); }
                for (UmlAttribute atttt : t3.values()) {
                    if (atttt.getVisibility() != Visibility.PRIVATE) {
                        tmp4.add(new AttributeClassInformation(atttt.getName(),
                                invertClassUml.get(tmpCid).getName())); } } } }
        attriCounterF.put(cid, new1);
        attriUmlF.put(cid, new2);
        invertAttriUmlF.put(cid, new3);
        classInfo.put(cid, tmp4); }

    public Visibility getClassAttributeVisibility(
            String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className); } else {
            String classId = classUml.get(className);
            if (!invertAttriUmlF.containsKey(classId)) {
                updateAttriF(classId); }
            if (!attriCounterF.get(classId).containsKey(attributeName)) {
                throw new AttributeNotFoundException(
                        className, attributeName); } else {
                if (attriCounterF.get(classId).get(attributeName) >= 2) {
                    throw new AttributeDuplicatedException(
                            className, attributeName); } else {
                    String attriId =
                            attriUmlF.get(classId).get(attributeName);
                    return invertAttriUmlF.get(classId).
                            get(attriId).getVisibility(); } } } }

    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className);
            } else {
                String tmpClassId = classUml.get(className);
                while (generalizationUml.containsKey(tmpClassId)) {
                    tmpClassId = generalizationUml.get(tmpClassId); }
                return invertClassUml.get(tmpClassId).getName(); } } }

    private HashSet<UmlInterface> updateMyFace(String classId) {
        HashSet<UmlInterface> ans = new HashSet<>();
        if (realization.size() != 0) {
            for (String id : realization.keySet()) {
                if (id.equals(classId)) {
                    for (String str: realization.get(id)) {
                        ans.add(invertFaceUml.get(str)); }
                }
            }
        }
        return ans;
    }

    private HashSet<UmlInterface> mergeList(
            HashSet<UmlInterface> l1, HashSet<UmlInterface> l2) {
        HashSet<UmlInterface> ans = new HashSet<>();
        for (UmlInterface a : l1) {
            ans.add(a);
        }
        for (UmlInterface a : l2) {
            ans.add(a);
        }
        return ans;
    }

    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className);
        } else {
            String classId = classUml.get(className);
            if (!faceInfo.containsKey(classId)) {
                String tmpClassId = classId;
                HashSet<UmlInterface> ans = updateMyFace(classId);
                while (generalizationUml.containsKey(tmpClassId)) {
                    tmpClassId = generalizationUml.get(tmpClassId);
                    HashSet<UmlInterface> list = updateMyFace(tmpClassId);
                    ans = mergeList(list, ans);
                }
                HashSet<UmlInterface> ans2 = updateMyFace(classId);
                for (UmlInterface face : ans) {
                    String iter = face.getId();
                    while (generalizationUml.containsKey(iter)) {
                        iter = generalizationUml.get(iter);
                        if (invertFaceUml.containsKey(iter)) {
                            ans2.add(invertFaceUml.get(iter));
                        }
                    }
                }
                ans = mergeList(ans, ans2);
                List<String> names = new ArrayList<>();
                for (UmlInterface face: ans) {
                    names.add(face.getName());
                }
                faceInfo.put(classId, names);
            }
            return faceInfo.get(classId);
        }
    }

    public List<AttributeClassInformation> getInformationNotHidden(
            String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className);
        } else {
            String classId = classUml.get(className);
            if (!classInfo.containsKey(classId)) {
                updateAttriF(classId);
            }
            return classInfo.get(classId);
        }
    }
}