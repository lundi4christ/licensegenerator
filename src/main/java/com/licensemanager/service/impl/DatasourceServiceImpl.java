package com.licensemanager.service.impl;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licensemanager.bean.dataSourceDb;
import com.licensemanager.repository.DatasourceRepository;
import com.licensemanager.service.DatasourceService;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    @Autowired
    private DatasourceRepository datasourceRepository;

    @FXML
    private Label label2;

    @Override
    public dataSourceDb save(dataSourceDb entity) {
        // dataSourceDb getdb1 = datasourceRepository.findByDatabasenm(entity.getDatabasenm());
        dataSourceDb getdb = datasourceRepository.findByDatasourcenm(entity.getDatasourcenm());

        if (getdb != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setTitle("");
            alert.setContentText(getdb.getDatasourcenm() + " " + "datasource already exist");
            alert.showAndWait();
//            System.out.println("*********************" + getdb.getDatabasenm() + " " + "datasource already exist");
        } else {
            getdb = datasourceRepository.save(entity);
        }
        return getdb;
    }

    @Override
    public dataSourceDb findByDatabasenm(String datasource) {
        return datasourceRepository.findByDatabasenm(datasource);
    }

    @Override
    public dataSourceDb findByDatasourcenm(String datasourcenm) {
        return datasourceRepository.findByDatasourcenm(datasourcenm);
    }

    @Override
    public dataSourceDb saveDatasource(dataSourceDb entity) {
        return datasourceRepository.save(entity);
    }

    @Override
    public dataSourceDb findByDataSource(dataSourceDb datasourcenm) {
        return null;
    }


    @Override
    public dataSourceDb update(dataSourceDb entity) {

        return datasourceRepository.save(entity);
    }

    @Override
    public void delete(dataSourceDb entity) {
        datasourceRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
		//return datasourceRepository.delete(id);
    }


    @Override
    public void deleteInBatch(List<dataSourceDb> entities) {

    }

    @Override
    public dataSourceDb find(Long id) {
    return null;
      //  return datasourceRepository.findById(id);
    }

    @Override
    public List<dataSourceDb> findAll() {

        return datasourceRepository.findAll();
    }


}
