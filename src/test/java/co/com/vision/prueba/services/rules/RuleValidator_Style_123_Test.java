package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;

import junit.framework.Assert;

import org.junit.Test;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.rules.Rule_Style_123;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.services.parsers.utils.ProcessObtainer;

/**
 * Style 0115. A throwing intermediate event should be labeled.
 * 
 * @author Felipe Triana<ftrianakast@gmail.com>
 *
 */

public class RuleValidator_Style_123_Test {
	ProcessParserImpl processParser = new ProcessParserImpl();
	ProcessObtainer processObtainer = new ProcessObtainer();
	Rule_Style_123 ruleValidator = new Rule_Style_123();

	static final String processPath = "./src/test/resources/BPMN/Sample_4.xpdl";

	@Test
	public void testRule() {
		try {
			testRuleOverProcess(processObtainer
					.getProcessFromPathFile(processPath));
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
