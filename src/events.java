
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phamb
 */
public class events implements Comparable,Serializable {
    private String ngay,loai,noidung,diadiem,thoigian;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public events(String ngay,String loai,String noidung,String diadiem,String thoigian )
    {
        this.ngay=ngay;
        this.loai = loai;
        this.noidung = noidung;
        this.diadiem = diadiem;
        this.thoigian = thoigian;
              
    }
    
    public events()
    {
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Get()">
     public String get_ngay()
     {
         return this.ngay;
     }
     public String get_diadiem()
     {
         return this.diadiem;
     }
     public String get_loai()
     {
         return this.loai;
     }
     public String get_noidung()
     {
         return this.noidung;
     }
     public String get_thoigian()
     {
         return this.thoigian;
     }
     // </editor-fold>
     
     // <editor-fold defaultstate="collapsed" desc="Set()">
     public void set_ngay(String s)
     {
         this.ngay = s;
     }
     public void set_diadiem(String s)
     {
         this.diadiem = s;
     }
     public void set_loai(String s)
     {
         this.loai = s;
     }
     public void set_noidung(String s)
     {
         this.noidung = s;
     }
     public void set_thoigian(String s)
     {
         this.thoigian = s;
     }
     // </editor-fold>
     
    @Override
    public int compareTo(Object o)
    {
        String comparedate= ((events)o).get_ngay();
        return this.ngay.compareTo(comparedate);
    }
     
    
}
