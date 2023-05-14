package io.lightfeather.springtemplate.dto;

public class Supervisor {
    private String jurisdiction;
    private String lastName;
    private String firstName;
    public Supervisor(){
    }

    public Supervisor(String jurisdiction, String lastName, String firstName) {
        this.jurisdiction = jurisdiction;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return jurisdiction + " - " + lastName +
                ", " + firstName;
    }
}
