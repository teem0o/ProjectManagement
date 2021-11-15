package com.jrp.pma.services;

import java.time.LocalDate;
import java.util.List;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entities.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository empRepo;
	private final UserAccountRepository userAccountRepo;
//
//	public EmployeeService(EmployeeRepository empRepo, UserAccountRepository userAccountRepo) {
//		this.empRepo = empRepo;
//		this.userAccountRepo = userAccountRepo;
//	}


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

		System.out.println();

		return userAccountRepo.save(user);
	}



	@Transactional
	public Iterable<Employee> getAll() {
		return empRepo.findAll();
	}
	@Transactional
	public Iterable<Employee> getAll(Pageable pageable) {
		return empRepo.findAll(pageable);
	}

	public List<EmployeeProject> employeeProjects() {
		return empRepo.employeeProjects();
	}


	public Employee findByEmployeeId(long theId) {
		// TODO Auto-generated method stub
		return empRepo.findByEmployeeId(theId);
	}


	public void delete(Employee theEmp) {
//		theEmp.setDeleteDate(LocalDate.now());
		empRepo.delete(theEmp);
	}

}