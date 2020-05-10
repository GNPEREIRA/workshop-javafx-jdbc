package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService sellerService;

	@FXML
	private Button btNew;

	@FXML
	private TableView<Seller> tableViewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableCollumnSellerId;

	@FXML
	private TableColumn<Seller, String> tableColumnSellerName;

	@FXML
	private TableColumn<Seller, String> tableColumnSellerEmail;

	@FXML
	private TableColumn<Seller, Date> tableColumnSellerBirthDate;

	@FXML
	private TableColumn<Seller, Double> tableColumnSellerBaseSalary;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtNew(ActionEvent event) {
		Seller obj = new Seller();
		Stage parentStage = Utils.currentStage(event);
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {
		this.sellerService = service;
	}

	// método que inicia o comportamento das colunas
	private void initializeNodes() {
		tableCollumnSellerId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnSellerName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnSellerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnSellerBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnSellerBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

		//formata a data
		Utils.formatTableColumnDate(tableColumnSellerBirthDate, "dd/MM/yyyy");
		
		//Formata o baseSalary com 2 casas decimais
		Utils.formatTableColumnDouble(tableColumnSellerBaseSalary, 2);
		// faz com que a TableView ocupe toda a tela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}

	// método responsavel por acessar os serviços, carregar a lista e mostrar na
	// tableView
	public void updateTableView() {
		if (sellerService == null) {
			throw new IllegalStateException("O serviço está nulo");
		}

		// recupera a lista de banco atraves do metodo da classe SellerService
		List<Seller> list = sellerService.findAll();

		// carrega a lista dentro da ObservableList
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);

		initEditButtons();
		initRemoveButtons();

	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		// lógica para abrir a tela de form
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		try {
			Pane pane = loader.load();

			// carrega os dados do objeto no formulário
			// injeçoes de dependencia

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Seller data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro de visualização", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}// fim initEditButtons

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}// fim initRemoveButtons

	private void removeEntity(Seller obj) {

		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certezaque deseja remover?");

		if (result.get() == ButtonType.OK) {
			if (sellerService == null) {
				throw new IllegalStateException("Serviço está nulo");
			}

			try {
				sellerService.remove(obj);
				updateTableView();

			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}// fim removeEntity
}
