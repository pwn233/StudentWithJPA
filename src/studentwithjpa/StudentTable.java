/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentwithjpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseHandler;

/**
 *
 * @author pwn233
 */
public class StudentTable {
    public static int updateStudent(DatabaseHandler dbHandler, Student stud) {
        String sql = "UPDATE STUDENT SET NAME=?, GPA=? WHERE ID=?";
        int rowUpdated;
        try {
            rowUpdated = dbHandler.update(sql, stud.getName(), stud.getGpa(), stud.getId());
        }
        catch (SQLException ex ) {
            rowUpdated = 0;
        }
        
        return rowUpdated;
    }
     public static int removeStudent(DatabaseHandler dbHandler, Student stud) {
         String sql ="DELETE FROM STUDENT WHERE ID=?";
         
         int rowDeleted;
         try {
            rowDeleted = dbHandler.update(sql, stud.getId());
         }
         catch (SQLException ex ) {
             rowDeleted = 0;
         }
        return rowDeleted;
    }
    public static int insertStudent(DatabaseHandler dbHandler, Student stud) {
         String sql = "INSERT INTO STUDENT (ID, NAME, SALARY)" + 
               " values (?,?,?)";
         
         int rowInserted;
         try {
             rowInserted = dbHandler.update(sql, stud.getId(), stud.getName(), stud.getGpa());
         }
         catch(SQLException ex ) {
             rowInserted = 0;
         }
         return rowInserted;
    } 
     public static Student findStudentByID(DatabaseHandler dbHandler, int id) throws SQLException {
        String sql = "SELECT * FROM STUDENT WHERE ID = ?";
        ResultSet rs;
        Student stud = null;
        rs = dbHandler.query(sql, id);
        if (rs.next()) {
           stud = new Student();
           stud.setId(rs.getInt("ID"));
           stud.setName(rs.getString("NAME"));
           stud.setGpa(rs.getDouble("GPA"));
       }
        return stud;
        
    }
    public static ArrayList<Student> findStudentByName(DatabaseHandler dbHandler, String name) throws SQLException {
        String sql = "SELECT * FROM STUDENT WHERE NAME = ?";
        ResultSet rs;
        ArrayList<Student> studList = null;
        rs = dbHandler.query(sql, name);
        studList = extractStudent(rs);
        return studList;
        
    } 
    public static ArrayList<Student> findALLStudent(DatabaseHandler dbHandler) throws SQLException {
        String sql = "SELECT * FROM STUDENT ORDER BY ID";
        ResultSet rs; 
        rs = dbHandler.query(sql);
        ArrayList<Student> studList = extractStudent(rs);
        return studList;
    }
    private static ArrayList<Student> extractStudent(ResultSet rs) {
        ArrayList<Student> studList = new ArrayList<>();
        try {
            while(rs.next()) {
                Student student = new Student();
                try {
                    student.setId(rs.getInt("ID"));
                    student.setName(rs.getString("NAME"));
                    student.setGpa(rs.getDouble("GPA"));
                } catch (SQLException ex) {
                    Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                studList.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(studList.isEmpty()) {
            studList = null;
        }
        return studList;
    }
}
