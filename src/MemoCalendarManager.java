
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
    
public class MemoCalendarManager extends CalendarDataManager
{
    // <editor-fold defaultstate="collapsed" desc="declare variables">
    int dem=0;
    eventmanager todolist ;
    
    JButton todayBut;
    JLabel todayLab;
    JButton todolistBut;
    JButton lYearBut;
    JButton lMonBut;
    JLabel curMMYYYYLab;
    JButton nMonBut;
    JButton nYearBut;    
   
    JButton weekDaysName[];
    JButton dateButs[][] = new JButton[6][7];
    JLabel infoClock;
    JLabel selectedDate;
    
     //Event input
    JTextField timeTf;
    JTextField locationTf;
    JComboBox<String> CateBox;
    JTextArea memoArea;
    
    JScrollPane memoAreaSP;
    JPanel memoSubPanel;
    JButton saveBut; 
    JButton delBut;
        
    //Flags
    protected boolean isShowLunar;
    protected boolean isMemo;
    
    JLabel bottomInfo = new JLabel("<html><font color=white>Welcome to Memo Calendar!</html>");
    public static int tempi,tempj;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="focusToday()">
    public void focusToday()
    {
        tempi = today.get(Calendar.WEEK_OF_MONTH);
        tempj = today.get(Calendar.DAY_OF_WEEK)-1;
	if((tempj+1) == 1)
        {       
            dateButs[tempi][tempj].setBorderPainted(true);
            dateButs[tempi][tempj].setBorder(BorderFactory.createLineBorder(Color.blue));
        }
        else
        {
            tempi --;
            dateButs[tempi][tempj].setBorderPainted(true);
            dateButs[tempi][tempj].setBorder(BorderFactory.createLineBorder(Color.blue));
        }  
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="readMemo()">
    public void readMemo() 
    {
			File f = new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
                        if(f.exists()){
                            String temp = calYear+"/"+((calMonth+1)<10?"0":"")+(calMonth+1)+"/"+(calDayOfMon<10?"0":"")+calDayOfMon;
                            memoArea.setText((todolist.getEvent_existdate(temp)).get_noidung());
                            CateBox.setSelectedItem((todolist.getEvent_existdate(temp)).get_loai());
                            timeTf.setText((todolist.getEvent_existdate(temp)).get_thoigian());
                            locationTf.setText((todolist.getEvent_existdate(temp)).get_diadiem());
                            isMemo = true;
			}
			else 
                        {
                            memoArea.setText("");
                            isMemo = false;
                        }
                        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="showCal()">
    public void showCal()
    {
	for(int i=0;i<CAL_HEIGHT;i++){
            for(int j=0;j<CAL_WIDTH;j++){
			String fontColor="white";
			if(j==0) fontColor="#FF00C0";
			else if(j==6) fontColor="#1a75ff";
			int s2l[]= SolarConverttoLunar.gets2l(calDates[i][j], calMonth+1, calYear);		
			File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
			if(f.exists()){
				dateButs[i][j].setText("<html><b><font size=5 color=#ff751a>"+calDates[i][j]+"</font></b>"
									+ (isShowLunar==true?"<br><font size=2 color=#ff751a>"+s2l[0]+"/"+s2l[1]+"</font>":"")
									+ "</html>");
			}
			else 
			{ 
				dateButs[i][j].setText("<html><font size=5 color="+fontColor+">"+calDates[i][j]+"</font>"
										+ (isShowLunar==true?"<br><font size=2 color="+fontColor+">"+s2l[0]+"/"+s2l[1]+"</font>":"")
										+ "</html>");
			}
						
			JLabel todayMark = new JLabel("<html><font color=green>*</html>");
			dateButs[i][j].removeAll();
			if(calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR) && calDates[i][j] == today.get(Calendar.DAY_OF_MONTH))
					{
				dateButs[i][j].add(todayMark);
				dateButs[i][j].setToolTipText("Today");
			}
					
			if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
			else dateButs[i][j].setVisible(true);
            }
	}
    }  
    // </editor-fold>
    
}
