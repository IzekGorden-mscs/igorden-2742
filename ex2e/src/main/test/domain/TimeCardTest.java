package domain;

import exceptions.TimeCardIllegalArgException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCardTest {
    TimeCard timeCard;

    @BeforeEach
    void setUp() {
        this.timeCard = new TimeCard(
                LocalDateTime.of(2018, 10, 22, 8, 0),
                LocalDateTime.of(2018, 10, 22, 18, 0));
    }

    @Test
    void copy() {
        TimeCard timeCard2 = this.timeCard.copy();
        assertEquals(this.timeCard, timeCard2);
    }

    @Test
    void toStringTest() {
        String strTimeCard = this.timeCard.toString();
        int i = strTimeCard.indexOf("startDateTime");
        strTimeCard = strTimeCard.substring(i);
        String result = "startDateTime=2018/10/22 08:00AM, endDateTime=2018/10/22 06:00PM, hours=10.00";
        assertEquals(result, strTimeCard);
    }

    @Test
    void startTimeEqualsEndTimeTest() {
        assertThrows(TimeCardIllegalArgException.class,
                () -> new TimeCard(
                        LocalDateTime.of(2018, 10, 22, 8, 0),
                        LocalDateTime.of(2018, 10, 22, 8, 0)));
    }

    @Test
    void startTimeAfterEndTimeTest() {
        assertThrows(TimeCardIllegalArgException.class,
                () -> new TimeCard(
                        LocalDateTime.of(2018, 10, 22, 9, 0),
                        LocalDateTime.of(2018, 10, 22, 8, 0)));
    }

    //works in batch, but not when run solo
    @Test
    void jsonStringifyTest() {
        assertEquals("{\"id\": 10002, \"startDateTime\": \"2018/10/22 08:00AM\",\"endDateTime\": \"2018/10/22 06:00PM\"}",
                this.timeCard.jsonStringify());
    }
}