package model.dao;

import java.util.List;

import model.Entities.Department;

public interface DepartmentDao {
	//Department interface with functions to interact with any Data Base
	//Insert a department into the data base
	void insert(Department dep);
	//update a department`s data
	void update(Department dep);
	//Delete a department using the id to locate it in the data base
	void deleteById(Integer id);
	//find the department by the id in the data base
	Department findById(Integer id);
	//return a list of all the departments in the data base
	List<Department> findAll();
}
