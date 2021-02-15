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
import java.awt.Component;
import java.sql.*;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.*;

import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboPopup;

import static thequestioner.SignIn.master_username;
import static thequestioner.Credential_Storage.*;
/**
 *
 * @author Md Ausaf Rashid
 */

//Important Note: In this class, name(of SignUp_1 shares similar properties with Title of this class. Therefore there Name and Title can be used interchangely)
//Same goes for gender combo box
public class Ask_Question extends javax.swing.JFrame {

    
    int xMouse;
    int yMouse;
    
     Statement st = null;
     Connection conn = null;
     ResultSet rs = null;
    
     boolean initialClickedTopicInput=false;
    boolean TopicCheck=false;
    
    
 public void setBorderColorOfComboBoxPopup(JComboBox<String> comboBox, Color color){
    Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
    BasicComboPopup popup = (BasicComboPopup) child;
    popup.setBorder(new LineBorder(color));
}   
    

    
    
    
    void establishOfflineConnection(){
       try{
    Class.forName("com.mysql.jdbc.Driver");
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
            
            st=conn.createStatement();}catch(Exception e){}
    }
   
    
    Color white=new Color(204,204,204);
    
    /**
     * Creates new form SignUp_Part1
     */

    
       boolean initialClickedTitleInput=false;
    boolean TitleCheck=false;
    
    
    public void finalVerify(){
        

        
            ask_label.setVisible(TitleCheck&&TopicCheck);
        
            
        
   
}
  public void colorizeGenderChooserComboBox() {
        try{
        
            TopicChooserComboBox.setRenderer(new Colorizer<String>("Choose.."));
        TopicChooserComboBox.setSelectedIndex(-1); 
        }catch(Exception e){
            System.out.println(e);
        }
    }  
     DefaultComboBoxModel comboObject=null;
  private void getTopicNamesAndSetThemOnTheList(){
      
      establishOfflineConnection();
      try{
        
            String str="select topic_name from TopicNameTable where topic_name != 'NA';";

            rs=st.executeQuery(str);
            comboObject=(DefaultComboBoxModel)TopicChooserComboBox.getModel();
        while(rs.next())
            comboObject.addElement(rs.getString("topic_name"));
   TopicChooserComboBox.setModel(comboObject);
        }catch(Exception e){
            System.out.println("Exception from getTopicNamesAndSetThemOnTheList="+e);
            showNetworkError();
            closeConnection();
        }
      closeConnection();
  
  
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
  
    public Ask_Question() {
        

        
        
        initComponents();
        reset();
       new Worker().execute(); //Background task to run finalVerify() started. 
                               //Final verify checks if all fields are filled correctly and displays next button

                               
                               
                               Greeting_Label.setText("Hello, "+master_username+"!");
      getTopicNamesAndSetThemOnTheList();               
                               
        colorizeGenderChooserComboBox();
       setBorderColorOfComboBoxPopup(TopicChooserComboBox, white);
        
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
        GenderWarning = new javax.swing.JLabel();
        l6 = new javax.swing.JLabel();
        TopicChooserComboBox = new javax.swing.JComboBox<>();
        NameWarning = new javax.swing.JLabel();
        l5 = new javax.swing.JLabel();
        l4 = new javax.swing.JLabel();
        l3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        TitleInput = new javax.swing.JTextField();
        ask_label = new javax.swing.JLabel();
        MinLabel = new javax.swing.JLabel();
        ExitLabel = new javax.swing.JLabel();
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
        Greeting_Label.setForeground(new java.awt.Color(204, 204, 204));
        Greeting_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Greeting_Label.setText("Hello, 123456789123231233");
        Greeting_Label.setToolTipText("");
        Greeting_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Greeting_Label);
        Greeting_Label.setBounds(715, 93, 180, 23);

        GenderWarning.setForeground(new java.awt.Color(255, 255, 255));
        GenderWarning.setText(" ");
        GenderWarning.setMaximumSize(new java.awt.Dimension(197, 14));
        GenderWarning.setMinimumSize(new java.awt.Dimension(197, 14));
        GenderWarning.setPreferredSize(new java.awt.Dimension(197, 14));
        getContentPane().add(GenderWarning);
        GenderWarning.setBounds(142, 450, 197, 14);

        l6.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l6.setForeground(new java.awt.Color(255, 255, 255));
        l6.setText("Topic:");
        getContentPane().add(l6);
        l6.setBounds(30, 420, 70, 30);

        TopicChooserComboBox.setBackground(new Color(0,0,0,0));
        TopicChooserComboBox.setForeground(white);
        TopicChooserComboBox.setBorder(javax.swing.BorderFactory.createLineBorder(white));
        TopicChooserComboBox.setMaximumSize(new java.awt.Dimension(197, 20));
        TopicChooserComboBox.setMinimumSize(new java.awt.Dimension(197, 20));
        TopicChooserComboBox.setOpaque(false);
        TopicChooserComboBox.setPreferredSize(new java.awt.Dimension(197, 20));
        TopicChooserComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TopicChooserComboBoxItemStateChanged(evt);
            }
        });
        TopicChooserComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TopicChooserComboBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TopicChooserComboBoxFocusLost(evt);
            }
        });
        getContentPane().add(TopicChooserComboBox);
        TopicChooserComboBox.setBounds(142, 420, 350, 30);

        NameWarning.setForeground(new java.awt.Color(255, 255, 255));
        NameWarning.setText(" ");
        NameWarning.setMaximumSize(new java.awt.Dimension(197, 14));
        NameWarning.setMinimumSize(new java.awt.Dimension(197, 14));
        NameWarning.setPreferredSize(new java.awt.Dimension(197, 14));
        getContentPane().add(NameWarning);
        NameWarning.setBounds(140, 190, 197, 14);

        l5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        l5.setForeground(new java.awt.Color(255, 255, 255));
        l5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        l5.setText("(optional)");
        l5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(l5);
        l5.setBounds(30, 240, 54, 30);

        l4.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l4.setForeground(new java.awt.Color(255, 255, 255));
        l4.setText("Description:");
        getContentPane().add(l4);
        l4.setBounds(30, 210, 88, 30);

        l3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        l3.setForeground(new java.awt.Color(255, 255, 255));
        l3.setText("Title:");
        getContentPane().add(l3);
        l3.setBounds(30, 160, 90, 30);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(140, 210, 700, 200);

        TitleInput.setBackground((new Color(0,0,0,0)));
        TitleInput.setForeground(white);
        TitleInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        TitleInput.setMaximumSize(new java.awt.Dimension(197, 20));
        TitleInput.setMinimumSize(new java.awt.Dimension(197, 20));
        TitleInput.setOpaque(false);
        TitleInput.setPreferredSize(new java.awt.Dimension(197, 20));
        TitleInput.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TitleInputCaretUpdate(evt);
            }
        });
        TitleInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TitleInputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TitleInputFocusLost(evt);
            }
        });
        getContentPane().add(TitleInput);
        TitleInput.setBounds(140, 160, 700, 30);

        ask_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ask_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
        ask_label.setText("Ask?");
        ask_label.setIconTextGap(-77);
        ask_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ask_label.setDoubleBuffered(true);
        ask_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ask_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ask_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ask_labelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ask_labelMouseReleased(evt);
            }
        });
        getContentPane().add(ask_label);
        ask_label.setBounds(398, 480, 100, 30);

        MinLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/minbutton.png"))); // NOI18N
        MinLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
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

        jLabel1.setBackground((new Color(0,0,0,0)));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background_home_page.png"))); // NOI18N
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

        setSize(new java.awt.Dimension(900, 645));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        this.setLocation(evt.getXOnScreen() - xMouse, evt.getYOnScreen() - yMouse);           // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
       
        xMouse = evt.getX();        // TODO add your handling code here:
        yMouse = evt.getY();         // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MousePressed

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
        
        this.dispose();
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


    private void ask_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ask_labelMouseEntered

ImageIcon green_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
ask_label.setIcon(green_icon);
        // TODO add your handling code here:
    }//GEN-LAST:event_ask_labelMouseEntered

    private void ask_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ask_labelMouseExited

ImageIcon blank_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank.png"));        
ask_label.setIcon(blank_icon);
// TODO add your handling code here:
    }//GEN-LAST:event_ask_labelMouseExited

    private void ask_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ask_labelMouseReleased


       
        
        String title=TitleInput.getText();
        String desc=jTextArea1.getText();
        String topic=(String)TopicChooserComboBox.getSelectedItem();
        
        
        
        int serial=0;
        establishOfflineConnection();
        
        try{
        
            String query="select MAX(CAST(substring_index(serial_code,'_',-1) AS UNSIGNED))\"Max_Index\" from listofallquestions;";
            rs=st.executeQuery(query);
            rs.next();
            try{    serial=rs.getInt("Max_Index"); serial++; }catch(Exception e){serial=0;}
            
       
        
        String serial_code=master_username.concat("_"+topic).concat("_"+serial);
        
        
            
            String query1="insert into listofallquestions values('"
                    + serial_code
                    + "','"
                    + title
                    + "','"
                    + desc
                    + "',"
                    + "0,0,0,0);";
            st.executeUpdate(query1);
            
            System.out.println(query1);
            JOptionPane.showMessageDialog(null, "Question succesfully posted.");
            
            reset();
            
           
        }catch(Exception e){
            System.out.println("E="+e);
            showNetworkError();
        }
        
        
        closeConnection();
        

        // TODO add your handling code here:
    }//GEN-LAST:event_ask_labelMouseReleased
void reset(){
    
    initialClickedTitleInput=false;
    initialClickedTopicInput=false;
    TitleInput.setText("");
    TopicChooserComboBox.setSelectedIndex(-1);
    jTextArea1.setText("");
}
    void showNetworkError(){
        JOptionPane.showMessageDialog(null,"Network Error");
        closeConnection();
    }
    
public boolean checkNameEnteredIsLegalAndShowWarning(){

    boolean result;
    int NameLength=TitleInput.getText().length();
    
    if(NameLength<3){
        showLabelWarning(NameWarning, Color.RED, "Question is too short.");
        result=false;
    }else if(NameLength>400){
        showLabelWarning(NameWarning, Color.RED, "Question is too long.");
        result=false;
    }else{
        
        showLabelWarning(NameWarning, new Color(0,255,1,255),"");
        result=true;
    }
    
    return result;
}

public boolean checkNameEnteredIsLegal(){

    boolean result;
    int NameLength=TitleInput.getText().length();
    
    if(NameLength<3){
       result=false;
       
    }else if(NameLength>400){
        result=false;
        showLabelWarning(NameWarning, Color.RED, "Question is too long.");
    }else{
        
         showLabelWarning(NameWarning, new Color(0,255,1,255),"");
        result=true;
    }
    
    return result;
}

public void showLabelWarning(JLabel label, Color color, String message){
    label.setText(message);
    label.setForeground(color);
}

 public void showLabelWarning(JLabel label, Color color, String message, JComboBox jcombobox){
    
   
    
    
    
    label.setText(message);
    label.setForeground(color);

    
}

    
    
    private void TitleInputCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TitleInputCaretUpdate
if(initialClickedTitleInput){            
                TitleCheck=checkNameEnteredIsLegalAndShowWarning();
        }else{
                TitleCheck=checkNameEnteredIsLegal();
        }        
        // TODO add your handling code here:
    }//GEN-LAST:event_TitleInputCaretUpdate

    private void TitleInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TitleInputFocusGained

        if(initialClickedTitleInput==false){
            TitleInput.setText("");
        }

       

        // TODO add your handling code here:
    }//GEN-LAST:event_TitleInputFocusGained

    private void TitleInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TitleInputFocusLost
initialClickedTitleInput=true; //boolean variable to show that user has clicked on that component atleast once     
       TitleCheck=checkNameEnteredIsLegalAndShowWarning();  
        
    }//GEN-LAST:event_TitleInputFocusLost

    private void TopicChooserComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TopicChooserComboBoxItemStateChanged

        if(initialClickedTopicInput){
            if(TopicChooserComboBox.getSelectedItem()==null){
                showLabelWarning(GenderWarning,Color.RED,"Invalid Choice",TopicChooserComboBox);
                TopicCheck=false;
            }else{
                showLabelWarning(GenderWarning,new Color(0,255,1,255),"",TopicChooserComboBox);
                TopicCheck=true;

            }

        }else{
            if(TopicChooserComboBox.getSelectedItem()==null)
            TopicCheck=false;
            else
            TopicCheck=true;

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TopicChooserComboBoxItemStateChanged

    private void TopicChooserComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TopicChooserComboBoxFocusGained
        initialClickedTopicInput=true;
        // TODO add your handling code here:
    }//GEN-LAST:event_TopicChooserComboBoxFocusGained

    private void TopicChooserComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TopicChooserComboBoxFocusLost
        if(TopicChooserComboBox.getSelectedItem()==null){
            showLabelWarning(GenderWarning,Color.RED,"Invalid Choice",TopicChooserComboBox);
            TopicCheck=false;
        }else{
            showLabelWarning(GenderWarning,new Color(0,255,1,255),"",TopicChooserComboBox);
            TopicCheck=true;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_TopicChooserComboBoxFocusLost

 
    
    
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
            java.util.logging.Logger.getLogger(Ask_Question.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ask_Question.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ask_Question.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ask_Question.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
              
               
                
               
               new Ask_Question().setVisible(true);
                
            }
            
            
        });

  
       

    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ExitLabel;
    private javax.swing.JLabel GenderWarning;
    private javax.swing.JLabel Greeting_Label;
    private javax.swing.JLabel MinLabel;
    private javax.swing.JLabel NameWarning;
    private javax.swing.JTextField TitleInput;
    private javax.swing.JComboBox<String> TopicChooserComboBox;
    private javax.swing.JLabel ask_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel l3;
    private javax.swing.JLabel l4;
    private javax.swing.JLabel l5;
    private javax.swing.JLabel l6;
    // End of variables declaration//GEN-END:variables




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

           finalVerify();
           
           //put background task here
    }
   
}


class Colorizer<String> extends JLabel implements ListCellRenderer {
private String _title;

      

        public Colorizer(String title) {
            super();
            setOpaque(true);
            _title = title;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

   
            
            setBorder(null);
            
            
                setForeground(white);
            
            setBackground(Color.BLACK);

  if (isSelected) {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

  
      if (index == -1 && value == null) setText(""+_title);
            else setText(value.toString());
            return this;
        }
        
      
    } 



}


