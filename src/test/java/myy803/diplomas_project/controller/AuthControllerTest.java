package myy803.diplomas_project.controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import myy803.diplomas_project.dao.UserDAO;
import myy803.diplomas_project.model.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class AuthControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	AuthController authController;
	@Autowired
    private UserDAO userDAO;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	
	@Test
	void testAuthControllerIsNotNull() {
		Assertions.assertNotNull(authController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	@Test
    void testReturnsSigninPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signin"));
    }

    @Test
    void testReturnsRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"));
    }

	@Test
	void testSavePage() throws Exception {
	    String username = "Bob";
	    String password = "123";

	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(password);

	    User user = new User(username, encodedPassword, Role.PROFESSOR);
	    userDAO.save(user);

	    // Retrieve the generated ID from the saved user
	    int userId = user.getId();
	    String userPassword = user.getPassword();
	    
	    
	    // Set the ID in the User object
	    user.setId(userId);
	    user.setPassword(userPassword);
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(user.getId()));

	    mockMvc.perform(post("/save")
	    		.flashAttr("user", user)) // Pass the user directly to the controller method
        		.andExpect(status().isOk())
	            .andExpect(view().name("auth/signin"));

	    userDAO.delete(user);
	}


}
