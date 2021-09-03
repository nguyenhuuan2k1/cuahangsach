
package giaodienchuan.model.BackEnd.QuanLyTacGia;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class QuanLyTacGiaDAO {
    
    ConnectionDB qltgConnection;

    public QuanLyTacGiaDAO() {

    }

    public ArrayList<TacGia> readDB() {
        qltgConnection = new ConnectionDB();
        ArrayList<TacGia> dstg = new ArrayList<>();
        try {
            String qry = "SELECT * FROM tacgia";
            ResultSet r = qltgConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String matg = r.getString(1);
                    String tentg = r.getString(2);
                    String mota = r.getString(3);

                    dstg.add(new TacGia(matg, tentg, mota));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng tác giả");
        } finally {
            qltgConnection.closeConnect();
        }
        return dstg;
    }

    public ArrayList<TacGia> search(String columnName, String value) {
        qltgConnection = new ConnectionDB();
        ArrayList<TacGia> dstg = new ArrayList<>();

        try {
            String qry = "SELECT * FROM tacgia WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qltgConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String matg = r.getString(1);
                    String tentg = r.getString(2);
                    String mota = r.getString(3);

                    dstg.add(new TacGia(matg, tentg, mota));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng tác giả");
        } finally {
            qltgConnection.closeConnect();
        }

        return dstg;
    }

    public Boolean add(TacGia tg) {
        qltgConnection = new ConnectionDB();
        Boolean ok = qltgConnection.sqlUpdate("INSERT INTO `tacgia` (`MaTG`, `TenTG`, `Mota`) VALUES ('"
                + tg.getMaTG() + "', '" + tg.getTenTG() + "', '" + tg.getMoTa()+ "');");
        qltgConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String matg) {
        qltgConnection = new ConnectionDB();
        Boolean ok = qltgConnection.sqlUpdate("DELETE FROM `tacgia` WHERE `tacgia`.`MaTG` = '" + matg + "'");
        qltgConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaTG, String TenTG, String Mota) {
        qltgConnection = new ConnectionDB();
        Boolean ok = qltgConnection.sqlUpdate("Update tacgia Set TenTG='" + TenTG + "', Mota='" + Mota + "' where MaTG='" + MaTG + "'");
        qltgConnection.closeConnect();
        return ok;
    }

    public void close() {
        qltgConnection.closeConnect();
    }
}
