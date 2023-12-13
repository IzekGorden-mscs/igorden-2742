package ui;

import dataaccess.PeopleJSONParser;
import domain.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import static dataaccess.PeopleJSONParser.*;

public class Main {
    private static final String json = "{\n" +
            "  \"person\": {\n" +
            "    \"personId\": \"101\",\n" +
            "    \"lastName\": \"Little\",\n" +
            "    \"firstName\": \"Nemo\",\n" +
            "    \"userName\": \"NemoLitt\"\n" +
            "  }\n" +
            "}";
    public static void main(String[] args) {

        ArrayList<Person> people = new ArrayList<Person>();


        Person person = null;
        Tenant tenant = null;
        HourlyAdministrator hourlyAdministrator = null;
        ContractAdministrator contractAdministrator = null;
        try {
            PeopleJSONParser.readFile("ex2d/src/main/resources/people.json");
//            person = getPerson();
//            tenant = getTenant();
//            contractAdministrator = getContractAdministrator();
//            hourlyAdministrator = getHourlyAdministrator();
            people = PeopleJSONParser.getPeople();

        }
        catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch(IOException e) {
            System.out.println("IO exception");
        }

        if(tenant != null) {
            people.add(person);
        }
        if(person != null) {
            people.add(tenant);
        }
        if(person != null) {
            people.add(contractAdministrator);
        }
        if(person != null) {
            people.add(hourlyAdministrator);
        }

        for(Person p : people){
            System.out.println(p);
            if(p.getClass() == HourlyAdministrator.class){
                HourlyAdministrator hourlyAdmin = (HourlyAdministrator)p;
                ArrayList<TimeCard> cards = hourlyAdmin.getTimeCards();
                if(hourlyAdmin != null && cards != null) {
                    for (TimeCard timeCard : cards) {
                        System.out.println("\t"+ timeCard);
                    }
                }
            }
        }
    }
}