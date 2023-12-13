package domain;

import domain.HourlyAdministrator;
import domain.TimeCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class HourlyAdministratorTest {

    private HourlyAdministrator hourlyAdmin;

    @BeforeEach
    void setUp() {
        hourlyAdmin = new HourlyAdministrator(
                201, "John", "Doe", "john.doe", LocalDateTime.of(1990, 5, 15, 0, 0, 0),
                "987654321", "555-1234", LocalDateTime.of(2023, 12, 12, 0, 0, 0).minusDays(365), 15.0);

        // Add some TimeCard entries for testing
        LocalDateTime startTime1 = LocalDateTime.of(2023, 1, 1, 9, 0, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2023, 1, 1, 17, 0, 0);
        hourlyAdmin.addTimeCard(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2023, 1, 2, 10, 0, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2023, 1, 2, 18, 0, 0);
        hourlyAdmin.addTimeCard(startTime2, endTime2);
    }

    @Test
    void toStringTest() {
        String result = "201, John Doe Administrator{birthDate=1990-05-15T00:00, ssn='987654321', phone='555-1234', employmentStartDate=2022-12-12T00:00} HourlyAdministrator{hourlyRate=15.0, timeCards=[TimeCard{timeCardId=10003, startDateTime=2023/01/01 09:00AM, endDateTime=2023/01/01 05:00PM}, TimeCard{timeCardId=10004, startDateTime=2023/01/02 10:00AM, endDateTime=2023/01/02 06:00PM}], birthDate=1990-05-15T00:00, ssn='987654321', phone='555-1234', employmentStartDate=2022-12-12T00:00}";
        assertEquals(hourlyAdmin.toString(), result);
    }

    @Test
    void calcGrossPay() {
        assertEquals(240.0, this.hourlyAdmin.calcGrossPay());
    }

    @Test
    void addTimeCard() {

        LocalDateTime startTime3 = LocalDateTime.of(2023, 1, 3, 8, 0, 0);
        LocalDateTime endTime3 = LocalDateTime.of(2023, 1, 3, 16, 0, 0);
        TimeCard card = new TimeCard(startTime3, endTime3);
        hourlyAdmin.addTimeCard(card);

        assertEquals(card, hourlyAdmin.getTimeCard(2));
    }

    @Test
    void removeTimeCard() {
        LocalDateTime startTime3 = LocalDateTime.of(2023, 1, 3, 8, 0, 0);
        LocalDateTime endTime3 = LocalDateTime.of(2023, 1, 3, 16, 0, 0);
        TimeCard card = new TimeCard(startTime3, endTime3);
        hourlyAdmin.addTimeCard(card);


        TimeCard removedTimeCard = hourlyAdmin.removeTimeCard(2);
        assertEquals(card, removedTimeCard);
        assertNotSame(card, removedTimeCard);
    }

}
