package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void exampleTest1() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("input", "First sentence. Second sentence.");
		String json = jsonObject.toJSONString();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "Application/json");
		
		this.mvc.perform(
				post("/exercise/word_count_per_sentence")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
	            .content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void exampleTest2() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("input", "First sentence. Second sentence.");
		String json = jsonObject.toJSONString();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "Application/json");
		
		this.mvc.perform(
				post("/exercise/total_letter_count")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
	            .content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
