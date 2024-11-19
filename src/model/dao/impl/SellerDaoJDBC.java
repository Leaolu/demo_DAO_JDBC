package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	@Override
	public void insert(Seller sell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller sell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

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
				Department dep = instatiateDepartment(rs);
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

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
