package com.furkanguzel.springbootwebfluxmongodb.repo;

import com.furkanguzel.springbootwebfluxmongodb.entity.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
