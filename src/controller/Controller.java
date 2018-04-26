// Valerie Hlavinka
// CSC 240 Program 1
// 9-21-2017

package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.MyFrame;

public class Controller {
  
    private int whichUser = 0; // identifies which user is logging in
      
// ARRAY DATABASE
    /* RIGHT WAY TO DO ARRAY LIST
    private static Database(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    */
    public static ArrayList<String> db = new ArrayList<>();
    static {
        db.add("ab111111@wcupa.edu a2Foofoo");
        db.add("cd222222@wcupa.edu b.Barbar");
        db.add("ef333333@wcupa.edu 123456.x");       
    }   
    Database u1 = new Database(db.get(0));
    Database u2 = new Database(db.get(1));
    Database u3 = new Database(db.get(2));

    private final MyFrame frame = new MyFrame();
       
//*********************************************************************************                                                                                
//  C O N S T R U C T O R
//********************************************************************************* 
  public Controller() {
    frame.setTitle( getClass().getSimpleName() );
    frame.setLocationRelativeTo(null);
 // EVENT HANDLERS
    JButton dmp = frame.getDumpDatabase();
    JButton change = frame.getChangePassButton();
    JTextField username = frame.getUsername();
    JTextField currentPass = frame.getCurrentPassword();
    JTextField newPass = frame.getNewPassword();
    JTextArea message = frame.getMessages();
    
 // SEED TEXT INTO FIELD ON STARTUP
    // 1. VALID USER AND NEW PASSWORD
    username.setText("ab111111@wcupa.edu");
    currentPass.setText("a2Foofoo");
    newPass.setText("a3Foofoo");
    
    // 2. VALID USER (CASE INSENSITIVE) BUT PASSWORD NOT STRONG ENOUGH
    //username.setText("AB111111@WCUPA.EDU");
    //currentPass.setText("a2Foofoo");
    //newPass.setText("aFoofoo");
    
    // 3. INVALID, USER NOT FOUND
    //username.setText("Ah111333@WCUPA.EDU");
    //currentPass.setText("a2Foofoo");
    //newPass.setText("a3Foofoo!");
    
 
 // DUMP DATABASE BUTTON
    dmp.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            for(int i = 0; i < 3; i++) {
              System.out.println(db.get(i)); // Print database to standard output 
            }                         
        }
    } );

// CHANGE PASSWORD BUTTON
    change.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            frame.getMessages().setEditable(false);
  
            String user = username.getText();
            String cPass = currentPass.getText();
            String nPass = newPass.getText();
            
            boolean isValid1 = isValidFormat(user); // username correct format 
            boolean isValid2 = isValidUser(user); // username found in database
            boolean isValid3 = isValidCurrentPass(cPass); // current password matches
            boolean isValid4 = isValidCharacters(nPass); // new password has 8 characters
            boolean isValid5 = isValidCurrentPass(nPass); // new password is not the same
            boolean isValid6 = isValidStrength(nPass); // new password is strong enough
            
            message.setText(null); // clear message window 
            
            if (!isValid1) {        
                message.setText("Validation: login format incorrect");
            } else
            if (!isValid2) {    
              message.setText("Validation: no such user exists");
            } else
            if (!isValid3){
                message.setText("Validation: current password incorrect");
            } else
            if (!isValid4) {
                message.setText("Validation: OK\nNew Password: too short");
            } else
            if (isValid5) {
                message.setText("Validation: OK\nNew Password: cannot be the current password");
            } else
            if (!isValid6) {
                message.setText("Validation: OK\nNew Password: too weak");
            } else {
                message.setText("Validation: OK\nNewPassword: OK");
                // call function to update password
                switch (whichUser) {
                    case 1: u1.passwordDB = nPass;
                        break;
                    case 2: u2.passwordDB = nPass;
                        break;
                    case 3: u3.passwordDB = nPass;
                        break;
                    default: message.setText("An unknown error has occured");
                        break;
                }
            }
        }
       } );
 
   
  }
//*********************************************************************************
//                                                                                 
//  P R I V A T E       M E M B E R     F U N C T I O N S
//
//********************************************************************************* 

  private boolean isValidFormat(String u) {
        // MUST have 2 letters + 6 digits + "@wcupa.edu"
        // EXAMPLE: vh888222@wcupa.edu
        if(u.length() != 18)
            return false;
        else if(u.substring(0,2).matches("[a-zA-Z]+") )
        {
            if(u.substring(2,8).matches("[\\d]+"))
            {
                if(u.substring(8).matches("(@wcupa.edu)|(@WCUPA.EDU)"))
                    return true;
            }
        }         
        return false;
    }

    private boolean isValidUser(String u) {                    
        // TEST THAT USERNAME IS IN DATABASE 
        u = u.toLowerCase();
        if (u.equals(u1.getUsernameDB())) {
            whichUser = 1;
            return true;
        }
        else if (u.equals(u2.getUsernameDB())) {
            whichUser = 2;
            return true;
        }
        else if (u.equals(u3.getUsernameDB())) {
            whichUser = 3;
            return true;
        }
        else 
            return false;
    } 
    
    private boolean isValidCurrentPass (String p) {          
        // TEST THAT PASSWORD MATCHES CORRECT USER
        boolean bool = false;
        switch (whichUser) {
            case 1: bool = p.equals(u1.getPasswordDB());
                break;
            case 2: bool = p.equals(u2.getPasswordDB());
                break;
            case 3: bool = p.equals(u3.getPasswordDB());
                break;
            default: bool = false;
                break;
        }
        return bool;
    }
    
    private boolean isValidCharacters (String p) {           
        // TEST THAT NEW PASSWORD IS 8 CHARACTERS
        int count = 0;
        
        for(int i = 0; i <= p.length(); i++)
            count++;
        if (count < 8 )
            return false;
        else
            return true;
    }
    
    private boolean isValidStrength (String p) {
        // TEST THAT NEW PASSWORD HAS 3 OF 4 CHARACTERISTICS: 
        // -LOWER CASE LETTER
        // -UPPERCASE LETTER
        // -DIGIT
        // -SPECIAL CHARACTERS
        int count = 0;
        
        if (p.matches(".*[a-z].*")) 
            count++;
        if (p.matches(".*[A-Z].*")) 
            count++;
        if (p.matches(".*[0-9].*"))
            count++;
        if (p.matches(".*[^a-zA-Z0-9].*"))
            count++;
        
        if(!(count >= 3))
            return false;
        else
            return true;
    }
 
//*********************************************************************************
//                                                                                 
//  M A I N 
//
//*********************************************************************************
  public static void main(String[] args) {
    Controller app = new Controller();
    app.frame.setVisible(true);

  }
}