package ui;

import domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Person person = new Person(101, "Nemo", "Little", "NemoLitt");
        Tenant tenant = new Tenant(101, "Nemo", "Little", "NemoLitt", LocalDateTime.of(2001, 01, 23, 0, 0, 0),
                "1234567890", "6511234567", "Target", "Customer Service Representative", 20.00, LocalDateTime.now().minusDays(999));
        System.out.println(tenant.toString());

        HourlyAdministrator hourlyAdministrator = new HourlyAdministrator(
                201, "John", "Doe", "john.doe", LocalDateTime.of(1990, 5, 15, 0, 0, 0),
                "987654321", "555-1234", LocalDateTime.now().minusDays(365), 15.0);

        LocalDateTime startTime1 = LocalDateTime.of(2023, 1, 1, 9, 0, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2023, 1, 1, 17, 0, 0);
        hourlyAdministrator.addTimeCard(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2023, 1, 2, 10, 0, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2023, 1, 2, 18, 0, 0);
        hourlyAdministrator.addTimeCard(startTime2, endTime2);

        double grossPay = hourlyAdministrator.calcGrossPay();
        System.out.println("Gross Pay: $" + grossPay);


        ContractAdministrator contractAdministrator = new ContractAdministrator(
                301, "Jane", "Smith", "jane.smith", LocalDateTime.of(1985, 8, 20, 0, 0, 0),
                "876543210", "555-5678", LocalDateTime.now().minusDays(180), 2000.0);
        double grossPayContract = contractAdministrator.calcGrossPay();
        System.out.println("Contract Administrator Gross Pay: $" + grossPayContract);


        System.out.println("\n");
        ArrayList<Administrator> administrators = new ArrayList<Administrator>();
        administrators.add(contractAdministrator);
        administrators.add(hourlyAdministrator);
        for(Administrator a: administrators){
            System.out.println(a);
        }
        System.out.println("\n");
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(tenant);
        people.add(person);
        people.add(contractAdministrator);
        people.add(hourlyAdministrator);
        for(Person p: people){
            System.out.println(p);
        }

        System.out.println("\n");
        double totalPay = 0;
        for(Administrator a: administrators){
            totalPay += a.calcGrossPay();
        }
        System.out.println("$"+totalPay);
    }

}
