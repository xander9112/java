package com.xander.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.xander.demo.models.Client;

public interface EmployeeRepository extends CrudRepository<Client, Integer> {

}
