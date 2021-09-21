package com.codetreatise.repository;

import com.codetreatise.bean.licenseGeneratorDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseGeneratorRepository extends JpaRepository<licenseGeneratorDb, Long> {

    licenseGeneratorDb findByDbsourcenm(String dbsourcenm);

}
