package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class TimeCardTest {
    TimeCard card;
    @BeforeEach
    void setUp() {
        LocalDateTime startTime1 = LocalDateTime.of(2023, 1, 1, 9, 0, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2023, 1, 1, 17, 0, 0);
        card = new TimeCard(startTime1, endTime1);
    }

    @Test
    void copy() {
        TimeCard card2 = card.copy();
        assertEquals(card2, card);
        assertNotSame(card, card2);
    }

    @Test
    void testToString() {
        String result = "TimeCard{timeCardId=10001, startDateTime=2023/01/01 09:00AM, endDateTime=2023/01/01 05:00PM}";
        assertEquals(result, card.toString());
    }
}