package hr.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileResource {
	@JsonProperty("id")
	private String id;

	@JsonProperty("fileName")
	private String fileName;

	@JsonProperty("fileType")
	private String fileType;

	@JsonProperty("study")
	private String study;

	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("comments")
	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "FileResource [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", study=" + study + ", subject=" + subject + ", comment=" + comment + "]";
	}

}
