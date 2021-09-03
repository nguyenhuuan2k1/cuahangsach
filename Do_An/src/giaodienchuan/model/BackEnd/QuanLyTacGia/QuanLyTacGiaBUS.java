
package giaodienchuan.model.BackEnd.QuanLyTacGia;


import java.util.ArrayList;
/**
 *
 * @author DELL
 */
public class QuanLyTacGiaBUS {
    private ArrayList<TacGia> dstg = new ArrayList<>();
    private QuanLyTacGiaDAO qltgDAO = new QuanLyTacGiaDAO();

    public QuanLyTacGiaBUS() {
        dstg = qltgDAO.readDB();
    }

    public void showConsole() {
        dstg.forEach((tg) -> {
            System.out.print(tg.getMaTG() + " ");
            System.out.print(tg.getTenTG());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã tác giả", "Tên tác giả", "Mô tả"};
    }
    
    public String getNextID() {
        return "TG" + String.valueOf(this.dstg.size() + 1);
    }

    public void readDB() {
        dstg = qltgDAO.readDB();
    }
    
    public TacGia getTacGia(String matg) {
        for (TacGia tg : dstg) {
            if (tg.getMaTG().equals(matg)) {
                return tg;
            }
        }
        return null;
    }

    public ArrayList<TacGia> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<TacGia> result = new ArrayList<>();

        dstg.forEach((tg) -> {
            if (type.equals("Tất cả")) {
                if (tg.getMaTG().toLowerCase().contains(value.toLowerCase())
                        || tg.getTenTG().toLowerCase().contains(value.toLowerCase())
                        || tg.getMoTa().toLowerCase().contains(value.toLowerCase()))  {
                    result.add(tg);
                }
            } else {
                switch (type) {
                    case "Mã tác giả":
                        if (tg.getMaTG().toLowerCase().contains(value.toLowerCase())) {
                            result.add(tg);
                        }
                        break;
                    case "Tên tác giả":
                        if (tg.getTenTG().toLowerCase().contains(value.toLowerCase())) {
                            result.add(tg);
                        }
                        break;
                    case "Mô tả":
                        if (tg.getMoTa().toLowerCase().contains(value.toLowerCase())) {
                            result.add(tg);
                        }
                        break;

                }
            }

        });

        return result;
    }

    public Boolean add(TacGia tg) {
        Boolean ok = qltgDAO.add(tg);

        if (ok) {
            dstg.add(tg);
        }
        return ok;
    }

    public Boolean add(String matg, String tentg, String mota) {
        TacGia tg = new TacGia(matg, tentg, mota);
        return add(tg);
    }

    public Boolean delete(String matg) {
        Boolean ok = qltgDAO.delete(matg);

        if (ok) {
            for (int i = (dstg.size() - 1); i >= 0; i--) {
                if (dstg.get(i).getMaTG().equals(matg)) {
                    dstg.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String matg, String tentg, String mota) {
        Boolean ok = qltgDAO.update(matg, tentg, mota);

        if (ok) {
            dstg.forEach((tg) -> {
                if (tg.getMaTG().equals(matg)) {
                    tg.setTenTG(tentg);
                    tg.setMoTa(mota);
                }
            });
        }

        return ok;
    }

    public ArrayList<TacGia> getDstg() {
        return dstg;
    }
}
