package edu.pw.hwmonitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.controllers.UserController;
import edu.pw.hwmonitor.controllers.requests.DataRequest;
import edu.pw.hwmonitor.feeders.Feeder;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.Measurement;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



@RunWith(SpringRunner.class)
public class UserControllerTests {

    private MockMvc mockMvc;
    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;
    private ObjectMapper mapper = new ObjectMapper();

    private UserController controller;

    @Before
    public void setup() {
        securityManager=Mockito.mock(SecurityManager.class);
        companyRepository=Mockito.mock(CompanyRepository.class);
        feederRepository=Mockito.mock(FeederRepository.class);
        measurementRepository=Mockito.mock(MeasurementRepository.class);
        controller = new UserController(securityManager, companyRepository, feederRepository, measurementRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void givenNewFeederShouldRegister()throws Exception {

        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_COMPANY");

        Company company = new Company();
        company.setName("company");
        company.setCompanyId(1L);
        company.setRole("ROLE_COMPANY");

        Feeder f1 = new Feeder();
        f1.setSerial(12345);
        List<Feeder> lf1 = new ArrayList<>();
        lf1.add(f1);

        //when

        //getUserAuthorizations method mocks
        Mockito.when(securityManager.getRoles()).thenReturn(roles);
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_COMPANY")).thenReturn(Optional.of(company));
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_USER")).thenReturn(Optional.empty());

        //post method mocks
        Mockito.when(feederRepository.findTopBySerialEqualsAndCompanyIdEquals(123,1L)).thenReturn(Optional.empty());

        //then
        MockHttpServletResponse response = mockMvc.perform(post("/user/create-feeder")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\"serial\":\"123\",\"companyId\":\"1\",\"description\":\"desc\",\"secret\":\"s1\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        BDDMockito.verify(feederRepository).save(Mockito.any(Feeder.class));
    }

    @Test
    public void givenAlreadyExistingFeederShouldReturnError()throws Exception {

        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_COMPANY");

        Company company = new Company();
        company.setName("company");
        company.setCompanyId(1L);
        company.setRole("ROLE_COMPANY");

        Feeder f1 = new Feeder();
        f1.setSerial(12345);
        List<Feeder> lf1 = new ArrayList<>();
        lf1.add(f1);

        //when

        //getUserAuthorizations method mocks
        Mockito.when(securityManager.getRoles()).thenReturn(roles);
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_COMPANY")).thenReturn(Optional.of(company));
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_USER")).thenReturn(Optional.empty());

        //post method mocks
        Mockito.when(feederRepository.findTopBySerialEqualsAndCompanyIdEquals(123,1L)).thenReturn(Optional.of(f1));

        //then
        MockHttpServletResponse response = mockMvc.perform(post("/user/create-feeder")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\"serial\":\"123\",\"companyId\":\"1\",\"description\":\"desc\",\"secret\":\"s1\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
        BDDMockito.verify(feederRepository,Mockito.never()).save(Mockito.any(Feeder.class));
    }

    @Test
    public void givenDataRequestShouldReturnMeasurement() throws Exception {

        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_FIRST");
        Company first = new Company();
        first.setName("test");
        first.setCompanyId(1L);
        List<Company> expected = new ArrayList<>();
        expected.add(first);
        Feeder testFeeder = new Feeder();
        testFeeder.setSerial(1);
        testFeeder.setCompanyId(first.getCompanyId());
        Measurement testMeasurement = new Measurement();
        testMeasurement.setValue(10);
        testMeasurement.setFeederId(1);
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(testMeasurement);
        DataRequest testRequest = new DataRequest();
        testRequest.setSerial(1);
        testRequest.setBegin("2018-06-02 15:26:04");
        testRequest.setEnd("2018-06-03 15:26:08");
        testMeasurement.setTime(testRequest.getBegin());

        //when
        when(securityManager.getRoles()).thenReturn(roles);
        when(companyRepository.findTopByRoleEquals("ROLE_FIRST")).thenReturn(Optional.of(first));
        when(feederRepository.findTopBySerialEqualsAndCompanyIdEquals(1,1L)).thenReturn(Optional.of(testFeeder));
        when(measurementRepository.findAllByFeederIdEqualsAndTimeIsGreaterThanAndTimeIsLessThanOrderByTimeAsc(testFeeder.getId(), testRequest.getBegin(), testRequest.getEnd())).thenReturn(measurements);

        //then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/feeder-measurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"serial\":\"1\", \"begin\":\"2018-06-02 15:26:04\", \"end\":\"2018-06-03 15:26:08\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("[{\"serial\":\"1\",\"timestamp\":\"2018-06-02 15:26:04\",\"value\":\"10\"}]");
    }

    @Test
    public void shouldReturnDataWhenUserIsAuthorisedToAccessFeeder() throws Exception {

        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_FIRST");
        Company first = new Company();
        first.setName("test");
        List<Company> expected = new ArrayList<>();
        expected.add(first);
        Feeder testFeeder = new Feeder();
        testFeeder.setSecret("test");
        testFeeder.setDescription("test feeder");
        List<Feeder> feeders = new ArrayList<>();
        feeders.add(testFeeder);

        //when
        when(securityManager.getRoles()).thenReturn(roles);
        when(companyRepository.findTopByRoleEquals("ROLE_FIRST")).thenReturn(Optional.of(first));
        when(feederRepository.findAllByCompanyIdEquals(first.getCompanyId())).thenReturn(feeders);

        //then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/feeder-metadata"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("[{\"id\":null,\"serial\":null,\"companyId\":null,\"description\":\"test feeder\",\"secret\":\"test\"}]");
    }


}
