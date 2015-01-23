package co.com.vision.prueba.services.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.services.ErrorMessageGenerator;

/**
 * 
 * Style 0104: Two activities in the same process should not have the same name.
 * (Use global activity to reuse a single activity in a process.)
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class RuleValidator_Style_104 implements RuleValidator {

	private static ValidationRule validationRule = new ValidationRule(
			"Style_0104",
			"Two activities in the same process should not have the same name.");


	
	@Override
	public Optional<ValidationErrorMessage> validateWorkflowProcess(WorkflowProcess process) {
		List<Node> activities = process.getActivities();
		List<Node> repeatedActivities = findRepeatedNamedActivities(activities);

		if (repeatedActivities.size() == 0) {
			return Optional.empty();
		} else {
			return Optional.of(ErrorMessageGenerator.generateErrorMessage(
					repeatedActivities, validationRule));
		}
	}

	/**
	 * It validates if there are activities with the same name
	 * 
	 * @param activities
	 * @return
	 */
	private List<Node> findRepeatedNamedActivities(List<Node> activities) {
		List<Node> repeatedActivities = new ArrayList<Node>();
		for (int i = 0; i < activities.size(); i++) {
			Activity someActivity = (Activity) activities.get(i);
			boolean reapeatedActivityFinded = false;
			for (int j = i + 1; i < activities.size(); i++) {
				Activity otherActivity = (Activity) activities.get(j);
				if (someActivity.getName().equals(otherActivity.getName())) {
					reapeatedActivityFinded = true;
				}
			}
			if (reapeatedActivityFinded) {
				repeatedActivities.add(someActivity);
			}
		}
		return repeatedActivities;
	}

	

}
