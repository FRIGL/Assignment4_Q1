import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class DatabaseOperations {

    //UPDATE THESE IN ACCORDANCE TO YOUR OWN DATABASE!!!
    private final String url = "jdbc:postgresql://localhost:5432/DemoDB";
    private final String user = "postgres";
    private final String password = "password";

    //prints all students in the database
    public void getAllStudents()
    {   
        String SQL = "SELECT student FROM students student;";
        try(Connection connection = DriverManager.getConnection(url, user, password); Statement stmt = connection.createStatement()) 
        {
            ResultSet resultSet = stmt.executeQuery(SQL);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnsNumber = resultSetMetaData.getColumnCount();
            while (resultSet.next())//more or less just copyed this block: https://stackoverflow.com/questions/24229442/print-the-data-in-resultset-along-with-column-names#:~:text=ResultSet%20resultSet%20%3D%20statement,println(%22%22)%3B%0A%7D
            {
               for (int i = 1; i <= columnsNumber; i++) 
                {
                    if(i > 1){ System.out.print(",  "); }
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
                }
                System.out.println("");     
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    //adds a new student to the database
    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) 
    {
        String SQL = "INSERT INTO students(first_name,last_name,email,enrollment_date) VALUES(?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL))
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

    // Update student's email based on student_id
    public void updateStudentEmail(int student_id, String newEmail) 
    {
        String SQL = "UPDATE students SET email=? WHERE student_id=?";
        try(Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL))
        {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("User email updated!");
        } 
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    // Delete student based on student_id
    public void deleteStudent(int student_id)
    {
        String SQL = "DELETE FROM students WHERE student_id=?";
        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(SQL))
        {
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted!");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) 
    {
        DatabaseOperations databaseOperations = new DatabaseOperations();

        //print all students
        databaseOperations.getAllStudents();

        //add a student
        databaseOperations.addStudent("firstName", "lastName", "firstName.lastName@example.com", new Date(System.currentTimeMillis()));

        //update a students Email
        databaseOperations.updateStudentEmail(1, "new.email@example.com");

        //delete a student
        databaseOperations.deleteStudent(1);
    }
}