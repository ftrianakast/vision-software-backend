package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;

/**
 * 
 * @author Felipe Triana<ftrianakast@gmail.com>
 *
 */
public interface RuleValidator {

	/**
	 * Validates if a bizagi process is valid according to some existing rules
	 * 
	 * @return
	 */
	default Optional<List<ValidationErrorMessage>> validate(Process process) {
		List<ValidationErrorMessage> validationErrorMessages = process
				.getWorkFlowProcesses()
				.stream()
				.map(workflowProcess -> validateWorkflowProcess(workflowProcess))
				.filter(optionError -> optionError.isPresent())
				.map(optionError -> optionError.get())
				.collect(Collectors.toList());

		Optional<List<ValidationErrorMessage>> response = validationErrorMessages
				.size() > 0 ? Optional.of(validationErrorMessages) : Optional
				.empty();

		return response;
	}

	/**
	 * Validates a specific workflow process
	 * @param workflowProcess
	 * @return
	 */
	public Optional<ValidationErrorMessage> validateWorkflowProcess(
			WorkflowProcess workflowProcess);

}
