package com.example.SpringJPA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
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
						// TODO:
						System.out.println("Departments:" + repository.findByDepartment(department));

// Execute the repository method that finds
// employees by department and print the results.
					}
					case 2 -> {
						System.out.print("Minimum salary: ");
						BigDecimal salary = new BigDecimal(scanner.nextLine());
						// TODO:
// Execute the repository method that finds
// employees with salary greater than the given value
// and print the results.
					}
					case 3 -> {
						System.out.print("Last name contains: ");
						String text = scanner.nextLine();
						// TODO:
// Execute the repository method that finds
// employees whose last name contains the given text
// and print the results.
					}
					case 4 -> {
						System.out.print("Hire date (yyyy-MM-dd): ");
						LocalDate hireDate = LocalDate.parse(scanner.nextLine());
						// TODO:
// Execute the JPQL query method that returns
// employees hired after the given date.
					}
					case 5 -> {
						System.out.print("Minimum salary: ");
						BigDecimal salary = new BigDecimal(scanner.nextLine());
						// TODO:
// Execute the native query that returns employees
// whose salary is greater than the given value.
					}
					case 6 -> {
						// TODO:
// Retrieve the first page containing
// 5 employees and print the results.
					}
					case 7 -> {
						// TODO:
// Retrieve all employees sorted
// by salary in descending order
// and print the results.
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
}
