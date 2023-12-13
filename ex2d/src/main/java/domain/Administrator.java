package domain;

import java.time.LocalDateTime;

public abstract class Administrator extends Person{
    LocalDateTime birthDate;
    String ssn;
    String phone;
    LocalDateTime employmentStartDate;

    public Administrator(int personId, String firstName, String lastName, String username, LocalDateTime birthDate, String ssn, String phone, LocalDateTime employmentStartDate) {
        super(personId, firstName, lastName, username);
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.phone = phone;
        this.employmentStartDate = employmentStartDate;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(LocalDateTime employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Administrator{" +
                "birthDate=" + birthDate +
                ", ssn='" + ssn + '\'' +
                ", phone='" + phone + '\'' +
                ", employmentStartDate=" + employmentStartDate +
                '}';
    }

    public abstract double calcGrossPay();

}
