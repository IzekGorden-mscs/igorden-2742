package domain;

public class GDate {
    private int julianDay = 0;

    public GDate() {
        this.julianDay = 2451545; //default date is 2000.01.01
    }

    public GDate(int n) {
        this.julianDay = n;
    }
    public GDate(GDate date2) {
        this.julianDay = date2.julianDay;
    }

    public GDate(int year, int month, int day){
        //mathematical formula to calculate julian day given gregorian date
        //JDN = (1461 × (Y + 4800 + (M − 14)/12))/4 +(367 × (M − 2 − 12 × ((M − 14)/12)))/12 − (3 × ((Y + 4900 + (M - 14)/12)/100))/4 + D − 32075
        this.julianDay = (1461 * (year + 4800 + (month - 14)/12))/4 +(367 * (month - 2 - 12 * ((month - 14)/12)))/12 - (3 * ((year + 4900 + (month - 14)/12)/100))/4 + day - 32075;
    }

    public int year(){
        int l = this.julianDay + 68569;
        int n = ( 4 * l ) / 146097;
        l = l - ( 146097 * n + 3 ) / 4;
        int i = ( 4000 * ( l + 1 ) ) / 1461001;
        l = l - ( 1461 * i ) / 4 + 31;
        int j = ( 80 * l ) / 2447;
        l = j / 11;
        int y = 100 * ( n - 49 ) + i + l;
        return y;
    }
    public int month(){
        int l = this.julianDay + 68569;
        int n = ( 4 * l ) / 146097;
        l = l - ( 146097 * n + 3 ) / 4;
        int i = ( 4000 * ( l + 1 ) ) / 1461001;
        l = l - ( 1461 * i ) / 4 + 31;
        int j = ( 80 * l ) / 2447;
        l = j / 11;
        int m = j + 2 - ( 12 * l );
        return m;
    }
    public int day(){
        int l = this.julianDay + 68569;
        int n = ( 4 * l ) / 146097;
        l = l - ( 146097 * n + 3 ) / 4;
        int i = ( 4000 * ( l + 1 ) ) / 1461001;
        l = l - ( 1461 * i ) / 4 + 31;
        int j = ( 80 * l ) / 2447;
        int d = l - ( 2447 * j ) / 80;
        return d;
    }


    public boolean greaterThan(GDate date1) {
       return this.julianDay > date1.julianDay;
    }

    public int diff(GDate date2) {
        return this.julianDay - date2.julianDay;
    }

    public GDate add(int i) {
        return new GDate(this.julianDay + i);
    }

    public GDate copy() {
        return new GDate(this.julianDay);
    }

    public int julianDay() {
        return this.julianDay;
    }

    public boolean equals(GDate date) {
        return (this.julianDay == date.julianDay);
    }

    @Override
    public String toString() {
        int y = this.year();
        int m = this.month();
        int d = this.day();
        //string should follow formula of year + '.'+ month+ '.'+ day
        return String.format("%04d.%02d.%02d",y, m, d );
    }
}
