package ivan.denysiuk.employeesservice.service

import ivan.denysiuk.employeesservice.domain.dto.EmployeeDTO
import ivan.denysiuk.employeesservice.domain.entity.Employee
import ivan.denysiuk.employeesservice.repository.EmployeeRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EmployeeServiceImplTest extends Specification {

    def employeeRepository = Mock(EmployeeRepository)
    @Subject def employeeService = new EmployeeServiceImpl(employeeRepository)

    @Shared EmployeeDTO employeeDto
    @Shared List<Employee> listOfEmployee = []

    def setupSpec(){

    }

    def "SaveToSystem"() {



        //dto | entity |
        //employee   |
        //dispatcher |
        //busdriver  |
        //accountant |
        //null       |
    }

    def "UpdateById"() {

    }

    def "PatchById"() {

    }

    def "GetById"() {

    }

    def "GetByFullName"() {

    }

    def "GetByPesel"() {

    }

    def "GetByPassCode"() {
    }

    def "DeleteById"() {
    }

    def "GetAll"() {
    }

    def "ExistById"() {
    }

    def "CountAll"() {
    }
}
