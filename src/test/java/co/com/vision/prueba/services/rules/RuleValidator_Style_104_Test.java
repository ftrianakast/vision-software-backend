package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;

import junit.framework.Assert;

import org.junit.Test;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.rules.Rule_Style_104;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.services.parsers.utils.ProcessObtainer;

/**
 * Test the rule STYLE_104
 * @author Felipe Triana<ftrianakast@gmail.com>
 *
 */
public class RuleValidator_Style_104_Test {
	
	ProcessParserImpl processParser = new ProcessParserImpl();

	ProcessObtainer processObtainer = new ProcessObtainer();

	Rule_Style_104 ruleValidator = new Rule_Style_104();

	static final String processPath = "./src/test/resources/BPMN/Sample_3.xpdl";

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
