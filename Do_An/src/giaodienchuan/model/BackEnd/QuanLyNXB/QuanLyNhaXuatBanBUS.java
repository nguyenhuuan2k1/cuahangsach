
package giaodienchuan.model.BackEnd.QuanLyNXB;

import java.util.ArrayList;
/**
 *
 * @author DELL
 */
public class QuanLyNhaXuatBanBUS {
    
    private ArrayList<NhaXuatBan> dsnxb = new ArrayList<>();
    private QuanLyNhaXuatBanDAO qlnxbDAO = new QuanLyNhaXuatBanDAO();

    public QuanLyNhaXuatBanBUS() {
        dsnxb = qlnxbDAO.readDB();
    }

    public void showConsole() {
        dsnxb.forEach((nxb) -> {
            System.out.print(nxb.getMaNXB() + " ");
            System.out.print(nxb.getTenNXB());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã nhà xuất bản", "Tên nhà xuất bản", "Địa chỉ"};
    }
    
    public String getNextID() {
        return "NXB" + String.valueOf(this.dsnxb.size() + 1);
    }

    public void readDB() {
        dsnxb = qlnxbDAO.readDB();
    }
    
    public NhaXuatBan getNhaXuatBan(String manxb) {
        for (NhaXuatBan nxb : dsnxb) {
            if (nxb.getMaNXB().equals(manxb)) {
                return nxb;
            }
        }
        return null;
    }

    public ArrayList<NhaXuatBan> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<NhaXuatBan> result = new ArrayList<>();

        dsnxb.forEach((nxb) -> {
            if (type.equals("Tất cả")) {
                if (nxb.getMaNXB().toLowerCase().contains(value.toLowerCase())
                        || nxb.getTenNXB().toLowerCase().contains(value.toLowerCase())
                        || nxb.getDiaChi().toLowerCase().contains(value.toLowerCase()))  {
                    result.add(nxb);
                }
            } else {
                switch (type) {
                    case "Mã nhà xuất bản":
                        if (nxb.getMaNXB().toLowerCase().contains(value.toLowerCase())) {
                            result.add(nxb);
                        }
                        break;
                    case "Tên nhà xuất bản":
                        if (nxb.getTenNXB().toLowerCase().contains(value.toLowerCase())) {
                            result.add(nxb);
                        }
                        break;
                    case "Địa chỉ":
                        if (nxb.getDiaChi().toLowerCase().contains(value.toLowerCase())) {
                            result.add(nxb);
                        }
                        break;

                }
            }

        });

        return result;
    }

    public Boolean add(NhaXuatBan nxb) {
        Boolean ok = qlnxbDAO.add(nxb);

        if (ok) {
            dsnxb.add(nxb);
        }
        return ok;
    }

    public Boolean add(String manxb, String tennxb, String diachi) {
        NhaXuatBan nxb = new NhaXuatBan(manxb, tennxb, diachi);
        return add(nxb);
    }

    public Boolean delete(String manxb) {
        Boolean ok = qlnxbDAO.delete(manxb);

        if (ok) {
            for (int i = (dsnxb.size() - 1); i >= 0; i--) {
                if (dsnxb.get(i).getMaNXB().equals(manxb)) {
                    dsnxb.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String manxb, String tennxb, String diachi) {
        Boolean ok = qlnxbDAO.update(manxb, tennxb, diachi);

        if (ok) {
            dsnxb.forEach((nxb) -> {
                if (nxb.getMaNXB().equals(manxb)) {
                    nxb.setTenNXB(tennxb);
                    nxb.setDiaChi(diachi);
                }
            });
        }

        return ok;
    }

    public ArrayList<NhaXuatBan> getDsnxb() {
        return dsnxb;
    }
}
