// Valerie Hlavinka
// CSC 240 Program 1
// 9-21-2017

package controller;

public class Database {
    
    public String usernameDB;
    public String passwordDB;
    
    public Database() {
      
    }
    
    public Database(String userPass) {
        usernameDB = userPass.substring(0,18);
        passwordDB = userPass.substring(19);
    }
    public void setPasswordDB (String userPass) {
        passwordDB = userPass;
    }
    public String getUsernameDB () {
        return usernameDB;
    }
    public String getPasswordDB () {
        return passwordDB;
    }
            
    public static void main(String[] args) {

    
    }

    
}
