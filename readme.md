# Added Class and files

## jp.co.axa.apidemo.dto.EmployeeDto
- DTO for Employee entity

## jp.co.axa.apidemo.exceptions.EmployeeNotFoundException
- Thrown when Employee not found, with 404 http status code
## jp.co.axa.apidemo.exceptions.ParameterNotFoundException
- Thrown when Required parameters not found, with 422 stauts code

## jp.co.axa.apidemo.mappers.DtoEntityMappers
- Mappers that offers various mapping bettween structurs;

## jp.co.axa.apidemo.security.SpringSecurityConfig
- A security configuration with SimpleAuthentication mechanism
- username and passwords
  1. admin:password
    - can access all api
  1. user:password
    - can access only `/api/v1/employees`

## resources::data.sql
- Some data to Employee table

## Test
- Added test for DtoEntityMappers
- Added test for EmployeeService


## jp.co.axa.apidemo.dto.EmployeeDto

# Updated

## pom.xml
- Added encoding utf-8 for the entire source code
- Added mapstruct related configuration.
- Added devtools for development support
- Added springboot security 
- Added plugin to propagate mapstruct
- Removed version information for lombok, prefer to use version provided by springboot


## jp.co.axa.apidemo.ApiDemoApplication
- Changes tab to spaces to match with other java files.
- enable Caching


## jp.co.axa.apidemo.controllers.EmployeeController
- Removed method `setEmployeeService`. It is unnecessary for two reasons.
  1. Springboot will autowire the field
  1. Updating private field of Service alike Object by external agent should be discouraged.
- Replace `System.out.println` to `log4j2.info` for all methods

## jp.co.axa.apidemo.services.EmployeeService
- Renamed method `retrieveEmployees` to `findAll` to match with JpaRepository.
- Renamed method `getEmployee` to `findById` to match with JpaRepository.
- Renamed method `saveEmployee` to `save` to match with JpaRepository.
- Renamed method `updateEmployee` to `update`.
- Added new line at the end

## jp.co.axa.apidemo.services.EmployeeServiceImpl
- Removed `setEmployeeRepository`. It is unnecessary for two reasons.
  1. Springboot will autowire the field
  1. Updating private field of Service alike Object by external agent should be discouraged.
- Improved `findById`, renamed from `getEmployee`. Returned null instead of throwing exception when value is not available.
- Renamed `retrieveEmployees` to `findAll`
- Improved `save`, renamed from `saveEmployee`. Added validation for fields and throw exception if missing. Changed variable from `Employee` to `EmployeeDto`
- Improved `delete`, renamed from `deleteEmployee`. Added validation for availability of Employee. Throwing exception if not available
- Improved `update`, renamed from `updateEmployee`. Fixed bug. Added validation for empty string.

## application.property
- Added Initial Script to insert data to Employee table


# What could have been done if I had enough time
1. Fully implement authorization
1. Fully implement caching with horizontal expansion cability
1. Add test on mapper class
1. Integration test by calling each and individual api


# MY experience in Java
- I have more than 6 years experience with Java (1.8)
- I have 4+ years experience of Springboot application development.
- I do not have any experience with SpringBoot Security.
