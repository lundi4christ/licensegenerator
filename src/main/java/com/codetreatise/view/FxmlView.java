package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {
    DASHBOARD {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("license.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/license.fxml";
        }
    },

    FORM1 {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("datasource.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/form1.fxml";
        }
    },
    FORM2 {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("licensegenerator.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/form2.fxml";
        }
    };

    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
