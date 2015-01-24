package co.com.vision.prueba.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Felipe Triana<ftriankast@gmail.com>
 * @version 1.0
 */
@Stateless
public class XMLAssembler {

	/**
	 * 
	 * @param document
	 * @return
	 * @throws Exception
	 */
	public static Optional<Document> getReadXMLObject(byte[] document) {
		try {
			return Optional.of(getXMLObjectRepresentation(document));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * 
	 * @param document
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static Document getXMLObjectRepresentation(byte[] document)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document realDocument = builder.parse(new ByteArrayInputStream(document));
		return realDocument;
	}
}
