package hr.example.model;

import org.springframework.stereotype.Component;

@Component
public class FileResourceAssembler {

	public FileResource toResource(File file) {
		FileResource resource = new FileResource();
		resource.setFileName(file.getFileName());
		resource.setFileType(file.getFileType());
		resource.setId(file.getId());
		resource.setStudy(file.getStudy());
		resource.setSubject(file.getSubject());
		resource.setComment(file.getComment());
		return resource;
	}

}
