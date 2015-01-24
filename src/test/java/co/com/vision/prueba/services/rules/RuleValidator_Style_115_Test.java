package co.com.vision.prueba.services.rules;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.services.parsers.ProcessParser;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.services.parsers.utils.ProcessObtainer;

/**
 * Style 0115. A throwing intermediate event should be labeled.
 * @author Felipe Triana<ftrianakast@gmail.com>
 *
 */

@RunWith(Arquillian.class)
public class RuleValidator_Style_115_Test {
	@Inject
	ProcessParserImpl processParser;

	@Inject
	ProcessObtainer processObtainer;

	@Inject
	RuleValidator_Style_115 ruleValidator;

	static final String processPath = "./src/test/resources/BPMN/Sample_1.xpdl";

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(
				RuleValidator.class, RuleValidator_Style_115.class,
				ProcessParser.class, ProcessParserImpl.class,
				ProcessObtainer.class);
	}

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
