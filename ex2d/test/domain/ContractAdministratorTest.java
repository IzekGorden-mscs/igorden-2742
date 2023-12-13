package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContractAdministratorTest {

    private ContractAdministrator contractAdmin;

    @BeforeEach
    void setUp() {
        contractAdmin = new ContractAdministrator(
                301, "Jane", "Smith", "jane.smith", LocalDateTime.of(1985, 8, 20, 0, 0, 0),
                "876543210", "555-5678", LocalDateTime.now().minusDays(180), 2000.0);
    }

    @Test
    void toStringTest(){
        String result = "301, Jane Smith Administrator{birthDate=1985-08-20T00:00, ssn='876543210', phone='555-5678', employmentStartDate=2023-06-15T00:00}";
        assertEquals(contractAdmin.toString(), result);
    }

    @Test
    void calcGrossPay() {
        assertEquals(12000.0, this.contractAdmin.calcGrossPay());
    }
}