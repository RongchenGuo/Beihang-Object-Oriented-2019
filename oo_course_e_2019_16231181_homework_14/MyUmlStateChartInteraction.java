import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MyUmlStateChartInteraction {
    // state machine id to region id
    private HashMap<String, String> stateMachine2Region;
    // state machine name to counter
    private HashMap<String, Integer> stateMachineCounter;
    // state machine name to state machine id (incomplete)
    private HashMap<String, String> stateMachineUml;
    // region id to (pre/ post)state ids
    private HashMap<String, HashSet<String>> regionPres;
    private HashMap<String, HashSet<String>> regionStates;
    private HashMap<String, HashSet<String>> regionPosts;
    // region id to transition ids
    private HashMap<String, HashSet<String>> region2Trans;
    // regionId to (stateCounter)
    private HashMap<String, HashMap<String, Integer>> stateCounter;
    // regionId to (stateName to stateId) (incomplete)
    private HashMap<String, HashMap<String, String>> stateUml;
    // transSource to transTarget
    private HashMap<String, HashSet<String>> transitions;
    // stateId to reachable  state number (middle)
    private HashMap<String, Integer> middle;

    public MyUmlStateChartInteraction(ArrayList<UmlElement> elements) {
        stateMachine2Region = new HashMap<>();
        stateMachineCounter = new HashMap<>();
        stateMachineUml = new HashMap<>();
        regionPres = new HashMap<>();
        regionPosts = new HashMap<>();
        regionStates = new HashMap<>();
        region2Trans = new HashMap<>();
        stateCounter = new HashMap<>();
        stateUml = new HashMap<>();
        transitions = new HashMap<>();
        middle = new HashMap<>();
        if (elements.size() == 0) {
            return;
        }
        for (UmlElement e : elements) {
            switch (e.getElementType()) {
                case UML_REGION:
                    UmlRegion aa = (UmlRegion) e;
                    stateMachine2Region.put(aa.getParentId(), aa.getId());
                    break;
                case UML_STATE_MACHINE:
                    UmlStateMachine bb = (UmlStateMachine) e;
                    stateMachineUml.put(bb.getName(), bb.getId());
                    int tmpInt =
                            stateMachineCounter.getOrDefault(bb.getName(), 0);
                    stateMachineCounter.put(bb.getName(), tmpInt + 1);
                    break;
                case UML_PSEUDOSTATE:
                    UmlPseudostate cc = (UmlPseudostate) e;
                    HashSet<String> tmp = new HashSet<>();
                    if (regionPres.containsKey(cc.getParentId())) {
                        tmp = regionPres.get(cc.getParentId());
                    }
                    tmp.add(cc.getId());
                    regionPres.put(cc.getParentId(), tmp);
                    break;
                case UML_STATE:
                    UmlState dd = (UmlState) e;
                    tmp = new HashSet<>();
                    if (regionStates.containsKey(dd.getParentId())) {
                        tmp = regionStates.get(dd.getParentId());
                    }
                    tmp.add(dd.getId());
                    regionStates.put(dd.getParentId(), tmp);
                    HashMap<String, Integer> tmp1 = stateCounter.getOrDefault(
                            dd.getParentId(), new HashMap<>());
                    int tmp2 = tmp1.getOrDefault(dd.getName(), 0);
                    tmp1.put(dd.getName(), tmp2 + 1);
                    stateCounter.put(dd.getParentId(), tmp1);
                    HashMap<String, String> tmp3 = stateUml.getOrDefault(
                            dd.getParentId(), new HashMap<>());
                    tmp3.put(dd.getName(), dd.getId());
                    stateUml.put(dd.getParentId(), tmp3);
                    break;
                case UML_FINAL_STATE:
                    UmlFinalState ee = (UmlFinalState) e;
                    tmp = new HashSet<>();
                    if (regionPosts.containsKey(ee.getParentId())) {
                        tmp = regionPosts.get(ee.getParentId());
                    }
                    tmp.add(ee.getId());
                    regionPosts.put(ee.getParentId(), tmp);
                    break;
                case UML_EVENT:
                    break;
                case UML_OPAQUE_BEHAVIOR:
                    break;
                case UML_TRANSITION:
                    UmlTransition hh = (UmlTransition) e;
                    tmp = new HashSet<>();
                    if (region2Trans.containsKey(hh.getParentId())) {
                        tmp = region2Trans.get(hh.getParentId());
                    }
                    tmp.add(hh.getId());
                    region2Trans.put(hh.getParentId(), tmp);
                    HashSet<String> tmp5 = new HashSet<>();
                    if (transitions.containsKey(hh.getSource())) {
                        tmp5 = transitions.get(hh.getSource());
                    }
                    tmp5.add(hh.getTarget());
                    transitions.put(hh.getSource(), tmp5);
                    break;
                default:
                    break;
            }
        }
    }

    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        if (!stateMachineCounter.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (stateMachineCounter.get(stateMachineName) != 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            String machineId = stateMachineUml.get(stateMachineName);
            String regionId = stateMachine2Region.get(machineId);
            int ans = 0;
            if (regionStates.containsKey(regionId)) {
                ans = regionStates.get(regionId).size();
            }
            if (regionPres.containsKey(regionId)) { ans += 1; }
            if (regionPosts.containsKey(regionId)) { ans += 1; }
            return ans;
        }
    }

    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        if (!stateMachineCounter.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (stateMachineCounter.get(stateMachineName) != 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            String machineId = stateMachineUml.get(stateMachineName);
            String regionId = stateMachine2Region.get(machineId);
            if (region2Trans.containsKey(regionId)) {
                return region2Trans.get(regionId).size();
            }
            return 0;
        }
    }

    public int getSubsequentStateCount(
            String stateMachineName, String stateName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        if (!stateMachineCounter.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (stateMachineCounter.get(stateMachineName) != 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        } else {
            String machineId = stateMachineUml.get(stateMachineName);
            String regionId = stateMachine2Region.get(machineId);
            if (!stateCounter.containsKey(regionId)) {
                throw new StateNotFoundException(stateMachineName, stateName);
            } else if (!stateCounter.get(regionId).containsKey(stateName)) {
                throw new StateNotFoundException(stateMachineName, stateName);
            } else if (stateCounter.get(regionId).get(stateName) != 1) {
                throw new StateDuplicatedException(stateMachineName, stateName);
            } else {
                String stateId = stateUml.get(regionId).get(stateName);
                if (middle.containsKey(stateId)) {
                    return middle.get(stateId);
                }
                HashSet<String> ansSet = new HashSet<>();
                LinkedList<String> q = new LinkedList<>();
                q.addLast(stateId);
                HashSet<String> tranSet = new HashSet<>();
                tranSet.add(stateId);
                while (q.size() != 0) {
                    String tmpStateId = q.removeFirst();
                    if (transitions.containsKey(tmpStateId)) {
                        HashSet<String> tmp = transitions.get(tmpStateId);
                        for (String s: tmp) {
                            if (!tranSet.contains(s)) {
                                tranSet.add(s);
                                q.addLast(s);
                                ansSet.add(s);
                            } else {
                                ansSet.add(s);
                            }
                        }
                    }
                }
                middle.put(stateId, ansSet.size());
                return ansSet.size();
            }
        }
    }
}
