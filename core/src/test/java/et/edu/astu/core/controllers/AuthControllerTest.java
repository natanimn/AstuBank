package et.edu.astu.core.controllers;

import et.edu.astu.common.dto.AdminLoginResponse;
import et.edu.astu.common.dto.LoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testAdminLogin() throws Exception {
        LoginRequest request = new LoginRequest("admin", "admin");
        AdminLoginResponse successful = new AdminLoginResponse(true);
        AdminLoginResponse unsuccessful = new AdminLoginResponse(false);

        mvc.perform(
                post("/api/auth/admin/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request))

        ).andExpectAll(
                status().isOk(),
                content().json(
                        mapper.writeValueAsString(successful)
                )
        );


        mvc.perform(
                post("/api/auth/admin/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(new LoginRequest("admin", "user")))

        ).andExpectAll(
                status().isOk(),
                content().json(
                        mapper.writeValueAsString(unsuccessful)
                )
        );


        mvc.perform(
                post("/api/auth/admin/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(new LoginRequest("user", "user")))

        ).andExpectAll(
                status().isOk(),
                content().json(
                        mapper.writeValueAsString(unsuccessful)
                )
        );
    }
}
