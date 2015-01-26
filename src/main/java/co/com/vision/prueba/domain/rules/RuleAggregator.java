package co.com.vision.prueba.domain.rules;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;

@Stateless
public class RuleAggregator {

	@EJB
	private RuleFactory ruleFactory;

	/**
	 * validates multiple rules
	 * 
	 * @return
	 */
	public List<ValidationErrorMessage> validateRules(List<String> names,
			Process process) {
		List<Rule> rules = names.stream()
				.map(name -> ruleFactory.getRule(name))
				.filter(optionalRule -> optionalRule.isPresent())
				.map(optionalRule -> optionalRule.get())
				.collect(Collectors.toList());

		List<ValidationErrorMessage> errorMessages = rules.stream()
				.map(rule -> rule.validate(process))
				.filter(optionalErrors -> optionalErrors.isPresent())
				.map(optionalErros -> optionalErros.get())
				.flatMap(errorList -> errorList.stream())
				.collect(Collectors.toList());

		return errorMessages;
	}

}
