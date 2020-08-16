package com.jrp.pma.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;

	@Autowired
	UserAccountRepository userAccountRepo;

	@GetMapping
	public String displayProjects(Model model, Authentication authentication) {
		List<Project> projects = proService.getAll();
		UserAccount x = userAccountRepo.findByUserName(authentication.getName());
		List<Project> projectsFromEmployee = proService.getProjectByEmployees(x.getUserId());

		model.addAttribute("projects", projects);
		model.addAttribute("projectsFromEmp", projectsFromEmployee);

		return "projects/list-projects";
	}
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		
		Project aProject = new Project();
		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);
		
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Project project, @RequestParam List<Long> employees, Model model, Errors errors) {

//		if (project.getStartDate().compareTo(project.getEndDate())>0){
//
//		}
		//NSFW
		if(errors.hasErrors())
			return "projects/new-project";

//		LocalDate x = project.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		proService.save(project);

		return "redirect:/projects";
		
	}
//	@PostMapping("/update")//delet eme
//	public String updateProject(Project project, @RequestParam List<Long> employees, Model model) {
//		proService.save(project);
//		return "redirect:/projects";
//	}
//	@PatchMapping(path="/update/{id}")
//	public String partialUpdate(@PathVariable("id") long id, @RequestBody Project patchProject) {
//		Project project = proService.findByProjectId(id);
//
//		if(patchProject.getName() != null) {
//			project.setName(patchProject.getName());
//		}
//		if(patchProject.getStage() != null) {
//			project.setStage(patchProject.getStage());
//		}
//		if(patchProject.getDescription() != null) {
//			project.setDescription(patchProject.getDescription());
//		}
//		if(patchProject.getEmployees() != null) {
//			project.setEmployees(patchProject.getEmployees());
//		}
//		if(patchProject.getStartDate() != null) {
//			project.setStartDate(patchProject.getStartDate());
//		}
//		if(patchProject.getEndDate() != null) {
//			project.setEndDate(patchProject.getEndDate());
//		}
//		proService.save(project);
//		return "redirect:/projects";
//
//	}

	@GetMapping("/{id}")
	public String updateProjectForm(@PathVariable("id") long theId, Model model) {

		Project aProject = proService.findByProjectId(theId);
		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);

		return "projects/new-project";
	}
	@RequestMapping("/{id}")
	public String deleteProject(@PathVariable("id") long theId, Model model) {
		Project aProject = proService.findByProjectId(theId);
		proService.delete(aProject);
		return "redirect:/projects";
	}
	
	@GetMapping("/timelines")
	public String displayProjectTimelines(Model model) throws JsonProcessingException {
		
		List<TimeChartData> timelineData = proService.getTimeData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonTimelineString = objectMapper.writeValueAsString(timelineData);

		System.out.println("---------- project timelines ----------");
		System.out.println(jsonTimelineString);
		
		model.addAttribute("projectTimeList", jsonTimelineString);
		
		return "projects/project-timelines";
	}

}
