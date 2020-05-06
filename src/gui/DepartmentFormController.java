package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	//dependencia para departamento
	private Department entity;
	
	@FXML
	private TextField textId;
	
	@FXML
	private TextField textName;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	//instância do departamento
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onbtSaveAction() {
		System.out.println("SaveAction click");
	}
	
	@FXML
	public void onbtCancelAction() {
		System.out.println("cancelAction click");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {	
	intializeNodes();
		
	}
	
	//restrições dos campos editáveis
	private void intializeNodes() {
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 70);
	}
	
	//popula as caixas de texto do form
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entidade nula.");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
	}
	
}
