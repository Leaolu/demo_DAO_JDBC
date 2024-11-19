package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.Entities.Department;
import model.Entities.Seller;
import model.dao.SellerDao;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	public SellerDaoJDBC() {
	}
	//get the data of the ResultSet and Department and put into a Seller object
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}
	//get the data of the ResultSet and put into a Department object
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	@Override
	public void insert(Seller sell) {
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			//Execute a SQL code to Insert the data of a new seller
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+"VALUES "
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			//set all the ? to the data we want to store
			st.setString(1, sell.getName());
			st.setString(2,  sell.getEmail());
			st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
			st.setDouble(4, sell.getBaseSalary());
			st.setInt(5,  sell.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			//checks if the rows affected are positive
			if(rowsAffected > 0) {
				//if true it checks if rs.next() is true
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					//if yes it sets the id of the seller manually
					int id = rs.getInt(1);
					sell.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected");
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
	public void update(Seller sell) {
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			//SQL code that orders to update the seller with the id given by the 6th PlaceHolder
			//with the data provided in the 5 first PlaceHolders
			st = conn.prepareStatement(
					"UPDATE seller "
					+"SET Name=?, Email=?, BirthDate=?, BaseSalary=?,DepartmentId=? "
					+"WHERE id = ?");
			//set each PlaceHolder to the data passed by the parameter
			st.setString(1, sell.getName());
			st.setString(2,  sell.getEmail());
			st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
			st.setDouble(4, sell.getBaseSalary());
			st.setInt(5,  sell.getDepartment().getId());
			st.setInt(6, sell.getId());
			
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
			st = conn.prepareStatement(
					"DELETE FROM seller "
					+"WHERE Id=?");
			st.setInt(1, id);
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
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//conn connects with the data base
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ?");
			//substitutes the first ? with the id
			st.setInt(1, id);
			rs = st.executeQuery();
			//if rs.next() doesn`t return false
			if(rs.next()) {
				//Return the data of the ResultSet in a Seller object
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				return seller;
				
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			//close statement and resultset
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}
	//Searches the id of the department and return a list of sellers that are in the same department
	public List<Seller> findByDepartment(Department dep){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			//Execute SQL code on the data base at mySQL 
			//get the department and seller sheets together
			//and find the sellers by the id of the department that they are in
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE DepartmentId = ? " 
					+"ORDER BY Name");
			//set the first ? to the Department's id
			st.setInt(1,  dep.getId());
			rs = st.executeQuery();
			//Creates the Seller List and the Map with Integer as key and Department as the data
			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			//rs.next() will return false once there is no more data with this id
			while(rs.next() != false) {
				//Using HashMap to store the department
				//avoid implementing multiple objects that are the same
				dep = map.get(rs.getInt("DepartmentId"));
				//if there is no map with such key, create one and put into the Map
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				//create the Seller object and put it into the List
				Seller seller = instantiateSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//Create a list of sellers
			List<Seller> sellers = new ArrayList<>();
			conn = DB.getConnection();
			//Execute a SQL code in the mySQL to get all the sellers
			//combine with all the departments and put the sellers sorted by name
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name");
			rs = st.executeQuery();
			//same logic using Map to avoid creating Departments that are the same
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next() != false) {
				//dep gets the Department with the key DepartmentId
				Department dep = map.get(rs.getInt("DepartmentId"));
				//if there is no Department with such key, create one and put into the Map
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				//create the Seller object and put into the List
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

}
