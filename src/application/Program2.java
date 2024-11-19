package application;

import java.util.List;
import java.util.Scanner;

import model.Entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("=== Test 1: department findById");
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n=== Test 2: department findAll");
		List<Department> list = departmentDao.findAll();
		list.stream().forEach(System.out::println);
		System.out.println("\n=== Test 3: department insert");
		dep = new Department(null, "Engineering");
		departmentDao.insert(dep);
		System.out.println("Insertion completed!");
		System.out.println("\n=== Test 4: department update");
		dep = departmentDao.findById(1);
		dep.setName("Violin");
		departmentDao.update(dep);
		System.out.println("Update completed!");
		System.out.println("\n=== Test 5: department delete");
		System.out.print("What is the Id of the department you want to delet? "); 
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Deletion complete");
		sc.close();
		
	}

}
