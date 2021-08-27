package com.furkanguzel.springbootwebfluxmongodb;

import com.furkanguzel.springbootwebfluxmongodb.entity.Employee;
import com.furkanguzel.springbootwebfluxmongodb.repo.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class WebfluxMongoDbApplication {

    private final EmployeeRepository employeeRepository;

    public WebfluxMongoDbApplication(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxMongoDbApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void appStart() {
        if (employeeRepository.count().block() == 0) {
            IntStream.range(0, 100)
                    .mapToObj(this::generate)
                    .map(employeeRepository::save)
                    .collect(Collectors.toList())
                    .forEach(item -> item.subscribe(System.out::println));
        }
    }

    private Employee generate(int i) {
        return Employee.builder()
                .name("Name" + i)
                .surname("Surname" + i)
                .birthDate(LocalDate.now())
                .build();
    }
}
