package com.jrp.pma.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.jrp.pma.entities.Employee;
import com.jrp.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;


	@GetMapping
	public String displayEmployees(Model model) {
		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);
		return "employees/list-employees";
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		
		Employee anEmployee = new Employee();
		
		model.addAttribute("employee", anEmployee);
		
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(Model model, @Valid Employee employee, Errors errors) {
		
		if(errors.hasErrors())
			return "employees/new-employee";
		
		// save to the database using an employee crud repository
		empService.save(employee);
		//save to user accounts database
		empService.saveToAccounts(employee);

		
		return "redirect:/employees";
	}

	@RequestMapping("/update")
	public String updateEmployee(Model model, Employee employee, Errors errors) {

		//NSFW
		if(errors.hasErrors())
			return "employees/update-employee";

		empService.save(employee);
		//save to user accounts database
		empService.saveToAccounts(employee);

		return "redirect:/employees";
	}

	@RequestMapping("/{id}")
	public String displayEmployeeUpdateForm(@PathVariable("id") long theId, Model model) {
		
		Employee theEmp = empService.findByEmployeeId(theId);
		
		model.addAttribute("employee", theEmp);
		
		
		return "employees/update-employee";
	}
	
//	@GetMapping("delete")
	@RequestMapping("/{id}")
	public String deleteEmployee(@PathVariable("id") long theId, Model model) {
		Employee theEmp = empService.findByEmployeeId(theId);
		empService.delete(theEmp);
		return "redirect:/employees";
	}

}
