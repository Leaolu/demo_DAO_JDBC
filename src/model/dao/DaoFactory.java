package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	//Creates a SellerDaoJDBC without exposing the implementation
		public static SellerDao createSellerDao() {
			return new SellerDaoJDBC();
		}
}
