package dao;

import model.Student;
import util.DBConnection;
import java.sql.*;

public class StudentDAO {

    public void addStudent(Student s) {
        String sql = "INSERT INTO students (first_name, last_name, age, email) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setInt(3, s.getAge());
            ps.setString(4, s.getEmail());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet viewStudents() {
        String sql = "SELECT * FROM students";
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet searchStudent(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
