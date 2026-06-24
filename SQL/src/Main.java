//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            System.out.println("=== JDBC CRUD Application ===\n");

             Repo repo = new Repo();

        System.out.println("Create Table \n");
        // 1. Create table
            repo.createEmployeesTable();


        System.out.println("\n Insert Employees \n");
            // 2. Insert employees
        repo.insertEmployee("John Doe", "IT", 75000.00);
        repo.insertEmployee("Jane Smith", "HR", 65000.00);
        repo.insertEmployee("Bob Johnson", "IT", 80000.00);
        repo.insertEmployee("Alice Williams", "Finance", 70000.00);
        repo.insertEmployee("Charlie Brown", "IT", 72000.00);
        repo.insertEmployee("Diana Prince", "HR", 68000.00);


        System.out.println("\n Find Employee By ID \n");
            // 3. Find employee by id
        repo.findEmployeeById(3);


        System.out.println("\n List All Employees \n");
            // 4. List all employees
        repo.listAllEmployees();


        System.out.println("\n Update Employee \n");
            // 5. Update employee salary and department
        repo.updateEmployeeSalaryAndDepartment("Finance", 72000.00, 2);


        System.out.println("\n Find Employee By ID \n");
            // 6. Show updated employee
        repo.findEmployeeById(2);


        System.out.println("\n Get Employee Analytics \n");
            // 7. Analytics: count and average salary by department
        repo.getEmployeeAnalyticsByDepartment();


        System.out.println("\n Delete Employee By ID \n");
            // 8. Delete employee
        repo.deleteEmployeeById(5);


        System.out.println("\n List ALL Employees \n");
            // 9. List all employees after deletion
        repo.listAllEmployees();

            System.out.println("\n=== Application completed ===");
    }
}