package model.dao;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	//Creates a SellerDaoJDBC without exposing its implementation
		public static SellerDao createSellerDao() {
			return new SellerDaoJDBC();
		}
		//creates a DepartmentDaoJDBC without exposing its implementation
		public static DepartmentDao createDepartmentDao() {
			return new DepartmentDaoJDBC();
		}
}
