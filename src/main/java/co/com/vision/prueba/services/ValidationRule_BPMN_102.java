package co.com.vision.prueba.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.activity.ActivityType;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;

/**
 * BPMN 0102: All flow objects other than end events and compensating activities
 * must have an outgoing sequence flow, if the process level includes any start
 * or end events.
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ValidationRule_BPMN_102 implements ValidationRule {

	@Override
	public Optional<ValidationErrorMessage> validate(WorkflowProcess process) {
		List<Event> events = process.getEvents();
		List<Activity> activities = process.getActivities();
		ValidationErrorMessage validationMessage = new ValidationErrorMessage(
				"The rule BPMN_102 was validated for the process: "
						+ process.getName());

		if (includesProcessStartOrEndEvents(process.getEvents())) {
			List<Node> candidateEvents = excludeEventByEventType(events,
					EventGeneralType.END);
			List<Node> candidateActivities = excludeActivitiesByActivityType(
					activities, ActivityType.COMPENSATING_ACTIVITY);
			List<Transition> transitions = process.getTransitions();

			if (!hasNodesOutgoingFlow(candidateActivities, transitions)
					&& !hasNodesOutgoingFlow(candidateEvents, transitions)) {
				validationMessage = new ValidationErrorMessage("Error "
						+ process.getName());
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param events
	 * @return
	 */
	public boolean includesProcessStartOrEndEvents(List<Event> events) {
		boolean includesStartEventCondition = events.stream().anyMatch(
				event -> event.getGeneralType().equals(EventGeneralType.START));
		boolean includesEndEventCondition = events.stream().anyMatch(
				event -> event.getGeneralType().equals(EventGeneralType.END));
		return includesEndEventCondition || includesStartEventCondition;
	}

	/**
	 * 
	 * @param activities
	 * @param activityType
	 * @return
	 */
	private List<Node> excludeActivitiesByActivityType(
			List<Activity> activities, ActivityType activityType) {
		List<Node> candidateActivites = activities
				.stream()
				.filter(activity -> !activity.getActivityType().equals(
						activityType)).collect(Collectors.toList());
		return candidateActivites;
	}

	/**
	 * 
	 * @param events
	 * @param evenType
	 * @return
	 */
	private List<Node> excludeEventByEventType(List<Event> events,
			EventGeneralType evenType) {
		List<Node> candidateEvents = events.stream()
				.filter(event -> event.getGeneralType().equals(evenType))
				.collect(Collectors.toList());

		return candidateEvents;
	}

	/**
	 * 
	 * @param nodes
	 * @param transitions
	 * @return
	 */
	private boolean hasNodesOutgoingFlow(List<Node> nodes,
			List<Transition> transitions) {
		return nodes.stream().allMatch(
				node -> hasNodeOutgoingTransition(node, transitions));
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
