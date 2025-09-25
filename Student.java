package com.sis.model;

public class Student {
    private String registerNo;
    private String firstName;
    private String lastName;

    public Student() {}

    public Student(String registerNo, String firstName, String lastName) {
        this.registerNo = registerNo;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRegisterNo() { return registerNo; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        return registerNo + ": " + firstName + " " + lastName;
    }
}
