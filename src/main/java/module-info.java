module com.mes.motorph {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires eu.hansolo.toolbox;
//    requires eu.hansolo.toolboxfx;
//    requires eu.hansolo.fx.countries;
//    requires eu.hansolo.fx.heatmap;

    opens com.mes.motorph to javafx.fxml;
    exports com.mes.motorph;
    exports com.mes.motorph.controller;
    opens com.mes.motorph.controller to javafx.fxml;
}