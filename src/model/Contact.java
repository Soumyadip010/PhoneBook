package model;

import java.math.BigDecimal;

public class Contact {
    private String name,email,address;
    private BigDecimal number,id;

    public Contact(String name,BigDecimal number, String email, String address) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
    }

    //GETTERS of the variables :
    public BigDecimal getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getNumber() {
        return number;
    }

    //SETTERS of the variable :
    public void setId(BigDecimal id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
