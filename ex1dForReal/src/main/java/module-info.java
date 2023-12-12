module com.ex1daaaa {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                    
    opens com.ex1daaaa to javafx.fxml;
    exports com.ex1daaaa;
}