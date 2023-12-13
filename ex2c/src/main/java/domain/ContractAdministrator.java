package domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class ContractAdministrator extends Administrator{
private double monthlyRate;
    public ContractAdministrator(int personId, String firstName, String lastName, String username,
                                 LocalDateTime birthDate, String ssn, String phone, LocalDateTime employmentStartDate, double monthlyRate) {
        super(personId, firstName, lastName, username, birthDate, ssn, phone, employmentStartDate);
        this.monthlyRate = monthlyRate;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    @Override
    public double calcGrossPay(){
        Duration duration = Duration.between(employmentStartDate, LocalDateTime.now());
        return monthlyRate * duration.toDays()/30;
    }
}
