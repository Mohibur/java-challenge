package jp.co.axa.apidemo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.ParameterNotFoundException;
import jp.co.axa.apidemo.mappers.DtoEntityMappers;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EntityManager em;

    @Autowired
    DtoEntityMappers mappers;

    void truncateTable() {
        em.createNativeQuery("TRUNCATE TABLE EMPLOYEE;").executeUpdate();
    }

    @Test
    public void testRegisterEmployee_fail_whenNameIsNull() {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName(null);
            employeeDto.setDepartment("deprtment");
            employeeDto.setSalary(1000);
            employeeService.save(employeeDto);
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Employee name cannot be null or empty", pnfe.getMessage());
        }

    }

    public void testRegisterEmployee_fail_whenNameIsEmpty() {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("");
            employeeDto.setDepartment("deprtment");
            employeeDto.setSalary(1000);
            employeeService.save(employeeDto);
            fail("Should not reach here");
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Employee name cannot be null or empty", pnfe.getMessage());
        }
    }

    public void testRegisterEmployee_fail_whenDepartmentIsNull() {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("John");
            employeeDto.setDepartment(null);
            employeeDto.setSalary(1000);
            employeeService.save(employeeDto);
            fail("Should not reach here");
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Department name cannot be null or empty", pnfe.getMessage());
        }
    }

    public void testRegisterEmployee_fail_whenDepartmentIsEmpty() {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("John");
            employeeDto.setDepartment("");
            employeeDto.setSalary(1000);
            employeeService.save(employeeDto);
            fail("Should not reach here");
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Department name is empty", pnfe.getMessage());
        }
    }

    public void testRegisterEmployee_fail_whenSalaryIsNull() {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("John");
            employeeDto.setDepartment("Department");
            employeeDto.setSalary(null);
            employeeService.save(employeeDto);
            fail("Should not reach here");
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Salary cannot be null or or less than zero", pnfe.getMessage());
        }

    }

    public void testRegisterEmployee_fail_whenSalaryIsNegative() {

        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("John");
            employeeDto.setDepartment("Department");
            employeeDto.setSalary(-1);
            employeeService.save(employeeDto);
            fail("Should not reach here");
        } catch (ParameterNotFoundException pnfe) {
            assertEquals("Salary cannot be null or or less than zero", pnfe.getMessage());
        }
    }

    public void testRegisterEmployee_Sucess_allvalue() {
        truncateTable();
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName("John");
            employeeDto.setDepartment("Department");
            employeeDto.setSalary(1000);

            Employee entity = employeeService.save(employeeDto);

            assertEquals("John", entity.getName());
            assertEquals("Department", entity.getDepartment());
            assertEquals(1000, (int) entity.getSalary());
        } catch (ParameterNotFoundException pnfe) {
            fail("Should not reach here");
        }
    }

    @Test
    public void testGetEmployee_Success_nonExistingValue() {
        truncateTable();
        Employee employee = employeeService.findById(100L);
        assertNull(employee);
    }

    @Test
    public void testGetEmployee_Success_existingValue() {
        truncateTable();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("John");
        employeeDto.setDepartment("Department");
        employeeDto.setSalary(10000);
        Employee reuslt = employeeService.save(employeeDto);

        Employee employee = employeeService.findById(reuslt.getId());
        assertNotNull(employee);
        assertEquals("John", employee.getName());
        assertEquals("Department", employee.getDepartment());
        assertEquals(10000, (long) employee.getSalary());
    }

    @Test
    public void deleteEmployee_fail_nonExisting() {
        truncateTable();
        try {
            employeeService.delete(1000L);
        } catch (Exception e) {
            assertEquals("Employee not found. employeeId: 1000", e.getMessage());
        }
    }

    private Employee register() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("John");
        employeeDto.setDepartment("Department");
        employeeDto.setSalary(10000);
        return employeeService.save(employeeDto);
    }

    @Test
    public void deleteEmployee_success_existing() {
        truncateTable();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("John");
        employeeDto.setDepartment("Department");
        employeeDto.setSalary(10000);
        Employee reuslt = employeeService.save(employeeDto);
        assertEquals(1, employeeService.findAll().size());
        try {
            employeeService.delete(reuslt.getId());
            assertEquals(0, employeeService.findAll().size());
        } catch (Exception e) {
            fail("Should not reach here");
        }
    }

    @Test
    public void updateEmployee_fail_whenEmployeeIdDoesNotExists() {
        truncateTable();
        EmployeeDto employeeDto = new EmployeeDto();

        try {
            employeeService.update(employeeDto, 1000L);
            fail("Should not reach here");
        } catch (Exception e) {
            assertEquals("Employee not found. employeeId: 1000", e.getMessage());

        }
    }

    @Test
    public void updateEmployee_fail_whenNameIsEmpty() {
        truncateTable();
        Employee employee = register();

        employee.setName("");

        EmployeeDto employeeDto = mappers.fromEntity(employee);

        try {
            employeeService.update(employeeDto, employee.getId());
            fail("Should not reach here");
        } catch (Exception e) {
            assertEquals("Employee Name cannot be empty", e.getMessage());
        }
    }

    @Test
    public void updateEmployee_fail_whenDepartmentIsEmpty() {
        truncateTable();
        Employee employee = register();

        employee.setName("Johny");
        employee.setDepartment("");

        EmployeeDto employeeDto = mappers.fromEntity(employee);

        try {
            employeeService.update(employeeDto, employee.getId());
            fail("Should not reach here");
        } catch (Exception e) {
            assertEquals("Employee Department cannot be empty", e.getMessage());
        }
    }

    @Test
    public void updateEmployee_fail_whenSalaryIsNullOrLessThanZero() {
        truncateTable();
        Employee employee = register();

        employee.setSalary(-1);

        EmployeeDto employeeDto = mappers.fromEntity(employee);

        try {
            employeeService.update(employeeDto, employee.getId());
            fail("Should not reach here");
        } catch (Exception e) {
            assertEquals("Salary cannot be null or or less than zero", e.getMessage());
        }
    }

    @Test
    public void updateEmployee_success_InAllCases() {
        Employee employee = register();

        EmployeeDto employeeDto = mappers.fromEntity(employee);

        // Changing only name
        employeeDto.setName("New Guy");
        // employeeDto.setDepartment("New Department");
        // employeeDto.setSalary(10000);
        try {
            Employee updatedEmployee = employeeService.update(employeeDto, employee.getId());
            assertEquals(employee.getId(), updatedEmployee.getId());
            assertEquals("New Guy", updatedEmployee.getName());
            assertEquals("Department", updatedEmployee.getDepartment());
            assertEquals(10000, (int) updatedEmployee.getSalary());
        } catch (Exception e) {
            e.fillInStackTrace();
            fail("Should not reach here");
        }

        // Changing only Department
        // employeeDto.setName("New Guy");
        employeeDto.setDepartment("New Department");
        // employeeDto.setSalary(10000);
        try {
            Employee updatedEmployee = employeeService.update(employeeDto, employee.getId());
            assertEquals(employee.getId(), updatedEmployee.getId());
            assertEquals("New Guy", updatedEmployee.getName());
            assertEquals("New Department", updatedEmployee.getDepartment());
            assertEquals(10000, (int) updatedEmployee.getSalary());
        } catch (Exception e) {
            e.fillInStackTrace();
            fail("Should not reach here");
        }

        // Changing only Salary
        // employeeDto.setName("New Guy");
        // employeeDto.setDepartment("New Department");
        employeeDto.setSalary(9900);
        try {
            Employee updatedEmployee = employeeService.update(employeeDto, employee.getId());
            assertEquals(employee.getId(), updatedEmployee.getId());
            assertEquals("New Guy", updatedEmployee.getName());
            assertEquals("New Department", updatedEmployee.getDepartment());
            assertEquals(9900, (int) updatedEmployee.getSalary());
        } catch (Exception e) {
            e.fillInStackTrace();
            fail("Should not reach here");
        }

    }
}
