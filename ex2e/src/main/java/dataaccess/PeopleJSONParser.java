package dataaccess;

import domain.*;
import exceptions.PersonIllegalArgumentException;
import exceptions.TimeCardIllegalArgException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PeopleJSONParser {
    private static String json = "{}";
    private static ArrayList<Exception> exceptions = new ArrayList<Exception>();
    private static ArrayList<Exception> domainExceptions = new ArrayList<Exception>();

    public static ArrayList<Exception> getExceptions() {
        return exceptions;
    }

    public static ArrayList<Exception> getDomainExceptions() {
        return domainExceptions;
    }

    public static void readFile(String path) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new FileReader(path))) {
            String line = "";
            StringBuilder sbJSON = new StringBuilder(400);
            while ((line = in.readLine()) != null) {
                sbJSON.append(line + "\n");
            }
            json = sbJSON.toString();
        }
    }

    public static Person getPerson(JSONObject obj) {
        Person person = null;
        try {
            String userName = obj.getString("userName");
            String lastName = obj.getString("lastName");
            int personId = obj.getInt("personId");
            String firstName = obj.getString("firstName");
            person = new Person(personId, firstName, lastName, userName);

        } catch (Exception e) {
            String msg = e.toString();
            msg += "\nCause: " + e.getCause();
            //System.out.println(msg);

        }
        return person;
    }

    public static Tenant getTenant(JSONObject obj) {
        Tenant tenant = null;
        String userName = obj.getString("userName");
        String lastName = obj.getString("lastName");
        int personId = obj.getInt("personId");
        String firstName = obj.getString("firstName");
        LocalDateTime birthDate = LocalDateTime.parse(obj.getString("birthDate").replace("/", "-") + "T00:00:00.00");
        String ssn = obj.getString("ssn");
        String phone = obj.getString("phone");
        String employer = obj.getString("employer");
        String occupation = obj.getString("occupation");
        double grossPay = obj.getDouble("grossPay");
        LocalDateTime employmentStartDate = LocalDateTime.parse(obj.getString("employmentStartDate").replace("/", "-") + "T00:00:00.00");


        tenant = new Tenant(personId, firstName, lastName, userName, birthDate, ssn, phone, employer, occupation, grossPay, employmentStartDate);

        return tenant;
    }

    public static ContractAdministrator getContractAdministrator(JSONObject obj) {
        ContractAdministrator contractAdministrator = null;

        int personId = obj.getInt("personId");
        String firstName = obj.getString("firstName");
        String lastName = obj.getString("lastName");
        String username = obj.getString("userName");
        LocalDateTime birthDate = LocalDateTime.parse(obj.getString("birthDate").replace("/", "-") + "T00:00:00.00");
        String ssn = obj.getString("ssn");
        String phone = obj.getString("phone");
        LocalDateTime employmentStartDate = LocalDateTime.parse(obj.getString("employmentStartDate").replace("/", "-") + "T00:00:00.00");
        double monthlyRate = obj.getDouble("monthlyRate");
        contractAdministrator = new ContractAdministrator(personId, firstName, lastName, username, birthDate, ssn, phone, employmentStartDate, monthlyRate);


        return contractAdministrator;
    }

    public static HourlyAdministrator getHourlyAdministrator(JSONObject obj) {
        HourlyAdministrator hourlyAdministrator = null;
        int personId = obj.getInt("personId");
        String firstName = obj.getString("firstName");
        String lastName = obj.getString("lastName");
        String username = obj.getString("userName");
        LocalDateTime birthDate = LocalDateTime.parse(obj.getString("birthDate").replace("/", "-") + "T00:00:00.00");
        String ssn = obj.getString("ssn");
        String phone = obj.getString("phone");
        LocalDateTime employmentStartDate = LocalDateTime.parse(obj.getString("employmentStartDate").replace("/", "-") + "T00:00:00.00");
        double hourlyRate = obj.getDouble("hourlyRate");

        // Parse the timeCards array
        JSONArray timeCardsArray = obj.getJSONArray("timeCards");
        ArrayList<TimeCard> timeCards = new ArrayList<>();

        for (int i = 0; i < timeCardsArray.length(); i++) {
            JSONObject timeCardObj = timeCardsArray.getJSONObject(i);

            int id = timeCardObj.getInt("id");
            LocalDateTime startDateTime = LocalDateTime.parse(timeCardObj.getString("startDateTime"), DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma"));
            LocalDateTime endDateTime = LocalDateTime.parse(timeCardObj.getString("endDateTime"), DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma"));

            TimeCard timeCard = new TimeCard(id, startDateTime, endDateTime);
            timeCards.add(timeCard);
        }

        hourlyAdministrator = new HourlyAdministrator(personId, firstName, lastName, username, birthDate, ssn, phone, employmentStartDate, hourlyRate);

        if (hourlyAdministrator != null && timeCards != null) {
            for (TimeCard timeCard : timeCards) {
                hourlyAdministrator.addTimeCard(timeCard);
            }
        }

        return hourlyAdministrator;
    }

    public static ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<Person>();

        JSONArray obj = new JSONObject(json).getJSONArray("people");
        for (int i = 0; i < obj.length(); i++) {
            try {
                JSONObject item = obj.getJSONObject(i);
                switch (item.getString("subclass")) {
                    case "person":
                        Person person = getPerson(item);
                        if (person != null)
                            people.add(person);
                        break;
                    case "tenant":
                        Tenant tenant = getTenant(item);
                        if (tenant != null)
                            people.add(tenant);
                        break;
                    case "contractAdministrator":
                        ContractAdministrator cA = getContractAdministrator(item);
                        if (cA != null)
                            people.add(cA);
                        break;
                    case "hourlyAdministrator":
                        HourlyAdministrator hA = getHourlyAdministrator(item);
                        if (hA != null)
                            people.add(hA);
                        break;
                    default:
                        String msg = item.getString("subclass") + " is not a valid person object subclass";
                        throw new IllegalArgumentException(msg);

                }
            } catch (PersonIllegalArgumentException | TimeCardIllegalArgException e) {
//                String msg = e.toString();
//                msg += "\n" + i + ":" + e.getCause();
//                System.out.println(msg);
                domainExceptions.add(e);
            } catch (Exception e) {
//                String msg = e.toString();
//                msg += "\n" + i + ":" + e.getCause();
//                System.out.println(msg);
                exceptions.add(e);
            }
        }
        return people;
    }
}
