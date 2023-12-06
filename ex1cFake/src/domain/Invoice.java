package domain;

import java.util.ArrayList;

public class Invoice {
    private int invoiceId;
    private int status;
    private GDate invoiceDate;
    private GDate dueDate;
    private ArrayList<LineItem> lineItems;

    /**
     *
     * @param status
     * @param invoiceDate
     * @param dueDate
     */
    public Invoice(int status, GDate invoiceDate, GDate dueDate) {
        this.invoiceId = DbContext.getNextInvoiceId();
        this.status = status;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.lineItems = new ArrayList<LineItem>();
    }

    /**
     *
     * @param invoice
     */
    public Invoice(Invoice invoice) {
        this.invoiceId = invoice.invoiceId;
        this.status = invoice.status;
        //this.invoiceDate = invoice.invoiceDate;
        this.invoiceDate = new GDate(invoice.invoiceDate.julianDay());
        //this.dueDate = invoice.dueDate;
        this.dueDate = new GDate(invoice.dueDate.julianDay());
        this.lineItems = invoice.getLineItems();
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

    public GDate getInvoiceDate() {
        return invoiceDate;
    }

    public GDate getDueDate() {
        return dueDate;
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
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", status=" + status +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                '}';
    }
}