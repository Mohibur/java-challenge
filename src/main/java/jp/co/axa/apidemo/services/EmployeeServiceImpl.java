package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.exceptions.ParameterNotFoundException;
import jp.co.axa.apidemo.mappers.DtoEntityMappers;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohibur Rashid
 *
 */
@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    DtoEntityMappers dtoEntitymappers;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long employeeId) {
        if (employeeId == null) {
            log.info("Employee Id is null");
            return null;
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isEmpty()) {
            log.info("Employee not found with id" + employeeId);
            return null;
        }

        return employeeOptional.get();
    }

    public Employee save(EmployeeDto employeeDto) {
        if (Strings.nullToEmpty(employeeDto.getName()).isEmpty()) {
            String msg = "Employee name cannot be null or empty";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }

        if (Strings.nullToEmpty(employeeDto.getDepartment()).isBlank()) {
            String msg = "Department name cannot be null or empty";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }

        if (employeeDto.getSalary() == null || employeeDto.getSalary() < 0) {
            String msg = "Salary cannot be null or or less than zero";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }

        return employeeRepository.save(dtoEntitymappers.fromDto(employeeDto));
    }

    private String getEmployeeNotFoundMsg(Long employeeId) {
        return "employeeId: " + employeeId;
    }

    public void delete(Long employeeId) {
        if (employeeId == null || employeeRepository.findById(employeeId).isEmpty()) {
            throw new EmployeeNotFoundException(getEmployeeNotFoundMsg(employeeId));
        }

        employeeRepository.deleteById(employeeId);
    }

    public Employee update(EmployeeDto employeeDto, Long employeeId) {
        if (employeeId == null) {
            log.info("EmployeeId is null");
            throw new EmployeeNotFoundException(getEmployeeNotFoundMsg(employeeId));
        }

        Employee employee = this.findById(employeeId);
        if (employee == null) {
            log.info("Employee not found");
            throw new EmployeeNotFoundException(getEmployeeNotFoundMsg(employeeId));
        }

        if (employeeDto.getName() != null && employeeDto.getName().isEmpty()) {
            String msg = "Employee Name cannot be empty";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }
        if (employeeDto.getDepartment() != null && employeeDto.getDepartment().isEmpty()) {
            String msg = "Employee Department cannot be empty";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }

        if (employeeDto.getSalary() < 0) {
            String msg = "Salary cannot be null or or less than zero";
            log.info(msg);
            throw new ParameterNotFoundException(msg);
        }

        dtoEntitymappers.employeeDtoToEntity(employeeDto, employee);
        return employeeRepository.save(employee);
    }
}
