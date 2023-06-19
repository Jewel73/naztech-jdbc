package com.naztech.naztechjdbc.test;

import java.util.List;

public class Utils {

	public static void PrintList(List<Student> students) {
			
		if (students.size() != 0) {
        	for (Student student : students) {
        		System.out.println("Student Details:");
                System.out.println("ID: " + student.getId());
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());
                System.out.println("Grade: " + student.getGrade());
			}
            
        }else {
        	System.out.print("No Data found, Sorry");
        }
	}
}
