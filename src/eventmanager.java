


import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phamb
 */
public class eventmanager
{
    private ArrayList<events> list;
    ObjectInputStream inStream;
    
    public eventmanager()
    {
        list = new ArrayList<events>();
    }
    
    public boolean themevt(events evt)
    {
        list.add(evt);
        return true;
    }
    
    public boolean Is_empty()
    {
        return this.list.isEmpty();
    }
    
    public ArrayList<events> getList()
    {
        return list;
    }
    
    public int soEvent()
    {
        return list.size();
    }
    
    public boolean Xoa(events evt)
    {
        list.remove(evt);
        return true;
    }
    
    public boolean Xoa_index(int index)
    {
        this.list.remove(index);
        return true;
    }
    
    public events getEvent_existdate(String ngay)
    {
        for(events evt:list)
        {
            if(evt.get_ngay().equals(ngay))
                return evt;
        }
        return null;
    }
    
    public boolean is_existdate(events evt)
    {
        for(events e:list)
        {
            if(evt.get_ngay().equals(e.get_ngay()))
                return true;
        }
        return false;
    }
    public eventmanager sort()
    {
        Collections.sort(this.list);
        return this;
    }
    public void clear()
    {
        list.removeAll(list);
    }
}
