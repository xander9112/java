package com.xander.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.models.Client;
import com.xander.demo.repositories.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class ClientController {
  @GetMapping("/anonymous")
  public String getAnonymousInfo() {
    return "Anonymous";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('default-roles-neoflex')")
  public String getUserInfo() {
    return "user info";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String getAdminInfo() {
    return "admin info";
  }

  @GetMapping("/service")
  @PreAuthorize("hasRole('SERVICE')")
  public String getServiceInfo() {
    return "service info";
  }

  @GetMapping("/me")
  public Object getMe() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
  // private final EmployeeRepository employeeRepository;

  // @Autowired
  // public ClientController(EmployeeRepository employeeRepository) {
  // this.employeeRepository = employeeRepository;
  // }

  // @PostMapping(value = "/clients")
  // public ResponseEntity<?> create(@RequestBody Client client) {
  // employeeRepository.save(client);

  // return new ResponseEntity<>(HttpStatus.CREATED);
  // }

  // @GetMapping(value = "/clients")
  // public ResponseEntity<Iterable<Client>> read() {
  // final Iterable<Client> clients = employeeRepository.findAll();

  // return clients != null
  // ? new ResponseEntity<>(clients, HttpStatus.OK)
  // : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  // }

  // @GetMapping(value = "/clients/{id}")
  // public ResponseEntity<Optional<Client>> read(@PathVariable(name = "id") int
  // id) {
  // final Optional<Client> client = employeeRepository.findById(id);

  // return client != null
  // ? new ResponseEntity<>(client, HttpStatus.OK)
  // : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  // }

  // @PutMapping(value = "/clients/{id}")
  // public ResponseEntity<?> update(@PathVariable(name = "id") int id,
  // @RequestBody Client client) {
  // final Optional<Client> _client = employeeRepository.findById(id);

  // if (_client.isEmpty()) {
  // return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  // }

  // employeeRepository.save(client);

  // return new ResponseEntity<>(HttpStatus.OK);
  // }

  // @DeleteMapping(value = "/clients/{id}")
  // public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
  // final Optional<Client> _client = employeeRepository.findById(id);

  // if (_client.isEmpty()) {
  // return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  // }

  // employeeRepository.deleteById(id);

  // return new ResponseEntity<>(HttpStatus.OK);
  // }
}