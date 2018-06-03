package edu.pw.hwmonitor;

import edu.pw.hwmonitor.controllers.CommonController;
import edu.pw.hwmonitor.security.SecurityManager;
import edu.pw.hwmonitor.user.User;
import edu.pw.hwmonitor.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CommonControllerTests {

    private MockMvc mockMvc;

    private UserRepository userRepository;
    private SecurityManager securityManager;
    private CommonController controller;

    @Before
    public void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        securityManager = Mockito.mock(SecurityManager.class);
        controller = new CommonController(userRepository,securityManager);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void givenNewPasswordAndCorrectOldPasswordShouldReturnOk() throws Exception {

        //given
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("test");

        //when
        when(securityManager.getUsername()).thenReturn("test");
        when(userRepository.findByUsernameEquals("test")).thenReturn(Optional.of(testUser));


        mockMvc.perform(post("/common/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oldPassword\":\"test\", \"newPassword\":\"test1\"}")
                .accept(MediaType.APPLICATION_JSON))
        //then
        .andExpect(status().isOk());
    }

    @Test
    public void givenIncorrectOldPasswordShouldReturnNotAcceptable() throws Exception {
        
        //given
        User testUser = new User();
        testUser.setPassword("testIncorrect");
        testUser.setUsername("test");

        //when
        when(securityManager.getUsername()).thenReturn(testUser.getUsername());
        when(userRepository.findByUsernameEquals("test")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/common/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oldPassword\":\"testCorrect\", \"newPassword\":\"test1\"}")
                .accept(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isNotAcceptable());
    }
}
