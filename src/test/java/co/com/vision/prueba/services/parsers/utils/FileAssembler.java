package co.com.vision.prueba.services.parsers.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileAssembler {
	/**
	 * @param path
	 * @return
	 */
	public static Optional<byte[]> getBytesFile(Path path) {
		try {
			byte[] currentFile = Files.readAllBytes(path);
			return Optional.of(currentFile);
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
