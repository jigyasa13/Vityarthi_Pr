package com.sis;

import com.sis.dao.AttendanceDAO;
import com.sis.dao.ReportDAO;
import com.sis.dao.StudentDAO;
import com.sis.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        StudentDAO sdao = new StudentDAO();
        ReportDAO rdao = new ReportDAO();
        AttendanceDAO adao = new AttendanceDAO();

        System.out.println("Students:");
        List<Student> all = sdao.listAll();
        all.forEach(System.out::println);

        System.out.println();
        rdao.printGradeReport("20001", 1);
        System.out.println();
        rdao.printGPA("20001", 1);
        System.out.println();
        adao.printAttendanceReport("20001", 1, "2025-02-01", "2025-03-01");
    }
}
