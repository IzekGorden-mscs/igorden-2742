module com.ex2a {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                    
    opens com.ex2a to javafx.fxml;
    exports com.ex2a;
}