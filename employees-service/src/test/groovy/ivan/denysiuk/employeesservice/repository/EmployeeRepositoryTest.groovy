package ivan.denysiuk.employeesservice.repository

import ivan.denysiuk.employeesservice.domain.entity.Accountant
import ivan.denysiuk.employeesservice.domain.entity.BusDriver
import ivan.denysiuk.employeesservice.domain.entity.Dispatcher
import ivan.denysiuk.employeesservice.domain.entity.Employee
import spock.lang.Specification
import spock.lang.Subject

class EmployeeRepositoryTest extends Specification {

    @Subject EmployeeRepository employeeRepository

    Employee employee
    Dispatcher dispatcher
    Accountant accountant
    BusDriver busDriver

    void cleanup() {

    }

    void "customSetup"() {



    }
    def "GetEmployeeById"() {





    }
    def "searchByFullName"() {

    }

    def "GetByPasscode"() {
    }

    def "GetByPesel"() {
    }

    def "GetAll"() {
    }

    def "CountAll"() {
    }
}
