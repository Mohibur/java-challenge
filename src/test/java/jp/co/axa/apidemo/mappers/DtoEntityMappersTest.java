package jp.co.axa.apidemo.mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DtoEntityMappersTest {

    @Autowired
    DtoEntityMappers mappers;

    public Employee getEmployee() {
        Employee entity = new Employee();
        entity.setId(1L);
        entity.setName("Johny");
        entity.setDepartment("Marketing Department");
        entity.setSalary(1000);
        return entity;
    }

    @Test
    public void testEmployeeMappers() {
        Employee entity = getEmployee();

        EmployeeDto dto = new EmployeeDto();
        dto.setDepartment("Sales Department");
        dto.setName("Mohan");
        dto.setSalary(1001);

        mappers.employeeDtoToEntity(dto, entity);

        assertEquals(1l, (long) entity.getId());
        assertEquals("Mohan", entity.getName());
        assertEquals("Sales Department", entity.getDepartment());
        assertEquals(1001, (int) entity.getSalary());
    }

    @Test
    public void testEmployeeMappersName() {
        Employee entity = getEmployee();

        EmployeeDto dto = new EmployeeDto();
        dto.setName("Mohan");

        mappers.employeeDtoToEntity(dto, entity);

        assertEquals(1l, (long) entity.getId());
        assertEquals("Mohan", entity.getName());
        assertEquals("Marketing Department", entity.getDepartment());
        assertEquals(1000, (int) entity.getSalary());
    }

    @Test
    public void testEmployeeMappersDepartment() {
        Employee entity = getEmployee();

        EmployeeDto dto = new EmployeeDto();
        dto.setDepartment("Marketing Department");

        mappers.employeeDtoToEntity(dto, entity);

        assertEquals(1l, (long) entity.getId());
        assertEquals("Johny", entity.getName());
        assertEquals("Marketing Department", entity.getDepartment());
        assertEquals(1000, (int) entity.getSalary());
    }

    @Test
    public void testEmployeeMappersSalary() {
        Employee entity = getEmployee();

        EmployeeDto dto = new EmployeeDto();
        dto.setSalary(1001);

        mappers.employeeDtoToEntity(dto, entity);

        assertEquals(1l, (long) entity.getId());
        assertEquals("Johny", entity.getName());
        assertEquals("Marketing Department", entity.getDepartment());
        assertEquals(1001, (int) entity.getSalary());
    }
}
