package application;

import java.util.List;

import model.Entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class Program2 {

	public static void main(String[] args) {
		System.out.println("=== Test 1: department findById");
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n=== Test 2: department findAll");
		List<Department> list = departmentDao.findAll();
		list.stream().forEach(System.out::println);
	}

}
