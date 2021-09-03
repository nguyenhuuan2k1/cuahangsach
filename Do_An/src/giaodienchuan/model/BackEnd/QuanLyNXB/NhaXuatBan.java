
package giaodienchuan.model.BackEnd.QuanLyNXB;

/**
 *
 * @author DELL
 */
public class NhaXuatBan {
    String MaNXB, TenNXB, DiaChi;
    
    public NhaXuatBan(String MaNXB, String TenNXB, String DiaChi) {
        this.MaNXB = MaNXB;
        this.TenNXB = TenNXB;
        this.DiaChi = DiaChi;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getMaNXB() {
        return MaNXB;
    }

    public void setMaNXB(String MaNXB) {
        this.MaNXB = MaNXB;
    }

    public String getTenNXB() {
        return TenNXB;
    }

    public void setTenNXB(String TenNXB) {
        this.TenNXB = TenNXB;
    }
}
