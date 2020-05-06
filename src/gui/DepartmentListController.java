package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	//dependencia departmentservice 
	//Para aclopamento fraco, criar um m�todo para inje��o de dependencia
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
	public void onBtNewAction(ActionEvent event) {
		Department obj = new Department();
		Stage parentStage = Utils.currentStage(event);
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	
	//m�todo de inje��o de depend�ncias
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// para manipular a tabela
		initializeNodes();
		
	}

	//m�todo tabela inicia o comportamento das colunas.
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//faz com que a table ocupe todo o Stage.
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	//responsavel por acessar os servi�os, carregar a lista e mostrar na tableView
	public void updateTableView() {
		
		/*
		 * caso o programador n�o lance o 
		 * m�todo de inje��o de depend�ncia
		 */
		if(service == null) {
			throw new IllegalStateException("Servi�o est� nulo");
		}
		
		//recupera o MOCK da lista na classe DepartmentService
		List<Department> list = service.findAll();
		
		//carrega a lista dentro do ObservableList
		obsList = FXCollections.observableArrayList(list);
		
		//carrega a lista na TableView
		tableViewDepartment.setItems(obsList);
		
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			//l�gica para abrir a tela de form
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//carrega os dados do objeto no formul�rio
			//inje�oes de dependencia
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Error Load View", e.getMessage(), AlertType.ERROR);
		}
	}
}
