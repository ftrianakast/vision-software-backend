package co.com.vision.prueba.domain.rules;

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
 * Style 0123: A throwing Message event should have outgoing message flow
 * 
 * @author Felipe Triana<ftrianakast@gmail.com>
 * @version 1.0
 */
public class Rule_Style_123 implements Rule {

	private static ValidationRule validationRule = new ValidationRule(
			"Style 0123",
			"A throwing Message event should have outgoing message flow");

	@Override
	public Optional<List<ValidationErrorMessage>> validate(Process process) {
		List<WorkflowProcess> workflowProcesses = process
				.getWorkFlowProcesses().stream()
				.filter(ep -> ep.getEvents().isPresent())
				.collect(Collectors.toList());

		List<Node> throwEvents = workflowProcesses
				.stream()
				.map(workP -> workP.getEvents().get())
				.flatMap(eventList -> eventList.stream())
				.map(event -> ((Event) event))
				.filter(event -> event.getCatchThrow().isPresent()
						&& event.getCatchThrow().get().equals("THROW"))
				.map(event -> ((Node) event)).collect(Collectors.toList());

		List<Node> throwEventsWithoutOutgoingMessage = getThrowingEventsWithoutOutgoingFlow(
				throwEvents, process.getMessageFlows().get());

		List<ValidationErrorMessage> validationErrors = new ArrayList<ValidationErrorMessage>();
		ValidationErrorMessage validationError = ErrorMessageGenerator
				.generateErrorMessage(throwEventsWithoutOutgoingMessage,
						validationRule);
		validationErrors.add(validationError);
		return Optional.of(validationErrors);
	}

	@Override
	public Optional<ValidationErrorMessage> validateWorkflowProcess(
			WorkflowProcess workflowProcess) {
		return Optional.empty();
	}

	/**
	 * 
	 * @param throwingEvents
	 * @param transitions
	 * @return
	 */
	private List<Node> getThrowingEventsWithoutOutgoingFlow(
			List<Node> throwingEvents, List<Transition> transitions) {
		return throwingEvents
				.stream()
				.map(throwingEvent -> getThrowingEventWithoutOutgoingFlow(
						(Event) throwingEvent, transitions))
				.filter(throwingEvent -> throwingEvent.isPresent())
				.map(throwingEvent -> throwingEvent.get())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param throwingEvent
	 * @param transitions
	 * @return
	 */
	private Optional<Event> getThrowingEventWithoutOutgoingFlow(
			Event throwingEvent, List<Transition> transitions) {

		List<Transition> throwingTransitions = transitions
				.stream()
				.filter(messageFlow -> messageFlow.getFrom().getId()
						.equals(throwingEvent.getId()))
				.collect(Collectors.toList());

		if (throwingTransitions.size() > 0) {
			return Optional.empty();
		} else {
			return Optional.of(throwingEvent);
		}
	}
}
