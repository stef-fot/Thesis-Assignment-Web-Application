package myy803.diplomas_project.controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.dao.UserDAO;
import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.User;
import myy803.diplomas_project.service.ProfService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.Subject;

class ProfControllerTest {
	   @Autowired
	    MockMvc mockMvc;
	   
	   @InjectMocks
	    ProfController profController;
	   @Mock
	    private UserDAO userdao;

	    @Mock
	    private ProfDAO profdao;
	   @Mock
	    ProfService profService; // Mock the ProfService dependency
	   @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(profController).build();
	    }
	   
		@Test
	    void testsaveProfessor() throws Exception {
	        mockMvc.perform(get("/professor/save"))
	                .andExpect(status().is3xxRedirection()) // Expecting a redirect
	                .andExpect(redirectedUrl("/professor/dashboard")); // Asserting the redirected URL
	    }
		
		@Test
	    @WithMockUser(username = "username")
	    void testgetProfessorProfile() throws Exception {
	        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	        Authentication authentication = mock(Authentication.class);
	        when(authentication.getName()).thenReturn("username");
	        securityContext.setAuthentication(authentication);
	        SecurityContextHolder.setContext(securityContext);

	        mockMvc.perform(get("/professor/profile"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("professor/info"));

	        // Reset the security context after the test
	        SecurityContextHolder.clearContext();
	    }
		@Test
	    @WithMockUser(username = "username")
	    void testgetProfessorSubjects() throws Exception {
	        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	        Authentication authentication = mock(Authentication.class);
	        when(authentication.getName()).thenReturn("username");
	        securityContext.setAuthentication(authentication);
	        SecurityContextHolder.setContext(securityContext);

	        mockMvc.perform(get("/professor/subjects"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("professor/supervisedSubjects"));

	        // Reset the security context after the test
	        SecurityContextHolder.clearContext();
	    }
		
		@Test
	    @WithMockUser(username = "username")
	    void testaddSupervisedSubject() throws Exception {
	        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	        Authentication authentication = mock(Authentication.class);
	        when(authentication.getName()).thenReturn("username");
	        securityContext.setAuthentication(authentication);
	        SecurityContextHolder.setContext(securityContext);

	        mockMvc.perform(get("/professor/addSubject"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("professor/addSupervisedSubjectForm"));

	        // Reset the security context after the test
	        SecurityContextHolder.clearContext();
	    }

}
