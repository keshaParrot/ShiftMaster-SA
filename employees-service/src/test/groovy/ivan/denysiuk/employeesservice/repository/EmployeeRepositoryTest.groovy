package ivan.denysiuk.employeesservice.repository


import ivan.denysiuk.employeesservice.domain.entity.Accountant
import ivan.denysiuk.employeesservice.domain.entity.BusDriver
import ivan.denysiuk.employeesservice.domain.entity.Employee
import ivan.denysiuk.employeesservice.domain.entity.Manager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest extends Specification {

    @Autowired EmployeeRepository employeeRepository

    void cleanup() {
        employeeRepository.deleteAll()
    }

    def "customSetup"() {
        Employee employee = new Employee()
        employee.id = 1L
        employee.firstName = "Erik"
        employee.lastName = "Kowalski"
        employee.pesel = "11111111111"

        Manager manager = new Manager()
        manager.id = 2L
        manager.firstName = "Iga"
        manager.lastName = "Pawelec"
        manager.pesel = "22222222222"



    }
    def "GetEmployeeById"() {
        given:
            customSetup()

        when:

        then:

        where:
            entity | dto
        // сценарій де сутності нема в базі
        // сценарій де сутність є, але не того типу шо запросив користувач (департамент)
        // сценарій де сутність є(employee), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(dispatcher), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(busDriver), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(accountant), і вона того типу шо запросив користувач (департамент)

    }
    def "searchByFullName"() {
        // сценарій де сутності нема в базі
        // сценарій де сутність є, але не того типу шо запросив користувач (департамент)
        // сценарій де сутність є(employee), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(dispatcher), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(busDriver), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(accountant), і вона того типу шо запросив користувач (департамент)
    }

    def "GetByPesel"() {
        // сценарій де сутності нема в базі
        // сценарій де сутність є, але не того типу шо запросив користувач (департамент)
        // сценарій де сутність є(employee), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(dispatcher), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(busDriver), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(accountant), і вона того типу шо запросив користувач (департамент)
    }

    def "GetAll"() {
        // сценарій де сутності нема в базі
        // сценарій де сутність є, але не того типу шо запросив користувач (департамент)
        // сценарій де сутність є(employee), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(dispatcher), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(busDriver), і вона того типу шо запросив користувач (департамент)
        // сценарій де сутність є(accountant), і вона того типу шо запросив користувач (департамент)
    }

    def "CountAll"() {
        // сценарій де в базі данних немає записів
        // сценарій де в базі данних не найшлося записів з вказаним департаментом
        // сценарій де в базі данних найшлися записи з вказаним департаментом
    }
}
