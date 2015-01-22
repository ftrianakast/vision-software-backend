package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.activity.ActivityType;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;
import co.com.vision.prueba.services.ErrorMessageGenerator;

/**
 * BPMN 0102: All flow objects other than end events and compensating activities
 * must have an outgoing sequence flow, if the process level includes any start
 * or end events.
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class RuleValidator_BPMN_102 implements RuleValidator {

	private static ValidationRule validationRule = new ValidationRule(
			"BPMN_0102",
			"All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.");

	@Override
	public Optional<ValidationErrorMessage> validate(WorkflowProcess process) {
		List<Node> events = process.getEvents();
		List<Node> activities = process.getActivities();

		if (includesProcessStartOrEndEvents(process.getEvents())) {
			List<Node> candidateEvents = excludeEventByEventType(events,
					EventGeneralType.EndEvent);
			List<Node> candidateActivities = excludeActivitiesByActivityType(
					activities, ActivityType.COMPENSATING_ACTIVITY);
			List<Transition> transitions = process.getTransitions();

			List<Node> erroneousActivities = getNodesWithoutOutgoingFlow(
					candidateActivities, transitions);
			List<Node> erroneousEvents = getNodesWithoutOutgoingFlow(
					candidateEvents, transitions);

			erroneousActivities.addAll(erroneousEvents);

			return Optional.of(ErrorMessageGenerator.generateErrorMessage(
					erroneousActivities, validationRule));
		} else {
			return Optional.empty();
		}

	}

	/**
	 * 
	 * @param events
	 * @return
	 */
	public boolean includesProcessStartOrEndEvents(List<Node> events) {
		boolean includesStartEventCondition = events.stream().anyMatch(
				event -> ((Event) event).getGeneralType().equals(
						EventGeneralType.StartEvent));
		boolean includesEndEventCondition = events.stream().anyMatch(
				event -> ((Event) event).getGeneralType().equals(
						EventGeneralType.EndEvent));
		return includesEndEventCondition || includesStartEventCondition;
	}

	/**
	 * 
	 * @param activities
	 * @param activityType
	 * @return
	 */
	private List<Node> excludeActivitiesByActivityType(List<Node> activities,
			ActivityType activityType) {
		List<Node> candidateActivites = activities
				.stream()
				.filter(activity -> !((Activity) activity).getActivityType()
						.equals(activityType)).collect(Collectors.toList());
		return candidateActivites;
	}

	/**
	 * 
	 * @param events
	 * @param evenType
	 * @return
	 */
	private List<Node> excludeEventByEventType(List<Node> events,
			EventGeneralType evenType) {
		List<Node> candidateEvents = events
				.stream()
				.filter(event -> ((Event) event).getGeneralType().equals(
						evenType)).collect(Collectors.toList());

		return candidateEvents;
	}

	/**
	 * 
	 * @param nodes
	 * @param transitions
	 * @return
	 */
	private List<Node> getNodesWithoutOutgoingFlow(List<Node> nodes,
			List<Transition> transitions) {
		return nodes.stream()
				.filter(node -> hasNodeOutgoingTransition(node, transitions))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param node
	 * @param transitions
	 * @return
	 */
	private boolean hasNodeOutgoingTransition(Node node,
			List<Transition> transitions) {
		return transitions.stream()
				.anyMatch(
						transition -> transition.getFrom().getId()
								.equals(node.getId()));

	}
}
