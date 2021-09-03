/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyTacGia;

/**
 *
 * @author DELL
 */
public class TacGia {
    String MaTG, TenTG, MoTa;
    
    public TacGia(String MaTG, String TenTG, String MoTa) {
        this.MaTG = MaTG;
        this.TenTG = TenTG;
        this.MoTa = MoTa;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getMaTG() {
        return MaTG;
    }

    public void setMaTG(String MaTG) {
        this.MaTG = MaTG;
    }

    public String getTenTG() {
        return TenTG;
    }

    public void setTenTG(String TenTG) {
        this.TenTG = TenTG;
    }
}
