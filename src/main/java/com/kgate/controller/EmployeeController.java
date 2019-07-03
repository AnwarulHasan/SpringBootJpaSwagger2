package com.kgate.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kgate.exception.ResourceNotFoundException;
import com.kgate.model.Country;
import com.kgate.model.Department;
import com.kgate.model.Employee;
import com.kgate.model.State;
import com.kgate.repository.CountryRepository;
import com.kgate.repository.DepartmentRepository;
import com.kgate.repository.EmployeeRepository;
import com.kgate.repository.StateRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository deptRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/home")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@GetMapping("/employees")
	@ApiOperation(value = "View a list of available employees", response = List.class)
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@PostMapping("/save")
	@ApiOperation(value = "Add an employee")
	public Employee createEmployee(
			@ApiParam(value = "Employee object store in database table", required = true) @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployyeById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + id));
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id, @RequestBody Employee emp)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id" + id));
		employee.setFirstName(emp.getFirstName());
		employee.setLastName(emp.getLastName());
		employee.setEmailId(emp.getEmailId());
		employee.setId(emp.getId());
		Employee updated = employeeRepository.save(employee);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/employee/{id}")
	@ApiOperation(value = "Delete a employee by id")
	public Map<String, Boolean> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + id));
		employeeRepository.delete(emp);
		Map<String, Boolean> response = new LinkedHashMap<String, Boolean>();
		response.put("Deleted...", Boolean.TRUE);
		return response;
	}

	@PostMapping({ "/department" })
	@ApiOperation(value = "Add a department")
	public ResponseEntity<?> saveDept(@RequestBody Department dept) {
		return ResponseEntity.ok().body(deptRepository.save(dept));
	}

	@PostMapping("/addCountry")
	@ApiOperation(value = "Add a Country")
	public ResponseEntity<?> addCountry(@RequestBody Country country) {
		return ResponseEntity.ok().body(countryRepository.save(country));
	}

	@PostMapping("/addState")
	@ApiOperation(value = "Add an State")
	public ResponseEntity<?> addState(@RequestBody State state) {
		return ResponseEntity.ok().body(stateRepository.save(state));
	}
}
