package com.example.ex1e;

import domain.DbContext;
import domain.Invoice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class InvoiceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceApplication.class.getResource("invoice-view.fxml"));
        InvoiceController invoiceController = new InvoiceController();
        ArrayList<Invoice> invoices;
        invoices = DbContext.getInvoices();
        invoiceController.initData(invoices);
        fxmlLoader.setController(invoiceController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Invoice Thingy");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}