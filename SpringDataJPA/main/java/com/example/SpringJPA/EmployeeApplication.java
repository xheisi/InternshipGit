package com.example.SpringJPA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EmployeeApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository repository) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("\n===== Employee Management =====");
				System.out.println("1 - Find employees by department");
				System.out.println("2 - Find employees with salary greater than");
				System.out.println("3 - Find employees whose last name contains");
				System.out.println("4 - Find employees hired after a date");
				System.out.println("5 - Execute native salary query");
				System.out.println("6 - Show first page (5 employees)");
				System.out.println("7 - Show employees sorted by salary (descending)");
				System.out.println("0 - Exit");
				System.out.print("Choose an option: ");
				int option = Integer.parseInt(scanner.nextLine());
				switch (option) {
					case 1 -> {
						System.out.print("Department: ");
						String department = scanner.nextLine();
						List<Employee> employees = repository.findByDepartment(department);
						printEmployees(employees);
					}
					case 2 -> {
						System.out.print("Minimum salary: ");
						BigDecimal salary = new BigDecimal(scanner.nextLine());
						List<Employee> employees = repository.findBySalaryGreaterThan(salary);
						printEmployees(employees);
					}
					case 3 -> {
						System.out.print("Last name contains: ");
						String text = scanner.nextLine();
						List<Employee> employees = repository.findByLastNameContaining(text);
						printEmployees(employees);
					}
					case 4 -> {
						System.out.print("Hire date (yyyy-MM-dd): ");
						LocalDate hireDate = LocalDate.parse(scanner.nextLine());
						List<Employee> employees = repository.findEmployeesHiredAfter(hireDate);
						printEmployees(employees);
					}
					case 5 -> {
						System.out.print("Minimum salary: ");
						BigDecimal salary = new BigDecimal(scanner.nextLine());
						List<Employee> employees = repository.findEmployeesBySalaryGreaterThanNative(salary);
						printEmployees(employees);
					}
					case 6 -> {
						Pageable pageable = PageRequest.of(0, 5);
						Page<Employee> page = repository.findAll(pageable);
						printEmployees(page.getContent());
					}
					case 7 -> {
						List<Employee> employees = repository.findAll(Sort.by(Sort.Direction.DESC, "salary"));
						printEmployees(employees);
					}
					case 0 -> {
						System.out.println("Goodbye!");
						return;
					}
					default -> System.out.println("Invalid option.");
				}
			}
		};
	}

	private void printEmployees(List<Employee> employees) {
		if (employees.isEmpty()) {
			System.out.println("No employees found.");
			return;
		}
		employees.forEach(System.out::println);
	}
}