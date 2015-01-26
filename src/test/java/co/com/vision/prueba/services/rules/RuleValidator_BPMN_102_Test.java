package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;

import junit.framework.Assert;

import org.junit.Test;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.rules.Rule_BPMN_102;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.services.parsers.utils.ProcessObtainer;

/**
 * Test for validation of rule BPMN_102
 * @author Felipe Triana
 *
 */
public class RuleValidator_BPMN_102_Test {

	ProcessParserImpl processParser = new ProcessParserImpl();

	ProcessObtainer processObtainer = new ProcessObtainer();
	
	Rule_BPMN_102 ruleValidator = new Rule_BPMN_102();

	
	static final String processPath = "./src/test/resources/BPMN/Sample_2.xpdl";


	@Test
	public void testRule() {
		try {
			testRuleOverProcess(processObtainer.getProcessFromPathFile(processPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRuleOverProcess(Process process) {
		Optional<List<ValidationErrorMessage>> validation = ruleValidator
				.validate(process);
		Assert.assertEquals(true, validation.isPresent());
	}
}
