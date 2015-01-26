package co.com.vision.prueba.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.rules.Rule;
import co.com.vision.prueba.domain.rules.RuleAggregator;
import co.com.vision.prueba.domain.rules.RuleFactory;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.utils.Constants;
import co.com.vision.prueba.utils.XMLAssembler;

import com.sun.jersey.multipart.FormDataParam;

@RequestScoped
@Path("/validation/rules")
public class RuleCtrl {

	@EJB
	private XMLAssembler xmlAssembler;

	@EJB
	private ProcessParserImpl parserImpl;

	@EJB
	private RuleFactory ruleFactory;

	@EJB
	private RuleAggregator ruleAggregator;

	@POST
	@Path("/all")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ValidationErrorMessage> validateAllRules(
			@FormDataParam("file") InputStream is) {
		Optional<Document> xmlDocument = XMLAssembler.getXMLFromInputStream(is);
		if (xmlDocument.isPresent()) {
			Process process = parserImpl.parseProcess(xmlDocument.get());
			return ruleAggregator.validateRules(Constants.ALL_RULES, process);
		} else {
			return new ArrayList<ValidationErrorMessage>();
		}
	}

	@POST
	@Path("/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ValidationErrorMessage> validateSomeRules(
			@FormDataParam("file") InputStream is,
			@QueryParam("names") String ruleNames) {
		Optional<Document> xmlDocument = XMLAssembler.getXMLFromInputStream(is);
		List<String> rules = Arrays.asList(ruleNames.toString().split(","));
		System.out.println(rules.get(0));
		
		if (xmlDocument.isPresent()) {
			Process process = parserImpl.parseProcess(xmlDocument.get());
			return ruleAggregator.validateRules(rules, process);
		} else {
			return new ArrayList<ValidationErrorMessage>();
		}
	}

	@POST
	@Path("/{name}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ValidationErrorMessage> validateRule(
			@PathParam("name") String name,
			@FormDataParam("file") InputStream is) throws IOException,
			ParserConfigurationException, SAXException {
		Optional<List<ValidationErrorMessage>> errorMessages = Optional
				.of(new ArrayList<>());
		Optional<Document> xmlDocument = XMLAssembler.getXMLFromInputStream(is);

		if (xmlDocument.isPresent()) {
			Optional<Rule> rule = ruleFactory.getRule(name);
			Process process = parserImpl.parseProcess(xmlDocument.get());
			if (rule.isPresent()) {
				errorMessages = rule.get().validate(process);
			}
		}
		if (errorMessages.isPresent()) {
			return errorMessages.get();
		} else {
			return new ArrayList<ValidationErrorMessage>();
		}
	}
}
