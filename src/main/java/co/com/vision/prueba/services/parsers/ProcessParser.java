package co.com.vision.prueba.services.parsers;

import java.util.Optional;

import co.com.vision.prueba.domain.Process;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public interface ProcessParser {
	/**
	 * Parse a presumible BPMN Process
	 * @param document
	 * @return
	 */
	public Optional<Process> parseProcess(byte[] document);
}
