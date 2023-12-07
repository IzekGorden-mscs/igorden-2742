module com.ex1c {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                    
    opens com.ex1c to javafx.fxml;
    exports com.ex1c;
}