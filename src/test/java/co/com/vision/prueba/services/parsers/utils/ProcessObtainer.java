package co.com.vision.prueba.services.parsers.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.inject.Inject;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;
import co.com.vision.prueba.utils.XMLAssembler;

/**
 * 
 * @author ftrianakast
 *
 */
public class ProcessObtainer {

	@Inject
	static ProcessParserImpl processParser;

	/**
	 * 
	 * @param processPath
	 * @return
	 * @throws Exception
	 */
	public Process getProcessFromPathFile(String processPath)
			throws Exception {
		Path samplePath = Paths.get(processPath);
		Optional<byte[]> bytesFile = FileAssembler.getBytesFile(samplePath);
		Process process = processParser.parseProcess(XMLAssembler
				.getReadXMLObject(bytesFile.get()).get());
		return process;
	}
}
