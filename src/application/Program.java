package application;




import java.util.Date;

import model.Entities.Department;
import model.Entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		//Department object 
		Department dep = new Department(1, "Library");
		System.out.println(dep);
		//Seller object
		Seller seller = new Seller(21, "Max", "max@gmail.com", new Date(), 3500.0, dep);
		System.out.println(seller);
		//SellerDao object
		SellerDao sellerDao = DaoFactory.createSellerDao();
	}

}
