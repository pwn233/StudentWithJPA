/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentwithjpa;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utilities.DatabaseDriver;
import utilities.DatabaseHandler;

/**
 *
 * @author pwn233
 */
public class StudentWithJPA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        String driver = "org.apache.derby.jdbc.ClientDriver";
        String url = "jdbc:derby://localhost:1527/student";
        String user = "stu";
        String passwd = "stu";
        DatabaseDriver dbDriver = new DatabaseDriver(driver, url, user, passwd);
        DatabaseHandler dbHandler = new DatabaseHandler(dbDriver);
        
        Student stud1 = new Student(1, "mr.a", 2.19);
        Student stud2 = new Student(2, "mr.b", 3.52);
        Student stud3 = new Student(3, "mr.c", 2.33);
        
        persist(stud1);
        persist(stud2);
        persist(stud3);
        
        /*
        //updapte database table
        stud1 = new Student(1, "mr.a", 0.25);
        StudentTable.updateStudent(dbHandler, stud1);
        */
        
        showTable(dbHandler);
        /*
        //reset database table
        StudentTable.removeStudent(dbHandler, stud1);
        StudentTable.removeStudent(dbHandler, stud2);
        StudentTable.removeStudent(dbHandler, stud3);
        */
    }
    public static void showTable(DatabaseHandler dbHandler) throws SQLException{
        for(Student stud: StudentTable.findALLStudent(dbHandler)) {
            System.out.print(stud.getId()+ " ");
            System.out.print(stud.getName()+ " ");
            System.out.println(stud.getGpa()+ " ");
        }
    }

    public static void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentWithJPAPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}
