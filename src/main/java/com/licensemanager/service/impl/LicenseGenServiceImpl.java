package com.licensemanager.service.impl;

import com.licensemanager.bean.licenseGeneratorDb;
import com.licensemanager.repository.LicenseGeneratorRepository;
import com.licensemanager.service.LicenseGenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseGenServiceImpl implements LicenseGenService {

    @Autowired
    private LicenseGeneratorRepository licenseGeneratorRepository;


    @Override
    public licenseGeneratorDb save(licenseGeneratorDb entity) {
                licenseGeneratorDb licensedb = licenseGeneratorRepository.findByDbsourcenm(entity.getDbsourcenm());

                if(licensedb != null){

                    System.out.println(licensedb.getDbsourcenm() + " already exist");
                } else {
                    licenseGeneratorRepository.save(entity);
                }

        return licensedb;
    }

    @Override
    public licenseGeneratorDb findByDbsourcenm(String dbsourcenm) {
        return licenseGeneratorRepository.findByDbsourcenm(dbsourcenm);
    }

    @Override
    public licenseGeneratorDb update(licenseGeneratorDb entity) {
        return licenseGeneratorRepository.save(entity);
    }

    @Override
    public void delete(licenseGeneratorDb entity) {

    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteInBatch(List<licenseGeneratorDb> entities) {

    }

    @Override
    public licenseGeneratorDb find(Long id) {
        return null;
    }

    @Override
    public licenseGeneratorDb findByDatabasenm(String datasource) {
        return null;
    }

    @Override
    public licenseGeneratorDb findByDatasourcenm(String datasourcenm) {

        return null;
    }

    @Override
    public List<licenseGeneratorDb> findAll() {
        return null;
    }


}
