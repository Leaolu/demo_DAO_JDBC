package model.dao;

import java.util.List;

import model.Entities.Seller;

public interface SellerDao {
	//Seller Interface to interact with any data base
	//Insert a seller into the data base
	void insert(Seller sell);
	//update a seller`s data
	void update(Seller sell);
	//Delete a seller using the id to locate it in the data base
	void deleteById(Integer id);
	//find the seller by the id in the data base
	Seller findById(Integer id);
	//return a list of all the sellers in the data base
	List<Seller> findAll();
}
