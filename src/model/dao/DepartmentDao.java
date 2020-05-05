package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	//inserir dados de departamento através do argumento obj
	void insert( Department obj);
	//update de dados de departamento através do argumento obj
	void update(Department obj);
	//delete  de dados de departamento através do argumento id
	void deleteById(Integer id);
	//retorna um departamento através do id
	Department findById(Integer id);
	//retorna todos os departamentos numa lista
	List<Department> findAll();
	
	
}
