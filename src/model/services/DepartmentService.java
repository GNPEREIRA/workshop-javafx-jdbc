package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	
	
	//métod FindALL MOCK de dados
	public List<Department> findAll(){
		
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, "Books"));
		list.add(new Department(2, "Electronics"));
		list.add(new Department(3, "Workout"));
		
		return list;
	}
	
}
