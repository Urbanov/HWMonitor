package edu.pw.hwmonitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.controllers.UserController;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class ControllerTest {

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
        controller = new UserController(securityManager,companyRepository,feederRepository,measurementRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void shouldReturnListOfCompanies()throws Exception {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_FIRST");
        roles.add("ROLE_SECOND");
        Company first = new Company();
        first.setName("f");
        Company second = new Company();
        second.setName("f");
        List<Company> expected = new ArrayList<>();
        expected.add(first);
        expected.add(second);

        //when
        Mockito.when(securityManager.getRoles()).thenReturn(roles);
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_FIRST")).thenReturn(Optional.of(first));
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_SECOND")).thenReturn(Optional.of(second));


        //then
        List<Company> result = Whitebox.<List<Company>>invokeMethod(controller, "companyAuthorizations");
        assertThat(result).isEqualTo(expected);
    }

    /*
    @Test
    public void shouldReturnTrueWhenFeederNotAllowed()throws Exception {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_FIRST");
        roles.add("ROLE_SECOND");

        Company first = new Company();
        first.setName("f");
        first.setCompanyId(1L);

        Company second = new Company();
        second.setName("f");
        second.setCompanyId(2L);

        List<Company> expected = new ArrayList<>();
        expected.add(first);
        expected.add(second);

        List<Company> cl = new ArrayList<>();
        cl.add(first);
        cl.add(second);

        Feeder f1 = new Feeder();
        f1.setSerial(12345);
        List<Feeder> lf1 = new ArrayList<>();
        lf1.add(f1);

        Feeder f2 = new Feeder();
        f2.setSerial(54321);
        List<Feeder> lf2 = new ArrayList<>();
        lf2.add(f2);

        //when
        Mockito.when(securityManager.getRoles()).thenReturn(roles);
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_FIRST")).thenReturn(Optional.of(first));
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_SECOND")).thenReturn(Optional.of(second));
        Mockito.when(feederRepository.findAllByCompanyIdEquals(1L)).thenReturn(lf1);
        Mockito.when(feederRepository.findAllByCompanyIdEquals(2L)).thenReturn(lf2);

        //then
        Boolean result = Whitebox.<Boolean>invokeMethod(controller, "isFeederAllowed",12345);
        assertThat(result).isEqualTo(true);
    }


    @Test
    public void shouldReturnFalseWhenFeederNotAllowed()throws Exception {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_FIRST");
        roles.add("ROLE_SECOND");

        Company first = new Company();
        first.setName("f");
        first.setCompanyId(1L);
        Company second = new Company();
        second.setName("f");
        second.setCompanyId(2L);
        List<Company> expected = new ArrayList<>();
        expected.add(first);
        expected.add(second);


        List<Company> cl = new ArrayList<>();
        cl.add(first);
        cl.add(second);

        Feeder f1 = new Feeder();
        f1.setSerial(12345);
        List<Feeder> lf1 = new ArrayList<>();
        lf1.add(f1);

        Feeder f2 = new Feeder();
        f2.setSerial(54321);
        List<Feeder> lf2 = new ArrayList<>();
        lf2.add(f2);

        Mockito.when(securityManager.getRoles()).thenReturn(roles);
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_FIRST")).thenReturn(Optional.of(first));
        Mockito.when(companyRepository.findTopByRoleEquals("ROLE_SECOND")).thenReturn(Optional.of(second));

        //when
        Mockito.when(feederRepository.findAllByCompanyIdEquals(1L)).thenReturn(lf1);
        Mockito.when(feederRepository.findAllByCompanyIdEquals(2L)).thenReturn(lf2);

        //then
        Boolean result = Whitebox.<Boolean>invokeMethod(controller, "isFeederAllowed",15141);
        assertThat(result).isEqualTo(false);
    }
    */
}
