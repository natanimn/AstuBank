package et.edu.astu.core.controllers.admin;

import et.edu.astu.common.dto.CreatedEmployeeResponse;
import et.edu.astu.common.dto.EmployeeRequest;
import et.edu.astu.core.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminEmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create/employee")
    public ResponseEntity<CreatedEmployeeResponse> createEmployee(@RequestBody EmployeeRequest request){
        return new ResponseEntity<>(employeeService.createEmployee(request), HttpStatus.CREATED);
    }
}
