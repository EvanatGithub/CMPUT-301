package com.company;

public class Main {

    public static void main(String[] args) {
	    bankAccount guy = new bankAccount();
	    guy.setBalance(50);
	    guy.deposit(25);
        System.out.println(guy.getBalance());
        guy.withdraw(74);
        System.out.println(guy.getBalance());
        guy.withdraw(20);
        System.out.println(guy.getBalance());
    }
}
