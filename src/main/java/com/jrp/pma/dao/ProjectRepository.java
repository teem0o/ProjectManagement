package com.jrp.pma.dao;

import java.util.List;

import com.jrp.pma.entities.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.TimeChartData;
import com.jrp.pma.entities.Project;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
	
	@Override
	public List<Project> findAll();
	
	@Query(nativeQuery=true, value="SELECT stage as label, COUNT(*) as value " + 
			"FROM project " + 
			"GROUP BY stage")
	public List<ChartData> getProjectStatus(); 
	
	@Query(nativeQuery=true, value="SELECT name as projectName, start_date as startDate, end_date as endDate"
			+ " FROM project WHERE start_date is not null")
	public List<TimeChartData> getTimeData();

	public Project findByProjectId(long theId);

//	select * from Project P join project_employee PE on p.project_id = PE.project_id where pe.employee_id = 2

	@Query(value = "select * from Project P join project_employee PE on p.project_id = PE.project_id where pe.employee_id = :theId",
			nativeQuery = true)
	public List<Project> findProjectByEmployees(@Param("theId") Long theId);



}
