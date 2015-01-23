package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.NodeType;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;
import co.com.vision.prueba.domain.event.EventSpecificType;
import co.com.vision.prueba.services.ErrorMessageGenerator;

/**
 * Style 0123: A throwing Message event should have outgoing message flow
 * 
 * @author Felipe Triana<ftrianakast@gmail.com>
 * @version 1.0
 */
public class RuleValidator_Style_123 implements RuleValidator {

	private static ValidationRule validationRule = new ValidationRule(
			"Style 0123",
			"A throwing Message event should have outgoing message flow");

	@Override
	public Optional<ValidationErrorMessage> validateWorkflowProcess(
			WorkflowProcess process) {

		List<Node> erroneousNodes = getThrowingMessageEventsWithoutOutgoingMessage(process
				.getTransitions());

		if (erroneousNodes.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(ErrorMessageGenerator.generateErrorMessage(
					erroneousNodes, validationRule));
		}
	}

	/**
	 * 
	 * @param transitions
	 * @return
	 */
	private List<Node> getThrowingMessageEventsWithoutOutgoingMessage(
			List<Transition> transitions) {
		List<Transition> transitionsFromThrowMessageEvent = transitions
				.stream()
				.filter(transition -> isAMessageEvent(transition.getFrom())
						&& ((Event) transition.getFrom()).getCatchThrow()
								.isPresent()).collect(Collectors.toList());

		List<Node> erroneousNodes = transitionsFromThrowMessageEvent.stream()
				.map(transition -> transition.getTo())
				.filter(event -> !isAMessageEvent(event))
				.collect(Collectors.toList());
		return erroneousNodes;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private boolean isAMessageEvent(Node node) {
		return node.getType().equals(NodeType.EVENT)
				&& ((Event) node).getGeneralType().equals(
						EventGeneralType.IntermediateEvent)
				&& ((Event) node).getSpecificType().equals(
						EventSpecificType.Message);
	}
}
