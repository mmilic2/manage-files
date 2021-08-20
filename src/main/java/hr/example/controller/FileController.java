package hr.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hr.example.model.File;
import hr.example.model.FileResource;
import hr.example.service.FileService;

@CrossOrigin(origins = "*")
@RestController
public class FileController {

	private final FileService fileService;

	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload/{study}/{subject}")
	public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("study") String study, @PathVariable("subject") String subject) {
		try {
			return new ResponseEntity<>(fileService.storeFile(file, study, subject), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
		File dbFile = fileService.getFile(id);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"").body(new ByteArrayResource(dbFile.getData()));
	}

	@DeleteMapping("/delete/{fileId}")
	public ResponseEntity<File> deleteFile(@PathVariable String fileId) {
		try {
			fileService.deleteFile(fileId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/files/{study}/{subject}")
	public ResponseEntity<List<FileResource>> getByStudyAndYear(@PathVariable String study, @PathVariable String subject) {
		try {
			return new ResponseEntity<List<FileResource>>(fileService.getByStudyAndSubject(study, subject), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/files/comment/{fileId}}")
	public ResponseEntity<FileResource> postFileComment(@PathVariable String fileId, @RequestBody String comment){
		try {
			return new ResponseEntity<FileResource>(fileService.postFileComment(fileId, comment), HttpStatus.OK);
		} catch {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
