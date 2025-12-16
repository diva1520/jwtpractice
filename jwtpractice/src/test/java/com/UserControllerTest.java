package com;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	void login_shouldReturnJwtToken() throws Exception {

		String body = """
				{
				\"userName\": \"karthick\"
				}
				""";

		mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
	}

	@Test
	void validate_withoutToken_shouldReturn403() throws Exception {

		mockMvc.perform(get("/api/test/validate")).andExpect(status().isForbidden());
	}
	
	@Test
	void validate_withValidToken_shouldReturnSub() throws Exception {


	String token = jwtUtil.generateToken(101L, "karthick");


	mockMvc.perform(get("/api/test/validate")
	.header("Authorization", "Bearer " + token))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.sub").value("karthick"));
	}
	
	@Test
	void validate_withInvalidToken_shouldReturn401() throws Exception {


	String invalidToken = "invalid.jwt.token";


	mockMvc.perform(get("/api/test/validate")
	.header("Authorization", "Bearer " + invalidToken))
	.andExpect(status().isUnauthorized());
	}

}