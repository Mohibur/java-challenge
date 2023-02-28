package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles All Employee related request
 * 
 * @author Mohibur Rashid
 *
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.findById(employeeId);
    }

    @PostMapping("")
    public void registerEmployee(EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        log.info("Employee registered successfully.");
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.delete(employeeId);
        log.info("Employee Deleted Successfully. Employee Id: " + employeeId);
    }

    @PutMapping("/{employeeId}")
    public void updateEmployee(@RequestBody EmployeeDto employee, @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.update(employee, employeeId);
        log.info("Employee Update Successfully. Employee Id: " + employeeId);
    }
}
