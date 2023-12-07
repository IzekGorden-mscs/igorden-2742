package com.ex1c;

import domain.GDate;
import domain.Invoice;
import domain.LineItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    public ComboBox invoiceCombo;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField amountField;
    @FXML
    public ListView lineItemList;
    @FXML
    public TextField totalField;

    private ArrayList<Invoice> invoices = new ArrayList<Invoice>();

    public InvoiceController() {
        GDate date1 = new GDate(2019, 9, 1);
        GDate date2 = new GDate(2019, 9, 11);
        Invoice invoice1 = new Invoice(1, date1, date2);
        invoice1.addLineItem(new LineItem(1.0, "description1"));
        invoice1.addLineItem(new LineItem(2.0, "description2"));
        invoice1.addLineItem(new LineItem(3.0, "description3"));
        invoice1.addLineItem(new LineItem(4.0, "description4"));
        invoices.add(invoice1);
        Invoice invoice2 = new Invoice(2, date1, date2);
        invoice2.addLineItem(new LineItem(5.0, "description5"));
        invoice2.addLineItem(new LineItem(6.0, "description6"));
        invoice2.addLineItem(new LineItem(7.0, "description7"));
        invoice2.addLineItem(new LineItem(8.0, "description8"));
        invoices.add(invoice2);
    }


    @FXML
    protected void initialize(){
        displayInvoice(0);
        for (Invoice invoice:this.invoices) {
            invoiceCombo.getItems().add(invoice.toShortString());
        }
        invoiceCombo.getSelectionModel().selectFirst();
        lineItemList.getSelectionModel().selectFirst();
        displayLineItem(0, this.invoices.get(0));
    }

    public void displayInvoice(int index){
        totalField.setText("0.0");
        Invoice invoice1 = this.invoices.get(index);
        if(invoice1 !=null) {
            this.invoiceIDField.setText(String.valueOf(invoice1.getInvoiceId()));
            this.statusField.setText(String.valueOf(invoice1.getStatus()));
            this.invoiceDateField.setText(String.valueOf(invoice1.getInvoiceDate()));
            this.dueDateField.setText(String.valueOf(invoice1.getDueDate()));
            this.lineItemList.getItems().clear();
            for (LineItem lineItem:invoice1.getLineItems()) {
                this.lineItemList.getItems().add(lineItem.toShortString());
            }
            lineItemList.getSelectionModel().selectFirst();
            if(lineItemList.getItems().size() > 0)
                displayLineItem();
        }
    }
    public Invoice getSelectedInvoice(){
        int indexInvoice = invoiceCombo.getSelectionModel().getSelectedIndex();
        if(indexInvoice == -1){
            indexInvoice = 0;
        }
        return this.invoices.get(indexInvoice);
    }
    public void displayLineItem(){
        int index = lineItemList.getSelectionModel().getSelectedIndex();
        if(index != -1) {
            Invoice invoice = this.getSelectedInvoice();
            LineItem lineItem = invoice.getLineItems().get(index);
            this.amountField.setText(String.valueOf(lineItem.getAmount()));
            this.descriptionField.setText(lineItem.getDescription());
        }
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
        GDate invoiceDate;
        GDate dueDate;
        try{
            status = Integer.parseInt(statusField.getText());
            invoiceDate = GDate.parse(invoiceDateField.getText());
            dueDate = GDate.parse(dueDateField.getText());
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

        int comboIndex = invoiceCombo.getSelectionModel().getSelectedIndex();
        invoiceCombo.getItems().clear();
        for (Invoice leInvoice:this.invoices) {
            invoiceCombo.getItems().add(leInvoice.toShortString());
        }
        invoiceCombo.getSelectionModel().select(comboIndex);
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