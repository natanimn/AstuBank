package et.edu.astu.core.controllers.admin;

import et.edu.astu.common.dto.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminEmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createEmployeeTest() throws Exception{
        EmployeeRequest request = new EmployeeRequest(
                "I am",
                "Test",
                "Employee"
        );

        mvc.perform(
                post("/api/admin/create/employee")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request))
        ).andExpectAll(status().isCreated());
    }
}
