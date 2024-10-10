package ivan.denysiuk.employeesservice.repository;



import ivan.denysiuk.employeesservice.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Employee entity tables on database.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Get instance from database by user-provided id of employee and department.
     *
     * @param id of employee
     * @param department the department of employee
     * @return employee, or a class that extends it, by their id
     */
    @Query("SELECT e FROM Employee e WHERE e.id = :employeeId AND e.department = :department")
    Employee getEmployeeById(@Param("employeeId") Long id, @Param("department") String department);
    /**
     * Get instance from database by user-provided pesel of employee and department.
     *
     * @param pesel of employee
     * @param department the department of employee
     * @return employee, or a class that extends it, by their pesel
     */
    @Query("SELECT e FROM Employee e WHERE e.pesel = :PESEL AND e.department = :department")
    Employee getByPesel(@Param("PESEL") String pesel, @Param("department") String department);
    /**
     * Get all instances from database, whose department matches the user-provided department.
     *
     * @param department the department of employees
     * @return list of employees by department
     */
    @Query("select e from Employee e where e.department = :department")
    List<Employee> getAll(@Param("department") String department);
    /**
     * Search all instances from database, who's full name is like user-provided full name and department matches.
     *
     * @param fullName, or part of full Name of employees
     * @param department the department of employees
     * @return list of employees, or a classes that extends it, by part of theirs full name
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :fullName, '%')) AND e.department = :department")
    List<Employee> searchByFullName(@Param("fullName") String fullName, @Param("department") String department);

    /**
     * Count all instances on database, who's department matches the user-provided department.
     *
     * @param department the department of employees
     * @return number of employees by department
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    int countAll(@Param("department") String department);
}
