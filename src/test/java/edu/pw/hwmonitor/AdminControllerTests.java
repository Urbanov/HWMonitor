package edu.pw.hwmonitor;

import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.companies.role.RoleRepository;
import edu.pw.hwmonitor.controllers.AdminController;
import edu.pw.hwmonitor.user.User;
import edu.pw.hwmonitor.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AdminControllerTests {

    private MockMvc mockMvc;

    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AdminController controller;

    @Before
    public void setUp() throws Exception {
        companyRepository = Mockito.mock(CompanyRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        controller = new AdminController(companyRepository, userRepository, roleRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void creatingCompanyWithNewNameShouldReturnOk() throws Exception {

        //when
        mockMvc.perform(post("/admin/create-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\", \"username\":\"test\", \"password\":\"test\"}")
                .accept(MediaType.APPLICATION_JSON))

        //then
        .andExpect(status().isOk());
    }

    @Test
    public void creatingCompanyWithAlreadyExistingCompanyNameShouldReturnNotAcceptable() throws Exception {

        //given
        Company testCompany = mock(Company.class);

        //when
        when(companyRepository.findByNameEquals("test")).thenReturn(Optional.of(testCompany));
        mockMvc.perform(post("/admin/create-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\", \"username\":\"test\", \"password\":\"test\"}")
                .accept(MediaType.APPLICATION_JSON))

        //then
        .andExpect(status().isNotAcceptable());
    }

    @Test
    public void creatingCompanyWithExistingUserNameShouldReturnNotAcceptable() throws Exception {
        //given
        User testUser = mock(User.class);

        //when
        when(userRepository.findByUsernameEquals("test")).thenReturn(Optional.of(testUser));
        mockMvc.perform(post("/admin/create-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\", \"username\":\"test\", \"password\":\"test\"}")
                .accept(MediaType.APPLICATION_JSON))

        //then
        .andExpect(status().isNotAcceptable());
    }
}
