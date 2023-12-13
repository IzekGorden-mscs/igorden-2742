package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Invoice {
    private Apartment apartment;
    private int invoiceId;
    private int status;
    private LocalDateTime invoiceDate;
    private LocalDateTime dueDate;
    private ArrayList<LineItem> lineItems;

    /**
     *
     * @param status
     * @param invoiceDate
     * @param dueDate
     */
    public Invoice(int status, LocalDateTime invoiceDate, LocalDateTime dueDate, Apartment apartment) {
        this.invoiceId = DbContext.getNextInvoiceId();
        this.status = status;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.lineItems = new ArrayList<LineItem>();
        this.apartment = apartment;
    }

    /**
     *
     * @param invoice
     */
    public Invoice(Invoice invoice) {
        this.invoiceId = invoice.invoiceId;
        this.status = invoice.status;
        //this.invoiceDate = invoice.invoiceDate;
        this.invoiceDate = invoice.invoiceDate;
        //this.dueDate = invoice.dueDate;
        this.dueDate = invoice.dueDate;
        this.lineItems = new ArrayList<>(invoice.getLineItems());
        if (apartment != null) {
            this.apartment = new Apartment(invoice.getApartment());
        }
        else{
            this.apartment = null;
        }
    }

    public Apartment getApartment() {
        if(this.apartment == null){
            return null;
        }
        return this.apartment.copy();
    }

    public Invoice copy() {
        return new Invoice(this);
    }

    /**
     *
     * @param lineItem
     */
    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem.copy());
    }

    /**
     *
     * @param index
     */
    /*public LineItem removeLineItem(int lineItemId) {
        for (LineItem lineItem : this.lineItems){
            if (lineItem.getLineItemId() == lineItemId){
                LineItem newLineItem = new LineItem(lineItem);
                this.lineItems.remove(lineItem);
                return newLineItem;
            }
        }
        return null;
    }*/
    public LineItem removeLineItem(int index) {
        if(this.lineItems.size() > 0 && index < this.lineItems.size() && index > -1) {
            LineItem deletedLineItem = new LineItem(this.lineItems.get(index));
            this.lineItems.remove(index);
            return deletedLineItem;
        }
        else{
            return null;
        }
    }
    /**
     *
     * @param lineItem
     */
    public LineItem removeLineItem(LineItem lineItem) {
        if(this.lineItems.size() > 0) {
            LineItem deletedLineItem = new LineItem(lineItem);
            this.lineItems.remove(lineItem);
            return deletedLineItem;
        }
        else{
            return null;
        }
    }

    public double total() {
        int sum = 0;
        for (LineItem lineItem : this.lineItems){
            sum += lineItem.getAmount();
        }
        return sum;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getInvoiceDate() {
        return LocalDateTime.parse(invoiceDate.toString());
    }

    public LocalDateTime getDueDate() {
        return LocalDateTime.parse(dueDate.toString());
    }

    public ArrayList<LineItem> getLineItems() {
        //return lineItems;
        ArrayList<LineItem> newLines = new ArrayList<LineItem>();
        for(LineItem lineItem : lineItems){
           newLines.add(lineItem.copy());
        }
        return newLines;
    }

    public LineItem getLineItem(int index){
        if(index > this.lineItems.size()-1 || index < 0){
            return null;
        }
        else {
            return this.lineItems.get(index).copy();
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return  invoiceId +
                ", " + status +
                ", " + invoiceDate.format(formatter) ;
    }

    public String toShortString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return  invoiceId +
                ", " + status +
                ", " + invoiceDate.format(formatter) ;
    }

    public LineItem getLineItemObject(int index) {
        return this.lineItems.get(index);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return (invoiceId == invoice.invoiceId) &&
                (status == invoice.status) &&
                (
                        Objects.equals(apartment, invoice.apartment) ||
                                (apartment.getApartmentNum() == "" && invoice.apartment == null)
                ) &&
                Objects.equals(invoiceDate, invoice.invoiceDate) &&
                Objects.equals(dueDate, invoice.dueDate) &&
                Objects.equals(lineItems, invoice.lineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apartment, invoiceId, status, invoiceDate, dueDate, lineItems);
    }
}