package et.edu.astu.core.services;

import et.edu.astu.common.dto.CreatedEmployeeResponse;
import et.edu.astu.common.dto.EmployeeLoginRequest;
import et.edu.astu.common.dto.EmployeeRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.core.generators.EmployeeGenerator;
import et.edu.astu.core.models.CustomEmployeeDetails;
import et.edu.astu.core.models.Employee;
import et.edu.astu.core.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeGenerator generator;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public Employee findById(String id){
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public CreatedEmployeeResponse createEmployee(EmployeeRequest dto){
        long count = repository.count();
        String username = generator.generateUsername(count);
        String password = generator.generatePassword();
        String encodedPassword = encoder.encode(password);
        Employee employee = new Employee(
                username,
                encodedPassword,
                dto.firstName(),
                dto.middleName(),
                dto.lastName()
        );
        repository.save(employee);

        return new CreatedEmployeeResponse(
                dto.firstName(),
                dto.middleName(),
                dto.lastName(),
                username,
                password
        );
    }

    protected LoginResponse login(EmployeeLoginRequest request){
        Employee employee = repository.findByUsername(request.username())
                .orElseThrow(() ->  new RuntimeException("Incorrect username or password"));

        if (encoder.matches(request.password(), employee.getPassword())){
            String token = jwtService.generateEmployeeJwt(employee.getId());
            return new LoginResponse(token);
        }
        throw new RuntimeException("Incorrect username or password");
    }

    public CustomEmployeeDetails findCustom(String id){
        Employee employee = findById(id);
        return new CustomEmployeeDetails(employee.getUsername(), employee.getPassword());
    }

}
