package co.com.vision.prueba.services.parsers;

import javax.ejb.Local;

import org.w3c.dom.Document;

import co.com.vision.prueba.domain.Process;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
@Local
public interface ProcessParser {
	/**
	 * Parse a presumible BPMN Process
	 * @param document
	 * @return
	 */
	public Process parseProcess(Document xml);
}
