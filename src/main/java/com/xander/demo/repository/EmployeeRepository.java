package com.xander.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.xander.demo.model.Client;

public interface EmployeeRepository extends CrudRepository<Client, Integer> {

}
