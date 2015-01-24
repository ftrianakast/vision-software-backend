package co.com.vision.prueba.domain;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Felipe Triana<ftrianakast@gmail.com>
 *
 */
public class Process {
	private List<WorkflowProcess> workFlowProcesses;

	private Optional<List<Transition>> messageFlows;

	/**
	 * Default Constructor
	 * @param workFlowProcesses
	 * @param messageFlows
	 */
	public Process(List<WorkflowProcess> workFlowProcesses,
			Optional<List<Transition>> messageFlows) {
		super();
		this.workFlowProcesses = workFlowProcesses;
		this.messageFlows = messageFlows;
	}
	
	
	public List<WorkflowProcess> getWorkFlowProcesses() {
		return workFlowProcesses;
	}

	public void setWorkFlowProcesses(List<WorkflowProcess> workFlowProcesses) {
		this.workFlowProcesses = workFlowProcesses;
	}

	public Optional<List<Transition>> getMessageFlows() {
		return messageFlows;
	}

	public void setMessageFlows(Optional<List<Transition>> messageFlows) {
		this.messageFlows = messageFlows;
	}
}
