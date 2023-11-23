import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class DatabaseOperations {

    private final String url = "jdbc:postgresql://localhost:5432/DemoDB";
    private final String user = "postgres";
    private final String password = "password";

    // Add a user
    public void addUser(String name, String email) {
        String SQL = "INSERT INTO users(name,email) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Add a student
    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        String SQL = "INSERT INTO students(first_name,last_name,email,enrollment_date) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL))
            {
                pstmt.setString(1, first_name);
                pstmt.setString(2, last_name);
                pstmt.setString(3, email);
                pstmt.setDate(4, enrollment_date);
                pstmt.executeUpdate();
                System.out.println("Student added successfully!");

            }
        catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
    }

    // Update user's email based on name
    public void modifyUserEmail(String name, String newEmail) {
        String SQL = "UPDATE users SET email=? WHERE name=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newEmail);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("User email updated!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete user based on name
    public void deleteUser(String name) {
        String SQL = "DELETE FROM users WHERE name=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("User deleted!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete student based on id
    public void deleteStudent(int studen_id) {
        String SQL = "DELETE FROM students WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(SQL))
            {

                pstmt.setString(1, name);
                pstmt.executeUpdate();
                System.out.println("User deleted!");
            }
        catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
    }

    public static void main(String[] args) {
      DatabaseOperations dbOps = new DatabaseOperations();
      Scanner scanner = new Scanner(System.in);
      
    dbOps.addStudent("John", "Doe", "john@example.com", new Date(10));

      // Add a user
      System.out.println("Would you like to add a user? (yes/no)");
      if (scanner.nextLine().equalsIgnoreCase("yes")) {
          dbOps.addUser("John Doe", "john@example.com");
      }
  
      // Modify user's email
      System.out.println("Would you like to modify a user's email? (yes/no)");
      if (scanner.nextLine().equalsIgnoreCase("yes")) {
          dbOps.modifyUserEmail("John Doe", "john.doe@example.com");
      }
  
      // Delete user
      System.out.println("Would you like to delete a user? (yes/no)");
      if (scanner.nextLine().equalsIgnoreCase("yes")) {
          dbOps.deleteUser("John Doe");
      }
      scanner.close();
  }
}