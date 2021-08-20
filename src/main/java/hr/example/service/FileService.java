package hr.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import hr.example.controller.FileController;
import hr.example.model.File;
import hr.example.model.FileResource;
import hr.example.model.FileResourceAssembler;
import hr.example.repository.FileRepository;

@Service
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileController.class);

	private final FileRepository fileRepository;
	private final FileResourceAssembler resourceAssembler;

	@Autowired
	public FileService(FileRepository fileRepository, FileResourceAssembler resourceAssembler) {
		this.fileRepository = fileRepository;
		this.resourceAssembler = resourceAssembler;
	}

	public File storeFile(MultipartFile file, String study, String subject) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {

			if (fileName.contains("..")) {
				throw new RuntimeException("Invalid path sequence");
			}

			File dbFile = new File(fileName, file.getContentType(), study, subject, file.getBytes());
			log.debug("Storing file with name: {}", dbFile.getFileName());
			return fileRepository.save(dbFile);

		} catch (IOException e) {
			throw new RuntimeException("Could not store file" + fileName);
		}
	}

	public File getFile(String fileId) {
		log.debug("Finding file with id: {}", fileId);
		return fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
	}

	public List<FileResource> getAll() {
		log.debug("Finding all files");
		List<File> fileFromDb = fileRepository.findAll();
		List<FileResource> fileResource = new ArrayList<>();
		fileFromDb.forEach(f -> fileResource.add(resourceAssembler.toResource(f)));
		log.info(fileResource.toString());
		return fileResource;

	}

	public void deleteFile(String fileId) {
		log.debug("Deleting file with id: {}", fileId);
		fileRepository.deleteById(fileId);
	}

	public List<FileResource> getByStudyAndSubject(String study, String subject) {
		List<File> fileFromDb = fileRepository.findByStudyAndSubject(study, subject);
		log.debug("Finding files with study: {}", study);
		List<FileResource> fileResource = new ArrayList<>();
		fileFromDb.forEach(f -> fileResource.add(resourceAssembler.toResource(f)));
		return fileResource;
	}
	
	public FileResource postFileComment(String fileId, String comment){
		log.debug("Finding file with id: {}", fileId);
		File file = fileRepository.findById(fileId).get();
		file.setComment(comment);
		log.debug("Saving comment to file with id: {}", fileId);
		return resourceAssembler.toResource(fileRepository.save(file));
	}

}
