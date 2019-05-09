/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author huynh
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class GUI extends MemoCalendarManager
{
    // <editor-fold defaultstate="collapsed" desc="Variables declaration">
    DefaultComboBoxModel  model1 = new DefaultComboBoxModel();
    JPanel ClockPanel;
    JPanel calOpPanel;
    JPanel calPanel;
    JPanel buttonPanel;
    JPanel MainPanel;
    JPanel infoPanel;
    JPanel memoPanel;
    JTabbedPane eventPanel;
    JPanel frameBottomPanel;
    JPanel frameSubPanelSouth;
    
    //Flags
    private boolean isShow;
    private boolean isShowEvent;
    
    //Clock Panel
    private javax.swing.JButton ShowCalButton;
    private javax.swing.JLabel jLAP;
    private javax.swing.JLabel jLDate;
    private javax.swing.JLabel jLTime; 
    private javax.swing.JLabel jLNotify; 
    
    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
    
    final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
    final String title = "Memo Calendar";
    final String SaveButMsg1 = "<html><font color=white>Event Saved.</html>";
    final String SaveButMsg2 = "<html><font color=white>Please fill out the note first..</html>";
    final String SaveButMsg3 = "<html><font color=red>ERROR : Failed to write file!</html>";
    final String DelButMsg1 = "<html><font color=white>Deleted event.</html>";
    final String DelButMsg2 = "<html><font color=white>Event was not created or already deleted.</html>";
    final String DelButMsg3 = "<html><font color=red>ERROR :Failed to delete file</html>";
   
    Color vi = new Color(51, 51, 51);
    ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    JFrame main;
    int today_temp = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    int WIDTH = 600;
    int HEIGHT = 740;
    int clock_HEIGHT = 170;
    int cal_HEIGHT = 450;
    int event_HEIGHT = 200;
    
    //=-----Tocmndo List----------------------------------------------------------
    File f= new File("MemoData/TodoList.txt");
    FileOutputStream out1;
    FileInputStream f2 ;
    TodoList tdl;
    Point pt;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GUI()">
    public GUI()
    {
        readtodoList();
        pt = new Point();
        tdl = new TodoList();
        main = new JFrame();
        main.setTitle(title);
        main.add(SupremePanel());
        Notice();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setBackground(vi);
        main.setSize(WIDTH, clock_HEIGHT);
        main.setLocation(100, 10);
        main.setIconImage(icon.getImage());  
        
        try
        {
            UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows
            SwingUtilities.updateComponentTreeUI(main) ;
	}
        catch(Exception e)
        {
            bottomInfo.setText("ERROR : LookAndFeel setting failed");
	}
        main.setVisible(true);
        //focusToday();
        show_bottom_info show_bot_if = new show_bottom_info();
        show_bot_if.start();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="SupremePanel">
    private JPanel SupremePanel()
    {
       JPanel Supreme = new JPanel();
       Supreme.setLayout(new BorderLayout());
       ClockPanel();
       Supreme.add(ClockPanel,BorderLayout.NORTH);
       MainPanel();
       Supreme.add(MainPanel,BorderLayout.CENTER);
       MainPanel.setVisible(false);
       frameSubPanelSouth.setVisible(false);
       return Supreme; 
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="ClockPanel">
    private void ClockPanel() 
    {
        ClockPanel = new javax.swing.JPanel();
        jLTime = new javax.swing.JLabel();
        jLDate = new javax.swing.JLabel();
        jLAP = new javax.swing.JLabel();
        jLNotify = new javax.swing.JLabel();
        ShowCalButton = new javax.swing.JButton();

        //ClockPanel.setPreferredSize(new java.awt.Dimension(WIDTH, 110));
        ClockPanel.setPreferredSize(new java.awt.Dimension(WIDTH, 130));

        jLTime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTime.setText("Time");
        jLTime.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLDate.setText("DATE");
        jLDate.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        
        jLNotify.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLNotify.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLNotify.setText("NOTIFY");
        jLNotify.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLAP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLAP.setText("AP");
        
        

        ShowCalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ShowCal.png"))); // NOI18N
        ShowCalButton.setToolTipText("Show Calendar");
        ShowCalButton.setBorder(null);
        ShowCalButton.setBorderPainted(false);
        ShowCalButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ShowCalButton.setFocusPainted(false);
        ShowCalButton.setMaximumSize(new java.awt.Dimension(160, 50));
        ShowCalButton.setMinimumSize(new java.awt.Dimension(160, 50));
        ShowCalButton.setPreferredSize(new java.awt.Dimension(160, 50));
        ShowCalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowCalButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ClockPanelLayout = new javax.swing.GroupLayout(ClockPanel);
        ClockPanel.setLayout(ClockPanelLayout);
        ClockPanelLayout.setHorizontalGroup(
            ClockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, ClockPanelLayout.createSequentialGroup()
                .addComponent(jLTime, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLAP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
            .addGroup(ClockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLNotify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShowCalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ClockPanelLayout.setVerticalGroup(
            ClockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTime)
                    .addComponent(jLAP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLDate, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLNotify, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ShowCalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );        

        
        ThreadControl threadCnl = new ThreadControl();
	threadCnl.start();
        ClockPanel.setBackground(vi);
        ShowCalButton.setBorderPainted(false);
        ShowCalButton.setContentAreaFilled(false);
        ShowCalButton.setBackground(vi);
        isShow = false;
    }
    // </editor-fold> 
         
    // <editor-fold defaultstate="collapsed" desc="MainPanel">
    private void MainPanel()
    {
        MainPanel = new JPanel();
        MainPanel.setBackground(vi);
        
	//calOpPanel, calPanel -> frameSubPanelNorth
        calOpPanel();
        calPanel();
	JPanel frameSubPanelNorth = new JPanel();
        frameSubPanelNorth.setBackground(vi);
	Dimension calOpPanelSize = calOpPanel.getPreferredSize();
	calOpPanelSize.height = 80;                                                 //fix
	calOpPanel.setPreferredSize(calOpPanelSize);
	frameSubPanelNorth.setLayout(new BorderLayout());
	frameSubPanelNorth.add(calOpPanel,BorderLayout.NORTH);
	frameSubPanelNorth.add(calPanel,BorderLayout.CENTER);               
        //Button panel
        buttonPanel();
        buttonPanel.setPreferredSize(new java.awt.Dimension(WIDTH, 30));
        
	frameSubPanelNorth.add(buttonPanel, BorderLayout.SOUTH);
        frameSubPanelNorth.setPreferredSize(new java.awt.Dimension(WIDTH, cal_HEIGHT-30));
        
        //Memo Panel
        frameSubPanelSouth();
               
	//MainPanel
	MainPanel.setLayout(new BorderLayout());
	MainPanel.add(frameSubPanelNorth, BorderLayout.NORTH);
        MainPanel.add(frameSubPanelSouth,BorderLayout.CENTER);
        MainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MainPanel.setPreferredSize(new java.awt.Dimension(WIDTH, cal_HEIGHT));
        
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="calOpPanel">
    private void calOpPanel()
    {
        calOpPanel = new JPanel();
        calOpPanel.setBackground(vi);
	todayBut = new JButton("<html><font size=4 color=white>Today</html>");
        todayBut.setFocusable(false);
        todayBut.setBorderPainted(false);
	todayBut.setContentAreaFilled(false);
        todayBut.setBackground(new Color(51, 133, 255));
	todayBut.setToolTipText("Today");
	todayBut.addActionListener(lForCalOpButtons);
        todayBut.setOpaque(true);
	todayLab = new JLabel("<html><font size=4 color=white>"+today.get(Calendar.DAY_OF_MONTH)+"/"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.YEAR)+"</html>");
        //todo list button
        todolistBut = new JButton("<html><font size=4 color=white>Todo List ></html>");
        todolistBut.setContentAreaFilled(false);
        todolistBut.setBorderPainted(false);
        todolistBut.setBackground(new Color(51, 133, 255));
        todolistBut.setFocusPainted(false);
        todolistBut.setOpaque(true);
        todolistBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TodoListButtonActionPerformed(evt);
            }
        });
        todolistBut.setToolTipText("Todo List :))");
        Dimension d= new Dimension(115, 25);
        todolistBut.setMinimumSize(d);
        todolistBut.setMaximumSize(d);
        todolistBut.setPreferredSize(d);
        //end todo list button
        lYearBut = new JButton("<<");
        lYearBut.setBorderPainted(false);
	lYearBut.setContentAreaFilled(false);
	lYearBut.setToolTipText("Previous Year");
	lYearBut.addActionListener(lForCalOpButtons);
	lMonBut = new JButton("<");
        lMonBut.setBorderPainted(false);
	lMonBut.setContentAreaFilled(false);
	lMonBut.setToolTipText("Previous Month");
	lMonBut.addActionListener(lForCalOpButtons);
	curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5><font color=white>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</font></th></tr></table></html>");
	nMonBut = new JButton(">");
        nMonBut.setContentAreaFilled(false);
        nMonBut.setBackground(vi);
	nMonBut.setToolTipText("Next Month");
	nMonBut.addActionListener(lForCalOpButtons);
	nYearBut = new JButton(">>");
        nYearBut.setContentAreaFilled(false);
        nYearBut.setBackground(vi);
	nYearBut.setToolTipText("Next Year");
	nYearBut.addActionListener(lForCalOpButtons);
	calOpPanel.setLayout(new GridBagLayout());
	GridBagConstraints calOpGC = new GridBagConstraints();
	calOpGC.gridx = 1;
	calOpGC.gridy = 1;
	calOpGC.gridwidth = 2;
	calOpGC.gridheight = 1;
	calOpGC.weightx = 1;
	calOpGC.weighty = 1;
	calOpGC.insets = new Insets(5,5,0,0);
	calOpGC.anchor = GridBagConstraints.WEST;
	calOpGC.fill = GridBagConstraints.NONE;
	calOpPanel.add(todayBut,calOpGC);
	calOpGC.gridwidth = 3;
	calOpGC.gridx = 2;
	calOpGC.gridy = 1;
	calOpPanel.add(todayLab,calOpGC);
        calOpGC.gridx = 5;
	calOpGC.gridy = 1;
	calOpGC.gridwidth = 2;
	calOpGC.gridheight = 1;
	calOpGC.weightx = 1;
	calOpGC.weighty = 1;
	calOpGC.insets = new Insets(5,5,0,0);
	calOpGC.anchor = GridBagConstraints.EAST;
	calOpGC.fill = GridBagConstraints.NONE;
	calOpPanel.add(todolistBut,calOpGC);
	calOpGC.anchor = GridBagConstraints.CENTER;
	calOpGC.gridwidth = 1;
	calOpGC.gridx = 1;
	calOpGC.gridy = 2;
	calOpPanel.add(lYearBut,calOpGC);
	calOpGC.gridwidth = 1;
	calOpGC.gridx = 2;
	calOpGC.gridy = 2;
	calOpPanel.add(lMonBut,calOpGC);
	calOpGC.gridwidth = 2;
	calOpGC.gridx = 3;
	calOpGC.gridy = 2;
	calOpPanel.add(curMMYYYYLab,calOpGC);
	calOpGC.gridwidth = 1;
	calOpGC.gridx = 5;
	calOpGC.gridy = 2;
	calOpPanel.add(nMonBut,calOpGC);
	calOpGC.gridwidth = 1;
	calOpGC.gridx = 6;
	calOpGC.gridy = 2;
	calOpPanel.add(nYearBut,calOpGC);          
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="calPanel">
    private void calPanel()
    {
        calPanel=new JPanel();
        calPanel.setBackground(vi);
	weekDaysName = new JButton[7];
	for(int i=0 ; i<CAL_WIDTH ; i++){
		weekDaysName[i]=new JButton("<html><font color=white size=5><b>"+WEEK_DAY_NAME[i]+"</b></html>");
		weekDaysName[i].setBorderPainted(false);
		weekDaysName[i].setContentAreaFilled(false);
		weekDaysName[i].setForeground(Color.WHITE);
		if(i == 0) weekDaysName[i].setBackground(new Color(255, 0, 255));
		else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
		else weekDaysName[i].setBackground(new Color(150, 150, 150));
		weekDaysName[i].setOpaque(true);
		weekDaysName[i].setFocusPainted(false);
		calPanel.add(weekDaysName[i]);
	}
	for(int i=0 ; i<CAL_HEIGHT ; i++){
		for(int j=0 ; j<CAL_WIDTH ; j++){
			dateButs[i][j]=new JButton();
                        dateButs[i][j].setFocusable(false);
			dateButs[i][j].setBorderPainted(false);
			dateButs[i][j].setContentAreaFilled(false);
			dateButs[i][j].setBackground(vi);
			dateButs[i][j].setOpaque(true);
			dateButs[i][j].addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent evt) {
                                DateButtonsActionPerformed(evt);
                            }			
                        });
                        //(lForDateButs);
			calPanel.add(dateButs[i][j]);
		}
	}
	calPanel.setLayout(new GridLayout(0,7,2,2));
	calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	showCal();
    }
    // </editor-fold> 
        
    // <editor-fold defaultstate="collapsed" desc="ButtonPanel">
    private void buttonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(vi);
        
        JButton addEventBut = new JButton("<html><font size=4>+ Event</font></html>");
        addEventBut.setContentAreaFilled(false);
        addEventBut.setBackground(new Color(51, 133, 255));
        addEventBut.setForeground(Color.WHITE);
        addEventBut.setOpaque(true);
	addEventBut.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt) {
                    AddEventButtonActionPerformed(evt);
                }			
	});
        Dimension d= new Dimension(100, 25);
        addEventBut.setMinimumSize(d);
        addEventBut.setMaximumSize(d);
        addEventBut.setPreferredSize(d);
        
        JButton showLunarBut = new JButton("<html><font size=4>Show Lunar Calendar</font></html>");
        showLunarBut.setContentAreaFilled(false);
        showLunarBut.setBackground(new Color(51, 133, 255));
        showLunarBut.setForeground(Color.WHITE);
        showLunarBut.setOpaque(true);
	showLunarBut.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt) {
                    showLunarButtonActionPerformed(evt);
                }			
	});
        d= new Dimension(170, 25);
        showLunarBut.setMinimumSize(d);
        showLunarBut.setMaximumSize(d);
        showLunarBut.setPreferredSize(d);
        
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints butPnGC = new GridBagConstraints();
	butPnGC.gridx = 1;
	butPnGC.gridy = 1;
	butPnGC.gridwidth = 2;
	butPnGC.gridheight = 1;
	butPnGC.weightx = 1;
	butPnGC.weighty = 1;
	butPnGC.insets = new Insets(5,5,0,0);
	butPnGC.anchor = GridBagConstraints.WEST;
	butPnGC.fill = GridBagConstraints.NONE;
	buttonPanel.add(showLunarBut,butPnGC);
        butPnGC.gridx = 5;
	butPnGC.gridy = 1;
	butPnGC.gridwidth = 2;
	butPnGC.gridheight = 1;
	butPnGC.weightx = 1;
	butPnGC.weighty = 1;
	butPnGC.insets = new Insets(5,5,0,0);
	butPnGC.anchor = GridBagConstraints.EAST;
	butPnGC.fill = GridBagConstraints.NONE;
	buttonPanel.add(addEventBut,butPnGC);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="infoPanel">
    private void infoPanel()
    {
        infoPanel = new JPanel();
        infoPanel.setBackground(vi);
	infoPanel.setLayout(new BorderLayout());
	infoClock = new JLabel("", SwingConstants.RIGHT);
	infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	infoPanel.add(infoClock, BorderLayout.NORTH);
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="memoPanel">
    private void memoPanel()
    {
        memoPanel=new JPanel();
        memoPanel.setBackground(vi);    
	selectedDate = new JLabel("<Html><font size=4 color=white>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</font></html>", SwingConstants.LEFT);
	selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));	
        memoArea = new JTextArea();
        memoArea.setBackground(vi);
        memoArea.setForeground(Color.WHITE);
        memoArea.setFont(new Font("Tahoma", 0, 19));
	memoArea.setLineWrap(true);
	memoArea.setWrapStyleWord(true);
	memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	readMemo();
	
	memoSubPanel=new JPanel();
        memoSubPanel.setBackground(vi);
	saveBut = new JButton("<html><font size=4>Save</font></html>");
        saveBut.setContentAreaFilled(false);
        saveBut.setBackground(new Color(51, 133, 255));
        saveBut.setForeground(Color.WHITE);
        saveBut.setOpaque(true);
	saveBut.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent evt) {
                    saveDetailActionPerformed(evt);
			
		}					
	});
	delBut = new JButton("<html><font size=4>Delete</font></html>");
        delBut.setContentAreaFilled(false);
        delBut.setBackground(new Color(51, 133, 255));
        delBut.setForeground(Color.WHITE);
        delBut.setOpaque(true);
	delBut.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {

                        memoArea.setText("");
                        File f = new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
                        
                        String str = calYear+"/"+((calMonth+1)<10?"0":"")+(calMonth+1)+"/"+(calDayOfMon<10?"0":"")+calDayOfMon;
                        events evt = todolist.getEvent_existdate(str);
                        if(f.exists()){
                            todolist.Xoa(evt);
                            writeTodolist();
                            f.delete();
                            showCal();
                            Notice();
                            dem = todolist.getList().size();
                            bottomInfo.setText(DelButMsg1);
                            if(tdl.isVisible())
                                update();
                        }
                        else
                            bottomInfo.setText(DelButMsg2);
		}					
	});
	memoSubPanel.add(saveBut);
	memoSubPanel.add(delBut);
	memoPanel.setLayout(new BorderLayout());
	memoPanel.add(selectedDate, BorderLayout.NORTH);
	memoPanel.add(memoAreaSP,BorderLayout.CENTER);
	memoPanel.add(memoSubPanel,BorderLayout.SOUTH);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Sub South Panel - Event Panel">
    private void frameSubPanelSouth()
    {
        frameBottomPanel = new JPanel();
        frameBottomPanel.setBackground(vi);
	frameBottomPanel.add(bottomInfo);        
        
	//infoPanel, memoPanel  -> frameSubPanelSouth
        eventPanel(); 
	frameSubPanelSouth = new JPanel();
        frameSubPanelSouth.setBackground(vi);
	frameSubPanelSouth.setLayout(new BorderLayout());
	frameSubPanelSouth.add(eventPanel,BorderLayout.CENTER);
        frameSubPanelSouth.add(frameBottomPanel,BorderLayout.SOUTH);
        frameSubPanelSouth.setPreferredSize(new java.awt.Dimension(WIDTH, event_HEIGHT));
    }
    // </editor-fold> 
        
    // <editor-fold defaultstate="collapsed" desc="Event Panel">                          
    private void eventPanel() {
        
        JScrollPane ScrollPanel;
	JLabel catLb;
	JPanel detail;
	JLabel locationLb;
	JLabel timeLb;
        JButton saveDetail; 
        
		
        eventPanel = new javax.swing.JTabbedPane();
        ScrollPanel = new javax.swing.JScrollPane();
        detail = new javax.swing.JPanel();
        catLb = new javax.swing.JLabel();
        CateBox = new javax.swing.JComboBox<>();
        CateBox.setModel(model1);
        timeLb = new javax.swing.JLabel();
        timeTf = new javax.swing.JTextField();
        locationLb = new javax.swing.JLabel();
        locationTf = new javax.swing.JTextField();
        saveDetail = new javax.swing.JButton();
        CorComboBox();
        eventPanel.setBackground(vi);
        eventPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        memoPanel();
        
        eventPanel.addTab("Event", memoPanel);
        ScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //Tab Details Panel
        detail.setBackground(new java.awt.Color(51, 51, 51));
        detail.setForeground(new java.awt.Color(255, 255, 255));

        catLb.setForeground(new java.awt.Color(255, 255, 255));
        catLb.setText("<html><font size=4>Categories</font></html>"); 

        CateBox.setBackground(new java.awt.Color(80, 80, 80));
        CateBox.setForeground(new java.awt.Color(255, 255, 255));
        CateBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Event", "Birthday", "Meeting", "Anniversary" }));

        timeLb.setForeground(new java.awt.Color(255, 255, 255));
        timeLb.setText("<html><font size=4>Time</font></html>");

        timeTf.setBackground(new java.awt.Color(80, 81, 79));
        timeTf.setForeground(new java.awt.Color(255, 255, 255));

        locationLb.setForeground(new java.awt.Color(255, 255, 255));
        locationLb.setText("<html><font size=4>Location</font></html>");

        locationTf.setBackground(new java.awt.Color(80, 81, 79));
        locationTf.setForeground(new java.awt.Color(255, 255, 255));

        saveDetail.setText("<html><font size=4>Save</font></html>");
        saveDetail.setContentAreaFilled(false);
        saveDetail.setBackground(new Color(51, 133, 255));
        saveDetail.setForeground(Color.WHITE);
        saveDetail.setOpaque(true);
        saveDetail.setMaximumSize(new java.awt.Dimension(61, 25));
        saveDetail.setPreferredSize(new java.awt.Dimension(61, 25));
        saveDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDetailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailLayout = new javax.swing.GroupLayout(detail);
        detail.setLayout(detailLayout);
        detailLayout.setHorizontalGroup(
            detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailLayout.createSequentialGroup()
                        .addComponent(catLb)
                        .addGap(58, 58, 58)
                        .addComponent(CateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailLayout.createSequentialGroup()
                        .addComponent(timeLb)
                        .addGap(90, 90, 90)
                        .addComponent(timeTf, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailLayout.createSequentialGroup()
                        .addComponent(locationLb)
                        .addGap(72, 72, 72)
                        .addGroup(detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detailLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(saveDetail))
                            .addComponent(locationTf, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        detailLayout.setVerticalGroup(
            detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(catLb))
                    .addComponent(CateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(timeLb))
                    .addComponent(timeTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(locationLb))
                    .addComponent(locationTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveDetail)
                .addContainerGap())
        );
        //end Tab Details Panel
        ScrollPanel.setViewportView(detail);
        eventPanel.addTab("Details", ScrollPanel);
    }// </editor-fold>       
    
    // <editor-fold defaultstate="collapsed" desc="show_bottom_info">
    private class show_bottom_info extends Thread
        {
            boolean msgCntFlag = false;
            String curStr = new String();
            public void run()
            {
                while(true)
                {
                    try
                    {
                        sleep(5000);   
                        String infoStr = bottomInfo.getText();
                        if(infoStr != " " && (msgCntFlag == false || curStr != infoStr))
                        {
                            msgCntFlag = true;
                            curStr = infoStr;
			}
			else if(infoStr != " " && msgCntFlag == true)
                        {
				msgCntFlag = false;
                                bottomInfo.setText(" ");
                        }
                        
                        
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println("Thread:Error");
                    }          
                }
            }
        }
    // </editor-fold> 
  
    // <editor-fold defaultstate="collapsed" desc="Button Action Performed">
    private void ShowCalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(!isShow){
            main.setSize(WIDTH, clock_HEIGHT+cal_HEIGHT);
            MainPanel.setVisible(true);
            ShowCalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HideCal.png")));
            isShow=true;
        }
        else{
            main.setSize(WIDTH, clock_HEIGHT);
            MainPanel.setVisible(false);
            ShowCalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ShowCal.png")));
            isShow=false;
            frameSubPanelSouth.setVisible(false);
            isMemo = false;
        }      
    }
    
    private void TodoListButtonActionPerformed(java.awt.event.ActionEvent evt){
        if(tdl.isVisible())
        {
            pt = tdl.getLocationOnScreen();
            tdl.setVisible(false);
        }
        else
        {
            tdl = new TodoList();
            tdl.setLocation(pt);
            tdl.setVisible(true);
        }
    }
    
    private void AddEventButtonActionPerformed(java.awt.event.ActionEvent evt){
        if (!isShowEvent)
        {
            main.setSize(WIDTH, HEIGHT);
            frameSubPanelSouth.setVisible(true);
            isShowEvent = true;
        }
        else
        {
            main.setSize(WIDTH, clock_HEIGHT+cal_HEIGHT);
            frameSubPanelSouth.setVisible(false);            
            isShowEvent = false;
        }
    }
    
    private void showLunarButtonActionPerformed(ActionEvent evt){
        if (!isShowLunar) 
        {
            isShowLunar = true;
            showCal();
        }
        else 
        {
            isShowLunar = false;
            showCal();
        }
    }
    
    public class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == todayBut)
			{
                            setToday();
                            DateButtonsActionPerformed(e);
                            focusToday();
			}
			else if(e.getSource() == lYearBut) moveMonth(-12);
			else if(e.getSource() == lMonBut) moveMonth(-1);
			else if(e.getSource() == nMonBut) moveMonth(1);
			else if(e.getSource() == nYearBut) moveMonth(12);
			curMMYYYYLab.setText("<html><table width=100><tr><th><font color=white><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			showCal();
		}
    }
    
    private void DateButtonsActionPerformed(ActionEvent evt){
        CateBox.setToolTipText("");
        timeTf.setText("");
        locationTf.setText("");
        readtodoList();
        showCal();
        Notice();
        int k=0,l=0;
	dateButs[tempi][tempj].setBorderPainted(false);
	for(int i=0 ; i<CAL_HEIGHT ; i++){
		for(int j=0 ; j<CAL_WIDTH ; j++){
			if(evt.getSource() == dateButs[i][j])
			{
				dateButs[i][j].setBorderPainted(true);
				dateButs[i][j].setBorder(BorderFactory.createLineBorder(Color.blue));
				tempi = k=i;
				tempj = l=j;
			}
		}
	}
	
	if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; 
	
	cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
	
	String dDayString = new String();
	int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
	if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
			&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
			&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
	else if(dDay >=0) dDayString = "Today+"+(dDay+1);
	else if(dDay < 0) dDayString = "Today-"+(dDay)*(-1);
	
	selectedDate.setText("<Html><font color=white><font size=4>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");
	
	readMemo();
        
        if (isMemo)
        {
            main.setSize(WIDTH, HEIGHT);
            frameSubPanelSouth.setVisible(true);
            isShowEvent = true;
        }
        
    }
    private void saveDetailActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        try {
            if(CheckInput())
            {
            
				File f= new File("MemoData");
				if(!f.isDirectory()) f.mkdir();
				
				String memo = memoArea.getText();
				if(memo.length()>0){
					BufferedWriter out = new BufferedWriter(new FileWriter("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt"));
					String str = memoArea.getText();
					out.write(str);
                                        out.close();
                                        str = calYear+"/"+((calMonth+1)<10?"0":"")+(calMonth+1)+"/"+(calDayOfMon<10?"0":"")+calDayOfMon;
                                        events e = new events(str,CateBox.getSelectedItem().toString(),memoArea.getText(),locationTf.getText(),timeTf.getText());
                                        if(todolist.getList().indexOf(e)==-1)
                                        {
                                            if(todolist.is_existdate(e))
                                            {
                                                todolist.Xoa(todolist.getEvent_existdate(str));
                                                todolist.themevt(e);
                                                dem = todolist.getList().size();
                                            }
                                            todolist.themevt(e);
                                            dem = todolist.getList().size();
                                        }
                                        writeTodolist();
                                        showCal();
                                        Notice();
                                        //out();
					bottomInfo.setText("<html><font color=white>"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt "+SaveButMsg1+"</font></html>");
                                        if(tdl.isVisible())
                                        {
                                            update();
                                        }
                                }
				else 
					bottomInfo.setText(SaveButMsg2);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill all the field in tab 'Details'!!!");
            }
	}
                        catch (IOException e) {
				bottomInfo.setText(SaveButMsg3);
			}
			
    }
    private void Notice(){
        String DateToday = (today.get(Calendar.YEAR))+"/"+((today.get(Calendar.MONTH)+1)<10?"0":"")+(today.get(Calendar.MONTH)+1)+"/"+((today.get(Calendar.DAY_OF_MONTH))<10?"0":"")+(today.get(Calendar.DAY_OF_MONTH));
        String DateNextday = (today.get(Calendar.YEAR))+"/"+((today.get(Calendar.MONTH)+1)<10?"0":"")+(today.get(Calendar.MONTH)+1)+"/"+((today.get(Calendar.DAY_OF_MONTH)+1)<10?"0":"")+(today.get(Calendar.DAY_OF_MONTH)+1);
        String f_DateToday = "MemoData/"+(today.get(Calendar.YEAR))+""+((today.get(Calendar.MONTH)+1)<10?"0":"")+(today.get(Calendar.MONTH)+1)+""+((today.get(Calendar.DAY_OF_MONTH))<10?"0":"")+(today.get(Calendar.DAY_OF_MONTH))+".txt";
        String f_DateNextday = "MemoData/"+(today.get(Calendar.YEAR))+""+((today.get(Calendar.MONTH)+1)<10?"0":"")+(today.get(Calendar.MONTH)+1)+""+((today.get(Calendar.DAY_OF_MONTH)+1)<10?"0":"")+(today.get(Calendar.DAY_OF_MONTH)+1)+".txt";
        if((new File(f_DateToday)).exists())
                            {
                                events ev = todolist.getEvent_existdate(DateToday);
                                String time = ((ev).get_thoigian());
                                jLNotify.setText("<html><font color=white>"+"You have an event at "+time+" today!" );
                            }
                    else if((new File(f_DateNextday)).exists())
                    {
                                events ev = todolist.getEvent_existdate(DateNextday);
                                String time = ((ev).get_thoigian());
                                jLNotify.setText("<html><font color=white>"+"You have an event at "+time+" tomorrow!" );
                    }
                    else
                        jLNotify.setText("<html><font color=white>"+"No new notifications!" );
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="ThreadControl">
    public class ThreadControl extends Thread
    {
        String DateName[] = {"","SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        String MonthName[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        public void run() {
            while (true) {
                try {
                    today = Calendar.getInstance();
                    String amPm = (today.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
                    String hour;
                    if(today_temp != today.get(Calendar.DAY_OF_MONTH))
                    {
                        Notice();
                        showCal();
                        today_temp = today.get(Calendar.DAY_OF_MONTH);
                    }
                    if (today.get(Calendar.HOUR) == 0) {
                        hour = "12";
                    } else if (today.get(Calendar.HOUR) == 12) {
                        hour = " 0";
                    } else {
                        hour = (today.get(Calendar.HOUR) < 10 ? " " : "") + today.get(Calendar.HOUR);
                    }
                    String min = (today.get(Calendar.MINUTE) < 10 ? "0" : "") + today.get(Calendar.MINUTE);
                    String sec = (today.get(Calendar.SECOND) < 10 ? "0" : "") + today.get(Calendar.SECOND);
                    jLTime.setText("<html><font color=white>"+" "+ hour + ":" + min + ":" + sec + "</html>");
                    jLAP.setText("<html><font color=white>"+" "+amPm+ "</html>");
                    jLDate.setText("<html><font color=white>"+DateName[today.get(Calendar.DAY_OF_WEEK)]+", "+MonthName[today.get(Calendar.MONTH)]+" "+today.get(Calendar.DAY_OF_MONTH)+", "+today.get(Calendar.YEAR)+"</html>");
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread:Error");
                }
            }
        }
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Read - Write Todo List">
    private void readtodoList() 
    {
        todolist = new eventmanager();
        dem = getsize();
        if(f.exists())
        {
            try {
                f2 = new FileInputStream(f);
                try {
                    ObjectInputStream inStream = new ObjectInputStream(f2);
                    for(int i=0;i<dem;i++)
                    {
                        try {
                            todolist.themevt((events) inStream.readObject());
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dem = (todolist.getList()).size();
                    inStream.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private int getsize()
    {
        int count = 0;
        File dir = new File("MemoData");
        File[] children = dir.listFiles();
        for (File file : children) {
            count++;
        }
        return count-1;
    }
    private void writeTodolist()
    {
        this.todolist = this.todolist.sort();
        ObjectOutputStream oStream;
        try {
            out1 = new FileOutputStream(f);
            try {
                oStream = new ObjectOutputStream(out1);
                for(events evt : this.todolist.getList())
            {
                try {
                    oStream.writeObject(evt);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
               oStream.close(); 
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
     public void out()
    {
       // todolist.clear();
        events e ;
        if(f.exists())
        {
            try {
                f2 = new FileInputStream(f);
                    ObjectInputStream inStream = new ObjectInputStream(f2);
                    for(int i=0;i<dem;i++)
                    {                        
                            e=(events) inStream.readObject();
                            System.out.println("Ngay: "+e.get_ngay());
                            System.out.println("Loai: "+e.get_loai());
                            System.out.println("Noi dung: "+e.get_noidung());
                            System.out.println("Thoi gian: "+e.get_thoigian());
                            System.out.println("Dia diem: "+e.get_diadiem());
                    }
                    inStream.close();
                }
            catch (FileNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            catch (ClassNotFoundException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
        }
    }
    public void update()
    {
        pt = tdl.getLocationOnScreen();
        tdl.dispose();
        tdl = new TodoList();
        tdl.setLocation(pt);
        tdl.setVisible(true);
    }
     public void CorComboBox()
    {
        try
        {
            CateBox.setRenderer(new Colorir<String>());
            CateBox.setBackground(Color.gray);
        }
        catch(Exception e)
                {
                    System.out.println(e);
                }
    }
     class Colorir<String> extends JLabel implements ListCellRenderer
            {
                int conta = 5;
                public Colorir()
                {
                    super();
                    setOpaque(true);
                }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            Object texto = model1.getElementAt(index);
            setBackground(Color.gray);
            return this;
        }
            }
    // </editor-fold> 
     
     // <editor-fold defaultstate="collapsed" desc="CheckInput()">
     private boolean CheckInput()
     {
         if(memoArea.getText().equals(""))
             return false;
         else if(timeTf.getText().equals(""))
             return false;
         else if(locationTf.getText().equals(""))
             return false;
         return true;
     }
     // </editor-fold> 
    
}