package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Apartment {
    private ArrayList<Invoice> invoices;
    private int apartmentId;
    private String apartmentNum;
    private int squareFeet;
    private int bathrooms;
    private double price;
    private LocalDateTime updated;
    private Person administrator;
    private Person tenant;

    public Apartment() {
        this.apartmentId = 0;
        this.apartmentNum = "";
        this.squareFeet = 0;
        this.bathrooms = 0;
        this.price = 0.0;
        this.updated = LocalDateTime.now();
        this.administrator = null;
        this.tenant = null;
        this.invoices = new ArrayList<Invoice>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Apartment other = (Apartment) obj;
        return apartmentId == other.apartmentId &&
                squareFeet == other.squareFeet &&
                bathrooms == other.bathrooms &&
                price == other.price &&
                apartmentNum.equals(other.apartmentNum) &&
                updated== other.updated &&
                administrator== other.administrator &&
                tenant== other.tenant &&
                invoices.equals(other.invoices);
    }

    public Apartment(Apartment apartment) {
        this.apartmentId = apartment.apartmentId;
        this.apartmentNum = apartment.apartmentNum;
        this.squareFeet = apartment.squareFeet;
        this.bathrooms = apartment.bathrooms;
        this.price = apartment.price;
        this.updated = apartment.updated;;
        this.administrator = apartment.administrator;
        this.tenant = apartment.tenant;
        this.invoices = apartment.invoices;
    }
    public void addInvoice(Invoice invoice){
        this.invoices.add(invoice);
    }
    public Invoice removeInvoice(int index){
        Invoice inv = null;
        if(index < this.invoices.size() && index >= 0) {
            inv = invoices.get(index).copy();
            this.invoices.remove(index);
        }
        else{
            String errMsg = index+ " is out of Bounds";
            throw new NullPointerException(errMsg);
        }
        return inv;
    }

//    public Apartment(int apartmentId, String location, String apartmentNum, int squareFeet, int bathrooms, double price) {
//        this.apartmentId = apartmentId;
//        this.apartmentNum = apartmentNum;
//        this.squareFeet = squareFeet;
//        this.bathrooms = bathrooms;
//        this.price = price;
//        this.updated = LocalDateTime.now();
//    }

    public int getApartmentId() {
        return apartmentId;
    }
    public String getApartmentNum() {
        return apartmentNum;
    }
    public int getSquareFeet() {
        return squareFeet;
    }
    public int getBathrooms() { return this.bathrooms; }
    public double getPrice() {
        return price;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }
    public Person getAdministrator() { return administrator; }
    public Person getTenant() { return tenant; }

    public String setApartmentId(int apartmentId) {
        String errMsg = "";
        if (apartmentId >= 100 && apartmentId <= 199)
            this.apartmentId = apartmentId;
        else{
            errMsg = Integer.toString(apartmentId) + " is invalid. ApartmentId must be >= 101 and <= 199";
        throw new IllegalArgumentException(errMsg);
    }
        return errMsg;
    }

    public String setApartmentNum(String apartmentNum) {
        String errMsg = "";
        if (apartmentNum != null && apartmentNum.length() >= 1 && apartmentNum.length() <= 4)
            this.apartmentNum = apartmentNum;
        else{
            errMsg = "ApartmentNum is required";
        throw new IllegalArgumentException(errMsg);
    }
        return errMsg;
    }

    public String setSquareFeet(int squareFeet) {
        String errMsg = "";
        if (squareFeet >= 200 && squareFeet <= 2000)
            this.squareFeet = squareFeet;
        else{
            errMsg = Integer.toString(squareFeet) + " is invalid. Square feet must be > 200 and < 2000.";
        throw new IllegalArgumentException(errMsg);
    }
        return errMsg;
    }

    public String setBathrooms(int bathrooms) {
        String errMsg = "";
        if (bathrooms >= 1 && bathrooms <= 3)
            this.bathrooms = bathrooms;
        else{
            errMsg = Integer.toString(bathrooms) + " is invalid. Bathrooms must be > 0 and < 4.";
        throw new IllegalArgumentException(errMsg);
    }
        return errMsg;
    }

    public String setPrice(double price) {
        String errMsg = "";
        if (price > 99.99 && price < 9999.99)
            this.price = price;
        else{
            errMsg = Double.toString(price) + " is invalid. Price must be > 99.99 and < 9999.99";
        throw new IllegalArgumentException(errMsg);
    }
        return errMsg;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setAdministrator(Person administrator) {
        this.administrator = administrator;
    }

    public void setTenant(Person tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return
                this.apartmentNum + ", $" + this.price + ", " + this.updated.format(formatter);
    }
    public String toShortString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return
                this.apartmentNum + ", $" + this.price + ", " + this.updated.format(formatter);
    }

    public Invoice getInvoice(int i) {
        if(i >= 0 && i < this.invoices.size()) {
            return this.invoices.get(i).copy();
        }
        else {
            String errMsg = i+ " is out of Bounds";
            throw new NullPointerException(errMsg);
        }
    }

    public ArrayList<Invoice> getInvoices() {
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
        for (Invoice inv : this.invoices) {
            invoices.add(inv.copy());
        }
        return invoices;
    }

    public void addInvoice(int i, Invoice invoice1) {
        invoices.add(i, invoice1);
    }

    public Apartment copy() {
        return new Apartment(this);
    }
}