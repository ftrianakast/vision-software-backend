package co.com.vision.prueba.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.domain.aux.ValidationErrorMessage;
import co.com.vision.prueba.domain.rules.Rule;
import co.com.vision.prueba.domain.rules.RuleFactory;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.utils.XMLAssembler;

import com.sun.jersey.multipart.FormDataParam;

@RequestScoped
@Path("/validation/rules")
public class RuleCtrl {

	@POST
	@Path("/{name}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ValidationErrorMessage> validateRule(
			@PathParam("name") String name,
			@FormDataParam("file") InputStream is) throws IOException,
			ParserConfigurationException, SAXException {
		Optional<Document> xmlDocument = XMLAssembler.getXMLFromInputStream(is);
		Optional<List<ValidationErrorMessage>> errorMessages = Optional
				.of(new ArrayList<>());

		if (xmlDocument.isPresent()) {
			RuleFactory ruleFactory = new RuleFactory();
			Optional<Rule> rule = ruleFactory.getRule(name);
			ProcessParserImpl parserImpl = new ProcessParserImpl();
			Process process = parserImpl.parseProcess(xmlDocument.get());

			if (rule.isPresent()) {
				System.out.println("esta presente");
				errorMessages = rule.get().validate(process);
			}
		}
		return errorMessages.get();
	}
}
