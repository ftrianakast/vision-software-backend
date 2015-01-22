package co.com.vision.prueba.domain.aux;

import java.util.List;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ValidationErrorMessage {

	private List<SpecificErrorMessage> specificErrorMessages;

	/**
	 * Default constructor
	 * 
	 * @param specificErrorMessages
	 */
	public ValidationErrorMessage(
			List<SpecificErrorMessage> specificErrorMessages) {
		super();
		this.specificErrorMessages = specificErrorMessages;
	}

	public List<SpecificErrorMessage> getSpecificErrorMessages() {
		return specificErrorMessages;
	}

	public void setSpecificErrorMessages(
			List<SpecificErrorMessage> specificErrorMessages) {
		this.specificErrorMessages = specificErrorMessages;
	}

}
