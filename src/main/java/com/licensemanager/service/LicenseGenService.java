package com.licensemanager.service;


import com.licensemanager.bean.licenseGeneratorDb;
import com.licensemanager.generic.GenericService;

public interface LicenseGenService extends GenericService<licenseGeneratorDb> {

    licenseGeneratorDb findByDbsourcenm(String dbsourcenm);

}
