package com.xander.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.model.Client;
import com.xander.demo.repository.EmployeeRepository;

@RestController
public class ClientController {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public ClientController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @PostMapping(value = "/clients")
  public ResponseEntity<?> create(@RequestBody Client client) {
    employeeRepository.save(client);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(value = "/clients")
  public ResponseEntity<Iterable<Client>> read() {
    final Iterable<Client> clients = employeeRepository.findAll();

    return clients != null
        ? new ResponseEntity<>(clients, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/clients/{id}")
  public ResponseEntity<Optional<Client>> read(@PathVariable(name = "id") int id) {
    final Optional<Client> client = employeeRepository.findById(id);

    return client != null
        ? new ResponseEntity<>(client, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping(value = "/clients/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Client client) {
    final Optional<Client> _client = employeeRepository.findById(id);

    if (_client.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    employeeRepository.save(client);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/clients/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
    final Optional<Client> _client = employeeRepository.findById(id);

    if (_client.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    employeeRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}