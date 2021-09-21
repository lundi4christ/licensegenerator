package com.licensemanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.licensemanager.bean.dataSourceDb;
import com.licensemanager.service.DatasourceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;

import com.licensemanager.config.StageManager;
import com.licensemanager.view.FxmlView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LicenseController implements Initializable{

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private DatasourceService datasourceService;

	@FXML
	private ComboBox<String> cbRole;

	@FXML
	private TableView<dataSourceDb> datasourceTable;

	@FXML
	private TableColumn<dataSourceDb, String> coljdbcdriver;

	@FXML
	private TableColumn<dataSourceDb, String> colhost;

	@FXML
	private TableColumn<dataSourceDb, Integer> colport;

	@FXML
	private TableColumn<dataSourceDb, String> coldatabasenm;

	@FXML
	private TableColumn<dataSourceDb, Boolean> coldelete;

	@FXML
	private Text datacount;

	@FXML
	private Text licensecount;

	@FXML
	private Text deleterec;

	ObservableList<dataSourceDb> databaseList = FXCollections.observableArrayList();
	private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");


	@FXML
	void buttonHandlerFormone(MouseEvent event) throws IOException {
		stageManager.switchScene(FxmlView.FORM1);
	}

	@FXML
	void buttonHandlerFormtwo(MouseEvent event) throws IOException {
		stageManager.switchScene(FxmlView.FORM2);
		/*Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/form2.fxml"));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root1));
		stage.setResizable(false);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();*/

	}

	@FXML
	void buttonHandlerFormlogin(MouseEvent event) throws IOException {
//		stageManager.switchScene(FxmlView.LOGIN);
		Parent root1 = FXMLLoader.load(getClass().getResource(""));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root1));
		stage.setResizable(false);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}

	@FXML
	public void exitMain(MouseEvent event) {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbRole.setItems(roles);
		loadTextCount();

		datasourceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadDatabaseDetail();
	}


	// TABLE

	private void loadDatabaseDetail(){
		databaseList.clear();
		databaseList.addAll(datasourceService.findAll());

		datasourceTable.setItems(databaseList);
	}

	private void setColumnProperties(){

		coljdbcdriver.setCellValueFactory(new PropertyValueFactory<>("jdbcdriver"));
		colhost.setCellValueFactory(new PropertyValueFactory<>("host"));
		colport.setCellValueFactory(new PropertyValueFactory<>("port"));
		coldatabasenm.setCellValueFactory(new PropertyValueFactory<>("databasenm"));
		//coldelete.setCellValueFactory((Callback<TableColumn.CellDataFeatures<dataSourceDb, Boolean>, ObservableValue<Boolean>>) deleterec);
		coldelete.setCellValueFactory(new PropertyValueFactory<>("Action"));
	}

	/*Callback<TableColumn.CellDataFeatures<dataSourceDb, Boolean>, ObservableValue<Boolean>> cellFactory =
			new Callback<Object, ObservableValue<Boolean>>() {

				@Override
				public ObservableValue<Boolean> call(Object param)				{
					final TableCell<dataSourceDb, Boolean> cell = new TableCell<dataSourceDb, Boolean>()
					{
						Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
						final Button btnEdit = new Button();

						@Override
						public void updateItem(Boolean check, boolean empty)
						{
							super.updateItem(check, empty);
							if(empty)
							{
								setGraphic(null);
								setText(null);
							}
							else{
								btnEdit.setOnAction(e ->{
									//dataSourceDb dataSourceDb = getTableView().getItems().get(getIndex());
										List<dataSourceDb> datadb = (List<dataSourceDb>) datasourceTable.getSelectionModel().getSelectedItem();

										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("");
										alert.setHeaderText(null);
										alert.setContentText("Are you sure you want to delete");
										Optional<ButtonType> action = alert.showAndWait();

										if(action.get() == ButtonType.OK)
											datasourceService.deleteInBatch(datadb);
																	//updatedataSourceDb(dataSourceDb);
								});

								btnEdit.setStyle("-fx-background-color: transparent;");
								ImageView iv = new ImageView();
								iv.setImage(imgEdit);
								iv.setPreserveRatio(true);
								iv.setSmooth(true);
								iv.setCache(true);
								btnEdit.setGraphic(iv);

								setGraphic(btnEdit);
								setAlignment(Pos.CENTER);
								setText(null);
							}
						}
					};
					return cell;
				}
			};*/

	private void delete(ActionEvent event){
		List<dataSourceDb> datadb = (List<dataSourceDb>) datasourceTable.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete");
		Optional<ButtonType> action = alert.showAndWait();

		if(action.get() == ButtonType.OK)
			datasourceService.deleteInBatch(datadb);
	}

	private void loadTextCount(){
		DriverManagerDataSource mydb = new DriverManagerDataSource();

		//mydb.setDriverClassName(getdriverclass);
		mydb.setUrl("jdbc:hsqldb:file:data/mydb");
		mydb.setUsername("sa");
		mydb.setPassword("sa");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mydb);
		SqlRowSet therowset = jdbcTemplate.queryForRowSet("SELECT count(*) FROM DATADBSOURCE");

		String sql = "SELECT COUNT(*) FROM DATADBSOURCE";
		int mycount = jdbcTemplate.queryForObject(sql, Integer.class);

		String sql2 = "SELECT COUNT(*) FROM LICENSEGENDB";
		int mycount2 = jdbcTemplate.queryForObject(sql2, Integer.class);

		System.out.println(mycount);
		System.out.println(mycount2);

		datacount.setText(String.valueOf(mycount));
		licensecount.setText(String.valueOf(mycount2));
	}
}
