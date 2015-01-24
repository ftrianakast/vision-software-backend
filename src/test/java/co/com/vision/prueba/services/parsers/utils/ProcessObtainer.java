package co.com.vision.prueba.services.parsers.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.inject.Inject;

import co.com.vision.prueba.domain.Process;
import co.com.vision.prueba.services.parsers.ProcessParserImpl;

/**
 * 
 * @author ftrianakast
 *
 */
public class ProcessObtainer {

	@Inject
	ProcessParserImpl processParser;
	
	/**
	 * 
	 * @param processPath
	 * @return
	 * @throws Exception
	 */
	public Process getProcessFromPathFile(String processPath) throws Exception {
		Path samplePath = Paths.get(processPath);
		Optional<byte[]> bytesFile = FileAssembler.getBytesFile(samplePath);

		Optional<Process> process = bytesFile
				.flatMap(processParser::parseProcess);
		if (process.isPresent()) {
			return process.get();
		} else {
			throw new Exception("There was an error parsing the file");
		}
	}
}
