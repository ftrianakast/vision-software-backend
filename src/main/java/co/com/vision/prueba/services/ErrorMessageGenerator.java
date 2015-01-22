package co.com.vision.prueba.services;

import java.util.List;
import java.util.stream.Collectors;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.ValidationRule;
import co.com.vision.prueba.domain.aux.SpecificErrorMessage;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ErrorMessageGenerator {
	public static ValidationErrorMessage generateErrorMessage(List<Node> nodes,
			ValidationRule validationRule) {
		return generateSpecificErrors(nodes, validationRule);
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	private static ValidationErrorMessage generateSpecificErrors(
			List<Node> problematicNodes, ValidationRule validationRule) {
		List<SpecificErrorMessage> errorMessages = problematicNodes
				.stream()
				.map(eventNotLabeled -> generateSpecificError(eventNotLabeled,
						validationRule)).collect(Collectors.toList());
		return new ValidationErrorMessage(errorMessages);
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	private static SpecificErrorMessage generateSpecificError(Node node,
			ValidationRule validationRule) {
		return new SpecificErrorMessage(node.getId(),
				"There was an error in the event because: "
						+ validationRule.getDescription(), node.getName());
	}
}
