package com.licensemanager.controller;

import com.licensemanager.bean.dataSourceDb;
import com.licensemanager.bean.licenseGeneratorDb;
import com.licensemanager.config.StageManager;
import com.licensemanager.service.DatasourceService;
import com.licensemanager.service.LicenseGenService;
import com.licensemanager.view.FxmlView;
import com.licensemanager.view.dataList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;


@Controller
public class DatasourceController implements Initializable{


	@FXML
	private ComboBox jdbcdriver;

	@FXML
	private TextField jdbcdrivertext;

	@FXML
	private TextField host;
	@FXML
	private TextField port;
	@FXML
	private TextField databasenm;
	@FXML
	private TextField username;
	/*@FXML
	private TextField password;*/
	@FXML
	private PasswordField password;
	@FXML
	private TextField datasourcenm;
	@FXML
	private TextField dataurl;
	@FXML
	private Label label;
	@FXML
	private Label label1;

	@FXML
	private ComboBox sourcedb;

	@FXML
	private Group deletedb;

	@FXML
	private Group editdb;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

/*	@Autowired
	private PasswordEncoder passwordEncoder;*/

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private DatasourceService datasourceService;

	@Autowired
	private LicenseGenService licenseGenService;

	ObservableList<String> jdbclist = FXCollections.observableArrayList("MS SQL Server Driver", "Oracle Driver", "MYSQL Driver");


	@FXML
	private void exit(ActionEvent event) {

		Platform.exit();
    }

    
    @FXML
    private void jdbcConfig(ActionEvent event){

			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			//dataSource.setDriverClassName((String) jdbcdriver.getSelectionModel().getSelectedItem());
			dataSource.setDriverClassName(jdbcdrivertext.getText());
			System.out.println(jdbcdrivertext.getText());

			if(jdbcdriver.getSelectionModel().getSelectedItem().equals("SQL Server Driver")) {
				dataSource.setUrl("jdbc:sqlserver://" + host.getText() + ":" + Integer.parseInt(port.getText()) + ";" + "database=" + databasenm.getText());
				System.out.println("jdbc:sqlserver://" + host.getText() + ":" + port.getText() + ";" + "database=" + databasenm.getText());
			}else if(jdbcdriver.getSelectionModel().getSelectedItem().equals("Oracle Sql Driver")){
				dataSource.setUrl("jdbc:oracle:thin:@" + host.getText() + ":" + Integer.parseInt(port.getText()) + ":" + databasenm.getText());
				System.out.println("jdbc:oracle:thin:@" + host.getText() + ":" + Integer.parseInt(port.getText()) + ":" + databasenm.getText());
			}else if(jdbcdriver.getSelectionModel().getSelectedItem().equals("MYSQL Driver")){
				dataSource.setUrl("jdbc:mysql://" + host.getText() + ":" + Integer.parseInt(port.getText()) + "/" + databasenm.getText());
				System.out.println("jdbc:mysql://" + host.getText() + ":" + Integer.parseInt(port.getText()) + "/" + databasenm.getText());
			}

			dataSource.setUsername(username.getText());
			System.out.println(username.getText());
			dataSource.setPassword(password.getText());
			System.out.println(password.getText());

			JdbcTemplate template = new JdbcTemplate(dataSource);
			//SqlRowSet sqlRowSet = template.queryForRowSet("SELECT * FROM ACCOUNT;");


		if (template != null) {
			label.setText("Saved Successfully");

			//Alert
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Saved Successfully");
			alert.setTitle("");
			alert.setHeaderText("");
			alert.showAndWait();
			//

				dataSourceDb datasource = new dataSourceDb();
				datasource.setJdbcdriver(getJdbcdriver());
				datasource.setHost(getHost());
				datasource.setPort(getPort());
				datasource.setDatabasenm(getDatabasenm());
				datasource.setUsername(getUsername());
				datasource.setDataurl(getDataurl());

			/*String originalInput = getPassword();
			String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());*/

				datasource.setPassword(getPassword());

				//datasource.setPassword(getPassword());
				datasource.setDatasourcenm(getDatasourcenm());

				if (datasourceService == null) {
					System.out.println("it is null");
				} else {
					System.out.println("not null");
				}
			datasourceService.save(datasource);


		} else {
			dataSourceDb datasource = datasourceService.findByDatabasenm(databasenm.getText());
			datasource.setJdbcdriver(getJdbcdriver());
			datasource.setHost(getHost());
			datasource.setPort(getPort());
			datasource.setDatabasenm(getDatabasenm());
			datasource.setUsername(getUsername());
			datasource.setPassword(getPassword());
			datasource.setDatasourcenm(getDatasourcenm());
			datasource.setDataurl(getDataurl());

			dataSourceDb datas = datasourceService.update(datasource);
			System.out.println(datas);

		}

		DriverManagerDataSource datas = new DriverManagerDataSource();

	}

	@FXML
	private void myupdate(MouseEvent event){
		dataSourceDb thedata = datasourceService.findByDatasourcenm(datasourcenm.getText());

		datasourceService.update(thedata);
	}

	@FXML
	private void updatedbsource(MouseEvent event){

		dataSourceDb deletedb = new dataSourceDb();
		deletedb.setDatasourcenm((String) sourcedb.getSelectionModel().getSelectedItem());
		System.out.println(deletedb.getDatasourcenm() + deletedb.getId());

		updateaction(deletedb);
	}

	@FXML
	private dataSourceDb updateaction(dataSourceDb datasourceup){

		String jdbcdrive = jdbcdrivertext.getText();
		String hosts = host.getText();
		String ports = port.getText();
		String dbname = databasenm.getText();
		String sourcenm = datasourcenm.getText();
		String usernm = username.getText();
		String pass = password.getText();
/*		//String originalInput = getPassword();
		String encodedString = Base64.getEncoder().encodeToString(pass.getBytes());*/
		String url = dataurl.getText();

		dataSourceDb updata = datasourceService.findByDatasourcenm(datasourceup.getDatasourcenm());
		int getid = Math.toIntExact(updata.getId());
		System.out.println("===============" + getid);
		int updatesourcedb = jdbcTemplate.update("update datadbsource set datadbsource.jdbcdriver='" + jdbcdrive + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatesourcedb);
		int updatehost = jdbcTemplate.update("update datadbsource set datadbsource.host='" + hosts + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatehost);
		int updateport = jdbcTemplate.update("update datadbsource set datadbsource.port='" + ports + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updateport);
		int updatedbnm = jdbcTemplate.update("update datadbsource set datadbsource.databasenm='" + dbname + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatedbnm);
		/*int updatesourcenm = jdbcTemplate.update("update datadbsource set datadbsource.datasourcenm='" + sourcenm + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatesourcenm);*/
		int updatesusernm = jdbcTemplate.update("update datadbsource set datadbsource.username='" + usernm + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatesusernm);
		int updatepass = jdbcTemplate.update("update datadbsource set datadbsource.password='" + pass + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updatepass);
		int updateurl = jdbcTemplate.update("update datadbsource set datadbsource.dataurl='" + url + "' where datadbsource.id= " + getid + " ");
		System.out.println("----------" + updateurl);

		return updata;
	}

	@FXML
	private void deletedbsource(MouseEvent event){

			dataSourceDb deletedb = new dataSourceDb();
			deletedb.setDatasourcenm((String) sourcedb.getSelectionModel().getSelectedItem());
			System.out.println(deletedb.getDatasourcenm() + deletedb.getId());

		deleteaction(deletedb);
	}

	@FXML
	private dataSourceDb deleteaction(dataSourceDb datasourcedel){
		dataSourceDb thesourcedel = datasourceService.findByDatasourcenm(datasourcedel.getDatasourcenm());
		int getid = Math.toIntExact(thesourcedel.getId());
		System.out.println("===============" + getid);
		int deletesourcedbkey = jdbcTemplate.update("delete from datadbsource where datadbsource.id= " + getid + " ");
		System.out.println("----------" + deletesourcedbkey);

		licenseGeneratorDb licensedata = licenseGenService.findByDbsourcenm(datasourcedel.getDatasourcenm());
		int getlicenseid = Math.toIntExact(licensedata.getId());
		System.out.println("===============" + getlicenseid);
		int deletelicense = jdbcTemplate.update("delete from licensegendb where licensegendb.id= " + getlicenseid + " ");
		System.out.println("----------" + deletelicense);
		return thesourcedel;
	}
    
   	@FXML
	private void clearField(ActionEvent event) {
		jdbcdriver.setValue("");
		host.clear();
		port.clear();
		databasenm.clear();
		username.clear();
		password.clear();
	}

	@FXML
	public void exitFormone(ActionEvent event) {

		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
	}

	public String getJdbcdriver() {
		return jdbcdrivertext.getText();
	}

	public String getHost() {
		return host.getText();
	}

	public int getPort() {
		return Integer.parseInt(port.getText());
	}

	public String getDatabasenm() {
		return databasenm.getText();
	}

	public String getDatasourcenm(){
		return datasourcenm.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	public String getPassword() {
		return password.getText();
	}
	public String getPasswordkey(){
		return password.getText();
	}
	public String getDataurl(){
		return dataurl.getText();
	}

	public void backtoDashboard(MouseEvent event) throws IOException {
			stageManager.switchScene(FxmlView.DASHBOARD);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

			jdbcdriver.setValue("");
			jdbcdriver.setItems(jdbclist);

		jdbcdriver.setOnAction((event) -> {
			if(jdbcdriver.getSelectionModel().getSelectedItem().equals("MS SQL Server Driver")){
				//jdbcdriver.setValue(dataList.SQL_SERVER_DRIVER.getDescription());
				jdbcdrivertext.setText(dataList.SQL_SERVER_DRIVER.getDescription());
			} else if(jdbcdriver.getSelectionModel().getSelectedItem().equals("Oracle Driver")) {
				jdbcdrivertext.setText(dataList.ORACLE_SQL_DRIVER.getDescription());
			} else if(jdbcdriver.getSelectionModel().getSelectedItem().equals("MYSQL Driver")){
				jdbcdrivertext.setText(dataList.MYSQL_DRIVER.getDescription());
			}
		});


		DriverManagerDataSource mydb = new DriverManagerDataSource();

		//mydb.setDriverClassName(getdriverclass);
		mydb.setUrl("jdbc:hsqldb:file:data/mydb");
		mydb.setUsername("sa");
		mydb.setPassword("sa");

		jdbcTemplate = new JdbcTemplate(mydb);
		SqlRowSet therowset = jdbcTemplate.queryForRowSet("SELECT datasourcenm  FROM DATADBSOURCE");

		while(therowset.next()){
			sourcedb.getItems().addAll(therowset.getString("datasourcenm"));
		}


		sourcedb.setOnAction((event) ->{
			dataSourceDb mydatasource = new dataSourceDb();
			mydatasource.setDatasourcenm((String) sourcedb.getSelectionModel().getSelectedItem());

			sourcedbdetails(mydatasource);

		});

	}

	private dataSourceDb sourcedbdetails(dataSourceDb datasource){
		dataSourceDb thedata = datasourceService.findByDatasourcenm(datasource.getDatasourcenm());

			if (jdbcdrivertext.getText().equalsIgnoreCase("com.microsoft.sqlserver.jdbc.SQLServerDriver")){
				jdbcdriver.setValue("MS SQL Server Driver");
			} else if (jdbcdrivertext.getText().equalsIgnoreCase("oracle.jdbc.OracleDriver")){
				jdbcdriver.setValue("Oracle Driver");
			} else if (jdbcdrivertext.getText().equalsIgnoreCase("com.mysql.jdbc.Driver")){
				jdbcdriver.setValue("MYSQL Driver");
			}
			host.setText(thedata.getHost());
			port.setText(Integer.toString(thedata.getPort()));
			databasenm.setText(thedata.getDatabasenm());
			datasourcenm.setText(thedata.getDatasourcenm());
			username.setText(thedata.getUsername());
			password.setText(thedata.getPassword());
			dataurl.setText(thedata.getDataurl());
			jdbcdrivertext.setText(thedata.getJdbcdriver());

		return thedata;
	}


	public String getJdbc() {
		return getStringFromResourceBundle("sql.server.jdbcdriver");
	}

	String getStringFromResourceBundle(String key){
		return ResourceBundle.getBundle("Bundle").getString(key);
	}


}


