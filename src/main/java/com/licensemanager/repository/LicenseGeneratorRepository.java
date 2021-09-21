package com.licensemanager.repository;

import com.licensemanager.bean.licenseGeneratorDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseGeneratorRepository extends JpaRepository<licenseGeneratorDb, Long> {

    licenseGeneratorDb findByDbsourcenm(String dbsourcenm);

}
