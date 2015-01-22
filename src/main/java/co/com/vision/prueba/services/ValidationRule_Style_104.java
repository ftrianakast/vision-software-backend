package co.com.vision.prueba.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;

/**
 * 
 * Style 0104: Two activities in the same process should not have the same name.
 * (Use global activity to reuse a single activity in a process.)
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ValidationRule_Style_104 implements ValidationRule {
	@Override
	public Optional<ValidationErrorMessage> validate(WorkflowProcess process) {
		List<Activity> activities = process.getActivities();
		List<Activity> repeatedActivities = findRepeatedNamedActivities(activities);
		ValidationErrorMessage validationMessage = null;

		if (repeatedActivities.size() == 0) {
			validationMessage = new ValidationErrorMessage(
					"The rule Style_104 was validated for process :"
							+ process.getName());
		} else {
			Activity firstRepeatedActivity = repeatedActivities.get(0);
			new ValidationErrorMessage(
					firstRepeatedActivity.getId(),
					"It was found at least one activity with the same name than other",
					firstRepeatedActivity.getName());
		}
		return Optional.empty();
	}

	/**
	 * It validates if there are activities with the same name
	 * 
	 * @param activities
	 * @return
	 */
	private List<Activity> findRepeatedNamedActivities(List<Activity> activities) {
		List<Activity> repeatedActivities = new ArrayList<Activity>();
		for (int i = 0; i < activities.size(); i++) {
			Activity someActivity = activities.get(i);
			boolean reapeatedActivityFinded = false;
			for (int j = i + 1; i < activities.size(); i++) {
				Activity otherActivity = activities.get(j);
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
