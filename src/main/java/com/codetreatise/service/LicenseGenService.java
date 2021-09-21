package com.codetreatise.service;


import com.codetreatise.bean.dataSourceDb;
import com.codetreatise.bean.licenseGeneratorDb;
import com.codetreatise.generic.GenericService;

public interface LicenseGenService extends GenericService<licenseGeneratorDb> {

    licenseGeneratorDb findByDbsourcenm(String dbsourcenm);

}
