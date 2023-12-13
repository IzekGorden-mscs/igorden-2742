package domain;

import exceptions.TimeCardIllegalArgException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HourlyAdministrator extends Administrator implements JSONStringifiable{
    private double hourlyRate;
    private ArrayList<TimeCard> timeCards = new ArrayList<TimeCard>();

    public HourlyAdministrator(int personId, String firstName, String lastName, String username, LocalDateTime birthDate, String ssn,
                               String phone, LocalDateTime employmentStartDate, double hourlyRate) {
        super(personId, firstName, lastName, username, birthDate, ssn, phone, employmentStartDate);
        this.hourlyRate = hourlyRate;
    }
    public void addTimeCard(LocalDateTime startDateTime, LocalDateTime endDateTime){
        TimeCard card = null;
        try {
            card = new TimeCard(startDateTime, endDateTime);
        }
        catch (TimeCardIllegalArgException e) {
            throw new TimeCardIllegalArgException("Invalid start/end in TimeCard ofr personId=" + this.getPersonId()+". " + e.getMessage());
        }
        timeCards.add(card);
    }

    public TimeCard removeTimeCard(int index){
        if(timeCards.size()<index || timeCards.size() ==0){
            return null;
        }
        TimeCard timecard = new TimeCard(timeCards.get(index));
        timeCards.remove(index);
        return timecard;
    }

    public TimeCard getTimeCard(int index){
        if(timeCards.size()<index || timeCards.size() ==0){
            return null;
        }
        return new TimeCard(timeCards.get(index));
    }

    @Override
    public String toString() {
        return super.toString() +
                " HourlyAdministrator{" +
                "hourlyRate=" + hourlyRate +
                //", timeCards=" + timeCards +
                ", birthDate=" + birthDate +
                ", ssn='" + ssn + '\'' +
                ", phone='" + phone + '\'' +
                ", employmentStartDate=" + employmentStartDate +
                '}';
    }

    public String jsonStringify(){
        StringBuilder sb = new StringBuilder(200);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        StringBuilder timeCardsString = new StringBuilder(200);
        for(TimeCard card : timeCards){
            timeCardsString.append(card.jsonStringify()+", \n\t   ");
        }
        //removes the last 4 characters from the string
        timeCardsString.delete(timeCardsString.length()-7, timeCardsString.length()-1);
        sb.append("    {\n" +
                "      \"subclass\": \"hourlyAdministrator\",\n" +
                "      \"personId\": "+super.getPersonId()+",\n" +
                "      \"lastName\": \""+super.getLastName()+"\",\n" +
                "      \"firstName\": \""+super.getFirstName()+"\",\n" +
                "      \"userName\": \""+super.getUserName()+"\",\n" +
                "      \"birthDate\": \""+birthDate.format(formatter) + "\",\n" +
                "      \"ssn\": \""+ssn+"\",\n" +
                "      \"phone\": \""+phone+"\",\n" +
                "      \"employmentStartDate\": \""+employmentStartDate.format(formatter) +"\",\n" +
                "      \"hourlyRate\": "+hourlyRate+",\n" +
                "      \"timeCards\": [\n" +
                "       "+ timeCardsString.toString() + "\n" +
                "      ]\n" +
                "    }");

        return sb.toString();
    }

    @Override
    public double calcGrossPay(){

        double total = 0;
        for(TimeCard card : timeCards){
            LocalDateTime start = card.getStartDate();
            LocalDateTime end = card.getEndDate();
            Duration duration = Duration.between(start, end);
           total += duration.toHours()*this.hourlyRate;
        }
        return total;
    }

    public void addTimeCard(TimeCard card) {
        timeCards.add(card);
    }

    public ArrayList<TimeCard> getTimeCards() {
        return new ArrayList<TimeCard>(timeCards);
    }

    public double calcTotalHours() {
        double totalHours =0;
        for(TimeCard card : timeCards){
            Duration duration = Duration.between(card.getStartDate(), card.getEndDate());
            totalHours += duration.toHours();
        }
        return totalHours;
    }
}
