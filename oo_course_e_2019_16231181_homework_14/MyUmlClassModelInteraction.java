import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MyUmlClassModelInteraction {
    private HashMap<String, ArrayList<String>> generalizationUml;
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
    private MyOperation myOperation;
    private MyAssociation myAssociation;
    private MyInterface myInterface;

    public MyUmlClassModelInteraction(ArrayList<UmlElement> elements) {
        myOperation = new MyOperation();
        myAssociation = new MyAssociation();
        myInterface = new MyInterface();
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
        classInfo = new HashMap<>();
        if (elements.size() == 0) { return; }
        for (UmlElement e : elements) {
            switch (e.getElementType()) {
                case UML_CLASS:
                    UmlClass aa = (UmlClass) e;
                    // System.out.println("C:"+aa.getId()+aa.getName());
                    int tmp = classCounter.getOrDefault(aa.getName(), 0);
                    classCounter.put(aa.getName(), tmp + 1);
                    classUml.put(aa.getName(), aa.getId());
                    invertClassUml.put(aa.getId(), aa);
                    myOperation.updateFirst(aa.getId());
                    if (!attriCounter.containsKey(aa.getId())) {
                        attriCounter.put(aa.getId(), new HashMap<>());
                        attriUml.put(aa.getId(), new HashMap<>());
                        invertAttriUml.put(aa.getId(), new HashMap<>()); }
                    break;
                case UML_OPERATION:
                    myOperation.updateOper(e);
                    break;
                case UML_ATTRIBUTE:
                    UmlAttribute cc = (UmlAttribute) e;
                    String tmpClassId = cc.getParentId();
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
                    myOperation.updatePara(e);
                    break;
                case UML_GENERALIZATION:
                    UmlGeneralization ee = (UmlGeneralization) e;
                    ArrayList<String> estr = generalizationUml.
                            getOrDefault(ee.getSource(), new ArrayList<>());
                    estr.add(ee.getTarget());
                    generalizationUml.put(ee.getSource(), estr);
                    break;
                case UML_INTERFACE:
                    myInterface.updateInterface(e);
                    break;
                case UML_INTERFACE_REALIZATION:
                    myInterface.updateRealization(e);
                    break;
                case UML_ASSOCIATION:
                    myAssociation.updateAsso(e);
                    break;
                case UML_ASSOCIATION_END:
                    myAssociation.updateAssoId(e);
                    break;
                default:
                    break; } } }

    public int getClassCount() { return invertClassUml.size(); }

    public int getClassOperationCount(
            String className, OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className); } else {
            String id = classUml.get(className);
            return myOperation.getClassOperationCount(id, queryType); } }

    public int getClassAttributeCount(
            String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
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
                        tmpClassId = generalizationUml.get(tmpClassId).get(0);
                        if (invertAttriUml.containsKey(tmpClassId)) {
                            counter += invertAttriUml.get(tmpClassId).size();
                        } }
                    return counter; } } } }

    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String classId = classUml.get(className);
                return myAssociation.getClassAssociationCount(
                        classId, invertClassUml, generalizationUml); } } }

    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String id = classUml.get(className);
                return myAssociation.getClassAssociatedClassList(
                        id, invertClassUml, generalizationUml); } } }

    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String classId = classUml.get(className);
                return myOperation.getClassOperationVisibility(
                        classId, operationName); } } }

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
            tmpCid = generalizationUml.get(tmpCid).get(0);
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
            throw new ClassNotFoundException(className); } else {
            if (classCounter.get(className) >= 2) {
                throw new ClassDuplicatedException(className); } else {
                String tmpClassId = classUml.get(className);
                while (generalizationUml.containsKey(tmpClassId)) {
                    tmpClassId = generalizationUml.get(tmpClassId).get(0); }
                return invertClassUml.get(tmpClassId).getName(); } } }

    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classCounter.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (classCounter.get(className) >= 2) {
            throw new ClassDuplicatedException(className);
        } else {
            String classId = classUml.get(className);
            return myInterface.getImplementInterfaceList(
                    classId, generalizationUml); } }

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
                updateAttriF(classId); }
            return classInfo.get(classId); } }

    public void checkForUml002() throws UmlRule002Exception {
        Set<AttributeClassInformation> ans = new HashSet<>();
        if (invertClassUml.size() != 0) {
            for (String classId: invertClassUml.keySet()) {
                String classNam = invertClassUml.get(classId).getName();
                HashSet<String> names = new HashSet<>();
                if (invertAttriUml.containsKey(classId)) {
                    for (UmlAttribute b: invertAttriUml.get(classId).values()) {
                        // System.out.println(classNam + "  " + b.getName());
                        if (b.getName() == null) { continue; }
                        if (names.contains(b.getName())) {
                            ans.add(new AttributeClassInformation(
                                    b.getName(),classNam));
                        } else { names.add(b.getName()); } } }
                ArrayList<String> ls = myAssociation.checkClassAsso(classId);
                if (ls.size() != 0) {
                    for (String s: ls) {
                        // System.out.println(classNam + "  " + s);
                        if (s == null) { continue; }
                        if (names.contains(s)) {
                            ans.add(new AttributeClassInformation(s, classNam));
                        } else { names.add(s); } } } } }
        if (ans.size() != 0) { throw new UmlRule002Exception(ans); } }

    public void checkForUml008() throws UmlRule008Exception {
        Set<UmlClassOrInterface> ans = new HashSet<>();
        if (invertClassUml.size() != 0) {
            for (String classId : invertClassUml.keySet()) {
                HashSet<String> t = new HashSet<>();
                t.add(classId);
                String tmpClassId = classId;
                while (generalizationUml.containsKey(tmpClassId)) {
                    tmpClassId = generalizationUml.get(tmpClassId).get(0);
                    if (tmpClassId.equals(classId)) {
                        ans.add(invertClassUml.get(classId));
                        break;
                    } if (t.contains(tmpClassId)) { break; } else {
                        t.add(tmpClassId); } } } }
        ArrayList<UmlClassOrInterface> ls =
                myInterface.checkRule2(generalizationUml);
        if (ls.size() != 0) {
            for (UmlClassOrInterface tmp: ls) { ans.add(tmp); }
        }
        if (ans.size() != 0) { throw new UmlRule008Exception(ans); } }

    public void checkForUml009() throws UmlRule009Exception {
        Set<UmlClassOrInterface> ans = new HashSet<>();
        if (invertClassUml.size() != 0) {
            for (String classId : invertClassUml.keySet()) {
                ArrayList<String> tmp =
                        myInterface.checkImplementInterfaceList3(
                                classId, generalizationUml);
                HashSet<String> tmp2 = new HashSet<>();
                for (String s: tmp) { tmp2.add(s); }
                if (tmp2.size() != tmp.size()) {
                    ans.add(invertClassUml.get(classId)); } } }
        ArrayList<UmlClassOrInterface> ls =
                myInterface.checkRule3(generalizationUml);
        if (ls.size() != 0) {
            for (UmlClassOrInterface tmp: ls) { ans.add(tmp); }
        }
        if (ans.size() != 0) { throw new UmlRule009Exception(ans); } }
}