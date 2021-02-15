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
import java.awt.Cursor;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


import java.sql.*;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.*;

import static thequestioner.SignIn.master_username;
import static thequestioner.Credential_Storage.*;
/**
 *
 * @author Md Ausaf Rashid
 */

//FOR ALL: MyQuestions means MyResponses
//FOR ALL: MyContent means Questions asked by me.

public class HOMEPAGE extends javax.swing.JFrame {

    Statement st = null;
    Statement st2 = null;
    
    Connection conn = null;
    ResultSet master_rs=null;
    
    //for temporary connection
    Statement st1 = null;
    Connection conn1 = null;
    
    
    //for MQ connections
    Statement st_MQ=null;
    Connection conn_MQ=null;
    
    
    
    boolean connectedToInternet = true;

   

    int xMouse;
    int yMouse;
    int n=0;
    final int INITIAL_LOADER_HT = 5;
    final int FURTHER_LOADER_HT = 200;
    int ht = 0;


    private JLabel[] createLabels() {
        JLabel[] labels = new JLabel[n];
        for (int i = 0; i < n; i++) {
            labels[i] = new JLabel();
        }
        return labels;
    }
    private JCheckBox[] createCheckBox(int n) {
        JCheckBox[] box = new JCheckBox[n];
        for (int i = 0; i < n; i++) {
            box[i] = new JCheckBox();
        }
        return box;
    }

    private JTextArea[] createTextArea() {
        JTextArea[] textarea = new JTextArea[n];
        for (int i = 0; i < n; i++) {
            textarea[i] = new JTextArea();
        }
        return textarea;
    }

    private JPanel[] createPanels() {
        JPanel[] panels = new JPanel[n];
        for (int i = 0; i < n; i++) {
            panels[i] = new JPanel();

        }
        return panels;
    }
    
    
   

    
    
    /**
     * Creates new form SignUp_Part1
     */
    void getNoOfListItems(JList list) {

        for (int i = 0; i < list.getModel().getSize(); i++) {
            System.out.println(list.getModel().getElementAt(i));
        }

    }

    public void finalVerify() {

        if (connectedToInternet == false) {
            internetConnectionStatus_label.setVisible(true);
            String str = "Trying to connect to internet...";
            internetConnectionStatus_label.setText(str);
        } else {
            internetConnectionStatus_label.setText(null);
            internetConnectionStatus_label.setVisible(false);
        }
        
        
        loadMoreComplex();

        
        if((topic_check_boxes_changed==true)&&(LabelClickedControl_FollowingLabel==true)){
            save_topic_label.setVisible(true);            
        }else{
            save_topic_label.setVisible(false);
        }
    }

    void update_topics_of_user(int n){
    
        
        
        setCursorToWaiting();
        enableAllCheckBoxes(false, n);
        establishTemporaryOfflineConnection();
        try{
            
            String query="delete from _"+master_username+" where following_topics != 'NA';";
                  st1.executeUpdate(query);
                  String topic_name;
                  for(int i=0;i<n;i++){
                      if(topic_check_box[i].isSelected()==true){
                          
                        topic_name=topic_check_box[i].getText();
                        st1.executeUpdate("insert into _"
                                + master_username
                                + "(following_topics) values('"
                                        + topic_name
                                        + "');");
                      }
                  }
            
            
            
            
            JOptionPane.showMessageDialog(null,"Topics saved succesfully!");
            topic_check_boxes_changed=false;
        }catch(Exception e){
            
            closeTemporaryOfflineConnection();
            showNetworkError();
        }
        closeTemporaryOfflineConnection();
        enableAllCheckBoxes(true, n);
        setCursortToNormal();
        
        
        
    }
    
    
  
    
   
    
    
    void establishOfflineConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
            st = conn.createStatement();
            st2=conn.createStatement();
            connectedToInternet=true;
        } catch (Exception e) {

            
            if (e.toString().equals(
                    "com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure\n"
                    + "\n"
                    + "The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server."
            )) {
                connectedToInternet = false;
            }else
                connectedToInternet = false;
            
            closeConnection();
            
        }
    }
    
   
    
    
    
    void establishTemporaryOfflineConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
            st1 = conn1.createStatement();
            connectedToInternet=true;
        } catch (Exception e) {

            
            if (e.toString().equals(
                    "com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure\n"
                    + "\n"
                    + "The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server."
            )) {
                connectedToInternet = false;
            }else
                connectedToInternet = false;
            
            closeTemporaryOfflineConnection();
            
        }
    }
    
    void closeTemporaryOfflineConnection(){
        try{
            conn1.close();
            st1.close();
        }catch(Exception e){
            //ignored
        }
    
    }

    void checkOfflineConnection() {
        Connection conn1=null;
        Statement st1=null;
          try {
            Class.forName("com.mysql.jdbc.Driver");

             conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, DB_USERNAME, DB_PASSWORD);
             st1 = conn1.createStatement();
             connectedToInternet=true;
             
              
             
        } catch (Exception e) {
            System.out.println("Exception from Est Conn1:" + e.getMessage());
            
            if (e.toString().equals(
                    "com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure\n"
                    + "\n"
                    + "The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server."
            )) {
                connectedToInternet = false;
            }else
                connectedToInternet=false;
            
        }
          try{st1.close();conn1.close();}catch(Exception e){/*ignored*/}
          
           

    }
    
    
    void closeConnection(){
        try{
            conn.close();
            st.close();
            st2.close();
            master_rs.close();
        }catch(Exception e){
            //ignore
        }
    }
   

    void hideAllPanels(){
        
        Home_Panel.setVisible(false);
        Following_Panel.setVisible(false);
        
        Home_Label.setForeground(new Color(153,153,153));
        MyQuestions_Label.setForeground(new Color(153,153,153));
        Following_Label.setForeground(new Color(153,153,153));
        MyQuestions_Label.setForeground(new Color(153,153,153));
        Ask_Label.setForeground(new Color(153,153,153));
        MyContent_Label.setForeground(new Color(153,153,153));
        
        LabelClickedControl_HomeLabel=false;
        LabelClickedControl_MyQuestionsLabel=false;
        LabelClickedControl_FollowingLabel=false;
        
        LabelClickedControl_MyContentLabel=false;
    }
    
    public String numShrinker(long n) {
        String str = "" + n;
        int k = 1000;
        int m = 1000000;
        int b = 1000000000;
        long t = 1000000000000l;

        if (n >= k && n < m) {
            str = str.substring(0, str.length() - 3);//trims last 3 characters
            str = str.concat("K");
        }

        if (n >= m && n < b) {
            str = str.substring(0, str.length() - 6);//trims last 6 characters
            str = str.concat("M");
        }

        if (n >= b && n < t) {
            str = str.substring(0, str.length() - 9);//trims last 9 characters
            str = str.concat("B");
        }

        if (n >= t) {
            str = str.substring(0, str.length() - 12);//trims last 12 characters
            str = str.concat("T");
        }

        return str;
    }

    void setCursorToWaiting() {
        int cursorType = Cursor.WAIT_CURSOR;
        Component glassPane = ((RootPaneContainer) jLabel1.getTopLevelAncestor()).getGlassPane();
        glassPane.setCursor(Cursor.getPredefinedCursor(cursorType));
        glassPane.setVisible(cursorType != Cursor.DEFAULT_CURSOR);
    }

    void setCursortToNormal() {
        int cursorType = Cursor.DEFAULT_CURSOR;
        Component glassPane = ((RootPaneContainer) jLabel1.getTopLevelAncestor()).getGlassPane();
        glassPane.setCursor(Cursor.getPredefinedCursor(cursorType));
        glassPane.setVisible(cursorType != Cursor.DEFAULT_CURSOR);

    }
    
    int n_topic=0;
    private void start_Home_Panel(String panel_identity) {

        Thread worker = new Thread() {
            public void run() {

                establishOfflineConnection();
                n = (getNValue());
                updateN();
                
                
                StringAnalyser obj = new StringAnalyser();
                
                for (int i = 0; i < n; i++) {
                        String displayer=""; //can be Home or MyQuestions or MyContent
                        
                        String username="";
                        String topic="";
                        boolean relevant_topic=false;
                        int vote=0;
                        
                        int response_tester=0;


                    String MasterQuery="select question,question_details,upvotes,downvotes,substring_index(substring_index(serial_code,'_',-2),'_',1)\"topic\","
                            + "substring(serial_code,1,INSTR(serial_code,'_')-1 )\"username\""
                            + " from ListOfAllQuestions"
                            + " where substring_index(serial_code,'_',-1)="+i
                            + ";";
                    
                    try {
                        
                        

                        master_rs=st.executeQuery(MasterQuery);
                        
                        master_rs.next();
                        quest_title[i] = master_rs.getString("question");
                        
                        username=master_rs.getString("username");
                        
                        if(panel_identity.equals("Home")){
                            
                            topic=master_rs.getString("topic");
                            ResultSet rs= st2.executeQuery("select following_topics from _"+master_username+" where following_topics="
                                        + "'"
                                        + topic
                                        + "';");
                            rs.next();
                        
                            try{
                                String str=rs.getString("following_topics");
                                relevant_topic=true;
                                //relevant topic
                            }catch(Exception e){
                                //irrelevant topic
                                relevant_topic=false;
                                
                                
                            }
                        }else{
                            relevant_topic=true;
                        }
                        
                        if(relevant_topic==true){
                        if(username.equals(master_username)){
                            //Question is of this user
                            if(panel_identity.equals("MyQuestions") || panel_identity.equals("Home")){
                                
                                quest_title[i]=null;
                            
                            }else if(panel_identity.equals("MyContent")){
                                
                                
                                
                                
                                    String query2="select response,vote from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";";
                                   
                                    
                                    ResultSet rs2=st2.executeQuery(query2);
                                    rs2.next();
                                    
                                    try{
                                        response_tester=rs2.getInt("response");
                                    }catch(Exception e){
                                        response_tester=0;
                                    }
                                    
                                    try{
                                        vote=rs2.getInt("vote");
                                        
                                    }catch(Exception e){
                                        vote=0;
                                        
                                    }
                                    
                                    
                                    
                            if(response_tester==1||response_tester==-1){
                                
                                //Some Response found.
                                
                                
                                displayer="MyQuestions";
                                                        //code to display stats and not choosing option
                                                        //get stats from database to display percentage
                                                        try{
                                                            establishTemporaryOfflineConnection();
                                                            String query1="select yes,no from ListOfAllQuestions where substring_index(serial_code,'_',-1)="
                                                            + "'"
                                                            + i
                                                            + "';";
            
                                                            ResultSet rs=st1.executeQuery(query1);
                                                            rs.next();
            
            
            
            
            
                                                            double yes=rs.getInt("yes");
                                                            double no=rs.getInt("no");
            
            
            
                                                            int yes_percent=(int)Math.round(((yes/(yes+no))*100));
                                                            int no_percent=100-yes_percent;
            
            
            
                                                            label_answer_yes[i].setVisible(false);
                                                            label_answer_no[i].setVisible(false);
            
            
                                                            label_stats_yes[i].setText(""+yes_percent+"%");
                                                            label_stats_no[i].setText(""+no_percent+"%");
                                                            label_stats_yes[i].setVisible(true);
                                                            label_stats_no[i].setVisible(true);
            
                                                            ImageIcon green_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
                                                            ImageIcon red_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_red.png"));
                                                            
                                                            ImageIcon blank_icon= new ImageIcon(getClass().getResource("/Images/blank.png"));
//                                                            label_stats_yes[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png")));
                                                            
                                                            if(response_tester==1){
                                                                label_stats_yes[i].setIcon(green_icon);
                                                                label_stats_no[i].setIcon(blank_icon);
                                                            }else if(response_tester==-1){
                                                                label_stats_yes[i].setIcon(blank_icon);
                                                                label_stats_no[i].setIcon(red_icon);
                                                            }else{
                                                                System.out.println("Something went wrong.");
                                                            }
                                                            
            
        }catch(Exception e){
            System.out.println("Exception from stats"+e);
                showNetworkError();
        }
                                
                                
                            }else if(response_tester==0){
                                //No response found. 
                               
                                displayer="Home";
                                
                            }
                        
                        
                                
                                
                                
                                
                            
                            }
                            
                            
                            
                        }else{
                        //question is not of this user
                            
                        if(panel_identity.equals("MyContent")){
                            quest_title[i]=null;
                        }else{
                            
                        //checking whether user has already responded to this question or not.
                        
                        
                        
                        
                        String query2="select response,vote from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";";
                        
                        
                        ResultSet rs2=st2.executeQuery(query2);
                        
                        
                        rs2.next();   
                            
                                    try{
                                        
                                        response_tester=rs2.getInt("response");
                                    }catch(Exception e){
                                        response_tester=0;
                                    }
                                    
                                    try{
                                        vote=rs2.getInt("vote");
                                       
                                    }catch(Exception e){
                                        vote=0;
                                       
                                    }
                                    
                        
                        
                            if(response_tester==1||response_tester==-1){
                                
                                //Some Response found. Handling it according to panel Identity
                                if(panel_identity.equals("Home")){
                                    quest_title[i]=null;//Since response is already there. This Question shall not be displayed in Home_Panel.
                                }else if(panel_identity.equals("MyQuestions")){
                                                        //Since response is already there. This Question shall be displayed in Question_Panel.
                                                        displayer="MyQuestions";
                                                        //code to display stats and not choosing option
                                                        //get stats from database to display percentage
                                                        try{
                                                            establishTemporaryOfflineConnection();
                                                            String query1="select yes,no from ListOfAllQuestions where substring_index(serial_code,'_',-1)="
                                                            + "'"
                                                            + i
                                                            + "';";
            
                                                            ResultSet rs=st1.executeQuery(query1);
                                                            rs.next();
            
            
            
            
            
                                                            double yes=rs.getInt("yes");
                                                            double no=rs.getInt("no");
            
            
            
                                                            int yes_percent=(int)Math.round(((yes/(yes+no))*100));
                                                            int no_percent=100-yes_percent;
            
            
            
                                                            label_answer_yes[i].setVisible(false);
                                                            label_answer_no[i].setVisible(false);
            
            
                                                            label_stats_yes[i].setText(""+yes_percent+"%");
                                                            label_stats_no[i].setText(""+no_percent+"%");
                                                            label_stats_yes[i].setVisible(true);
                                                            label_stats_no[i].setVisible(true);
            
                                                            ImageIcon green_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
                                                            ImageIcon red_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_red.png"));
                                                            
                                                            ImageIcon blank_icon= new ImageIcon(getClass().getResource("/Images/blank.png"));
//                                                            label_stats_yes[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png")));
                                                            
                                                            if(response_tester==1){
                                                                label_stats_yes[i].setIcon(green_icon);
                                                                label_stats_no[i].setIcon(blank_icon);
                                                            }else if(response_tester==-1){
                                                                label_stats_yes[i].setIcon(blank_icon);
                                                                label_stats_no[i].setIcon(red_icon);
                                                            }else{
                                                                System.out.println("Something went wrong.");
                                                            }
                                                            
            
        }catch(Exception e){
            System.out.println("Exception from stats"+e);
                showNetworkError();
        }
        
                                                        
                                                        
                                                        
                                                        
                                }else{
                                    System.out.println("Insecure code found"
                                            + "Only Parameters allowed is Home and MyQuestions. ");
                                }
                                
                            }else if(response_tester==0){
                                
                                 if(panel_identity.equals("Home")){
                                    //Since no response is found, this question will be displayed in HomePanel
                                    displayer="Home";
                                }else if(panel_identity.equals("MyQuestions")){
                                    //Since no response is found, this question will not be displayed in MyQuestionsPanel
                                    quest_title[i]=null;
                                }else{
                                    System.out.println("Insecure code found"
                                            + "Only Parameters allowed is Home and MyQuestions. ");
                                }
                                
                            }
                        
                        
                        }
                    }


                        

                        }else{
                            quest_title[i]=null;
                        }


                        
                        

                        if (quest_title[i] != null) {
                            

                            quest_desc[i] = master_rs.getString("question_details");
                            upvote_count_num[i] = master_rs.getInt("upvotes");
                            downvote_count_num[i] = master_rs.getInt("downvotes");
                            quest_topic[i] = master_rs.getString("topic");

                            upvote_count[i] = numShrinker(upvote_count_num[i]);
                            downvote_count[i] = numShrinker(downvote_count_num[i]);
                            
                            counter++;
                            max_i = i;
                        }



                    } catch (Exception e) {
                        
                        if (e.toString().equals("java.lang.NullPointerException") || e.toString().equals("com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: No operations allowed after statement closed.")) {
                            connectedToInternet = false;
                            closeConnection();
                        }
                        
                        if(e.toString().equals("java.sql.SQLException: Illegal operation on empty result set.")){
                        }else{
                            
                            connectedToInternet = false;
                            closeConnection();
                        
                        }
                        
                        
                    
                        
                        if(connectedToInternet==false){
                            
                            while(true){
                                try{Thread.sleep(3000);}catch(Exception e2){/*ignored*/}
                                    
                                    checkOfflineConnection();//more efficient than establish connection, as this function closes the connection too.
                                    
                                    if(connectedToInternet==true){
                                        
                                        break;
                                        
                                    }
                                
                              }
                            establishOfflineConnection();//re-establish lost connection
                            
                            quest_title[i]=null;//make this question title null, if in caase other parts are not loaded
                            i--;//repeat this iteration as to ensure other parts are loaded
                        }
                        
                    
               

                    }
                    
                    if(i<0){//means n_topic is not loaded yet
                        n=getNValue();
                        updateN();
                        continue;//supose it decrements i just at the 0th iteration, no going back to secure previous printing of post.
                    }

                    //this code is to handle deleted questions/null questions
                    try {

                        if (quest_title[i] == null) {

                            if (i != 0) {
                                panel_question_posts[i].setBounds(5, panel_question_posts[i - 1].getY() + panel_question_posts[i - 1].getHeight(), 0, 0);
                            } else {
                                panel_question_posts[i].setBounds(5, 0, 0, 0);
                            }
                            panel_question_posts[i].setVisible(false);

                            continue;

                        }

                    } catch (Exception e) {
                        System.out.println("something went wrong. Exception:" + e);
                        System.out.println("Valuse of i=:" + i);
                        
                    }
                    //this code is to handle deleted questions/null questions
                    
                    
                 
                    post_holder_panel.add(panel_question_posts[i]);
                    panel_question_posts[i].setBackground(new java.awt.Color(0, 0, 0));
                    panel_question_posts[i].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(146, 151, 161)));
                    panel_question_posts[i].setLayout(null);
                    post_holder_panel.add(panel_question_posts[i]);

                    if (i != 0) {
                        panel_question_posts[i].setBounds(5, 5 + panel_question_posts[i - 1].getY() + panel_question_posts[i - 1].getHeight(), 835, 160 + ((obj.getNumberOfLines(quest_title[i]) - 1) * 44) - 64 + 16 * (obj.getNumberOfLinesDesc(quest_desc[i])));
                    } else {
                        panel_question_posts[i].setBounds(5, 5, 835, 160 + ((obj.getNumberOfLines(quest_title[i]) - 1) * 44) - 64 + 16 * (obj.getNumberOfLinesDesc(quest_desc[i])));
                    }

                    textarea_title[i].setEditable(false);
                    textarea_title[i].setBackground(new java.awt.Color(0, 0, 0));
                    textarea_title[i].setColumns(20);
                    textarea_title[i].setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
                    textarea_title[i].setForeground(new java.awt.Color(255, 255, 255));
                    textarea_title[i].setLineWrap(true);
                    textarea_title[i].setRows(5);
                    textarea_title[i].setText(quest_title[i]);
                    textarea_title[i].setWrapStyleWord(true);
                    textarea_title[i].setBorder(null);
                    panel_question_posts[i].add(textarea_title[i]);
                    textarea_title[i].setBounds(5, 5, 800, 44 * (obj.getNumberOfLines(quest_title[i])));
                    //write code to setup areas according to :"i"
                    titleheight = textarea_title[i].getHeight();

                    textarea_desc[i].setEditable(false);
                    textarea_desc[i].setBackground(new java.awt.Color(0, 0, 0));
                    textarea_desc[i].setColumns(20);
                    textarea_desc[i].setForeground(new java.awt.Color(153, 153, 153));
                    textarea_desc[i].setLineWrap(true);
                    textarea_desc[i].setRows(5);
                    textarea_desc[i].setText(quest_desc[i]);
                    //textarea_desc[i].setText("dfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegndfcdfddfbdmnfndknmskfdndskfndkfnlkjdegn");
                    textarea_desc[i].setWrapStyleWord(true);
                    textarea_desc[i].setBorder(null);
                    panel_question_posts[i].add(textarea_desc[i]);

                    textarea_desc[i].setBounds(5, 50 + titleheight - DEFAULT_TITLE_HEIGHT, 800, 16 * (obj.getNumberOfLinesDesc(quest_desc[i])));

                    //write code to setup areas according to :"i"
                    descY = textarea_desc[i].getY();

                    label_answer_yes[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    label_answer_yes[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
                    label_answer_yes[i].setText("YES");
                    label_answer_yes[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    label_answer_yes[i].setDoubleBuffered(true);
                    label_answer_yes[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    panel_question_posts[i].add(label_answer_yes[i]);
                    label_answer_yes[i].setBounds(370, 127 + descY - DEFAULT_DESC_Y - 64 + textarea_desc[i].getHeight(), 47, 20);
                    final int f=i; 
                    label_answer_yes[i].addMouseListener(new java.awt.event.MouseAdapter() {
                           
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label_answer_yesMouseEntered(evt,f);
                        }
                        
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label_answer_yesMouseExited(evt,f);
                        }
    
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            label_answer_yesMouseReleased(evt,f);
                        }
                        
                    });

                    label_stats_yes[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    //label_stats_yes[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
                    label_stats_yes[i].setDoubleBuffered(true);
                    label_stats_yes[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    panel_question_posts[i].add(label_stats_yes[i]);
                    label_stats_yes[i].setBounds(370, 127 + descY - DEFAULT_DESC_Y - 64 + textarea_desc[i].getHeight(), 47, 20);
                    if(displayer.equals("Home")){
                        label_stats_yes[i].setVisible(false);
                    }
                    
                    
                    label_answer_no[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    label_answer_no[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
                    label_answer_no[i].setText("NO");
                    label_answer_no[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    label_answer_no[i].setDoubleBuffered(true);
                    label_answer_no[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    panel_question_posts[i].add(label_answer_no[i]);
                    label_answer_no[i].setBounds(418, 127 + descY - DEFAULT_DESC_Y - 64 + textarea_desc[i].getHeight(), 47, 20);
                    
                    label_answer_no[i].addMouseListener(new java.awt.event.MouseAdapter() {
                           
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label_answer_noMouseEntered(evt,f);
                        }
                        
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label_answer_noMouseExited(evt,f);
                        }
                        
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            label_answer_noMouseReleased(evt,f);
                        }
                        
                    });
                    
                    label_stats_no[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    //label_stats_no[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
                    label_stats_no[i].setDoubleBuffered(true);
                    label_stats_no[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    panel_question_posts[i].add(label_stats_no[i]);
                    label_stats_no[i].setBounds(418, 127 + descY - DEFAULT_DESC_Y - 64 + textarea_desc[i].getHeight(), 47, 20); 
                    if(displayer.equals("Home")){
                        label_stats_no[i].setVisible(false);
                    }

                    panel_question_posts_height = panel_question_posts[i].getHeight();

                    label_upvote[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    if(vote==0||vote==-1){
                        label_upvote[i].setIcon(blank_icon_upvote);
                    }else if(vote==1){
                        label_upvote[i].setIcon(green_icon_upvote);
                    }
                     // NOI18N
                    label_upvote[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    label_upvote[i].setDoubleBuffered(true);
                    panel_question_posts[i].add(label_upvote[i]);
                    label_upvote[i].setBounds(810, 60 + ((panel_question_posts_height - DEFAULT_PANEL_QUESTION_POSTS_HEIGHT) / 2), 20, 20);

                    panel_question_posts_height = panel_question_posts[i].getHeight();

                    label_downvote[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    if(vote==0||vote==1){
                        label_downvote[i].setIcon(blank_icon_downvote);
                    }else if(vote==-1){
                        label_downvote[i].setIcon(red_icon_downvote);
                    }
                     // NOI18N
                    label_downvote[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    label_downvote[i].setDoubleBuffered(true);
                    panel_question_posts[i].add(label_downvote[i]);
                    label_downvote[i].setBounds(810, 80 + ((panel_question_posts_height - DEFAULT_PANEL_QUESTION_POSTS_HEIGHT) / 2), 20, 20);
                    
                    label_upvote[i].addMouseListener(new java.awt.event.MouseAdapter() {
                           
    
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            label_upvoteMouseReleased(evt,f);
                        }
                        
                    });
                    
                    label_downvote[i].addMouseListener(new java.awt.event.MouseAdapter() {
                           
    
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                            label_downvoteMouseReleased(evt,f);
                        }
                        
                    });
                    
                    
                    
                    

                    panel_question_posts_height = panel_question_posts[i].getHeight();

                    label_upvote_counter[i].setForeground(new java.awt.Color(255, 255, 255));
                    label_upvote_counter[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    label_upvote_counter[i].setText(upvote_count[i]);
                    label_upvote_counter[i].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    panel_question_posts[i].add(label_upvote_counter[i]);
                    label_upvote_counter[i].setBounds(805, 49 + ((panel_question_posts_height - DEFAULT_PANEL_QUESTION_POSTS_HEIGHT) / 2), 30, 10);

                    panel_question_posts_height = panel_question_posts[i].getHeight();

                    label_downvote_counter[i].setForeground(new java.awt.Color(255, 255, 255));
                    label_downvote_counter[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    label_downvote_counter[i].setText(downvote_count[i]);
                    label_downvote_counter[i].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    panel_question_posts[i].add(label_downvote_counter[i]);
                    label_downvote_counter[i].setBounds(805, 101 + ((panel_question_posts_height - DEFAULT_PANEL_QUESTION_POSTS_HEIGHT) / 2), 30, 10);
                    
                    

                    label_topic[i].setForeground(new java.awt.Color(255, 255, 255));
                    label_topic[i].setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    label_topic[i].setText(quest_topic[i]);
                    panel_question_posts[i].add(label_topic[i]);
                    label_topic[i].setBounds(5, textarea_desc[i].getY() + textarea_desc[i].getHeight(), 350, 14);
                    //code to create load more label after last panel post
                    
                    

                   
                   
                   n = (getNValue());
                   

                }
                
                
                try{
                    conn.close();    
                    st.close();
                    master_rs.close();
                      
                    }catch(Exception e){/*ignored*/}
                    
            
                
                max_ht=(panel_question_posts[max_i].getY() + panel_question_posts[max_i].getHeight() + 5); 
                sizeFixed=true;
                
                if(post_holder_panel.getHeight()>=(max_ht)){
                    post_holder_panel.setPreferredSize(new java.awt.Dimension(848, max_ht));
                    
                }
                
                   
               
                
            }
        };

        worker.start();

    }
       
    private void start_Following_Panel(){
     Thread worker = new Thread() {
            public void run() {
                
                
                n_topic=0;
                
                ResultSet rs;
                establishOfflineConnection();
                try{
                    
                    try{
                        post_holder_panel1.removeAll();
                    }catch(Exception e){}
                    
                    String query="select count(topic_name) from TopicNameTable;";
                    rs=st.executeQuery(query);
                    rs.next();
                    n_topic=rs.getInt("count(topic_name)");
                    n_topic--;
                    topic_check_box=createCheckBox(n_topic);
                    for(int i=0;i<n_topic;i++){
                    
                        topic_check_box[i].setBackground(new Color(0, 0, 0, 0));
                        topic_check_box[i].setForeground(new java.awt.Color(255, 255, 255));
                        
                        post_holder_panel1.add(topic_check_box[i]);
                        topic_check_box[i].setBounds(5, 5+(28*i), 410, 23);
                        
                        
                        
                        
                        topic_check_box[i].addActionListener(new java.awt.event.ActionListener() {
                            
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                
                                topic_check_boxes_changed=true;
                            }
                        });
                        topic_check_box[i].setVisible(false);
                    
                        post_holder_panel1.setPreferredSize(new java.awt.Dimension(848,(5+(28*i) +23 +5) ));
                        Following_Panel.revalidate();
                        
                        
                        
                    }
                    
                    
                    
                    String query1="select topic_name from TopicNameTable where topic_name != 'NA';";

                    rs=st.executeQuery(query1);
                    int i=0;
                    while(rs.next()){
                        if(i>=n_topic){
                            break;
                        }
                        String topic=rs.getString("topic_name");
                        
                        topic_check_box[i].setText(topic);
                       
                        
                        ResultSet rs_5= st2.executeQuery("select following_topics from _"+master_username+" where following_topics="
                                        + "'"
                                        + topic
                                        + "';");
                            rs_5.next();
                        
                            try{
                                String str=rs_5.getString("following_topics");
                                
                                topic_check_box[i].setSelected(true);
                            }catch(Exception e){
                                topic_check_box[i].setSelected(false);
                                
                            }
                         topic_check_box[i].setVisible(true);
                        i++;
                        
                    }
                    
                    
                }catch(Exception e){
                    showNetworkError();
                    System.out.println("E="+e);
                    //redirect to home
                }
                closeConnection();
                

            
            }
     
     };

        worker.start();
     
     }
    
    
    int max_ht;
    int max_i=0;
    boolean sizeFixed;
    long counter;

    String[] quest_title;
    String[] quest_desc;
    String[] quest_topic;

    int upvote_count_num[];
    int downvote_count_num[];

    String[] upvote_count;
    String[] downvote_count;

    //declaration start_Home_Panel
    JPanel[] panel_question_posts;
    JTextArea[] textarea_title;
    JTextArea[] textarea_desc;
    int titleheight;
    JLabel[] label_answer_yes;
    JLabel[] label_answer_no;
    JLabel[] label_stats_yes;
    JLabel[] label_stats_no;
    final int DEFAULT_TITLE_HEIGHT = 44;
    final int DEFAULT_DESC_Y = 50;
    final int DEFAULT_PANEL_QUESTION_POSTS_HEIGHT = 160;
    int descY;
    int panel_question_posts_height;
    //panel_question_posts_height will always be of the form= 160+44l, where 'l' is the number of lines in the title
    //Hence panel_question_posts_height will always be even.
    JLabel[] label_upvote;
    JLabel[] label_downvote;
    JLabel[] label_upvote_counter;
    JLabel[] label_downvote_counter;
    JLabel[] label_topic;
    //declaration ends

    JCheckBox[] topic_check_box;
    

    void updateN() {
        
        max_i = 0;
        sizeFixed=false;
        
        
        
        quest_title = new String[n];
        quest_desc = new String[n];
        quest_topic = new String[n];

        upvote_count_num = new int[n];
        downvote_count_num = new int[n];

        upvote_count = new String[n];
        downvote_count = new String[n];


        //declaration start_Home_Panel
        panel_question_posts = createPanels();
        textarea_title = createTextArea();
        textarea_desc = createTextArea();
        titleheight = 0;
        
        label_answer_yes = createLabels();
        
        label_answer_no=createLabels();
        
        label_stats_no=createLabels();
        
        label_stats_yes=createLabels();
        
        descY = 0;
        
        panel_question_posts_height = 0;
        //panel_question_posts_height will always be of the form= 160+44l, where 'l' is the number of lines in the title
        //Hence panel_question_posts_height will always be even.
        
        label_upvote = createLabels();
        label_downvote = createLabels();
        label_upvote_counter = createLabels();
        label_downvote_counter = createLabels();
        label_topic = createLabels();
        counter = 0;

    }

    int getNValue() {
        int a;

        try {
            String Query = "SELECT max(CAST(substring_index(serial_code,'_',-1) AS UNSIGNED))\"maxquestion\" FROM ListOfAllQuestions;";
            ResultSet rs = st.executeQuery(Query);
            rs.next();
            a = rs.getInt("maxquestion");

//            System.out.println("N=" + a);
        } catch (Exception e) {
            System.out.println("Exception from getNValue:" + e);
            if (e.toString().equals("java.lang.NullPointerException")) {
                connectedToInternet = false;
                closeConnection();
            }
            a=0;
        }
        a++;
        return a;
    }

    public HOMEPAGE() {

        initComponents();

       

        new Worker().execute(); //Background task to run finalVerify() started. 
        

        setBackground(new Color(0, 0, 0, 0));//makes jframe background invisible. 
        Greeting_Label.setText("Hello, "+master_username+"!");
        
        
        start_Home_Panel("Home");
        
        //mimicking as if the user clicked home label.
        hideAllPanels();
        Home_Panel.setVisible(true);
        Home_Label.setForeground(Color.white);
        LabelClickedControl_HomeLabel=true;
        //mimicking over

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
        internetConnectionStatus_label = new javax.swing.JLabel();
        Ask_Label = new javax.swing.JLabel();
        MyContent_Label = new javax.swing.JLabel();
        Following_Label = new javax.swing.JLabel();
        MyQuestions_Label = new javax.swing.JLabel();
        Home_Label = new javax.swing.JLabel();
        save_topic_label = new javax.swing.JLabel();
        Home_Panel = new javax.swing.JScrollPane();
        post_holder_panel = new javax.swing.JPanel();
        load_more_label = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        upvote_label = new javax.swing.JLabel();
        downvote_label = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextArea3 = new javax.swing.JTextArea();
        jTextArea1 = new javax.swing.JTextArea();
        comment_label1 = new javax.swing.JLabel();
        comment_label = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();
        Following_Panel = new javax.swing.JScrollPane();
        post_holder_panel1 = new javax.swing.JPanel();
        MinLabel = new javax.swing.JLabel();
        ExitLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

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
        Greeting_Label.setText("Hello, username12\n");
        Greeting_Label.setToolTipText("");
        Greeting_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(Greeting_Label);
        Greeting_Label.setBounds(715, 93, 180, 23);

        internetConnectionStatus_label.setForeground(new java.awt.Color(255, 255, 255));
        internetConnectionStatus_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        internetConnectionStatus_label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(internetConnectionStatus_label);
        internetConnectionStatus_label.setBounds(610, 515, 270, 22);

        Ask_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        Ask_Label.setForeground(new java.awt.Color(153, 153, 153));
        Ask_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ask_Label.setText("Ask");
        Ask_Label.setToolTipText("");
        Ask_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ask_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Ask_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Ask_LabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Ask_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(Ask_Label);
        Ask_Label.setBounds(580, 88, 90, 23);

        MyContent_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        MyContent_Label.setForeground(new java.awt.Color(153, 153, 153));
        MyContent_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MyContent_Label.setText("My Content");
        MyContent_Label.setToolTipText("");
        MyContent_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MyContent_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MyContent_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MyContent_LabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MyContent_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(MyContent_Label);
        MyContent_Label.setBounds(438, 88, 140, 23);

        Following_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        Following_Label.setForeground(new java.awt.Color(153, 153, 153));
        Following_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Following_Label.setText("Following");
        Following_Label.setToolTipText("");
        Following_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Following_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Following_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Following_LabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Following_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(Following_Label);
        Following_Label.setBounds(295, 88, 140, 23);

        MyQuestions_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        MyQuestions_Label.setForeground(new java.awt.Color(153, 153, 153));
        MyQuestions_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MyQuestions_Label.setText("My Responses");
        MyQuestions_Label.setToolTipText("");
        MyQuestions_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MyQuestions_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MyQuestions_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MyQuestions_LabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MyQuestions_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(MyQuestions_Label);
        MyQuestions_Label.setBounds(145, 88, 140, 23);

        Home_Label.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        Home_Label.setForeground(new java.awt.Color(153, 153, 153));
        Home_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Home_Label.setText("Home");
        Home_Label.setToolTipText("");
        Home_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Home_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Home_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Home_LabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Home_LabelMouseReleased(evt);
            }
        });
        getContentPane().add(Home_Label);
        Home_Label.setBounds(5, 88, 140, 23);

        save_topic_label.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        save_topic_label.setForeground(Color.blue);
        save_topic_label.setText("<html> <body> <u>Save>></u> </body> </html>");
        save_topic_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_topic_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                save_topic_labelMouseReleased(evt);
            }
        });
        getContentPane().add(save_topic_label);
        save_topic_label.setBounds(812, 476, 60, 30);
        save_topic_label.setVisible(false);

        Home_Panel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        post_holder_panel.setBackground(new java.awt.Color(0, 0, 0));
        post_holder_panel.setAutoscrolls(true);
        post_holder_panel.setPreferredSize(null);
        post_holder_panel.setLayout(null);

        load_more_label.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        load_more_label.setForeground(new java.awt.Color(0, 0, 255));
        load_more_label.setText("<html> <body> <u>Load More...</u> </body> </html>");
        load_more_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        load_more_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                load_more_labelMouseReleased(evt);
            }
        });
        post_holder_panel.add(load_more_label);
        load_more_label.setBounds(730, 130, 95, 20);
        load_more_label.setVisible(false);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setMaximumSize(new java.awt.Dimension(865, 100));
        jPanel3.setMinimumSize(new java.awt.Dimension(865, 100));
        jPanel3.setLayout(null);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Topic Name.");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(5, 115, 350, 14);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("10M");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.add(jLabel6);
        jLabel6.setBounds(805, 49, 30, 10);

        upvote_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upvote_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/upvote.png"))); // NOI18N
        upvote_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        upvote_label.setDoubleBuffered(true);
        jPanel3.add(upvote_label);
        upvote_label.setBounds(810, 60, 20, 20);

        downvote_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        downvote_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/downvote.png"))); // NOI18N
        downvote_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        downvote_label.setDoubleBuffered(true);
        jPanel3.add(downvote_label);
        downvote_label.setBounds(810, 80, 20, 20);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("10M");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.add(jLabel7);
        jLabel7.setBounds(805, 101, 30, 10);

        jTextArea3.setEditable(false);
        jTextArea3.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea3.setColumns(20);
        jTextArea3.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setBorder(null);
        jTextArea3.setMaximumSize(new java.awt.Dimension(800, 70));
        jTextArea3.setMinimumSize(new java.awt.Dimension(800, 70));
        jTextArea3.setSelectedTextColor(new java.awt.Color(153, 153, 153));
        jPanel3.add(jTextArea3);
        jTextArea3.setBounds(5, 50, 800, 64);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(null);
        jPanel3.add(jTextArea1);
        jTextArea1.setBounds(5, 5, 800, 44);

        comment_label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comment_label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
        comment_label1.setText("No");
        comment_label1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comment_label1.setDoubleBuffered(true);
        comment_label1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(comment_label1);
        comment_label1.setBounds(418, 127, 47, 20);

        comment_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comment_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blank.png"))); // NOI18N
        comment_label.setText("Yes");
        comment_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comment_label.setDoubleBuffered(true);
        comment_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(comment_label);
        comment_label.setBounds(370, 127, 47, 20);

        post_holder_panel.add(jPanel3);
        jPanel3.setBounds(5, 5, 835, 160);
        jPanel3.setVisible(false);

        loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/loading_4_small.gif"))); // NOI18N
        loading.setText("jLabel3");
        post_holder_panel.add(loading);
        loading.setBounds(407, 340, 31, 30);
        loading.setVisible(false);

        Home_Panel.setViewportView(post_holder_panel);

        Home_Panel.getVerticalScrollBar().setUnitIncrement(16);

        getContentPane().add(Home_Panel);
        Home_Panel.setBounds(15, 130, 865, 385);
        Home_Panel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent ae) {
                int extent = Home_Panel.getVerticalScrollBar().getModel().getExtent();
                //System.out.println("Value: " + (Home_Panel.getVerticalScrollBar().getValue()+extent) + " Max: " + Home_Panel.getVerticalScrollBar().getMaximum());
                if((Home_Panel.getVerticalScrollBar().getValue()+extent)== Home_Panel.getVerticalScrollBar().getMaximum() ){
                    //System.out.println("MAX SCROLL DETECTED");

                    loadMoreComplex();

                }
            }
        });

        Home_Panel.setVisible(false);

        Following_Panel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        post_holder_panel1.setBackground(new java.awt.Color(0, 0, 0));
        post_holder_panel1.setAutoscrolls(true);
        post_holder_panel1.setPreferredSize(null);
        post_holder_panel1.setLayout(null);
        Following_Panel.setViewportView(post_holder_panel1);

        getContentPane().add(Following_Panel);
        Following_Panel.setBounds(15, 130, 865, 340);
        Following_Panel.getVerticalScrollBar().setUnitIncrement(16);

        Home_Panel.setVisible(false);

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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thequestioner/imageedit_3_7186155792.gif"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(380, 120, 520, 410);

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

    private void label_answer_yesMouseEntered(java.awt.event.MouseEvent evt, int i) {                                      
        ImageIcon II = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
        label_answer_yes[i].setIcon(II); // TODO add your handling code here:
    }   
    
    private void label_answer_yesMouseExited(java.awt.event.MouseEvent evt, int i) {                                      
        ImageIcon II = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank.png"));
        label_answer_yes[i].setIcon(II); // TODO add your handling code here:
    }
public void showNetworkError(){
    JOptionPane.showMessageDialog(null,"Network Error");
    closeConnection();
}
    private void label_answer_yesMouseReleased(java.awt.event.MouseEvent evt, int i) {                                      
        //when yes is chosen by the user
        
        
        establishTemporaryOfflineConnection();
        try{
            //update stats to table by reporting this users response
            String query="update ListOfAllQuestions set yes=yes+1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
            st1.executeUpdate(query);
            
            
            String query_=null;
            
            try{
                //change st1
                
                ResultSet rs=st1.executeQuery("select serial_code from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";");
                rs.next();
                String str=rs.getString("serial_code");
                //no exception means: use update instead of insert
                try{
                    query_="update _"+master_username
                        + " set response=1 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                }catch(Exception e){
                    
                }
                
            }catch(Exception ex){
                //exception means use insert instead of update
               query_="insert into _"+master_username+" values( (select serial_code from listofallquestions where substring_index(serial_code,'_',-1)="
                    + i +" ),"
                    + "1,"
                    + "0,"
                    + "'NA'"
                    + ");";
            }
            System.out.println("query spl"+query_);
             st1.executeUpdate(query_);
            
            
            //get stats from database to display percentage
            String query1="select yes,no from ListOfAllQuestions where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
            
            ResultSet rs=st1.executeQuery(query1);
            rs.next();
            
          
            double yes=rs.getInt("yes");
            double no=rs.getInt("no");
            
            
            
            int yes_percent=(int)Math.round(((yes/(yes+no))*100));
            int no_percent=100-yes_percent;
            
            
            
            label_answer_yes[i].setVisible(false);
            label_answer_no[i].setVisible(false);
            
            
            label_stats_yes[i].setText(""+yes_percent+"%");
            label_stats_no[i].setText(""+no_percent+"%");
            label_stats_yes[i].setVisible(true);
            label_stats_no[i].setVisible(true);
            
            ImageIcon green_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
            ImageIcon red_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_red.png"));
            label_stats_yes[i].setIcon(green_icon);
            label_stats_no[i].setIcon(red_icon);
            
        }catch(Exception e){
            System.out.println("Exception from stats"+e);
                showNetworkError();
        }
        closeTemporaryOfflineConnection();
        
    }
    
    private void label_answer_noMouseReleased(java.awt.event.MouseEvent evt, int i) {                                      
        //when no is chosen by the user
        establishTemporaryOfflineConnection();
         try{
            String query="update ListOfAllQuestions set no=no+1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
            st1.executeUpdate(query);
            


            String query_=null;
            
            try{
                //change st1
                
                ResultSet rs=st1.executeQuery("select serial_code from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";");
                rs.next();
                String str=rs.getString("serial_code");
                //no exception means: use update instead of insert
                try{
                    query_="update _"+master_username
                        + " set response=-1 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                }catch(Exception e){
                    
                }
                
            }catch(Exception ex){
                //exception means use insert instead of update
               query_="insert into _"+master_username+" values( (select serial_code from listofallquestions where substring_index(serial_code,'_',-1)="
                    + i +" ),"
                    + "-1,"
                    + "0,"
                    + "'NA'"
                    + ");";
            }
             st1.executeUpdate(query_);
             
             
            
            String query1="select yes,no from ListOfAllQuestions where substring_index(serial_code,'_',-1)="
                    + "'"
                    + i
                    + "';";
            
            ResultSet rs=st1.executeQuery(query1);
            rs.next();
            
            
            
            
            double yes=rs.getInt("yes");
            double no=rs.getInt("no");
            
           
            
            int yes_percent=(int)Math.round(((yes/(yes+no))*100));
            int no_percent=100-yes_percent;
            
            
            
            label_answer_yes[i].setVisible(false);
            label_answer_no[i].setVisible(false);
            
            
            label_stats_yes[i].setText(""+yes_percent+"%");
            label_stats_no[i].setText(""+no_percent+"%");
            label_stats_yes[i].setVisible(true);
            label_stats_no[i].setVisible(true);
            
            ImageIcon green_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_green.png"));
            ImageIcon red_icon = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_red.png"));
            label_stats_yes[i].setIcon(green_icon);
            label_stats_no[i].setIcon(red_icon);
            
        }catch(Exception e){
                showNetworkError();
        }
         closeTemporaryOfflineConnection();
    }
    
    private void label_answer_noMouseEntered(java.awt.event.MouseEvent evt, int i) {                                      
        ImageIcon II = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank_red.png"));
        label_answer_no[i].setIcon(II); // TODO add your handling code here:
    }   
    
    private void label_answer_noMouseExited(java.awt.event.MouseEvent evt, int i) {                                      
        ImageIcon II = new ImageIcon(getClass().getResource("/Images/Blank_Colorized/blank.png"));
        label_answer_no[i].setIcon(II); // TODO add your handling code here:
    }
    
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

    
    final ImageIcon red_icon_downvote = new ImageIcon(getClass().getResource("/Images/Votes_Colorized/downvote_red.png"));
    final ImageIcon blank_icon_downvote = new ImageIcon(getClass().getResource("/Images/Votes_Colorized/downvote.png"));
    final ImageIcon green_icon_upvote = new ImageIcon(getClass().getResource("/Images/Votes_Colorized/upvote_green.png"));
    final ImageIcon blank_icon_upvote = new ImageIcon(getClass().getResource("/Images/Votes_Colorized/upvote.png"));
    
    private void label_upvoteMouseReleased(java.awt.event.MouseEvent evt, int i) {
         
         
         
      
         
         if(label_downvote[i].getIcon().toString().equals(red_icon_downvote.toString()) ){
             
             establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set downvotes=downvotes-1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
               String query_=null;
            

                //change st1
                
                    query_="update _"+master_username
                        + " set vote=0 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                
                
            
                    st1.executeUpdate(query_);
                
                
                
                    label_downvote[i].setIcon(blank_icon_downvote);
                
            }catch(Exception e){
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
             
             
             
             
         }
         
         
         
         
         
         
         
         if(label_upvote[i].getIcon().toString().equals(green_icon_upvote.toString()) ){
             
             
             
             establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set upvotes=upvotes-1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
                String query_=null;
            
            
                //change st1
                
                
                
                
                    query_="update _"+master_username
                        + " set vote=0 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                
                
            
             st1.executeUpdate(query_);
                
                //update label_upvote_counter and label_downvote_counter
                String query3="select upvotes,downvotes"
                            + " from ListOfAllQuestions"
                            + " where substring_index(serial_code,'_',-1)="+i
                            + ";";
                ResultSet rs=st1.executeQuery(query3);
                rs.next();
                upvote_count[i]=numShrinker(rs.getInt("upvotes"));
                downvote_count[i]=numShrinker(rs.getInt("downvotes"));
                label_upvote_counter[i].setText(upvote_count[i]);
                label_downvote_counter[i].setText(downvote_count[i]);
                //updating ended
                
                
                
                label_upvote[i].setIcon(blank_icon_upvote);
                
            }catch(Exception e){
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
             
             
             
             
         }else{
            
            establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set upvotes=upvotes+1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
               String query_=null;
            
            try{
                //change st1
                
                ResultSet rs=st1.executeQuery("select serial_code from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";");
                rs.next();
                String str=rs.getString("serial_code");
                //no exception means: use update instead of insert
                try{
                    query_="update _"+master_username
                        + " set vote=1 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                }catch(Exception e){
                    
                }
                
            }catch(Exception ex){
                //exception means use insert instead of update
               query_="insert into _"+master_username+" values( (select serial_code from listofallquestions where substring_index(serial_code,'_',-1)="
                    + i +" ),"
                    + "0,"
                    + "1,"
                    + "'NA'"
                    + ");";
            }
             st1.executeUpdate(query_);
                
                //update label_upvote_counter and label_downvote_counter
                String query3="select upvotes,downvotes"
                            + " from ListOfAllQuestions"
                            + " where substring_index(serial_code,'_',-1)="+i
                            + ";";
                ResultSet rs=st1.executeQuery(query3);
                rs.next();
                upvote_count[i]=numShrinker(rs.getInt("upvotes"));
                downvote_count[i]=numShrinker(rs.getInt("downvotes"));
                label_upvote_counter[i].setText(upvote_count[i]);
                label_downvote_counter[i].setText(downvote_count[i]);
                //updating ended
                
                
                
                
                
                label_upvote[i].setIcon(green_icon_upvote);
            }catch(Exception e){
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
            
         }
         
         establishTemporaryOfflineConnection();
         
         try{
             
             
             
         
         }catch(Exception e){
         
         }
         
         
         closeTemporaryOfflineConnection();
         
     }
    
    private void label_downvoteMouseReleased(java.awt.event.MouseEvent evt, int i) {
         
         
          if(label_upvote[i].getIcon().toString().equals(green_icon_upvote.toString()) ){
             
             
             
             establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set upvotes=upvotes-1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
                String query_=null;
            
            
                //change st1
                
                
                
                    query_="update _"+master_username
                        + " set vote=0 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                
                
            
             st1.executeUpdate(query_);
                
                
                
                label_upvote[i].setIcon(blank_icon_upvote);
                
            }catch(Exception e){
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
             
             
             
             
         }
         
         
         
         
         
         
         
         
         if(label_downvote[i].getIcon().toString().equals(red_icon_downvote.toString()) ){
             
             establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set downvotes=downvotes-1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
                String query_=null;
            
                //change st1
                
                
                
                
                    query_="update _"+master_username
                        + " set vote=0 "
                        + "where substring_index(serial_code,'_',-1)="+i+";";
                
                
            
             st1.executeUpdate(query_);
                
                //update label_upvote_counter and label_downvote_counter
                String query3="select upvotes,downvotes"
                            + " from ListOfAllQuestions"
                            + " where substring_index(serial_code,'_',-1)="+i
                            + ";";
                ResultSet rs=st1.executeQuery(query3);
                rs.next();
                upvote_count[i]=numShrinker(rs.getInt("upvotes"));
                downvote_count[i]=numShrinker(rs.getInt("downvotes"));
                label_upvote_counter[i].setText(upvote_count[i]);
                label_downvote_counter[i].setText(downvote_count[i]);
                //updating ended
                
                
                label_downvote[i].setIcon(blank_icon_downvote);
                
            }catch(Exception e){
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
             
             
             
             
         }else{
            
            establishTemporaryOfflineConnection();
            try{
                
                String query1="update ListOfAllQuestions set downvotes=downvotes+1 where substring_index(serial_code,'_',-1)="
                    + ""
                    + i
                    + ";";
                st1.executeUpdate(query1);
                
                String query_=null;
            
            try{
                //change st1
                
                ResultSet rs=st1.executeQuery("select serial_code from _"+master_username+" where substring_index(serial_code,'_',-1)="+i+";");
                rs.next();
                String str=rs.getString("serial_code");
                //no exception means: use update instead of insert
                try{
                    query_="update _"+master_username
                        + " set vote=-1"
                        + " where substring_index(serial_code,'_',-1)="+i+";";
                }catch(Exception e){
                   
                }
                
            }catch(Exception ex){
                //exception means use insert instead of update
               query_="insert into _"+master_username+" values( (select serial_code from listofallquestions where substring_index(serial_code,'_',-1)="
                    + i +" ),"
                    + "0,"
                    + "-1,"
                    + "'NA'"
                    + ");";
            }
               
             st1.executeUpdate(query_);
                
                //update label_upvote_counter and label_downvote_counter
                String query3="select upvotes,downvotes"
                            + " from ListOfAllQuestions"
                            + " where substring_index(serial_code,'_',-1)="+i
                            + ";";
                ResultSet rs=st1.executeQuery(query3);
                rs.next();
                upvote_count[i]=numShrinker(rs.getInt("upvotes"));
                downvote_count[i]=numShrinker(rs.getInt("downvotes"));
                label_upvote_counter[i].setText(upvote_count[i]);
                label_downvote_counter[i].setText(downvote_count[i]);
                //updating ended
                
                
                label_downvote[i].setIcon(red_icon_downvote);
            }catch(Exception e){
                
                showNetworkError();
            }
            
            
            
            closeTemporaryOfflineConnection();
            
         }
     }
    
    

    private void MyQuestions_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyQuestions_LabelMouseEntered

        MyQuestions_Label.setForeground(new Color(255, 255, 255));
        // TODO add your handling code here:
    }//GEN-LAST:event_MyQuestions_LabelMouseEntered

   
    
    private void MyQuestions_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyQuestions_LabelMouseExited
    if(LabelClickedControl_MyQuestionsLabel==false){
        MyQuestions_Label.setForeground(new Color(153, 153, 153));   
    }     // TODO add your handling code here:
    }//GEN-LAST:event_MyQuestions_LabelMouseExited

    private void Following_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Following_LabelMouseEntered
        Following_Label.setForeground(new Color(255, 255, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_Following_LabelMouseEntered

    private void Following_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Following_LabelMouseExited

if(LabelClickedControl_FollowingLabel==false){
        Following_Label.setForeground(new Color(153, 153, 153));
}         // TODO add your handling code here:
    }//GEN-LAST:event_Following_LabelMouseExited

    private void Ask_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ask_LabelMouseEntered
        Ask_Label.setForeground(new Color(255, 255, 255));         // TODO add your handling code here:
    }//GEN-LAST:event_Ask_LabelMouseEntered

    private void Ask_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ask_LabelMouseExited


        Ask_Label.setForeground(new Color(153, 153, 153));
         // TODO add your handling code here:
    }//GEN-LAST:event_Ask_LabelMouseExited

    private void load_more_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_more_labelMouseReleased

        loadMoreComplex();
        // TODO add your handling code here:
    }//GEN-LAST:event_load_more_labelMouseReleased

    private void MyQuestions_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyQuestions_LabelMouseReleased

        post_holder_panel.removeAll();
        hideAllPanels();
        Home_Panel.setVisible(true);
        closeConnection();
        start_Home_Panel("MyQuestions");
        MyQuestions_Label.setForeground(Color.white);
        LabelClickedControl_MyQuestionsLabel=true;
        
    }//GEN-LAST:event_MyQuestions_LabelMouseReleased

    boolean LabelClickedControl_HomeLabel=false;
    boolean LabelClickedControl_MyQuestionsLabel=false;
    boolean LabelClickedControl_FollowingLabel=false;
    
    boolean LabelClickedControl_MyContentLabel=false;
    
    
    
    
    private void Home_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home_LabelMouseEntered

        Home_Label.setForeground(new Color(255, 255, 255));        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_Home_LabelMouseEntered

    private void Home_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home_LabelMouseReleased
        
        post_holder_panel.removeAll();
        
        hideAllPanels();
        Home_Panel.setVisible(true);
        closeConnection();
        start_Home_Panel("Home");
        Home_Label.setForeground(Color.white);
        LabelClickedControl_HomeLabel=true;

        // TODO add your handling code here:
    }//GEN-LAST:event_Home_LabelMouseReleased

    private void Following_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Following_LabelMouseReleased

        hideAllPanels();
        
        LabelClickedControl_FollowingLabel=true;
        Following_Label.setForeground(Color.white);
        //code to show following_panel
        Following_Panel.setVisible(true);
        closeConnection();
        start_Following_Panel();
        // TODO add your handling code here:
    }//GEN-LAST:event_Following_LabelMouseReleased

    private void Ask_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ask_LabelMouseReleased

        
        //code to show ask_panel        
        Ask_Question obj=new Ask_Question();
        obj.setVisible(true);
  
        
// TODO add your handling code here:
    }//GEN-LAST:event_Ask_LabelMouseReleased

    private void Home_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home_LabelMouseExited

        if(LabelClickedControl_HomeLabel==false){
            Home_Label.setForeground(new Color(153, 153, 153));   
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_Home_LabelMouseExited

    private void MyContent_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyContent_LabelMouseEntered
            MyContent_Label.setForeground(new Color(255, 255, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_MyContent_LabelMouseEntered

    private void MyContent_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyContent_LabelMouseExited
 if(LabelClickedControl_MyContentLabel==false){
        MyContent_Label.setForeground(new Color(153, 153, 153));   
    }  
            
        // TODO add your handling code here:
    }//GEN-LAST:event_MyContent_LabelMouseExited

    private void MyContent_LabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyContent_LabelMouseReleased
        
        
        //code to show MyContent_panel        
        
        
        post_holder_panel.removeAll();
        hideAllPanels();
        Home_Panel.setVisible(true);
        closeConnection();
        start_Home_Panel("MyContent");
        MyContent_Label.setForeground(Color.white);
        LabelClickedControl_MyContentLabel=true;
        

// TODO add your handling code here:
    }//GEN-LAST:event_MyContent_LabelMouseReleased

    boolean topic_check_boxes_changed=false;
    
    
    
    
    private void save_topic_labelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save_topic_labelMouseReleased

        update_topics_of_user(n_topic);

        // TODO add your handling code here:
    }//GEN-LAST:event_save_topic_labelMouseReleased

    void enableAllCheckBoxes(boolean choice, int n){
        for(int i=0;i<n;i++){
            topic_check_box[i].setEnabled(choice);
        }
        
    }
   
    void loadMoreComplex() {

        Home_Panel.revalidate();
        post_holder_panel.revalidate();
            
        
        if(counter>0){    
            int extent = Home_Panel.getVerticalScrollBar().getModel().getExtent();
            try{
            if((Home_Panel.getVerticalScrollBar().getValue()+extent)== Home_Panel.getVerticalScrollBar().getMaximum()){
            //MAX SCROLL DETECTED
                
                    
                    if(sizeFixed==false){                       
                        if(connectedToInternet==true){
                            post_holder_panel.setPreferredSize(new java.awt.Dimension(848, post_holder_panel.getHeight() + FURTHER_LOADER_HT));
                        }else{
                           post_holder_panel.setPreferredSize(new java.awt.Dimension(848, panel_question_posts[max_i].getY() + panel_question_posts[max_i].getHeight() + 5));
                        }
                    }else{
                        if(post_holder_panel.getHeight()+FURTHER_LOADER_HT>=max_ht){
                            post_holder_panel.setPreferredSize(new java.awt.Dimension(848, max_ht));
                        }else{
                            post_holder_panel.setPreferredSize(new java.awt.Dimension(848, post_holder_panel.getHeight() + FURTHER_LOADER_HT));              
                        }
                    }
                  
                
            }else{
                if(connectedToInternet==false&&((Home_Panel.getVerticalScrollBar().getValue()+extent)>panel_question_posts[max_i].getY() + panel_question_posts[max_i].getHeight() + 5)){
                    post_holder_panel.setPreferredSize(new java.awt.Dimension(848, panel_question_posts[max_i].getY() + panel_question_posts[max_i].getHeight() + 5));
                }
            }
                }catch(Exception e){  
                    System.out.println("Exception from loadmore complex"+e);
                }
        } 
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
            java.util.logging.Logger.getLogger(HOMEPAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HOMEPAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HOMEPAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HOMEPAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
 
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new HOMEPAGE().setVisible(true);

            }

        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ask_Label;
    private javax.swing.JLabel ExitLabel;
    private javax.swing.JLabel Following_Label;
    private javax.swing.JScrollPane Following_Panel;
    private javax.swing.JLabel Greeting_Label;
    private javax.swing.JLabel Home_Label;
    private javax.swing.JScrollPane Home_Panel;
    private javax.swing.JLabel MinLabel;
    private javax.swing.JLabel MyContent_Label;
    private javax.swing.JLabel MyQuestions_Label;
    private javax.swing.JLabel comment_label;
    private javax.swing.JLabel comment_label1;
    private javax.swing.JLabel downvote_label;
    private javax.swing.JLabel internetConnectionStatus_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel load_more_label;
    private javax.swing.JLabel loading;
    private javax.swing.JPanel post_holder_panel;
    private javax.swing.JPanel post_holder_panel1;
    private javax.swing.JLabel save_topic_label;
    private javax.swing.JLabel upvote_label;
    // End of variables declaration//GEN-END:variables

    public class Worker extends SwingWorker<String, String> {

        @Override
        protected String doInBackground() throws Exception {
            //This is what's called in the .execute method
            for (int i = 0; true;) {
                //This sends the results to the .process method
                publish(String.valueOf(i));
                Thread.sleep(10);
            }

        }

        @Override
        protected void process(List<String> item) {
            //This updates the UI

            finalVerify();

            //put background task here
        }

    }

}
