/*
 * Classe de serviço responsável pelos métodos do CRUD
 */

package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao sellerDao = DaoFactory.createSellerDao();
	
	//método
	public List<Seller> findAll(){
		return sellerDao.findAll();
	}
	
	public void saveOrUpdate(Seller obj) {
		
		if(obj.getId() == null) {
			sellerDao.insert(obj);
		}else {
			sellerDao.update(obj);
		}
	}
	
	public void remove(Seller obj) {
		sellerDao.deleteById(obj.getId());
	}

}
