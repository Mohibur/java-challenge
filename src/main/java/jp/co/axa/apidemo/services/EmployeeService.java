package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * Handles EmployeeRepository. 
 * 
 * @author Mohibur Rashid
 *
 */
public interface EmployeeService {

    /**
     * Listing all the employee registered in the table.<br>
     * Returns empty List when no item is found
     * 
     * @return List&lt;Employee&gt;
     */
    public List<Employee> findAll();

    /**
     * Find item by employeeId. <br>
     * Returns null if employeeId is null or is not a valid id
     * 
     * @param employeeId
     * @return Employee
     */
    public Employee findById(Long employeeId);

    /**
     * Register new Employee<br>
     * Mandatory Fields: <br>
     * 1. <b>name</b> is not null and not empty <br>
     * 2. <b>department</b> is not null and not empty <br>
     * 3. <b>salary</b> >= 0
     * 
     * @param employeeDto
     * @return
     * 
     * @throws ParameterNotFoundException if fields are incorrect
     */
    public Employee save(EmployeeDto employeeDto);

    /**
     * Delete existing employee.<br>
     * employeeId must have to be valid
     * 
     * @param employeeId
     * @throws EmployeeNotFoundException if employeeId is not valid
     */
    public void delete(Long employeeId);

    /**
     * Update Employee information.<br>
     * Fields: <br>
     * 1. <b>name</b> can be null but cannot be not empty <br>
     * 2. <b>department</b> can be null but cannot be empty <br>
     * 3. <b>salary</b> can be null but cannot be less than zero
     * 
     * @param employeeDto
     * @param employeeId
     * 
     * @return
     */
    public Employee update(EmployeeDto employeeDto, Long employeeId);
}
