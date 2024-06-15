package ivan.denysiuk.employeesservice;

import ivan.denysiuk.employeesservice.domain.entity.BusDriver;
import ivan.denysiuk.employeesservice.domain.entity.Dispatcher;
import ivan.denysiuk.employeesservice.domain.entity.Employee;
import ivan.denysiuk.employeesservice.repository.EmployeeRepository;
import ivan.denysiuk.employeesservice.service.interfaces.EmployeeService;
import ivan.denysiuk.unswer.Result;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class bootstrap implements CommandLineRunner {
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    @Override
    public void run(String... args) throws Exception {
        employeeRepository.deleteAll();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setFirstName("ivan");
        dispatcher.setLastName("denysiuk");
        dispatcher.setPesel("28391023461");
        dispatcher.setPasscode("g12beuqg47gvuq3");
        dispatcher.setDepartment("A-01");


        employeeRepository.save(dispatcher);

        Long Lid = employeeRepository.findAll().get(employeeRepository.findAll().size()-1).getId();

        System.out.println(employeeService.getById(Lid,"A-01", Dispatcher.class).getValue());
        System.out.println(employeeService.getByPesel("28391023461","A-01", Dispatcher.class).getValue());
        System.out.println(employeeService.getByPassCode("g12beuqg47gvuq3","A-01", Dispatcher.class).getValue());
        System.out.println(employeeService.searchByFullName("ivan","A-01"));

    }
}
