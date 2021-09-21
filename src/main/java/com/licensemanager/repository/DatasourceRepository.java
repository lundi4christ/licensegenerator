package com.licensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.licensemanager.bean.dataSourceDb;

@Repository
public interface DatasourceRepository extends JpaRepository<dataSourceDb, Long> {

//        dataDBSource findbydatasource(dataDBSource datasourcenm);

    dataSourceDb findByDatabasenm(String datasource);

    dataSourceDb findByDatasourcenm(String datasourcenm);

    //void deleteDatasourcenm(Long id);
}
