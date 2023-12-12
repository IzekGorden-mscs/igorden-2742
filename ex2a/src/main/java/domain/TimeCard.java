package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TimeCard {
    private int timeCardId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public TimeCard(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.timeCardId = DbContext.getNextTimeCardId();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private TimeCard(int timeCardId, LocalDateTime startDateTime) {
        this.timeCardId = timeCardId;
        this.startDateTime = startDateTime;
    }
    public TimeCard(TimeCard timeCard) {
        this.timeCardId = timeCard.timeCardId;
        this.startDateTime = timeCard.startDateTime;
        this.endDateTime = timeCard.endDateTime;
    }
    public TimeCard copy(){
        return new TimeCard(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");
        return "TimeCard{" +
                "timeCardId=" + timeCardId +
                ", startDateTime=" + startDateTime.format(formatter) +
                ", endDateTime=" + endDateTime.format(formatter) +
                '}';
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
