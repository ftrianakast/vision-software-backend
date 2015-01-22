package co.com.vision.prueba.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.event.Event;

/**
 * 
 * Style 0115. A throwing intermediate event should be labeled.
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ValidationRule_Style_115 implements ValidationRule {

	@Override
	public Optional<ValidationErrorMessage> validate(WorkflowProcess process) {
		List<Event> events = process.getEvents();
		List<Event> eventsNotLabeled = events.stream()
				.filter(event -> event.getName().equals(null))
				.collect(Collectors.toList());

		if (eventsNotLabeled.size() == 0) {
			return Optional.empty();
		} else {
			return Optional.empty();
		}
	}

}
