package domain;

import exceptions.TimeCardIllegalArgException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TimeCard implements JSONStringifiable {
    private int timeCardId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public TimeCard(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)) {
            String msg = "startDate must be before endDate\n"+
                    "\tStart: " + startDateTime+
                    "\n\tEnd: " + endDateTime;
            //System.out.println(msg);
            throw new TimeCardIllegalArgException(msg);
        }
        else{
            this.timeCardId = DbContext.getNextTimeCardId();
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            }
    }

    private TimeCard(int timeCardId, LocalDateTime startDateTime) {
        this.timeCardId = timeCardId;
        this.startDateTime = startDateTime;
    }
    public TimeCard(TimeCard timeCard) {
        if(timeCard.startDateTime.isBefore(timeCard.endDateTime)) {
            this.timeCardId = timeCard.timeCardId;
            this.startDateTime = timeCard.startDateTime;
            this.endDateTime = timeCard.endDateTime;
        }
        else{
            String msg = "startDate must be before endDate\n"+
                    "\tStart: " + timeCard.startDateTime+
                    "\n\tEnd: " + timeCard.endDateTime;
            throw new TimeCardIllegalArgException(msg);
        }
    }

    public TimeCard(int id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(startDateTime.isBefore(endDateTime)) {
            this.timeCardId = id;
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
        }
        else{
            String msg = "startDate must be before endDate\n"+
                    "\tStart: " + startDateTime+
                    "\n\tEnd: " + endDateTime;
            throw new TimeCardIllegalArgException(msg);
        }
    }

    public TimeCard copy(){
        return new TimeCard(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");
        Double hours = (double) Duration.between(startDateTime, endDateTime).toHours();
        return "TimeCard{" +
                "timeCardId=" + timeCardId +
                ", startDateTime=" + startDateTime.format(formatter) +
                ", endDateTime=" + endDateTime.format(formatter) +
                ", hours=" + String.format("%.2f", hours);
    }

    public String jsonStringify(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");
        StringBuilder sb = new StringBuilder(200);
        sb.append("{\"id\": "+ this.timeCardId +", \"startDateTime\": \""+ this.startDateTime.format(formatter) +"\"," +"\"endDateTime\": \""+this.endDateTime.format(formatter)+"\"}");
        return sb.toString();
    }


    public LocalDateTime getStartDate() {
        return this.startDateTime;
    }

    public LocalDateTime getEndDate() {
        return this.endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeCard timeCard = (TimeCard) o;
        return timeCardId == timeCard.timeCardId &&
                Objects.equals(startDateTime, timeCard.startDateTime) &&
                Objects.equals(endDateTime, timeCard.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeCardId, startDateTime, endDateTime);
    }
}
