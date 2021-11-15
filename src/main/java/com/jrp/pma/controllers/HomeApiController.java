package com.jrp.pma.controllers;

import com.jrp.pma.entities.Employee;
import com.jrp.pma.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeApiController {

    private final EmployeeService empServ;

    @GetMapping("/employee/{page}/{size}")
    public ResponseEntity<Iterable> getEmployees(@PathVariable("page") int page, @PathVariable("size")int size) {
        Pageable pageAndSize = PageRequest.of(page, size);
        return new ResponseEntity<>(empServ.getAll(pageAndSize), HttpStatus.OK);
    }




    @PostMapping("/employee")
    public ResponseEntity<Employee> getEmployees(Employee employee) {
        return new ResponseEntity<>(empServ.save(employee), HttpStatus.OK);
    }
}
