package application;




import model.Entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		
		//SellerDao object
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		//Seller object with the id 3 in the data base
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
	}

}
