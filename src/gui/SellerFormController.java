package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entity;
	private SellerService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private TextField textId;

	@FXML
	private TextField textName;

	@FXML
	private TextField textEmail;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private TextField textBaseSalary;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorEmail;

	@FXML
	private Label labelErrorBirthDate;

	@FXML
	private Label labelErrorBaseSalary;

	// instancia o Seller
	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	// instancia do service
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	// método para inscrição na lista de listeners
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Injeção nula");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço nulo");
		}

		try {

			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangerListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Erro ao salvar no banco", null, e.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyDataChangerListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private Seller getFormData() {
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(textId.getText()));

		if (textName.getText() == null || textName.getText().trim().equals("")) {
			exception.addErrors("name", "Campo não pode estar vazio");
		}
		obj.setName(textName.getText());

		if (textEmail.getText() == null || textEmail.getText().trim().equals("")) {
			exception.addErrors("email", "Campo não pode estar vazio");
		}
		obj.setEmail(textEmail.getText());

		// obj.setBaseSalary(Utils.tryParseToDouble(textBaseSalary.getText()));

		if (textBaseSalary.getText() == null || textBaseSalary.getText().trim().equals("")) {
			exception.addErrors("baseSalary", "Campo não pode estar vazio");
		}
		// obj.setBaseSalary(textBaseSalary.getText());

		// flta data

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 70);
		Constraints.setTextFieldMaxLength(textEmail, 50);
		Constraints.setTextFieldDouble(textBaseSalary);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entidade nula");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
		textEmail.setText(entity.getEmail());
		
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		
		textBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}

		if (fields.contains("email")) {
			labelErrorEmail.setText(errors.get("email"));
		}

		if (fields.contains("baseSalary")) {
			labelErrorBaseSalary.setText(errors.get("baseSalary"));
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

}
