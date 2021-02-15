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

import java.sql.*;
import javax.swing.JOptionPane;
import static thequestioner.Credential_Storage.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Md Ausaf Rashid
 */
public class Initializer extends javax.swing.JFrame {

    /**
     * Creates new form Initializer
     */
    
    static void setProperty(String Propt_Name, String Propt_Value, String File_Name){
        
        Properties prop = new Properties();
	OutputStream output = null;
	try {
		output = new FileOutputStream(File_Name);
                prop.setProperty(Propt_Name, Propt_Value);
                prop.store(output, null);
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
    }
    
    
    Connection conn;
    Statement st;
    
    void establishOfflineConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
            st = conn.createStatement();
            
            
        } catch (Exception e) {

            closeConnection();
            
        }
    }
    void closeConnection(){
        try{
            conn.close();
            st.close();
            
        }catch(Exception e){
            //ignore
        }
    }
    public Initializer() {
        
//        initComponents();
        
establishOfflineConnection();

try{
    String str="create table ListOfAllQuestions(\n" +
"serial_code varchar(100) primary key, /* serial code pattern:<username(30)_topicname(20)_QuestionNo(10)> question number corresponds to the number of questions. */\n" +
"question varchar(400),\n" +
"question_details varchar(1000),\n" +
"upvotes int,\n" +
"downvotes int,\n" +
"yes int default 0, \n" +
"no int default 0\n" +
");";

    st.execute(str);
    String str1="create table TopicNameTable(\n" +
"topic_name varchar(60) primary key\n" +
");";
    st.execute(str1);
    
    String str2="create table UserInfoTable(\n" +
"Name varchar(30) not null,\n" +
"Gender varchar(6) not null,\n" +
"DOB date not null,\n" +
"Nationality varchar(32) not null,\n" +
"Email varchar(40) not null,\n" +
"Username varchar(30) not null,\n" +
"Password varchar(30) not null\n" +
");";
    st.execute(str2);
    
    
    String update1="insert into TopicNameTable values(\"Health and Medicine\");";
    st.execute(update1);
    
    String update2="insert into TopicNameTable values('History, Philosophy, Religion, and Humanities');";
    st.execute(update2);
    
    String update3="insert into TopicNameTable values('Literature, Languages, and Communication');";
    st.execute(update3);
    
    String update4="insert into TopicNameTable values('Science, Technology, Engineering, and Mathematics');";
    st.execute(update4);
    
    String update5="insert into TopicNameTable values('Startups');";
    st.execute(update5);
    
    String update6="insert into TopicNameTable values('Business, Work, and Careers');";
    st.execute(update6);
    
    String update7="insert into TopicNameTable values('Art, Design, and Style');";
    st.execute(update7);
    
    String update8="insert into TopicNameTable values('Recreation, Sports, Travel, and Activities');";
    st.execute(update8);
    
    String update9="insert into TopicNameTable values('Education, Schools, and Learning');";
    st.execute(update9);
    
    String update10="insert into TopicNameTable values('News, Entertainment, and Pop Culture');";
    st.execute(update10);
    
    String update11="insert into TopicNameTable values('Life, Relationships, and Self');";
    st.execute(update11);
    
    String update12="insert into TopicNameTable values('Politics, Law, Government, and Judiciary');";
    st.execute(update12);
    
    String update13="insert into TopicNameTable values('Food, Cuisines, and Cooking');";
    st.execute(update13);
    
    String update14="insert into TopicNameTable values('Social Media');";
    st.execute(update14);
    
    String update_na="insert into TopicNameTable values('NA');";
    st.execute(update_na);
    
    
    
    String update15="insert into listofallquestions values('user111_Education, Schools, and Learning_1',"
            + "'Do you prefer online classes over offline?'"
            + ",'',0,0,0,0);"; 
    String update16="insert into listofallquestions values('user111_Education, Schools, and Learning_2',"
            + "'Do you think that education sector should be privatised?'"
            + ",'',432,32,45,98);";
    String update17="insert into listofallquestions values('user111_Education, Schools, and Learning_3',"
            + "'Do You like participating in Class Discussions?'"
            + ",'',321,52,34,67);";
    String update18="insert into listofallquestions values('user111_Politics, Law, Government, and Judiciary_4',"
            + "'Should capital punishment be completely banned?'"
            + ",'',34442,34,23,4353);";
    String update19="insert into listofallquestions values('user111_Education, Schools, and Learning_5',"
            + "'Is Home Schooling a good idea in India?'"
            + ",'',32,5,355,245);";
    String update20="insert into listofallquestions values('user111_Life, Relationships, and Self_6',"
            + "'Do you consider yourself to be an introvert?'"
            + ",'',233,23,3133,232);";
    String update21="insert into listofallquestions values('user111_Art, Design, and Style_8',"
            + "'Do you like modern art over classical art?'"
            + ",'',376,66,65,900);";
    String update22="insert into listofallquestions values('user111_Business, Work, and Careers_9',"
            + "'Do you like your current job?'"
            + ",'',435,3,2,34);";
    String update23="insert into listofallquestions values('user111_Food, Cuisines, and Cooking_10',"
            + "'Do you think veganism help save the environment?'"
            + ",'',423,6,75,7);";
    String update24="insert into listofallquestions values('user111_Health and Medicine_11',"
            + "'Should human genome editing be legalized?'"
            + ",'',4563,4,52,5);";
    String update25="insert into listofallquestions values('user111_History, Philosophy, Religion, and Humanities_12',"
            + "'Have you visited the Egyptian pyramids?'"
            + ",'',456,5,54,45);";     
    String update26="insert into listofallquestions values('user111_Social Media_13',"
            + "'Do you think facebook is a big threat to individual privacy?'"
            + ",'',453,54,776,433);";
    st.execute(update15);
    st.execute(update16);
    st.execute(update17);
    st.execute(update18);
    st.execute(update19);
    st.execute(update20);
    st.execute(update21);
    st.execute(update22);
    st.execute(update23);
    st.execute(update24);
    st.execute(update25);
    st.execute(update26);
    
    
    JOptionPane.showMessageDialog(this, "Everything Initialized succesfully!");
    
    setProperty("db_initialised", "true","db_initialised.properties");
    
}catch(Exception e){
    showNetworkError();
    System.out.println("E"+e);
    
}


closeConnection();
        
        
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showNetworkError(){
    JOptionPane.showMessageDialog(null,"Network Error");
    closeConnection();
}
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
            java.util.logging.Logger.getLogger(Initializer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Initializer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Initializer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Initializer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Initializer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
