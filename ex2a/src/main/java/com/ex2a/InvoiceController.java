package com.ex2a;

import domain.Invoice;
import domain.LineItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InvoiceController {


    @FXML
    public TextField invoiceIDField;
    @FXML
    public TextField dueDateField;
    @FXML
    public TextField statusField;
    @FXML
    public TextField invoiceDateField;
    @FXML
    public ComboBox<Invoice> invoiceCombo;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField amountField;
    @FXML
    public ListView<LineItem> lineItemList;
    @FXML
    public TextField totalField;

    private static ArrayList<Invoice> invoices = new ArrayList<Invoice>();

    public InvoiceController() {
//        LocalDateTime date1 = LocalDateTime.of(2019, 9, 1, 0, 0 ,0);
//        LocalDateTime date2 = LocalDateTime.of(2019, 9, 11, 0, 0 ,0);
//        Invoice invoice1 = new Invoice(1, date1, date2, new Apartment());
//        invoice1.addLineItem(new LineItem(1.0, "description1"));
//        invoice1.addLineItem(new LineItem(2.0, "description2"));
//        invoice1.addLineItem(new LineItem(3.0, "description3"));
//        invoice1.addLineItem(new LineItem(4.0, "description4"));
//        invoices.add(invoice1);
//        Invoice invoice2 = new Invoice(2, date1, date2, new Apartment());
//        invoice2.addLineItem(new LineItem(5.0, "description5"));
//        invoice2.addLineItem(new LineItem(6.0, "description6"));
//        invoice2.addLineItem(new LineItem(7.0, "description7"));
//        invoice2.addLineItem(new LineItem(8.0, "description8"));
//        invoices.add(invoice2);

    }


    public static void initData(ArrayList<Invoice> initInvoices){
        invoices = initInvoices;
    }

    @FXML
    protected void initialize() {
        if (invoices.size() > 0) {
            for (Invoice invoice : invoices) {
                invoiceCombo.getItems().add(invoice);
            }
            this.invoices = null;
            invoiceCombo.getSelectionModel().selectFirst();
            lineItemList.getSelectionModel().selectFirst();
            displayInvoice(0);
            displayLineItem(0, invoiceCombo.getItems().getFirst());
        }
    }

    public void displayInvoice(int index){
        totalField.setText("0.0");
        if(index >= 0 && index < invoiceCombo.getItems().size()){
        Invoice invoice1 = invoiceCombo.getItems().get(index);
        if(invoice1 !=null) {
            this.invoiceIDField.setText(String.valueOf(invoice1.getInvoiceId()));
            this.statusField.setText(String.valueOf(invoice1.getStatus()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            this.invoiceDateField.setText(String.valueOf(invoice1.getInvoiceDate().format(formatter)));
            this.dueDateField.setText(String.valueOf(invoice1.getDueDate().format(formatter)));
            this.lineItemList.getItems().clear();
            for (LineItem lineItem:invoice1.getLineItems()) {
                this.lineItemList.getItems().add(lineItem);
            }
            lineItemList.getSelectionModel().selectFirst();
            if(lineItemList.getItems().size() > 0)
                displayLineItem();
        }
        }
    }
    public Invoice getSelectedInvoice(){
        return invoiceCombo.getSelectionModel().getSelectedItem();
    }
    public void displayLineItem(){
        LineItem lineItem = lineItemList.getSelectionModel().getSelectedItem();
            this.amountField.setText(String.valueOf(lineItem.getAmount()));
            this.descriptionField.setText(lineItem.getDescription());

        calculateTotal();
    }
    public void displayLineItem(int index, Invoice invoice){
        LineItem lineItem = invoice.getLineItems().get(index);
        this.amountField.setText(String.valueOf(lineItem.getAmount()));
        this.descriptionField.setText(lineItem.getDescription());
        calculateTotal();
    }

    public void onComboSelect(ActionEvent actionEvent) {
        int index = invoiceCombo.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            index = 0;
        }
        displayInvoice(index);
    }

    public void onListSelect(MouseEvent mouseEvent) {
        displayLineItem();
    }
    //
    public void onAddLineItem(ActionEvent actionEvent) {
        double amount = 0;
        String description = "Error: empty or invalid";
        try {
            amount = Double.parseDouble(amountField.getText());
            description = descriptionField.getText();
        } catch
        (Exception e) {}
        LineItem lineItem = new LineItem(amount, description);
        getSelectedInvoice().addLineItem(lineItem);
        int index = invoiceCombo.getSelectionModel().getSelectedIndex();
        if (index == -1){
            index = 0;
        }
        displayInvoice(index);
    }

    public void onRemoveLineItem(ActionEvent actionEvent) {
        getSelectedInvoice().removeLineItem(lineItemList.getSelectionModel().getSelectedIndex());
        int index = invoiceCombo.getSelectionModel().getSelectedIndex();
        if (index == -1){
            index = 0;
        }
        displayInvoice(index);
    }

    public void onLineItemSave(ActionEvent actionEvent) {
        Invoice invoice = getSelectedInvoice();
        if(invoice != null) {
            int lineIndex = lineItemList.getSelectionModel().getSelectedIndex();
            if (invoice.getLineItem(lineIndex) != null) {
                double amount = invoice.getLineItem(lineIndex).getAmount();
                String description = invoice.getLineItem(lineIndex).getDescription();
                try {
                    amount = Double.parseDouble(amountField.getText());
                    description = descriptionField.getText();
                } catch
                (Exception e) {
                }
                LineItem lineItem = invoice.getLineItemObject(lineIndex);
                lineItem.setDescription(description);
                lineItem.setAmount(amount);
                int index = invoiceCombo.getSelectionModel().getSelectedIndex();
                if (index == -1) {
                    index = 0;
                }
                displayInvoice(index);
            }
        }
    }

    public void onInvoiceSave(ActionEvent actionEvent) {
        Invoice invoice = getSelectedInvoice();

        int status;
        LocalDateTime invoiceDate;
        LocalDateTime dueDate;
        try{
            status = Integer.parseInt(statusField.getText());

            String[] strArr = invoiceDateField.getText().split("/");
            int year = Integer.parseInt(strArr[0]);
            int month = Integer.parseInt(strArr[1]);
            int day = Integer.parseInt(strArr[2]);
            invoiceDate = LocalDateTime.of(year, month , day, 0, 0, 0);

            strArr = dueDateField.getText().split("/");
            year = Integer.parseInt(strArr[0]);
            month = Integer.parseInt(strArr[1]);
            day = Integer.parseInt(strArr[2]);
            dueDate = LocalDateTime.of(year, month , day, 0, 0, 0);
        }
        catch(Exception e){
            Invoice invoiceCopy = invoice.copy();
            status = invoiceCopy.getStatus();
            invoiceDate = invoiceCopy.getInvoiceDate();
            dueDate = invoiceCopy.getDueDate();
        }
        invoice.setStatus(status);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setDueDate(dueDate);
        //aaa

//        int comboIndex = invoiceCombo.getSelectionModel().getSelectedIndex();
//        invoiceCombo.getItems().clear();
//        for (Invoice leInvoice:this.invoices) {
//            invoiceCombo.getItems().add(leInvoice);
//        }
//        invoiceCombo.getSelectionModel().select(comboIndex);
    }
    public void calculateTotal(){
        double total = 0;
        ArrayList<LineItem> lineItems =  getSelectedInvoice().getLineItems();
        if(lineItems.size() > 0) {
            for (LineItem lineItem : lineItems) {
                total += lineItem.getAmount();
            }
            totalField.setText(String.valueOf(total));
        }
        else{
            totalField.setText("0.0");
        }
    }

}