//package co.com.vision.prueba.utils;
//
//import javax.ws.rs.FormParam;
//
//import org.jboss.resteasy.annotations.providers.multipart.PartType;
//
///**
// * 
// * @author Felipe Triana<ftrianakast@gmail.com>
// *
// */
//public class FileUploadForm {
//
//	/**
//	 * Default constructor
//	 */
//	public FileUploadForm() {
//	}
//
//	private byte[] fileData;
//	private String fileName;
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	@FormParam("fileName")
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//	public byte[] getFileData() {
//		return fileData;
//	}
//
//	@FormParam("selectedFile")
//	@PartType("application/octet-stream")
//	public void setFileData(byte[] fileData) {
//		this.fileData = fileData;
//	}
//}
