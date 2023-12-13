package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenantTest {
    Tenant tenant= null;
    @BeforeEach
    void setUp() {
        tenant = new Tenant(101, "Nemo", "Little", "NemoLitt", LocalDateTime.of(2001, 01, 23, 0, 0, 0),
                "1234567890", "6511234567", "Target", "Customer Service Representative", 20.00, LocalDateTime.of(2023, 12, 12, 0, 0, 0).minusDays(999));

    }

    @Test
    void testToString() {
        String result = "101, Nemo Little Tenant{birthDate=2001-01-23T00:00, ssn='1234567890', phone='6511234567', employer='Target', occupation='Customer Service Representative', grossPay=20.0, employmentStartDate=2021-03-18T00:00}";
        assertEquals(tenant.toString(), result);
    }
}