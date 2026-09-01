package com.emp;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.emp.dto.UserResponseDTO;
import com.emp.mapper.UserMapper;
import com.emp.model.User;
import com.emp.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {
	
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserMapper userMapper;
	

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminCanGetAllUsers() throws Exception {

        mockMvc.perform(
                get("/user/all")
                .with(user("admin").roles("ADMIN"))
        )
        .andExpect(status().isOk());
    }
    
    @Test
    void employeeCannotGetAllUsers() throws Exception {

        mockMvc.perform(
                get("/user/all")
                .with(user("employee").roles("EMPLOYEE"))
        )
        .andExpect(status().isForbidden());
    }
    
    @Test
    void employeeCanGetOwnProfile() throws Exception {

        User user = new User();
        user.setUsername("employee");

        UserResponseDTO response = new UserResponseDTO();

        when(userRepository.findUserByUsername("employee"))
                .thenReturn(user);

        when(userMapper.userToUserResponse(user))
                .thenReturn(response);

        mockMvc.perform(
                get("/user/profile")
                        .with(user("employee").roles("EMPLOYEE"))
        )
        .andExpect(status().isOk());
    }
    
    @Test
    void unauthenticatedUserCannotGetAllUsers() throws Exception {

        mockMvc.perform(
                get("/user/all")
        )
        .andExpect(status().isUnauthorized());
    }
}