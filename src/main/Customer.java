package main;

import java.util.Arrays;

public class Customer {
    private String customerReference;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String county;
    private String country;
    private String postCode;

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String ID) {
        this.customerReference = ID;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Customer(String[] customerAttributes) {
        if (customerAttributes.length >= 9) {
            this.customerReference = customerAttributes[0];
            this.firstName = customerAttributes[1];
            this.lastName = customerAttributes[2];
            this.addressLine1 = customerAttributes[3];
            this.addressLine2 = customerAttributes[4];
            this.town = customerAttributes[5];
            this.county = customerAttributes[6];
            this.country = customerAttributes[7];
            this.postCode = customerAttributes[8];
        } else {
            System.out.println(STR."Invalid Customer Details for \{Arrays.toString(customerAttributes)}");
        }


    }

}
