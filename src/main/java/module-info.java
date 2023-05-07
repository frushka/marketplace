module com.marketplace.marketplace {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.marketplace.marketplace.models;
    opens com.marketplace.marketplace.controllers to javafx.fxml;
    opens com.marketplace.marketplace.aplications to javafx.fxml;
    opens com.marketplace.marketplace.dialogs to javafx.fxml;

    exports com.marketplace.marketplace.models;
    exports com.marketplace.marketplace.controllers;
    exports com.marketplace.marketplace.dialogs;
    exports com.marketplace.marketplace.aplications;
}