package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

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
	
}
