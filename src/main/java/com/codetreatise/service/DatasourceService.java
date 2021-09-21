package com.codetreatise.service;

import com.codetreatise.bean.dataSourceDb;
import com.codetreatise.generic.GenericService;

public interface DatasourceService extends GenericService<dataSourceDb> {

    dataSourceDb saveDatasource(dataSourceDb entity);

    dataSourceDb findByDataSource(dataSourceDb datasourcenm);

    //void deleteDatasourcenm(Long id);


}
