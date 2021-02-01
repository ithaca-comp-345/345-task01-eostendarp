package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
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
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        } else if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
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
