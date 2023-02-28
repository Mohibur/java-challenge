package jp.co.axa.apidemo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

/**
 * @author Mohibur Rashid
 * 
 * Give support to convert between Objects
 *
 */
@Mapper(componentModel = "spring", // Enable spring to create autowired object 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, // When a source value is null, destination value will not be updated
    unmappedTargetPolicy = ReportingPolicy.IGNORE // Ask Java not to throw warning if a column is not missing
    )
public interface DtoEntityMappers {
    /**
     * Create an object of Employee from EmployeeDto
     * @param employeeDto
     * @return Employee
     */
    Employee fromDto(EmployeeDto employeeDto);

    /**
     * Create an object of EmployeeDto from Employee
     * @param employee
     * @return
     */
    EmployeeDto fromEntity(Employee employee);

    /**
     * Map EmployeeDto to Employee (Entity)
     * @param employeeDto
     * @param employee
     */
    void employeeDtoToEntity(EmployeeDto employeeDto, @MappingTarget Employee employee);
}
