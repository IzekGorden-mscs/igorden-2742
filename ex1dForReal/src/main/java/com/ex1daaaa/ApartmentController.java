package com.ex1daaaa;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.ex1daaaa.InvoiceController.initData;

public class ApartmentController {


    private final ArrayList<Apartment> apartments;
    private ArrayList<Person> people;
    @FXML
    public ComboBox apartmentCombo;
    @FXML
    public ComboBox adminCombo;
    @FXML
    public ComboBox tenantCombo;
    @FXML
    public TextField numField;
    @FXML
    public TextField sqField;
    @FXML
    public TextField bathroomField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField updatedField;
    private int aptIndex = 0;
    private ArrayList<Invoice> invoices;

    public ApartmentController() {
        this.apartments = DbContext.getApartments();
        this.people = DbContext.getPeople();
    }


    @FXML
    protected void initialize(){
        apartmentCombo.getItems().clear();
        adminCombo.getItems().clear();
        tenantCombo.getItems().clear();
        for (Apartment apt:this.apartments) {
            apartmentCombo.getItems().add(apt.toShortString());
        }
        for (Person p:this.people) {
            adminCombo.getItems().add(p.toShortString());
            tenantCombo.getItems().add(p.toShortString());
        }
        apartmentCombo.getSelectionModel().select(aptIndex);
        displayApartment();
    }

    private void displayAdmin(Person administrator) {

    }

    public void displayApartment() {
        int index = apartmentCombo.getSelectionModel().getSelectedIndex();
        if(index ==-1){
            index = 0;
        }
        Apartment apt = this.apartments.get(index);
        if (apt != null) {
            this.numField.setText(String.valueOf(apt.getApartmentNum()));
            this.sqField.setText(String.valueOf(apt.getSquareFeet()));
            this.bathroomField.setText(String.valueOf(apt.getBathrooms()));
            this.priceField.setText(String.valueOf(apt.getPrice()));
            this.updatedField.setText(String.valueOf(apt.getUpdated()));

            int adminId = apt.getAdministrator().getPersonId();
            int tenantId = apt.getTenant().getPersonId();
            int adminIndex = -1;
            int tenantIndex = -1;

            //selects the indexes of the admin and tenant in the adminCombobox and tenantCombobox
            for(int i = 0; i < adminCombo.getItems().size(); i++) {
                String itemStr = String.valueOf(adminCombo.getItems().get(i));
                String idStr = itemStr.split(",")[0];
                int id = Integer.parseInt(idStr);
                if(id == adminId){
                    adminIndex = i;
                }
            }
            adminCombo.getSelectionModel().select(adminIndex);

            for(int i = 0; i < tenantCombo.getItems().size(); i++) {
                String itemStr = String.valueOf(tenantCombo.getItems().get(i));
                String idStr = itemStr.split(",")[0];
                int id = Integer.parseInt(idStr);

                if (id == tenantId) {
                    tenantIndex = i;
                }
            }
            tenantCombo.getSelectionModel().select(tenantIndex);
        }
    }


    @FXML
    public void onApartmentSelect(ActionEvent actionEvent) {
        displayApartment();
    }

    @FXML
    public void onApartmentSave(ActionEvent actionEvent) {
        try {
            Apartment apt = getSelectedApartmentObject();
            Apartment copy = apt.copy();
            String error = "";
            error += apt.setApartmentNum(numField.getText());
            error += apt.setSquareFeet(Integer.parseInt(sqField.getText()));
            error += apt.setSquareFeet(Integer.parseInt(sqField.getText()));
            error += apt.setBathrooms(Integer.parseInt(bathroomField.getText()));
            error += apt.setPrice(Double.parseDouble(priceField.getText()));
            if ("" == error) {
                apt.setAdministrator(people.get(adminCombo.getSelectionModel().getSelectedIndex()));
                apt.setTenant(people.get(tenantCombo.getSelectionModel().getSelectedIndex()));

                apt.setUpdated(LocalDateTime.now());
                aptIndex = apartmentCombo.getSelectionModel().getSelectedIndex();
                initialize();
            } else {
                System.out.println(error);
            }


        } catch (Exception e) {
            System.out.println("Must be a number!");
        }
    }

    private Apartment getSelectedApartmentObject() {
        int index = apartmentCombo.getSelectionModel().getSelectedIndex();

        return apartments.get(index);
    }

    @FXML
    public void onViewInvoices(ActionEvent actionEvent) {
        try {
//            Parent root = FXMLLoader.load(getClass().getResource("invoice-view.fxml"));
//            Scene scene = new Scene(root);
//            Stage primaryStage =new Stage();
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Invoice Thingy");
//            primaryStage.show();

            //invoices = DbContext.getInvoices();
            invoices = getSelectedApartmentObject().getInvoices();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoice-view.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            InvoiceController invoiceController = new InvoiceController();
            invoiceController.initData(invoices);
            fxmlLoader.setController(invoiceController);
            Pane pane = (Pane) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Invoice Thingy");
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAdminSelect(ActionEvent actionEvent) {
    }

    @FXML
    public void onTenantSelect(ActionEvent actionEvent) {
    }
}