package application;




import java.util.List;

import model.Entities.Department;
import model.Entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		System.out.println("=== TEST 1: seller findById ===");
		//SellerDao object
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		//Seller object with the id 3 in the data base
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		list.stream().forEach(System.out::println);
	}

}
