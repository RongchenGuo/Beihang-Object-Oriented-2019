import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyOperation {
    private HashMap<String, HashMap<String, UmlOperation>> invertOperUml;
    private HashMap<String, HashSet<String>> paraUml;
    private HashMap<String, UmlParameter> invertParaUml;
    private HashMap<String, HashMap<OperationQueryType, Integer>> operQuer;

    public MyOperation() {
        invertOperUml = new HashMap<>();
        paraUml = new HashMap<>();
        invertParaUml = new HashMap<>();
        operQuer = new HashMap<>();
    }

    public void updateFirst(String s) {
        if (!invertOperUml.containsKey(s)) {
            invertOperUml.put(s, new HashMap<>());
        }
    }

    public void updateOper(UmlElement e) {
        UmlOperation bb = (UmlOperation) e;
        String tmpClassId = bb.getParentId();
        if (!invertOperUml.containsKey(tmpClassId)) {
            invertOperUml.put(tmpClassId, new HashMap<>());
        }
        HashMap<String, UmlOperation> tmp4 = invertOperUml.get(tmpClassId);
        tmp4.put(bb.getId(), bb);
        invertOperUml.put(tmpClassId, tmp4);
    }

    public void updatePara(UmlElement e) {
        UmlParameter dd = (UmlParameter) e;
        invertParaUml.put(dd.getId(), dd);
        HashSet<String> tmpParaSet;
        if (paraUml.containsKey(dd.getParentId())) {
            tmpParaSet = paraUml.get(dd.getParentId());
        } else {
            tmpParaSet = new HashSet<>();
        }
        tmpParaSet.add(dd.getId());
        paraUml.put(dd.getParentId(), tmpParaSet);
    }

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
                                break;
                        }
                    }
                }
                if (returnPara == 0) { nonReturnOp += 1; }
                else { returnOp += 1; }
                if (inPara != 0 || inoutPara != 0 || outPara != 0) {
                    paraOp += 1;
                }
                else { nonParaOp += 1; }
                allOp += 1;
            }
        }
        HashMap<OperationQueryType, Integer> tmp = new HashMap<>();
        tmp.put(OperationQueryType.NON_RETURN, nonReturnOp);
        tmp.put(OperationQueryType.RETURN, returnOp);
        tmp.put(OperationQueryType.NON_PARAM, nonParaOp);
        tmp.put(OperationQueryType.PARAM, paraOp);
        tmp.put(OperationQueryType.ALL, allOp);
        operQuer.put(classId, tmp);
    }

    public int getClassOperationCount(
            String classId, OperationQueryType queryType) {
        if (operQuer.containsKey(classId)) {
            return operQuer.get(classId).get(queryType);
        }
        updateOperCount(classId);
        return operQuer.get(classId).get(queryType);
    }

    public Map<Visibility, Integer> getClassOperationVisibility(
            String classId, String operationName) {
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
        return map;
    }
}
