package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	// declaração de dependÊncia com injeção atraves da Factory
	private DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

	// métod FindALL MOCK de dados
	/*
	 * public List<Department> findAll(){
	 * 
	 * List<Department> list = new ArrayList<>(); list.add(new Department(1,
	 * "Books")); list.add(new Department(2, "Electronics")); list.add(new
	 * Department(3, "Workout"));
	 * 
	 * return list; }
	 */

	public List<Department> findAll() {

		return departmentDao.findAll();
	}
	
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {
			departmentDao.insert(obj);
		}else {
			departmentDao.update(obj);
		}
	}
	
	public void remove(Department obj) {
		departmentDao.deleteById(obj.getId());
	}

}
