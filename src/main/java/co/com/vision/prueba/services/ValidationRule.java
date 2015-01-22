package co.com.vision.prueba.services;

import java.util.Optional;

import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;

public interface ValidationRule {

	/**
	 * Validates if a bizagi process is valid according to some existing rules
	 * 
	 * @return
	 */
	public Optional<ValidationErrorMessage> validate(WorkflowProcess process);

}
