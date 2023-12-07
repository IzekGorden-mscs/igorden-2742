package ui;
import domain.GDate;


public class Main {
    public static void main(String[] args){
        //constructors
        GDate date1 = new GDate();
        System.out.println("GDate():\t\t\t"+date1);
        GDate date2 = new GDate(2000, 1,1);
        System.out.println("GDate(2000, 1, 1):\t"+date2);
        GDate date3 = new GDate(date1);
        System.out.println("GDate(date1):\t\t"+date3);
        GDate date4 = new GDate(2451545);
        System.out.println("GDate(2451545):\t\t"+date4);
        GDate date5 = date1.copy();
        System.out.println("date1.copy():\t\t"+date5);

        //comparisons
        GDate date6 = new GDate(2100000);

        System.out.print(date1);
        System.out.print(date1.equals(date3) ? " = " : " != ");
        System.out.println(date3);

        System.out.print(date1);
        System.out.print(date1.equals(new GDate(1)) ? " = " : " != ");
        System.out.println(date6);

        System.out.print(date1);
        System.out.print(date1.greaterThan(date6) ? " > " : " <= ");
        System.out.println(date6);

        System.out.print(date6);
        System.out.print(date6.greaterThan(date1) ? " > " : " <= ");
        System.out.println(date1);

        System.out.print(new GDate(2001,1,1) + " - ");
        System.out.print(new GDate(2000,1,1) + " = ");
        System.out.println(new GDate(2001,1,1).diff(new GDate(2000,1,1)));

        System.out.print(new GDate(2002,1,1) + " - ");
        System.out.print(new GDate(2001,1,1) + " = ");
        System.out.println(new GDate(2002,1,1).diff(new GDate(2001,1,1)));

        System.out.print(new GDate(2000,1,1) + " + ");
        System.out.print("60 = ");
        System.out.println(new GDate(2000,1,1).add(60));

        System.out.print(new GDate(2001,1,1) + " + ");
        System.out.print("60 = ");
        System.out.println(new GDate(2001,1,1).add(60));

        System.out.print("Year of ");
        System.out.print(date1+" = ");
        System.out.println(date1.year());

        System.out.print("Month of ");
        System.out.print(date1+" = ");
        System.out.println(date1.month());

        System.out.print("Day of ");
        System.out.print(date1+" = ");
        System.out.println(date1.day());

        System.out.print("JDN of ");
        System.out.print(date1+" = ");
        System.out.println(date1.julianDay());
    }
}
