package org.fasttrackit.online_shop.transfer.customer;

import com.sun.istack.NotNull;

public class CreateCustomerRequest {
    @NotNull
    private String lastName;
    @NotNull
    private  String firstName;

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
        return "CreateCustomerRequest{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
