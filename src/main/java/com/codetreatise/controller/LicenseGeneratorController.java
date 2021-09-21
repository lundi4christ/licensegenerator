package com.codetreatise.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.codetreatise.bean.dataSourceDb;
import com.codetreatise.bean.licenseGeneratorDb;
import com.codetreatise.service.LicenseGenService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.service.DatasourceService;
import com.codetreatise.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


@Controller
public class LicenseGeneratorController implements Initializable{

/*	@Value("${spring.datasource.driver-class-name}")
	String getdriverclass;

	@Value("${spring.datasource.url}")
	String geturl;

	@Value("${spring.datasource.username}")
	String getuser;

	@Value("${spring.datasource.password}")
	String getpass;*/

	@Autowired
	private DatasourceService datasourceService;

	@Autowired
	private LicenseGenService licenseGenService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private StandardPBEStringEncryptor strEncrypt;

	@FXML
	public ComboBox status;

	@FXML
	public ComboBox productName;

	@FXML
	public ComboBox opsystem;

	@FXML
	public ComboBox env;

	@FXML
	public ComboBox deploy;

	@FXML
	public ComboBox datasource;

	@FXML
	public TextField version;

	@FXML
	public DatePicker effectivedate;

	@FXML
	public TextField durationdays;

	@FXML
	public TextField hostname;

	@FXML
	public TextField hostmac;

	@FXML
	public TextField remdays;

	@FXML
	public TextField gracedays;

	@FXML
	public Label conlabel;

	ObservableList<String> productlist = FXCollections.observableArrayList("Trade-X", "Kachasi", "Optimus");
	//ObservableList<String> datasourcetlist = FXCollections.observableArrayList("LICENSE", "TRADEX");
	ObservableList<String> opsystemlist = FXCollections.observableArrayList("WINDOWS", "AIX", "LINUX");
	ObservableList<String> envlist = FXCollections.observableArrayList("DEV", "UAT", "LIV");
	ObservableList<String> deploylist = FXCollections.observableArrayList("SINGLE", "CLUSTER");
	ObservableList<String> statuslist = FXCollections.observableArrayList("NEW", "EXTEND", "RENEW");


	@FXML
	public void generate(ActionEvent event) throws SQLException {
		dataSourceDb datas = new dataSourceDb();
		datas.setDatasourcenm((String) datasource.getSelectionModel().getSelectedItem());

		getData(datas);


	}

	@FXML
	public void exitFormtwo(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

	public void backtoDashboard(MouseEvent event) throws IOException {
		stageManager.switchScene(FxmlView.DASHBOARD);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		productName.setValue("Trade-X");
		productName.setItems(productlist);
		//datasource.setItems(datasourcetlist);
		status.setValue("NEW");
		status.setItems(statuslist);
		opsystem.setValue("LINUX");
		opsystem.setItems(opsystemlist);
		env.setValue("UAT");
		env.setItems(envlist);
		deploy.setValue("SINGLE");
		deploy.setItems(deploylist);

		DriverManagerDataSource mydb = new DriverManagerDataSource();

		//mydb.setDriverClassName(getdriverclass);
		mydb.setUrl("jdbc:hsqldb:file:data/mydb");
		mydb.setUsername("sa");
		mydb.setPassword("sa");

		jdbcTemplate = new JdbcTemplate(mydb);
		SqlRowSet therowset = jdbcTemplate.queryForRowSet("SELECT datasourcenm  FROM DATADBSOURCE");

		while(therowset.next()){
			datasource.getItems().addAll(therowset.getString("datasourcenm"));
		}

	// to repopulate the fields base on the database name selected
		datasource.setOnAction((event) -> {
			licenseGeneratorDb getdbnm = new licenseGeneratorDb();
			getdbnm.setDbsourcenm((String) datasource.getSelectionModel().getSelectedItem());
			System.out.println(getdbnm.getDbsourcenm());
			showkeydetails(getdbnm);


		});

	}

	private licenseGeneratorDb showkeydetails(licenseGeneratorDb licensedb){
		licenseGeneratorDb thedata = licenseGenService.findByDbsourcenm(licensedb.getDbsourcenm());
		System.out.println("gggggggggggggggggggggg " + thedata);
		status.setValue(thedata.getStatus());
		deploy.setValue(thedata.getDeploy());
		durationdays.setText(Integer.toString(thedata.getDurationdays()));
		effectivedate.setValue(thedata.getEffectivedate());
		env.setValue(thedata.getEnv());
		gracedays.setText(Integer.toString(thedata.getGracedays()));
		hostmac.setText(thedata.getHostmac());
		hostname.setText(thedata.getHostname());
		opsystem.setValue(thedata.getOpsystem());
		productName.setValue(thedata.getProductname());
		remdays.setText(Integer.toString(thedata.getRemdays()));
		version.setText(thedata.getVersion());


		return thedata;
	}


	public dataSourceDb getData(dataSourceDb datasources) throws SQLException {

		//dataSourceDb thedata = datasourceService.findByDatabasenm(datasources.getDatabasenm());

		dataSourceDb thedata = datasourceService.findByDatasourcenm(datasources.getDatasourcenm());
		System.out.println("gggggggggggggggggggggg " + thedata);

		DriverManagerDataSource mydb = new DriverManagerDataSource();

		String dbusername = thedata.getUsername();

		mydb.setDriverClassName(thedata.getJdbcdriver());
		mydb.setUrl("jdbc:sqlserver://" + thedata.getHost() + ";" + "database=" + thedata.getDatabasenm());
		mydb.setUsername(thedata.getUsername());

		String encrp = thedata.getPassword();
		System.out.println("encrypted " + encrp);

		byte[] result = Base64.getDecoder().decode(thedata.getPassword());
		String decodedString = new String(result);
		System.out.println("oooooooooooooo " + decodedString);

		mydb.setPassword(decodedString);


		try {
			mydb.getConnection(thedata.getUsername(),decodedString);
			if(mydb != null){
				conlabel.setText("Connected Successfully");
			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			if(e != null){
				conlabel.setText("Not Connected");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("SQL Error");
				alert.setContentText(e.getMessage());
				alert.show();
			}

		}

		jdbcTemplate = new JdbcTemplate(mydb);

		List<Map<String, Object>> getList = jdbcTemplate.queryForList("SELECT databasenm FROM DATADBSOURCE");

		for(Map m : getList) {
			dataSourceDb db = new dataSourceDb();
			db.setDatabasenm((String) m.get("account_nm"));

			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" + m);

		}

		return thedata;
	}

	@FXML
	private void clearField(ActionEvent event){
		conlabel.setText("");
		productName.setValue("");
		datasource.setValue("");
		status.setValue("");
		opsystem.setValue("");
		env.setValue("");
		deploy.setValue("");
		version.clear();
		durationdays.clear();
		hostname.clear();
		hostmac.clear();
		remdays.clear();
		gracedays.clear();
		effectivedate.setValue(LocalDate.parse(""));
	}




	public dataSourceDb queryLicenseGen(dataSourceDb datasources) throws SQLException {

		dataSourceDb thedata = datasourceService.findByDatasourcenm(datasources.getDatasourcenm());
		String myusername = thedata.getUsername();
		DriverManagerDataSource mydb = new DriverManagerDataSource();

		mydb.setDriverClassName(thedata.getJdbcdriver());
		mydb.setUrl("jdbc:sqlserver://" + thedata.getHost() + ";" + "database=" + thedata.getDatabasenm());
		mydb.setUsername(thedata.getUsername());

		byte[] result = Base64.getDecoder().decode(thedata.getPassword());
		String decodedString = new String(result);
		System.out.println("oooooooooooooo " + decodedString);

		mydb.setPassword(decodedString);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mydb);

	if(status.getSelectionModel().getSelectedItem().equals("NEW")) {

			String genkeys = getProdName()+"¬"+getVer()+"¬"+getOpsystem()+"¬"+getEnv()+"¬"+getEffectiveDate()+"¬"+getDurationdays()+"¬"+getRemdays()+"¬"+getGracedays()+"¬"+getHostname();
			String licensekey = passwordEncoder.encode(genkeys);

			strEncrypt.setPassword(licensekey);
			String licenseexpdate = strEncrypt.encrypt(getdate());
			String licenseremdays = strEncrypt.encrypt(getRem());
			String licensegracedays = strEncrypt.encrypt(getGrace());

			////////////////////////////////////////////////////////////////////////////////////////////
			System.out.println(genkeys);
			System.out.println(licensekey);
			System.out.println("<<<<<<<<<<< License Expiry Date >>>>>>> "+ getExpirydate());
			System.out.println("<<<<<<<<<<< License Expiry Date Encrypt>>>>>>> "+ licenseexpdate);
			System.out.println("<<<<<< License Remaining days " + getRemdays());
			System.out.println("<<<<<<<<<<< License Remaining Days Encrypt>>>>>>> "+ licenseremdays);
			System.out.println("<<<<<<< License Grace days " + getGracedays());
			System.out.println("<<<<<<<<<<< License Grace period Encrypt>>>>>>> "+ licensegracedays);
			///////////////////////////////////////////////////////////////////////////////////////////


		// get the selected datasource and compare with the datasource from the licensegeneratorDb
		licenseGeneratorDb licensedata = licenseGenService.findByDbsourcenm(datasources.getDatasourcenm());
		System.out.println("=========================" + licensedata);

			if(licensedata == null){
				int updatelicenkey = jdbcTemplate.update("update " + myusername + ".control_parameter set control_parameter.value='" + licensekey + "' where control_parameter.code='CP127'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updatelicenkey);
				int updateexpirydt = jdbcTemplate.update("update " + myusername + ".control_parameter set control_parameter.value='" + licenseexpdate + "' where control_parameter.code='CP110'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateexpirydt);
				int updateremdays = jdbcTemplate.update("update " + myusername + ".control_parameter set control_parameter.value='" + licenseremdays + "' where control_parameter.code='CP118'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateremdays);
				int updategraceperiod = jdbcTemplate.update("update " + myusername + ".control_parameter set control_parameter.value='" + licensegracedays + "' where control_parameter.code='CP119'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updategraceperiod);

				licenseGeneratorDb licensegendb = new licenseGeneratorDb();
				licensegendb.setDeploy(getDeploy());
				licensegendb.setDurationdays(getDurationdays());
				licensegendb.setEffectivedate(getEffectiveDate());
				licensegendb.setEnv(getEnv());
				licensegendb.setExpirydate(getExpirydate());
				licensegendb.setGracedays(getGracedays());
				licensegendb.setHostmac(getHostmac());
				licensegendb.setHostname(getHostname());
				licensegendb.setOpsystem(getOpsystem());
				licensegendb.setProductname(getProdName());
				licensegendb.setRemdays(getRemdays());
				licensegendb.setStatus(getStatus());
				licensegendb.setVersion(getVer());
				licensegendb.setDbsourcenm(getDbsourcename());

				licenseGenService.save(licensegendb);

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setContentText("Saved Successfully");
				alert.show();
				System.out.println("saved successfully ");

			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Datasource cannot be updated with NEW status");
				alert.show();

			}

		}

		if(status.getSelectionModel().getSelectedItem().equals("EXTEND")){

			String getlicensekey = "select value from "+myusername+".control_parameter where  code='CP127'";
			String key = jdbcTemplate.queryForObject(getlicensekey, String.class);;
			String genkeys2 = getProdName()+"¬"+getVer()+"¬"+getOpsystem()+"¬"+getEnv()+"¬"+getEffectiveDate()+"¬"+getDurationdays()+"¬"+getRemdays()+"¬"+getGracedays()+"¬"+getHostname();
			String licensekey2 = passwordEncoder.encode(genkeys2);
			StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
			decryptor.setPassword(key);

			String licenseexpdateextend = decryptor.encrypt(getdate());
			String licenseremdaysextend = decryptor.encrypt(getRem());
			String licensegracedaysextend = decryptor.encrypt(getGrace());

			System.out.println("<<<<<< License key " + licensekey2);
			System.out.println("<<<<<<<<<<< License Expiry Date Encrypt>>>>>>> "+ licenseexpdateextend);
			System.out.println("<<<<<<<<<<< License Remaining Days Encrypt>>>>>>> "+ licenseremdaysextend);
			System.out.println("<<<<<<<<<<< License Grace period Encrypt>>>>>>> "+ licensegracedaysextend);

			int updatelicenkey = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licensekey2 + "' where control_parameter.code='CP127'");
			System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updatelicenkey);
			int updateexpirydt = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licenseexpdateextend + "' where control_parameter.code='CP110'");
			System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateexpirydt);
			int updateremdays = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licenseremdaysextend + "' where control_parameter.code='CP118'");
			System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateremdays);
			int updategraceperiod = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licensegracedaysextend + "' where control_parameter.code='CP119'");
			System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updategraceperiod);

			licenseGeneratorDb licensegendb = licenseGenService.findByDbsourcenm((String) datasource.getSelectionModel().getSelectedItem());
			System.out.println("eeeexexexexexexexexex " + licensegendb);
			licensegendb.setDeploy(getDeploy());
			licensegendb.setDurationdays(getDurationdays());
			licensegendb.setEffectivedate(getEffectiveDate());
			licensegendb.setEnv(getEnv());
			licensegendb.setExpirydate(getExpirydate());
			licensegendb.setGracedays(getGracedays());
			licensegendb.setHostmac(getHostmac());
			licensegendb.setHostname(getHostname());
			licensegendb.setOpsystem(getOpsystem());
			licensegendb.setProductname(getProdName());
			licensegendb.setRemdays(getRemdays());
			licensegendb.setStatus(getStatus());
			licensegendb.setVersion(getVer());
			licensegendb.setDbsourcenm(getDbsourcename());

			licenseGenService.update(licensegendb);

			String formattedDate = licensegendb.getExpirydate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Successfully Extended - New Expiry date is " + formattedDate);
			alert.show();

			System.out.println("successfully Extended");
		}

		if(status.getSelectionModel().getSelectedItem().equals("RENEW")){

			licenseGeneratorDb licensedata = licenseGenService.findByDbsourcenm(datasources.getDatasourcenm());

			if(LocalDate.now().isAfter(licensedata.getExpirydate())){

				String getlicensekey = "select value from "+myusername+".control_parameter where  code='CP127'";
				String key = jdbcTemplate.queryForObject(getlicensekey, String.class);;
				String genkeys2 = getProdName()+"¬"+getVer()+"¬"+getOpsystem()+"¬"+getEnv()+"¬"+getEffectiveDate()+"¬"+getDurationdays()+"¬"+getRemdays()+"¬"+getGracedays()+"¬"+getHostname();
				String licensekey2 = passwordEncoder.encode(genkeys2);
				StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
				decryptor.setPassword(key);

				String licenseexpdateextend = decryptor.encrypt(getdate());
				String licenseremdaysextend = decryptor.encrypt(getRem());
				String licensegracedaysextend = decryptor.encrypt(getGrace());

				System.out.println("<<<<<< License key " + licensekey2);
				System.out.println("<<<<<<<<<<< License Expiry Date Encrypt>>>>>>> "+ licenseexpdateextend);
				System.out.println("<<<<<<<<<<< License Remaining Days Encrypt>>>>>>> "+ licenseremdaysextend);
				System.out.println("<<<<<<<<<<< License Grace period Encrypt>>>>>>> "+ licensegracedaysextend);

				int updatelicenkey = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licensekey2 + "' where control_parameter.code='CP127'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updatelicenkey);
				int updateexpirydt = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licenseexpdateextend + "' where control_parameter.code='CP110'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateexpirydt);
				int updateremdays = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licenseremdaysextend + "' where control_parameter.code='CP118'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updateremdays);
				int updategraceperiod = jdbcTemplate.update("update "+myusername+".control_parameter set control_parameter.value='" + licensegracedaysextend + "' where control_parameter.code='CP119'");
				System.out.println("<<<<<<<<<<<<<<<<<<<<< " + updategraceperiod);

				licenseGeneratorDb licensegendb = licenseGenService.findByDbsourcenm((String) datasource.getSelectionModel().getSelectedItem());
				System.out.println("eeeexexexexexexexexex " + licensegendb);
				licensegendb.setDeploy(getDeploy());
				licensegendb.setDurationdays(getDurationdays());
				licensegendb.setEffectivedate(getEffectiveDate());
				licensegendb.setEnv(getEnv());
				licensegendb.setExpirydate(getExpirydate());
				licensegendb.setGracedays(getGracedays());
				licensegendb.setHostmac(getHostmac());
				licensegendb.setHostname(getHostname());
				licensegendb.setOpsystem(getOpsystem());
				licensegendb.setProductname(getProdName());
				licensegendb.setRemdays(getRemdays());
				licensegendb.setStatus(getStatus());
				licensegendb.setVersion(getVer());
				licensegendb.setDbsourcenm(getDbsourcename());

				licenseGenService.update(licensegendb);

				String formattedDate = licensegendb.getExpirydate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Successfully Updated - New Expiry date is " + formattedDate);
				alert.show();

				System.out.println("successfully updated");
			} else {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setContentText("Expiry date cannot be less than " + licensedata.getExpirydate());
				alert.show();
			}

		}

		return thedata;
	}


	@FXML
	private void generateKey(ActionEvent event) throws SQLException {

		dataSourceDb datas = new dataSourceDb();
		datas.setDatasourcenm((String) datasource.getSelectionModel().getSelectedItem());

		queryLicenseGen(datas);

	}

	//getters and setters for the fields
	public String getStatus() { return (String) status.getSelectionModel().getSelectedItem();}
	public String getProdName(){return (String) productName.getSelectionModel().getSelectedItem();}
	public String getOpsystem(){return (String) opsystem.getSelectionModel().getSelectedItem();}
	public String getEnv(){return (String) env.getSelectionModel().getSelectedItem();}
	public String getDeploy(){return (String) deploy.getSelectionModel().getSelectedItem();}
	public String getVer(){	return Double.toString(Double.parseDouble(version.getText()));}
	public LocalDate getEffectiveDate(){return effectivedate.getValue();}
	public int getDurationdays(){return Integer.parseInt(durationdays.getText());}
	public String getHostname(){return hostname.getText();}
	public String getHostmac(){return hostmac.getText();}
	public int getRemdays(){return Integer.parseInt(remdays.getText());}
	public int getGracedays(){return Integer.parseInt(gracedays.getText());}
	public LocalDate getExpirydate(){
		return effectivedate.getValue().plusDays(Integer.parseInt(durationdays.getText()));
	}
	public String getDbsourcename(){
		return (String) datasource.getSelectionModel().getSelectedItem();
	}

	// converting date, days and int to String to encrypt
	public String getdate(){
		return getExpirydate().toString();
	}
	public String getRem(){
		return Integer.toString(getRemdays());
	}
	public String getGrace(){
		return Integer.toString(getGracedays());
	}


}
