package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.Entities.Department;
import model.dao.DepartmentDao;

public class DepartmentDaoJDBC implements DepartmentDao{
//All the functions down below follow the same logic as SellerJDBC	
	private Connection conn;
	public DepartmentDaoJDBC(Connection conn){
		this.conn = conn;
	}
	public DepartmentDaoJDBC() {
	}
	
	@Override
	public void insert(Department dep) {
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			//SQL code to insert into the department sheet 
			//the department with name that has a value of the placeholder for now
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			//set the value of the placeholder for the name of the department passed by the parameter
			st.setString(1,  dep.getName());
			
			int rowsAffected = st.executeUpdate();
			//if rows affected is positive, checks if the generated key is valid
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					//if true sets the id of the department passed as parameter to the new id of the data bank
					int id = rs.getInt(1);
					dep.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected Error! No rows affected!");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department dep) {
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			//SQL code that update the department data following the id 
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name=? "
					+ "WHERE Id = ?");
			//change the placeholders with the right data, the first with the name and the second with the id
			st.setString(1, dep.getName());
			st.setInt(2, dep.getId());
			
			st.executeUpdate();	
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			//SQL code that delete the department with the id that for now have the value of a placeholder
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ?");
			//set the placeholder to the value of the Id passed by the parameter
			st.setInt(1,  id);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				//if no rows are affected, it means that the Id does not exist in the data bank
				throw new DbException("Unexpected error! Id doesn't exist!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			//SQL code that select a department with a id that for now has the value of a placeHolder
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+ "WHERE id=?");
			//sets the placeholder value to the id passed by the parameter
			st.setInt(1,  id);
			rs = st.executeQuery();
			if(rs.next()) {
				//if true, creates a Department object with the data returned by rs and return this new Department
				Department dep = new Department();
				dep.setName(rs.getString("Name"));
				dep.setId(id);
				return dep;
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			//creates the list that will be returned in the end
			List<Department> list = new ArrayList<>();
			//SQL code that select all the Itens on department and sort it in alphabetical order 
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			while(rs.next()) {
				//if true, creates a Department object, includes it's data to the values stored in rs
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				//add the new Department to the list
				list.add(dep);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
