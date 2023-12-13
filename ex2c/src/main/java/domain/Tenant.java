package domain;

import java.time.LocalDateTime;

public class Tenant extends Person{
    private LocalDateTime birthDate;
    private String ssn;
    private String phone;
    private String employer;
    private String occupation;
    private double grossPay;
    private LocalDateTime employmentStartDate;


    @Override
    public String toString() {
        return super.toString() +
                " Tenant{" +
                "birthDate=" + birthDate +
                ", ssn='" + ssn + '\'' +
                ", phone='" + phone + '\'' +
                ", employer='" + employer + '\'' +
                ", occupation='" + occupation + '\'' +
                ", grossPay=" + grossPay +
                ", employmentStartDate=" + employmentStartDate +
                '}';
    }

    public Tenant(int personId, String FirstName, String LastName, String userName, LocalDateTime birthDate,
                  String ssn, String phone, String employer, String occupation, double grossPay, LocalDateTime employmentStartDate) {
        super(personId, FirstName, LastName, userName);
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.phone = phone;
        this.employer = employer;
        this.occupation = occupation;
        this.grossPay = grossPay;
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

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public LocalDateTime getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(LocalDateTime employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }
}
