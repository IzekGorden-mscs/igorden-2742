module com.example.ex1e {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                    
    opens com.example.ex1e to javafx.fxml;
    exports com.example.ex1e;
}