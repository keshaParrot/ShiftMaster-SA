package ivan.denysiuk.employeesservice;

import ivan.denysiuk.employeesservice.domain.dto.ManagerDTO;
import ivan.denysiuk.employeesservice.domain.enumeration.TypeOfContract;
import ivan.denysiuk.employeesservice.repository.EmployeeRepository;
import ivan.denysiuk.employeesservice.service.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class bootstrap implements CommandLineRunner {
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    @Override
    public void run(String... args) throws Exception {
        employeeRepository.deleteAll();
        ManagerDTO manager = new ManagerDTO();
        manager.setFirstName("ivan");
        manager.setLastName("denysiuk");
        manager.setPesel("28391023461");
        manager.setBirthDay(LocalDate.of(2000,1,12));
        manager.setDepartment("A-01");
        manager.setRate(30.0);
        manager.setPhoneNumber("883467278");
        manager.setTypeOfContract(TypeOfContract.EmploymentContract);


        System.out.println(employeeService.saveToSystem(manager));
        System.out.println(employeeRepository.findAll());
        //System.out.println(employeeRepository.findAll().stream().map(Employee::getAuthData));

        //Long Lid = employeeRepository.findAll().get(employeeRepository.findAll().size()-1).getId();

        //System.out.println(employeeService.getById(Lid,"A-01", Manager.class).getValue());
        //System.out.println(employeeService.getByPesel("28391023461","A-01", Manager.class).getValue());
        //System.out.println(employeeService.getByPassCode("g12beuqg47gvuq3","A-01", Manager.class).getValue());
        //System.out.println(employeeService.searchByFullName("ivan","A-01"));

    }
}
