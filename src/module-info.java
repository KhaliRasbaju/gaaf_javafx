module GAAF_V1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.net.http; // 👈 necesario para usar HttpClient
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens application to javafx.graphics, javafx.fxml;
    opens application.utils to javafx.fxml;   // si usas FXML en utils
    opens application.views to javafx.fxml;   // si cargas vistas desde FXML

    opens application.models.request to javafx.base;
    opens application.models.response to javafx.base;
    opens application.controllers to javafx.fxml;
    
    exports application.models.request;
    exports application.models.response;
    exports application.controllers;

}
