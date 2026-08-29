package mapper;

import org.springframework.stereotype.Component;

import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.model.Employee;

@Component
public class EmployeeMapper {
	
	public Employee employeeDtoToEmployee(EmployeeRequestDTO employeeRequestDTO) {
		Employee employee = new Employee();
		employee.setFirstName(employeeRequestDTO.getFirstName());
		employee.setLastName(employeeRequestDTO.getLastName());
		employee.setEmail(employeeRequestDTO.getEmail());
		employee.setPhone(employeeRequestDTO.getPhone());
		employee.setSalary(employeeRequestDTO.getSalary());
		employee.setJoiningDate(employeeRequestDTO.getJoiningDate());
		employee.setStatus(employeeRequestDTO.getStatus());

		return employee;		
	}
	
	public EmployeeResponseDTO employeeToEmployeeResponse(Employee emp) {
		EmployeeResponseDTO response=new EmployeeResponseDTO();
		response.setId(emp.getId());
		response.setFirstName(emp.getFirstName());
		response.setLastName(emp.getLastName());
		response.setEmail(emp.getEmail());
		response.setPhone(emp.getPhone());
		response.setSalary(emp.getSalary());
		response.setJoiningDate(emp.getJoiningDate());
		response.setStatus(emp.getStatus());
		response.setDepartmentId(emp.getDepartment().getId());
		
		return response;
	}
	
}
