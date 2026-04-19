package et.edu.astu.core.services;

import et.edu.astu.core.models.Employee;
import et.edu.astu.core.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public Employee findById(String id){
        return repository.findById(id).orElseThrow();
    }

}
