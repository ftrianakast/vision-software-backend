package co.com.vision.prueba.domain.rules;

import java.util.Optional;

import co.com.vision.prueba.utils.Constants;

/**
 * Builds rules depending on name
 * 
 * @author Felipe Triana <ftriankast@gmail.com>
 * @version 1.0
 */
public class RuleFactory {

	/**
	 * Default constructor
	 */
	public RuleFactory(){
		
	}
	
	/**
	 * Gets a rule, if the rule is avaliable, in other case return an empty
	 * optional
	 * 
	 * @param ruleName
	 * @return
	 */
	public Optional<Rule> getRule(String ruleName) {
		Optional<Rule> rule = Optional.empty();
		if (ruleName.equals(Constants.BPMN_102)) {
			rule = Optional.of(new Rule_BPMN_102());
		} else if (ruleName.equals(Constants.STYLE_104)) {
			rule = Optional.of(new Rule_Style_104());
		} else if (ruleName.equals(Constants.STYLE_115)) {
			rule = Optional.of(new Rule_Style_115());
		} else if (ruleName.equals(Constants.STYLE_122)) {
			rule = Optional.of(new Rule_Style_122());
		} else if (ruleName.equals(Constants.STYLE_123)) {
			rule = Optional.of(new Rule_Style_123());
		}
		return rule;
	}
}
