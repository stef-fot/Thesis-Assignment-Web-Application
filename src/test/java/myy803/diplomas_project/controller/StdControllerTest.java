package myy803.diplomas_project.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.ModelMap;

import myy803.diplomas_project.dao.UserDAO;
import myy803.diplomas_project.model.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import myy803.diplomas_project.dao.*;
import myy803.diplomas_project.service.*;

import java.util.List;
import java.util.Optional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class StdControllerTest {

    @Mock
    StdService stdService;

    @Mock
    SubjectDAO subjectDAO;

    @Mock
    StdDAO stdDAO;

    @Mock
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    StdController stdController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stdController).build();
    }
    
    
    @Test
    void testsaveStudent() throws Exception {
        mockMvc.perform(get("/student/save"))
                .andExpect(status().is3xxRedirection()) // Expecting a redirect
                .andExpect(redirectedUrl("/student/dashboard")); // Asserting the redirected URL
    }

    @Test
    @WithMockUser(username = "username")
    void testgetStudentProfile() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/student/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/info"));

        // Reset the security context after the test
        SecurityContextHolder.clearContext();
    }


    @Test
    public void testGetSubjectDetails() throws Exception {
        int subjectId = 1; // Provide a valid subject ID for testing

        mockMvc.perform(get("/student//details")
                .param("subject_id", String.valueOf(subjectId)))
                .andExpect(status().isOk())
                .andExpect(view().name("student/subjectDetails"));
    }
    
}
