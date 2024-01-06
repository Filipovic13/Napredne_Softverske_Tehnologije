package com.nst.domaci1.domain;

import java.io.Serializable;

public class MemberID implements Serializable {

    private String firstName;

    private String lastName;

    public MemberID() {
    }

    public MemberID(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
