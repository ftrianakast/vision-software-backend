package co.com.vision.prueba.services.parsers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.Transition;
import co.com.vision.prueba.domain.WorkflowProcess;
import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.event.Event;
import co.com.vision.prueba.services.parsers.utils.FileAssembler;
import co.com.vision.prueba.services.parsers.utils.ProcessObtainer;
import co.com.vision.prueba.utils.XMLAssembler;

/**
 * 
 * @author ftrianakast
 *
 */
@RunWith(Arquillian.class)
public class ProcessParserTest extends TestCase {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(
				ProcessParser.class, ProcessParserImpl.class,
				XMLAssembler.class);
	}

	@Inject
	ProcessParserImpl processParser;

	@Inject
	XMLAssembler xmlAssembler;

	static final List<String> activityNamesAllowed = Arrays.asList("A", "C",
			"A1", "C1");

	Map<String, String> nodesInolvedInMessageFlow = new HashMap<String, String>();

	@Test
	public void parseProcessSample1() throws Exception {
		ProcessObtainer processObtainer = new ProcessObtainer();

		Process process = processObtainer
				.getProcessFromPathFile("./src/test/resources/BPMN/Sample_4.xpdl");
		testWorkflowProcess(process);
	}

	/**
	 * 
	 * @param process
	 */
	private void testWorkflowProcess(Process process) {
		WorkflowProcess workflowProcess01 = process.getWorkFlowProcesses().get(
				0);
		WorkflowProcess workflowProcess02 = process.getWorkFlowProcesses().get(
				1);
		WorkflowProcess workflowProcess03 = process.getWorkFlowProcesses().get(
				2);
		Assert.assertEquals("Main Process", workflowProcess01.getName());
		Assert.assertEquals("P4", workflowProcess02.getName());
		Assert.assertEquals("P5", workflowProcess03.getName());
		Assert.assertEquals(true, testMessageFlows(process.getMessageFlows()
				.get()));
		Assert.assertEquals(true, testActivity(workflowProcess03
				.getActivities().get()));
		Assert.assertEquals(true, testEvents(workflowProcess02.getEvents()
				.get()));
	}

	/**
	 * 
	 * @param messageFlows
	 */
	private boolean testMessageFlows(List<Transition> messageFlows) {
		boolean isMessageFlowPresent = messageFlows.stream().anyMatch(
				messageFlow -> messageFlow.getFrom().getName().equals("B")
						&& messageFlow.getTo().getName().equals("B1"));
		return isMessageFlowPresent;
	}

	/**
	 * 
	 * @param activities
	 */
	private boolean testActivity(List<Node> activities) {
		boolean allActivitiesAreFine = activities.stream()
				.map(activity -> (Activity) activity)
				.anyMatch(activity -> isNameAllowed(activity.getName()));
		return allActivitiesAreFine;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private boolean isNameAllowed(String name) {
		return activityNamesAllowed.stream().anyMatch(
				activityName -> activityName.equals(name));
	}

	/**
	 * 
	 * @param list
	 */
	private boolean testEvents(List<Node> events) {
		System.out.println("------------------------------------------------");
		events.stream().forEach(event -> System.out.println(event.getName()));
		Event eventB = (Event) events.stream()
				.filter(event -> event.getName().equals("B")).findFirst().get();
		return eventB.getCatchThrow().isPresent();
	}

}
