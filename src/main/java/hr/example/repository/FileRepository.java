package hr.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.example.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

	@Override
	List<File> findAll();

	//List<File> findFileByStudyAndYear(String study, Integer year);

	List<File> findByStudyAndSubject(String study, String subject);
}
