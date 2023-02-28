package jp.co.axa.apidemo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for Employee Entity.
 * 
 * @author Mohibur Rashid
 *
 */
@Getter
@Setter
public class EmployeeDto {
    private String name;

    private Integer salary;

    private String department;
}
