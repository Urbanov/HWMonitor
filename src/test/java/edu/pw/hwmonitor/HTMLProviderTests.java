package edu.pw.hwmonitor;

import edu.pw.hwmonitor.controllers.HTMLProviderController;
import edu.pw.hwmonitor.security.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class HTMLProviderTests {

    private MockMvc mockMvc;
    private SecurityManager securityManager;
    private HTMLProviderController controller;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        controller = new HTMLProviderController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testRootMapping() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testIndexMapping() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserIndexMapping() throws Exception {
        mockMvc.perform(get("/user/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginMapping() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void test403Mapping() throws Exception {
        mockMvc.perform(get("/accessDenied"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginErrorMapping() throws Exception {
        mockMvc.perform(get("/login-error"))
                .andExpect(status().isOk());
    }




}