package com.jrp.pma.services;

import java.util.List;

import com.jrp.pma.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entities.Project;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository proRepo;
	
	
	public Project save(Project project) {
		return proRepo.save(project);
	}


	public List<Project> getAll() {
		return proRepo.findAll();
	}
	
	public List<ChartData> getProjectStatus(){
		return proRepo.getProjectStatus();
	}

	public List<TimeChartData> getTimeData(){
		return proRepo.getTimeData();
	}

	public Project findByProjectId(long theId) {
		return proRepo.findByProjectId(theId);
	}

	public void delete(Project theProj) {
		proRepo.delete(theProj);
	}

	public List<Project> getProjectByEmployees(Long theId){
		return proRepo.findProjectByEmployees(theId);
	}
}
