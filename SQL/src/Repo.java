import java.sql.*;
import javax.sql.*;

public class Repo {

    public void createEmployeesTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS employee2 (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                department VARCHAR(50) NOT NULL,
                salary DECIMAL(10, 2) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Connection connection = DBconn.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Table 'employee2' created or already exists\n");

        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertEmployee(String name, String department, double salary){
        String sql = """
                INSERT INTO employee2(name, department, salary)
                VALUES(?, ?, ?)
                """;
        try (Connection connection = DBconn.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);
            ps.executeUpdate();
            System.out.println("Inserted employee: " + name);

        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void updateEmployeeSalaryAndDepartment(String department, double salary, int id){
        String sql = """
                UPDATE employee2
                SET salary = ?, department = ?
                WHERE id = ?;
                """;

        try (Connection connection = DBconn.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, salary);
            ps.setString(2, department);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("\nUpdated employee id " + id + " -> department: " + department + ", salary: " + salary);
            } else {
                System.out.println("No employee found with id: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void findEmployeeById(int id) {
        String sql = """
                SELECT * FROM employee2
                WHERE id = ?
                """;

        try (Connection connection = DBconn.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID:         " + rs.getInt("id"));
                System.out.println("Name:       " + rs.getString("name"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Salary:     " + rs.getDouble("salary"));
                System.out.println("Created at: " + rs.getTimestamp("created_at"));

            } else{
                System.out.println("No employee found with id: " + id);
            }
        }catch(SQLException e){
            System.out.println("Error finding employee:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listAllEmployees(){
        String sql = """
                SELECT * FROM employee2
                """;
        try(Connection connection = DBconn.getConnection();
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("department") + " " + rs.getDouble("salary"));
            }
        }catch(SQLException e){
            System.out.println("Error listing all employees" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getEmployeeAnalyticsByDepartment(){
        String sql = """
                SELECT department, COUNT(*)  AS employee_count, AVG(salary)  AS average_salary
                FROM employee2 
                GROUP BY department
                """;
        try(Connection conn = DBconn.getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getString("department") + " " + rs.getInt("employee_count") + " " + rs.getDouble("average_salary"));
            }

        }catch(SQLException e){
                System.out.println("Error getting employee analytics by department" + e.getMessage());
                e.printStackTrace();
        }
    }

    public void deleteEmployeeById(int id){
        String sql= """
                DELETE FROM employee2
                WHERE id = ?
                """;
        try(Connection conn = DBconn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Deleted employee with id: " + id);
                } else {
                    System.out.println("No employee found with id: " + id);
                }
        }catch( Exception e){
            System.out.println("Error occured during delition" + e.getMessage());
            e.printStackTrace();
        }
    }
}
