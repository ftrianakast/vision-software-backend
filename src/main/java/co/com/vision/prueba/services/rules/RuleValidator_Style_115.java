package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.domain.event.EventGeneralType;
import co.com.vision.prueba.services.ErrorMessageGenerator;

/**
 * 
 * Style 0115. A throwing intermediate event should be labeled.
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class RuleValidator_Style_115 implements RuleValidator {

	private static ValidationRule validationRule = new ValidationRule(
			"Style_115", "A throwing intermediate event should be labeled.");

	@Override
	public Optional<ValidationErrorMessage> validateWorkflowProcess(
			WorkflowProcess process) {
		Optional<List<Node>> events = process.getEvents();

		if (events.isPresent()) {
			List<Node> eventsNotLabeled = getIntermediateEventsNotLabeled(events.get());

			if (eventsNotLabeled.size() == 0) {
				return Optional.empty();
			} else {
				return Optional.of(ErrorMessageGenerator.generateErrorMessage(
						eventsNotLabeled, validationRule));
			}
		} else {
			return Optional.empty();
		}

	}

	/**
	 * @param events
	 * @return
	 */
	private List<Node> getIntermediateEventsNotLabeled(List<Node> events) {
		List<Node> eventsNotLabeled = events
				.stream()
				.filter(event -> event.getName().equals("")
						&& ((Event) event).getGeneralType().equals(
								EventGeneralType.IntermediateEvent))
				.collect(Collectors.toList());

		return eventsNotLabeled;
	}

}
