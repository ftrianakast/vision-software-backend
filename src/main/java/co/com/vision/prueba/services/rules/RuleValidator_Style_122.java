package co.com.vision.prueba.services.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.services.ErrorMessageGenerator;

/**
 * Style 0122. A catching Message event should have incoming message flow.
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public class RuleValidator_Style_122 implements RuleValidator {

	private static ValidationRule validationRule = new ValidationRule(
			"Style 0122",
			"A catching Message event should have incoming message flow.");

	@Override
	public Optional<List<ValidationErrorMessage>> validate(Process process) {
		List<WorkflowProcess> workflowProcesses = process
				.getWorkFlowProcesses().stream()
				.filter(ep -> ep.getEvents().isPresent())
				.collect(Collectors.toList());
		if (process.getMessageFlows().isPresent()) {
			List<Node> catchingMessageEvents = getCatchingMessageEvents(workflowProcesses);

			List<Node> catchingMessageEventsWithoutIncomingFlow = getCatchingEventsWithoutIncomingFlow(
					catchingMessageEvents, process.getMessageFlows().get());

			List<ValidationErrorMessage> validationErrorMessages = new ArrayList<ValidationErrorMessage>();
			ValidationErrorMessage errorMessage = ErrorMessageGenerator
					.generateErrorMessage(
							catchingMessageEventsWithoutIncomingFlow,
							validationRule);

			validationErrorMessages.add(errorMessage);
			return Optional.of(validationErrorMessages);
		} else {
			return Optional.empty();
		}

	}

	@Override
	public Optional<ValidationErrorMessage> validateWorkflowProcess(
			WorkflowProcess workflowProcess) {
		return Optional.empty();
	}

	/**
	 * 
	 * @param events
	 * @return
	 */
	private List<Node> searchCatchingEvents(List<Node> events) {
		return events.stream()
				.filter(event -> ((Event) event).getCatchThrow().isPresent())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param workFlowProcesses
	 * @return
	 */
	private List<Node> getCatchingMessageEvents(
			List<WorkflowProcess> workFlowProcesses) {
		List<Node> catchingMessageEvents = workFlowProcesses
				.stream()
				.map(workflowProcess -> searchCatchingEvents(workflowProcess
						.getEvents().get()))
				.flatMap(catchingEventsWP -> catchingEventsWP.stream())
				.collect(Collectors.toList());
		return catchingMessageEvents;
	}

	/**
	 * 
	 * @param catchingEvents
	 * @param transitions
	 * @return
	 */
	private List<Node> getCatchingEventsWithoutIncomingFlow(
			List<Node> catchingEvents, List<Transition> transitions) {
		return catchingEvents
				.stream()
				.map(catchingEvent -> getCatchingEventWithoutIncomingFlow(
						(Event) catchingEvent, transitions))
				.filter(catchingEvent -> catchingEvent.isPresent())
				.map(catchingEvent -> catchingEvent.get())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param catchingEvent
	 * @param transitions
	 * @return
	 */
	private Optional<Event> getCatchingEventWithoutIncomingFlow(
			Event catchingEvent, List<Transition> transitions) {
		List<Transition> catchingTransitions = transitions
				.stream()
				.filter(messageFlow -> messageFlow.getTo().getId()
						.equals(catchingEvent.getId()))
				.collect(Collectors.toList());

		if (catchingTransitions.size() > 0) {
			return Optional.empty();
		} else {
			return Optional.of(catchingEvent);
		}
	}
}
