
package giaodienchuan.model.BackEnd.QuanLyNXB;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author DELL
 */
public class QuanLyNhaXuatBanDAO {
    
    ConnectionDB qlnxbConnection;

    public QuanLyNhaXuatBanDAO() {

    }

    public ArrayList<NhaXuatBan> readDB() {
        qlnxbConnection = new ConnectionDB();
        ArrayList<NhaXuatBan> dsnxb = new ArrayList<>();
        try {
            String qry = "SELECT * FROM nhaxuatban";
            ResultSet r = qlnxbConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String manxb = r.getString(1);
                    String tennxb = r.getString(2);
                    String diachi = r.getString(3);

                    dsnxb.add(new NhaXuatBan(manxb, tennxb, diachi));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng nhà xuất bản");
        } finally {
            qlnxbConnection.closeConnect();
        }
        return dsnxb;
    }

    public ArrayList<NhaXuatBan> search(String columnName, String value) {
        qlnxbConnection = new ConnectionDB();
        ArrayList<NhaXuatBan> dsnxb = new ArrayList<>();

        try {
            String qry = "SELECT * FROM nhaxuatban WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlnxbConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String manxb = r.getString(1);
                    String tennxb = r.getString(2);
                    String diachi = r.getString(3);

                    dsnxb.add(new NhaXuatBan(manxb, tennxb, diachi));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng nhà xuất bản");
        } finally {
            qlnxbConnection.closeConnect();
        }

        return dsnxb;
    }

    public Boolean add(NhaXuatBan nxb) {
        qlnxbConnection = new ConnectionDB();
        Boolean ok = qlnxbConnection.sqlUpdate("INSERT INTO `nhaxuatban` (`MaNXB`, `TenNXB`, `Diachi`) VALUES ('"
                + nxb.getMaNXB() + "', '" + nxb.getTenNXB() + "', '" + nxb.getDiaChi()+ "');");
        qlnxbConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String manxb) {
        qlnxbConnection = new ConnectionDB();
        Boolean ok = qlnxbConnection.sqlUpdate("DELETE FROM `nhaxuatban` WHERE `nhaxuatban`.`MaNXB` = '" + manxb + "'");
        qlnxbConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaNXB, String TenNXB, String Diachi) {
        qlnxbConnection = new ConnectionDB();
        Boolean ok = qlnxbConnection.sqlUpdate("Update nhaxuatban Set TenNXB='" + TenNXB + "', Diachi='" + Diachi + "' where MaNXB='" + MaNXB + "'");
        qlnxbConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlnxbConnection.closeConnect();
    }
}
