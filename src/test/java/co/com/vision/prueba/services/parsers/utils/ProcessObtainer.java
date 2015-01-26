package co.com.vision.prueba.services.parsers.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.w3c.dom.Document;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.utils.XMLAssembler;

/**
 * 
 * @author ftrianakast
 *
 */
public class ProcessObtainer {

	/**
	 * Process parser
	 */
	private ProcessParserImpl processParser = new ProcessParserImpl();

	/**
	 * 
	 * @param processPath
	 * @return
	 * @throws Exception
	 */
	public Process getProcessFromPathFile(String processPath) throws Exception {
		Path samplePath = Paths.get(processPath);
		Optional<byte[]> bytesFile = FileAssembler.getBytesFile(samplePath);
		XMLAssembler xmlAssembler = new XMLAssembler();
		Optional<Document> document = xmlAssembler.getReadXMLObject(bytesFile
				.get());
		Process process = processParser.parseProcess(document.get());
		return process;
	}
}
