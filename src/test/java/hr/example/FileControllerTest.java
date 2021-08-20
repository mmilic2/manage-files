package hr.example;


import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hr.example.controller.FileController;
import hr.example.model.File;
import hr.example.service.FileService;

@WebMvcTest(FileController.class)
class FileControllerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Mock
	File file;
	
	@MockBean
	FileService fileService;
	
	@Autowired
	MockMvc mockMvc;
	
	@AfterEach
	void tearDown() {
		reset(fileService);
	}
	
	@Test
	void getAllFilesTest() throws Exception {
		mockMvc.perform(get("/files"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void uploadFileTest() throws Exception{
		MockMultipartFile file = new MockMultipartFile("file", "hello.text"
				, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/upload").file(file))
		.andExpect(status().isCreated());
		System.out.println(fileService.getAll());
	}
	
	@Test
	public void downloadFileTest() throws Exception{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/download/")).andExpect(status().is4xxClientError());
	}
	
}
