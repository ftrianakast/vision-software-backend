package co.com.vision.prueba.services.parsers;

import java.util.List;
import java.util.Optional;

import co.com.vision.prueba.domain.WorkflowProcess;

/**
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public interface WorkflowProcessesParser {

	public Optional<List<WorkflowProcess>> parseXMLWorkflowProcesses(
			byte[] document);

}
