package com.licensemanager.service;

import com.licensemanager.bean.dataSourceDb;
import com.licensemanager.generic.GenericService;

public interface DatasourceService extends GenericService<dataSourceDb> {

    dataSourceDb saveDatasource(dataSourceDb entity);

    dataSourceDb findByDataSource(dataSourceDb datasourcenm);

    //void deleteDatasourcenm(Long id);

}
