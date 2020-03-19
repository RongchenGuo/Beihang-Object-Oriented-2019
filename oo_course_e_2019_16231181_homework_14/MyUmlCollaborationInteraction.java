import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyUmlCollaborationInteraction {
    // interactionName to times
    private HashMap<String, Integer> interCounter;
    // interactionName to interactionId (incomplete)
    private HashMap<String, String> interUml;
    // interactionId to lifelineIds
    private HashMap<String, HashSet<String>> lifelines;
    // interactionId to messages
    private HashMap<String, HashSet<String>> messages;
    // interactionId to (lifelineCounter)
    private HashMap<String, HashMap<String, Integer>> lifeCounter;
    // interactionId to (lifelineName to lifelineId) (incomplete)
    private HashMap<String, HashMap<String, String>> lifeUml;
    // lifelineId to incoming message ids
    private HashMap<String, HashSet<String>> incomings;

    public MyUmlCollaborationInteraction(ArrayList<UmlElement> elements) {
        interCounter = new HashMap<>();
        interUml = new HashMap<>();
        lifelines = new HashMap<>();
        messages = new HashMap<>();
        lifeCounter = new HashMap<>();
        lifeUml = new HashMap<>();
        incomings = new HashMap<>();
        if (elements.size() == 0) {
            return;
        }
        for (UmlElement e : elements) {
            switch (e.getElementType()) {
                case UML_INTERACTION:
                    UmlInteraction aa = (UmlInteraction) e;
                    int tmp = interCounter.getOrDefault(aa.getName(), 0);
                    interCounter.put(aa.getName(), tmp + 1);
                    interUml.put(aa.getName(), aa.getId());
                    break;
                case UML_MESSAGE:
                    UmlMessage bb = (UmlMessage) e;
                    HashSet<String> tmp2 = messages.getOrDefault(
                            bb.getParentId(), new HashSet<>());
                    tmp2.add(bb.getId());
                    messages.put(bb.getParentId(), tmp2);
                    HashSet<String> tmp6 = incomings.getOrDefault(
                            bb.getTarget(), new HashSet<>());
                    tmp6.add(bb.getId());
                    incomings.put(bb.getTarget(), tmp6);
                    break;
                case UML_ENDPOINT:
                    break;
                case UML_LIFELINE:
                    UmlLifeline dd = (UmlLifeline) e;
                    HashSet<String> tmp1 = lifelines.getOrDefault(
                            dd.getParentId(), new HashSet<>());
                    tmp1.add(dd.getId());
                    lifelines.put(dd.getParentId(), tmp1);
                    String interId = dd.getParentId();
                    HashMap<String, Integer> tmp3 =
                            lifeCounter.getOrDefault(interId, new HashMap<>());
                    int tmp4 = tmp3.getOrDefault(dd.getName(), 0);
                    tmp3.put(dd.getName(), tmp4 + 1);
                    lifeCounter.put(interId, tmp3);
                    HashMap<String, String> tmp5 =
                            lifeUml.getOrDefault(interId, new HashMap<>());
                    tmp5.put(dd.getName(), dd.getId());
                    lifeUml.put(interId, tmp5);
                    break;
                default:
                    break;
            }
        }
    }

    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        if (!interCounter.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (interCounter.get(interactionName) != 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            String interId = interUml.get(interactionName);
            HashSet<String> tmp = new HashSet<>();
            if (lifelines.containsKey(interId)) {
                tmp = lifelines.get(interId);
            }
            return tmp.size();
        }
    }

    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        if (!interCounter.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (interCounter.get(interactionName) != 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            String interId = interUml.get(interactionName);
            HashSet<String> tmp = new HashSet<>();
            if (messages.containsKey(interId)) {
                tmp = messages.get(interId);
            }
            return tmp.size();
        }
    }

    public int getIncomingMessageCount(String interactionName,
                                       String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        if (!interCounter.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (interCounter.get(interactionName) != 1) {
            throw new InteractionDuplicatedException(interactionName);
        } else {
            String interId = interUml.get(interactionName);
            if (!lifeCounter.containsKey(interId)) {
                throw new LifelineNotFoundException(
                        interactionName, lifelineName);
            } else if (!lifeCounter.get(interId).containsKey(lifelineName)) {
                throw new LifelineNotFoundException(
                        interactionName, lifelineName);
            } else if (lifeCounter.get(interId).get(lifelineName) != 1) {
                throw new LifelineDuplicatedException(
                        interactionName, lifelineName);
            } else {
                String lifelineId = lifeUml.get(interId).get(lifelineName);
                HashSet<String> ans = new HashSet<>();
                if (incomings.containsKey(lifelineId)) {
                    ans = incomings.get(lifelineId);
                }
                return ans.size();
            }
        }
    }
}
