package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	//dependencia departmentservice 
	//Para aclopamento fraco, criar um método para injeção de dependencia
	private DepartmentService service;
	
	@FXML
	private Button btNew;
	
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction() {
		//SELECT * FROM department;
	}
	
	
	//método de injeção de dependências
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// para manipular a tabela
		initializeNodes();
		
	}

	//método tabela inicia o comportamento das colunas.
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//faz com que a table ocupe todo o Stage.
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	//responsavel por acessar os serviços, carregar a lista e mostrar na tableView
	public void updateTableView() {
		
		/*
		 * caso o programador não lance o 
		 * método de injeção de dependência
		 */
		if(service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		
		//recupera o MOCK da lista na classe DepartmentService
		List<Department> list = service.findAll();
		
		//carrega a lista dentro do ObservableList
		obsList = FXCollections.observableArrayList(list);
		
		//carrega a lista na TableView
		tableViewDepartment.setItems(obsList);
		
	}
}
