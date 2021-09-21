/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.licensemanager.config;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppJavaConfig {
	
    @Autowired
    SpringFXMLLoader springFXMLLoader;

//    SpringFXMLLoader springFXMLLoader = new SpringFXMLLoader();

    /**
     * Useful when dumping stack trace to a string for logging.
     * @return ExceptionWriter contains logging utility methods
     */
/*    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        return new ExceptionWriter(new StringWriter());
    }*/

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }
    
    @Bean
    @Lazy(value = true) //Stage only created after Spring context bootstap
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public StandardPBEStringEncryptor encryptor(){

        return new StandardPBEStringEncryptor();
    }
}
