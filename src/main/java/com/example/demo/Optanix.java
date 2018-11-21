package com.example.demo;

import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exercise", method = RequestMethod.GET)
public class Optanix {

	@PostMapping("/word_count_per_sentence")
	public HttpEntity<String> getWordCount(HttpEntity<String> input) {
		// parse input
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) parser.parse(input.getBody());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String text = json.get("input").toString();
		text = text.trim();
		int wordCount = 0;
		int n = text.length();
		int offset = 0;
		String innerBody = "";
		for (int i = 0; i < n; i++) {
			if (text.substring(i, i + 1).equals(" "))
				if (i > 1) {
					if (!text.substring(i - 1, i).equals(" ") && !text.substring(i - 1, i).equals(".")
							&& !text.substring(i - 1, i).equals("?") && !text.substring(i - 1, i).equals("!"))
						wordCount++;
				} else
					wordCount++;
			if (text.substring(i, i + 1).equals(".") || text.substring(i, i + 1).equals("?")
					|| text.substring(i, i + 1).equals("!")) {
				wordCount++;
				innerBody += "{\"sentence\":\"" + text.substring(offset, i + 1).trim() + "\", \"word_count\":\""
						+ wordCount + "\"},";
				wordCount = 0;
				offset = i + 1;
			}
		}

		if (innerBody.endsWith("},"))
			innerBody = innerBody.substring(0, innerBody.length() - 1);
		else if (innerBody.isEmpty() && !text.isEmpty()) {
			innerBody = "{\"sentence\":\"" + text + "\", \"word_count\":\"1\"}";
		}

		// add headers to output
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "Application/json");
		// add body to output
		String body = "{\"output\":[" + innerBody + "]}";
		HttpEntity<String> output = new HttpEntity<String>(body, headers);

		return output;
	}

	@PostMapping("/total_letter_count")
	public HttpEntity<String> getLetterCount(HttpEntity<String> input) {
		// parse input
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) parser.parse(input.getBody());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String text = json.get("input").toString();
		text = text.trim();
		text = text.replace(" ", "");
		text = text.toLowerCase();
		char arr[] = text.toCharArray();
		Arrays.sort(arr);

		int charCount = 0;
		int n = arr.length;
		String innerBody = "";
		for (int i = 0; i < n - 1; i++) {
			if (Character.isLetter(arr[i])) {
				if (arr[i] != arr[i + 1]) {
					charCount++;
					innerBody += "{\"letter\":\"" + arr[i] + "\", \"total_count\":\"" + charCount + "\"},";
					charCount = 0;
				} else {
					charCount++;
				}
			}
		}

		if (innerBody.endsWith("},"))
			innerBody = innerBody.substring(0, innerBody.length() - 1);

		// add headers to output
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "Application/json");
		// add body to output
		String body = "{\"output\":[" + innerBody + "]}";
		HttpEntity<String> output = new HttpEntity<String>(body, headers);

		return output;
	}

}
