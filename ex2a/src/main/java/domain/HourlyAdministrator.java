package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HourlyAdministrator extends Administrator {
    private double hourlyRate;
    private ArrayList<TimeCard> timeCards = new ArrayList<TimeCard>();

    public HourlyAdministrator(int personId, String firstName, String lastName, String username, LocalDateTime birthDate, String ssn,
                               String phone, LocalDateTime employmentStartDate, double hourlyRate) {
        super(personId, firstName, lastName, username, birthDate, ssn, phone, employmentStartDate);
        this.hourlyRate = hourlyRate;
    }
    public void addTimeCard(LocalDateTime startDateTime, LocalDateTime endDateTime){
        TimeCard card = new TimeCard(startDateTime, endDateTime);
        timeCards.add(card);
    }

    public TimeCard removeTimeCard(int index){
        TimeCard timecard = new TimeCard(timeCards.get(index));
        timeCards.remove(index);
        return timecard;
    }

    public TimeCard getTimeCard(int index){
        return new TimeCard(timeCards.get(index));
    }

    @Override
    public String toString() {
        return super.toString() +
                " HourlyAdministrator{" +
                "hourlyRate=" + hourlyRate +
                ", timeCards=" + timeCards +
                ", birthDate=" + birthDate +
                ", ssn='" + ssn + '\'' +
                ", phone='" + phone + '\'' +
                ", employmentStartDate=" + employmentStartDate +
                '}';
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
}
