/* 
 * The MIT License
 *
 * Copyright 2021 Md Ausaf Rashid.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package thequestioner;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JOptionPane;
import static thequestioner.Credential_Storage.*;


 





/**
 *
 * @author Md Ausaf Rashid
 */
public class SignIn extends javax.swing.JFrame {

    
    int xMouse;
    int yMouse;
    
    //every class will get this master_username
    public static String master_username;
    public static String password;    
  
    
    
    Color white=new Color(204,204,204);
    
    /**
     * Creates new form SignUp_Part1
     */
    
    public void finalVerify(){
    
        if(loading.isVisible()==true){
            Greeting_Label.setVisible(false);
        }else{
            Greeting_Label.setVisible(true);
        }
        
        if(NameCheck&&NameCheck1){
            l8.setVisible(true);
        }else{
           l8.setVisible(false);
        }
        
   
    }
    
   
   
    
     boolean initialClickedNameInput=false;
     boolean NameCheck=false;
   
  
    
   Statement st = null;
   Connection conn = null;
   ResultSet rs = null;
    
void resetEverythingHere(){
    
    
    initialClickedNameInput=false;
     NameCheck=false;
    initialClickedNameInput1=false;
     NameCheck1=false;
     st = null;
     conn = null;
     rs = null;
}
    
    public SignIn() {
        
        
        initComponents();
        
       
        
        loading.setVisible(false);
        resetEverythingHere();
       new Worker().execute(); //Background task to run finalVerify() started. 
                               //Final verify checks if all fields are filled correctly and displays next button
                               
                               
        l8.setVisible(false);
        
        setBackground(new Color(0,0,0,0));//makes jframe background invisible. 
                                    
                



    
    }
    
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Greeting_Label = new javax.swing.JLabel();
        l7 = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        l4 = new javax.swing.JLabel();
        NameWarning1 = new javax.swing.JLabel();
        NameInput = new javax.swing.JTextField();
        l8 = new javax.swing.JLabel();
        MinLabel = new javax.swing.JLabel();
        ExitLabel = new javax.swing.JLabel();
        NameWarning = new javax.swing.JLabel();
        l3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(895, 645));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
        });
        getContentPane().setLayout(null);

        Greeting_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        Greeting_Label.setForeground(new java.awt.Color(0, 0, 255));
        Greeting_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Greeting_Label.setText("<html>\n<u>Sign Up</u>\n</html>");
        Greeting_Label.setToolTipText("");
        Greeting_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Greeting_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Greeting_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(Greeting_Label);
        Greeting_Label.setBounds(715, 87, 180, 23);

        l7.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        l7.setForeground(new java.awt.Color(255, 255, 255));
        l7.setText("Sign In:");
        getContentPane().add(l7);
        l7.setBounds(30, 150, 290, 50);

        loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/loading_4_small.gif"))); // NOI18N
        loading.setText("jLabel3");
        getContentPane().add(loading);
        loading.setBounds(400, 410, 30, 30);

        jPasswordField1.setBackground(new Color(0,0,0,0));
        jPasswordField1.setForeground(white);
        jPasswordField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordField1.setBorder(javax.swing.BorderFactory.createLineBorder(white));
        jPasswordField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jPasswordField1CaretUpdate(evt);
            }
        });
        jPasswordField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField1FocusLost(evt);
            }
        });
        getContentPane().add(jPasswordField1);
        jPasswordField1.setBounds(180, 260, 197, 30);

        l4.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l4.setForeground(white);
        l4.setText("Password:");
        getContentPane().add(l4);
        l4.setBounds(30, 260, 110, 30);

        NameWarning1.setForeground(new java.awt.Color(255, 255, 255));
        NameWarning1.setText(" ");
        NameWarning1.setMaximumSize(new java.awt.Dimension(197, 14));
        NameWarning1.setMinimumSize(new java.awt.Dimension(197, 14));
        NameWarning1.setPreferredSize(new java.awt.Dimension(197, 14));
        getContentPane().add(NameWarning1);
        NameWarning1.setBounds(180, 290, 197, 14);

        NameInput.setBackground((new Color(0,0,0,0)));
        NameInput.setForeground(white);
        NameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NameInput.setText("Your name goes here.");
        NameInput.setBorder(javax.swing.BorderFactory.createLineBorder(white));
        NameInput.setMaximumSize(new java.awt.Dimension(197, 20));
        NameInput.setMinimumSize(new java.awt.Dimension(197, 20));
        NameInput.setOpaque(false);
        NameInput.setPreferredSize(new java.awt.Dimension(197, 20));
        NameInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NameInputCaretUpdate(evt);
            }
        });
        NameInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NameInputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NameInputFocusLost(evt);
            }
        });
        NameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameInputActionPerformed(evt);
            }
        });
        getContentPane().add(NameInput);
        NameInput.setBounds(180, 210, 197, 30);

        l8.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l8.setForeground(new java.awt.Color(0, 0, 255));
        l8.setText("<html> <body> <u>Sign In</u> </body> </html>");
        l8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        l8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                l8MouseReleased(evt);
            }
        });
        getContentPane().add(l8);
        l8.setBounds(340, 410, 60, 30);

        MinLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/minbutton.png"))); // NOI18N
        MinLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MinLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MinLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MinLabelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MinLabelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MinLabelMouseReleased(evt);
            }
        });
        getContentPane().add(MinLabel);
        MinLabel.setBounds(845, 5, 20, 20);

        ExitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ExitLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/exitbutton.png"))); // NOI18N
        ExitLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ExitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitLabelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ExitLabelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ExitLabelMouseReleased(evt);
            }
        });
        getContentPane().add(ExitLabel);
        ExitLabel.setBounds(865, 5, 20, 20);

        NameWarning.setForeground(new java.awt.Color(255, 255, 255));
        NameWarning.setText(" ");
        NameWarning.setMaximumSize(new java.awt.Dimension(197, 14));
        NameWarning.setMinimumSize(new java.awt.Dimension(197, 14));
        NameWarning.setPreferredSize(new java.awt.Dimension(197, 14));
        getContentPane().add(NameWarning);
        NameWarning.setBounds(180, 240, 160, 14);

        l3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l3.setForeground(white);
        l3.setText("Username:");
        getContentPane().add(l3);
        l3.setBounds(30, 210, 90, 30);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/imageedit_3_7186155792.gif"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(380, 120, 520, 410);

        jLabel1.setBackground((new Color(0,0,0,0)));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/final_background.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(895, 645));
        jLabel1.setMinimumSize(new java.awt.Dimension(895, 645));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(895, 645));
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 895, 645);

        setSize(new java.awt.Dimension(895, 645));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        this.setLocation(evt.getXOnScreen() - xMouse, evt.getYOnScreen() - yMouse);           // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        xMouse = evt.getX();        // TODO add your handling code here:
        yMouse = evt.getY();         // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MousePressed

    private void ExitLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseClicked
      
        // TODO add your handling code here:
    }//GEN-LAST:event_ExitLabelMouseClicked

    private void MinLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinLabelMouseClicked
           // TODO add your handling code here:
    }//GEN-LAST:event_MinLabelMouseClicked

    private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
         FadeUtilityClass.fade(this, true);         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowDeiconified

    private void ExitLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseEntered
        
        ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/exit_green.gif"));
        ExitLabel.setIcon(II);
            // TODO add your handling code here:
    }//GEN-LAST:event_ExitLabelMouseEntered

    private void ExitLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseExited
ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/exitbutton.png"));
        ExitLabel.setIcon(II);       
        
// TODO add your handling code here:
    }//GEN-LAST:event_ExitLabelMouseExited

    private void ExitLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMousePressed
              ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/exit_darker_green.gif"));
        ExitLabel.setIcon(II);  // TODO add your handling code here:
    }//GEN-LAST:event_ExitLabelMousePressed

    private void ExitLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLabelMouseReleased
       ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/exit_green.gif"));
        ExitLabel.setIcon(II);
        System.exit(0);
// TODO add your handling code here:
    }//GEN-LAST:event_ExitLabelMouseReleased

    
    
    
    
    private void MinLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinLabelMouseEntered
       ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/min_green.gif"));
        MinLabel.setIcon(II); // TODO add your handling code here:
    }//GEN-LAST:event_MinLabelMouseEntered

    private void MinLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinLabelMouseExited
  ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/minbutton.png"));
        MinLabel.setIcon(II);        // TODO add your handling code here:
    }//GEN-LAST:event_MinLabelMouseExited

    private void MinLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinLabelMousePressed
      ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/min_darker_green.gif"));
        MinLabel.setIcon(II);  // TODO add your handling code here:
    }//GEN-LAST:event_MinLabelMousePressed

    private void MinLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinLabelMouseReleased
       ImageIcon II = new ImageIcon(getClass().getResource("/thequestioner/min_green.gif"));
        MinLabel.setIcon(II); 
 FadeUtilityClass.fade(this, false); 
// TODO add your handling code here:
    }//GEN-LAST:event_MinLabelMouseReleased


void establishOfflineConnection(){
       try{
    Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
            
            st=conn.createStatement();}catch(Exception e){}
    }

void closeConnection(){
        try{
            conn.close();
            st.close();
            rs.close();
        }catch(Exception e){
            //ignore
        }
    }
public void showNetworkError(){
    JOptionPane.showMessageDialog(null,"Network Error");
    closeConnection();
}

public boolean EmailValidate(String email1) {

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        Boolean b = email1.matches(EMAIL_REGEX);
            
            if(email1.length()>40)
                            b=false;
        

        return b;
    }
void checkUsernamePasswordValidity(){
       establishOfflineConnection();
       
       //check PasswordInput and master_username are correct.
       
       if(EmailValidate(master_username)){
           String query="select Username,Email,Password from userinfotable where Email="
                   + "'"
                   + master_username
                   + "';";
       try{
           
           rs=st.executeQuery(query);
           rs.next();

           
           try{
               String testing=rs.getString("Email");
               master_username=rs.getString("Username");
               String correct_password=rs.getString("Password");
               
               if(PasswordInput.equals(correct_password)){
                   System.out.println("Login Successfull!");
                   redirectToHomePage();
                   
               }else{
                   System.out.println("Incorrect password!");
                   JOptionPane.showMessageDialog(null, "Incorrect Password!","Login Error",2);
                   jPasswordField1.setText(null);
               }
               
           }catch(Exception e){
               if("Illegal operation on empty result set.".equals(e.getMessage())){
                   System.out.println("Username not found!");
                   JOptionPane.showMessageDialog(null, "Email not registered!","Login Error",2);
               }
               
               
           }
           
           
           
       }catch(Exception e){
           
           showNetworkError();
       
       }
           
           
       }else{
           String query="select Username,Email,Password from userinfotable where Username="
                   + "'"
                   + master_username
                   + "';";
       try{
           
           rs=st.executeQuery(query);
           rs.next();

           
           try{
               String testing=rs.getString("Username");
               
               String correct_password=rs.getString("Password");
               
               if(PasswordInput.equals(correct_password)){
                   System.out.println("Login Successfull!");
                   redirectToHomePage();
                   
                   
               }else{
                   System.out.println("Incorrect password!");
                   JOptionPane.showMessageDialog(null, "Incorrect Password!","Login Error",2);
                   jPasswordField1.setText(null);
               }
               
           }catch(Exception e){
               if("Illegal operation on empty result set.".equals(e.getMessage())){
                   System.out.println("Username not found!");
                   JOptionPane.showMessageDialog(null, "Username not registered!","Login Error",2);
               }
               
               
           }
           
           
           
       }catch(Exception e){
           
           showNetworkError();
       
       }
       
       }
       
       closeConnection();
   }
    
    private void start1() {
  Thread worker = new Thread() {
   public void run() {
    
       checkUsernamePasswordValidity();
       
   }
  };
  
  worker.start();
 }
   
    void redirectToHomePage(){
        
        HOMEPAGE obj=new HOMEPAGE();
        obj.setVisible(true);
        this.dispose();
    }
    
    int xMouse1, yMouse1;
    private void l8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l8MouseReleased

        master_username=NameInput.getText();        
        
        start1(); //analogous to start, with more functions
   
        // TODO add your handling code here:
    }//GEN-LAST:event_l8MouseReleased

    private void NameInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NameInputCaretUpdate

        
        if(initialClickedNameInput){
            NameCheck=checkNameEnteredIsLegalAndShowWarning();
        }else{
            NameCheck=checkNameEnteredIsLegal();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_NameInputCaretUpdate
public boolean checkNameEnteredIsLegalAndShowWarning(){

    boolean result;
    int NameLength=NameInput.getText().length();
    
    if(NameLength<3){
        showLabelWarning(NameWarning, Color.RED, "Username is too short.",NameInput);
        result=false;
    }else if(NameLength>30){
        showLabelWarning(NameWarning, Color.RED, "Username is too long.", NameInput);
        result=false;
    }else{
        
        showLabelWarning(NameWarning, new Color(0,255,1,255),"",NameInput);
        result=true;
    }
    
    return result;
}

public boolean checkNameEnteredIsLegal(){

    boolean result;
    int NameLength=NameInput.getText().length();
    
    if(NameLength<3){
       result=false;
       NameInput.setBorder(BorderFactory.createLineBorder(white));
    }else if(NameLength>30){
        result=false;
        showLabelWarning(NameWarning, Color.RED, "Username is too long.",NameInput);
    }else{
        
         showLabelWarning(NameWarning, new Color(0,255,1,255),"",NameInput);
        result=true;
    }
    
    return result;
}
    private void NameInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameInputFocusGained

        if(initialClickedNameInput==false){
            NameInput.setText("");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_NameInputFocusGained

    private void NameInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameInputFocusLost

        initialClickedNameInput=true; //boolean variable to show that user has clicked on that component atleast once
        NameCheck=checkNameEnteredIsLegalAndShowWarning();
        // TODO add your handling code here:
    }//GEN-LAST:event_NameInputFocusLost
 String PasswordInput;
public boolean checkNameEnteredIsLegalAndShowWarning1(){

    boolean result;
     PasswordInput = new String(jPasswordField1.getPassword());
    int NameLength=PasswordInput.length();
    
    if(NameLength<3){
        showLabelWarning(NameWarning1, Color.RED, "Password is too short.",jPasswordField1);
        result=false;
    }else if(NameLength>30){
        showLabelWarning(NameWarning1, Color.RED, "Password is too long.", jPasswordField1);
        result=false;
    }else{
        
        showLabelWarning(NameWarning1, new Color(0,255,1,255),"",jPasswordField1);
        result=true;
    }
    
    return result;
}
 boolean NameCheck1=false;
boolean initialClickedNameInput1=false;
public boolean checkNameEnteredIsLegal1(){
PasswordInput = new String(jPasswordField1.getPassword());
    boolean result;
    int NameLength=PasswordInput.length();
    
    if(NameLength<3){
       result=false;
       jPasswordField1.setBorder(BorderFactory.createLineBorder(white));
    }else if(NameLength>30){
        result=false;
        showLabelWarning(NameWarning1, Color.RED, "Password is too long.",jPasswordField1);
    }else{
        
         showLabelWarning(NameWarning1, new Color(0,255,1,255),"",jPasswordField1);
        result=true;
    }
    
    return result;
}
    private void NameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameInputActionPerformed

    private void jPasswordField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jPasswordField1CaretUpdate
 if(initialClickedNameInput1){
            NameCheck1=checkNameEnteredIsLegalAndShowWarning1();
        }else{
            NameCheck1=checkNameEnteredIsLegal1();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1CaretUpdate

    private void jPasswordField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusLost
 initialClickedNameInput1=true; //boolean variable to show that user has clicked on that component atleast once
        NameCheck1=checkNameEnteredIsLegalAndShowWarning1();        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1FocusLost

    private void Greeting_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Greeting_LabelMouseReleased
        
        SignUp_Part1 obj =new SignUp_Part1();
        obj.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_Greeting_LabelMouseReleased

 
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
      




        java.awt.EventQueue.invokeLater(new Runnable() {
             
            public void run() {
              
      
               new SignIn().setVisible(true);
                
            }
            
            
        });

        

   

   
       

    }
    
  
 
  

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ExitLabel;
    private javax.swing.JLabel Greeting_Label;
    private javax.swing.JLabel MinLabel;
    private javax.swing.JTextField NameInput;
    private javax.swing.JLabel NameWarning;
    private javax.swing.JLabel NameWarning1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel l3;
    private javax.swing.JLabel l4;
    private javax.swing.JLabel l7;
    private javax.swing.JLabel l8;
    private javax.swing.JLabel loading;
    // End of variables declaration//GEN-END:variables







public void showLabelWarning(JLabel label, Color color, String message, JTextField textfield){
    label.setText(message);
      label.setForeground(color);
    textfield.setBorder(BorderFactory.createLineBorder(color));

}

public void showLabelWarning( Color color,  JComboBox jcombobox){
    
    jcombobox.setBorder(BorderFactory.createLineBorder(color));   
    
}

public void showLabelWarning(JLabel label, Color color, String message){
    label.setText(message);
    label.setForeground(color);
}





public class Worker extends SwingWorker<String, String> {

    @Override
    protected String doInBackground() throws Exception {
        //This is what's called in the .execute method
        for(int i = 0; true; ){
            //This sends the results to the .process method
      publish(String.valueOf(i));
            Thread.sleep(100);
        }
//        return null;
    }

    @Override
    protected void process(List<String> item) {
        //This updates the UI
//        textArea.append(item + "\n");
           finalVerify();
          
           
           
           //put background task here
    }
   
}

}
