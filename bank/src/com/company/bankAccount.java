package com.company;

public class bankAccount {

    private int number;
    private double balance;
    private String name;
    private String email;
    private int phoneNumber;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void deposit(double value){
        setBalance(getBalance() + value);
    }
    public void withdraw(double value){
        if (balance > value) {
            setBalance(getBalance() - value);
        }else{
            System.out.println("insufficient funds!");
        }
    }
}
