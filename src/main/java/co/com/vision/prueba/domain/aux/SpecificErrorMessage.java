package co.com.vision.prueba.domain.aux;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class SpecificErrorMessage {

	private String elementId;

	private String message;

	private String elementName;

	/**
	 * 
	 * @param elementId
	 * @param message
	 * @param elementName
	 */
	public SpecificErrorMessage(String elementId, String message,
			String elementName) {
		super();
		this.elementId = elementId;
		this.message = message;
		this.elementName = elementName;
	}

	public SpecificErrorMessage(String message) {
		super();
		this.message = message;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

}
