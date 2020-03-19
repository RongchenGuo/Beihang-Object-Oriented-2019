import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    // TODO : IMPLEMENT
    private MyUmlClassModelInteraction myClass;
    private MyUmlCollaborationInteraction myCollaboration;
    private MyUmlStateChartInteraction myState;

    public MyUmlGeneralInteraction(UmlElement... elements) {
        ArrayList<UmlElement> l1 = new ArrayList<>();
        ArrayList<UmlElement> l2 = new ArrayList<>();
        ArrayList<UmlElement> l3 = new ArrayList<>();
        for (UmlElement e : elements) {
            switch (e.getElementType()) {
                case UML_CLASS:
                case UML_OPERATION:
                case UML_ATTRIBUTE:
                case UML_PARAMETER:
                case UML_GENERALIZATION:
                case UML_INTERFACE:
                case UML_INTERFACE_REALIZATION:
                case UML_ASSOCIATION:
                case UML_ASSOCIATION_END:
                    l1.add(e);
                    break;
                case UML_REGION:
                case UML_STATE_MACHINE:
                case UML_PSEUDOSTATE:
                case UML_STATE:
                case UML_FINAL_STATE:
                case UML_EVENT:
                case UML_OPAQUE_BEHAVIOR:
                case UML_TRANSITION:
                    l3.add(e);
                    break;
                case UML_INTERACTION:
                case UML_MESSAGE:
                case UML_ENDPOINT:
                case UML_LIFELINE:
                    l2.add(e);
                    break;
                default:
                    break;
            }
        }
        myClass = new MyUmlClassModelInteraction(l1);
        myCollaboration = new MyUmlCollaborationInteraction(l2);
        myState = new MyUmlStateChartInteraction(l3);
    }

    public int getClassCount() {
        return myClass.getClassCount();
    }

    public int getClassOperationCount(
            String className, OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getClassOperationCount(className, queryType);
    }

    public int getClassAttributeCount(
            String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getClassAttributeCount(className, queryType);
    }

    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getClassAssociationCount(className);
    }

    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getClassAssociatedClassList(className);
    }

    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getClassOperationVisibility(className, operationName);
    }

    public Visibility getClassAttributeVisibility(
            String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        return myClass.getClassAttributeVisibility(className, attributeName);
    }

    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getTopParentClass(className);
    }

    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getImplementInterfaceList(className);
    }

    public List<AttributeClassInformation> getInformationNotHidden(
            String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        return myClass.getInformationNotHidden(className);
    }

    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return myCollaboration.getParticipantCount(interactionName);
    }

    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return myCollaboration.getMessageCount(interactionName);
    }

    public int getIncomingMessageCount(
            String interactionName, String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        return myCollaboration.getIncomingMessageCount(
                interactionName, lifelineName);
    }

    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return myState.getStateCount(stateMachineName);
    }

    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return myState.getTransitionCount(stateMachineName);
    }

    public int getSubsequentStateCount(
            String stateMachineName, String stateName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        return myState.getSubsequentStateCount(stateMachineName, stateName);
    }

    public void checkForUml002() throws UmlRule002Exception {
        myClass.checkForUml002();
    }

    public void checkForUml008() throws UmlRule008Exception {
        myClass.checkForUml008();
    }

    public void checkForUml009() throws UmlRule009Exception {
        myClass.checkForUml009();
    }

}

