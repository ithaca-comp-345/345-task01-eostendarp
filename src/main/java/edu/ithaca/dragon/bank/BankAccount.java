package edu.ithaca.dragon.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * Returns true if amount has 2 or fewer decimal places and is non-negative, false otherwise
     * @param amount value to check
     * @return true if amount is valid, false otherwise
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        } else if (BigDecimal.valueOf(amount).scale() > 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates a BankAccount object if email and amount are valid
     * @param email account email address
     * @param startingBalance account starting balance
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)){
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if (isAmountValid(startingBalance)) {
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * Subracts amount from balance if amount is non-negative and greater than or equal to balance
     * @param amount value to withdraw from balance
     * @throws InsufficientFundsException
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("amount must be non-negative and have 2 or fewer decimal places");
        } else if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * Adds amount to balance if amount is valid
     * @param amount amount to deposit
     */
    public void deposit(double amount) {
        // TODO implement after tests
    }

    public static boolean isEmailValid(String email){
        
        //returns false if there is no '@' or '.'
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1){
            return false;
        }

        //returns false if there is an improper character at the end of the email address
        else if(email.charAt(email.length()-1)== '.' || email.charAt(email.length()-1)== '@' || email.charAt(email.length()-1)== '-'){
            return false;
        }

        //returns false if there is an improper character at the beginning of the email address
        else if(email.charAt(0)== '@' || email.charAt(0)== '_' || email.charAt(0)== '-' || email.charAt(0)== '.'){
            return false;
        }

        //returns false if there is an improper combination of characters
        else if(email.contains("@.") || email.contains("..") || email.contains(".@") || email.contains("_@") || email.contains("-@") || email.contains("@-") || email.contains("-.")){
            return false;
        }

        //returns false if the email contains improper characters
        else if(email.indexOf('#') != -1 || email.indexOf('*') != -1 || email.indexOf('%') != -1 || email.indexOf('(') != -1 || email.indexOf(')') != -1 || email.indexOf('^') != -1){
            return false;
        }

        //returns false if last part of domain length < 2
        else if(email.charAt(email.length()-2) == '.'){
            return false;
        }

        else if(email.indexOf('@') != -1){
            int count=0;
            for(int i=0; i<email.length(); i++){
                if(email.charAt(i)== '@'){
                    count++;
                }
            }
            if(count>1){
                return false;
            }
            return true;
        }
        else{
            return true;
        }
        
    }
}
