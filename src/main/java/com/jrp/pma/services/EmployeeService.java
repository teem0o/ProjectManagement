package com.jrp.pma.services;

import java.util.List;
import java.util.Optional;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	UserAccountRepository userAccountRepo;
	
	
	public Employee save(Employee employee) {
		return empRepo.save(employee);
	}

	public UserAccount saveToAccounts(Employee employee) {
		int i=2;
		UserAccount user = new UserAccount();
		user.setEmail(employee.getEmail());
		user.setEnabled(true);
		user.setPassword("$2a$10$nZV4lEoJDyDWpZyXQC.FN.O9azPgiAFpagBPBOrg8TCuT4g5xmhIq");
		user.setRole("ROLE_USER");
		user.setUserName(employee.getFirstName());



		return userAccountRepo.save(user);
	}



	public Iterable<Employee> getAll() {
		return empRepo.findAll();
	}


	public List<EmployeeProject> employeeProjects() {
		return empRepo.employeeProjects();
	}


	public Employee findByEmployeeId(long theId) {
		// TODO Auto-generated method stub
		return empRepo.findByEmployeeId(theId);
	}


	public void delete(Employee theEmp) {
		empRepo.delete(theEmp);
	}

}