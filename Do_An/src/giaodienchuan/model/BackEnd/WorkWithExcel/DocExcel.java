/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.KhuyenMai;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.QuanLyQuyenBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.Quyen;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.QuanLyTaiKhoanBUS;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.TaiKhoan;
import giaodienchuan.model.BackEnd.QuanLyTacGia.QuanLyTacGiaBUS;
import giaodienchuan.model.BackEnd.QuanLyTacGia.TacGia;
import giaodienchuan.model.BackEnd.QuanLyNXB.NhaXuatBan;
import giaodienchuan.model.BackEnd.QuanLyNXB.QuanLyNhaXuatBanBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Admin
 */
public class DocExcel {

    FileDialog fd = new FileDialog(new JFrame(), "?????c excel", FileDialog.LOAD);

    public DocExcel() {

    }

    private String getFile() {
        fd.setFile("*.xls");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }

    //?????c file excel Nh?? cung c???p
    public void docFileExcelNhaCungCap() {
        fd.setTitle("Nh???p d??? li???u nh?? cung c???p t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    String fax = cellIterator.next().getStringCellValue();

                    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();

                    NhaCungCap nccOld = qlnccBUS.getNhaCungCap(ma);
                    if (nccOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "?????a ch???", "SDT", "Fax"});
                            mtb.addRow(new String[]{
                                "C??:", nccOld.getMaNCC(),
                                nccOld.getTenNCC(),
                                nccOld.getDiaChi(),
                                nccOld.getSDT(),
                                nccOld.getFax()
                            });
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, diachi, sdt, fax
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlnccBUS.update(ma, ten, diachi, sdt, fax);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        NhaCungCap ncc = new NhaCungCap(ma, ten, diachi, sdt, fax);
                        qlnccBUS.add(ncc);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }
    
    //?????c file excel Nh?? xu???t b???n
    public void docFileExcelNhaXuatBan() {
        fd.setTitle("Nh???p d??? li???u nh?? xu???t b???n t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String diachi = cellIterator.next().getStringCellValue();

                    QuanLyNhaXuatBanBUS qlnxbBUS = new QuanLyNhaXuatBanBUS();

                    NhaXuatBan nxbOld = qlnxbBUS.getNhaXuatBan(ma);
                    if (nxbOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "?????a ch???"});
                            mtb.addRow(new String[]{
                                "C??:", nxbOld.getMaNXB(),
                                nxbOld.getTenNXB(),
                                nxbOld.getDiaChi()
                                
                            });
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, diachi
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlnxbBUS.update(ma, ten, diachi);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        NhaXuatBan nxb = new NhaXuatBan(ma, ten, diachi);
                        qlnxbBUS.add(nxb);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    // ?????c file excel quy???n
    public void docFileExcelQuyen() {
        fd.setTitle("Nh???p d??? li???u quy???n t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String chitiet = cellIterator.next().getStringCellValue();

                    QuanLyQuyenBUS qlqBUS = new QuanLyQuyenBUS();

                    Quyen qOld = qlqBUS.getQuyen(ma);
                    if (qOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "Chi ti???t quy???n"});
                            mtb.addRow(new String[]{
                                "C??:", qOld.getMaQuyen(),
                                qOld.getTenQuyen(),
                                qOld.getChiTietQuyen()
                            });
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, chitiet
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlqBUS.update(ma, ten, chitiet);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        Quyen q = new Quyen(ma, ten, chitiet);
                        qlqBUS.add(q);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel T??i kho???n
    public void docFileExcelTaiKhoan() {
        fd.setTitle("Nh???p d??? li???u t??i kho???n t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String taikhoan = cellIterator.next().getStringCellValue();
                    String matkhau = cellIterator.next().getStringCellValue();
                    String manv = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String maquyen = cellIterator.next().getStringCellValue();

                    QuanLyTaiKhoanBUS qltkBUS = new QuanLyTaiKhoanBUS();
                    TaiKhoan tkOld = qltkBUS.getTaiKhoan(manv);

                    if (tkOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "T??n t??i kho???n", "M???t kh???u", "M?? nh??n vi??n", "M?? quy???n"});
                            mtb.addRow(new String[]{
                                "C??:", tkOld.getUsername(),
                                tkOld.getPassword(),
                                tkOld.getMaNV(),
                                tkOld.getMaQuyen(),});
                            mtb.addRow(new String[]{
                                "M???i:", taikhoan, matkhau, manv, maquyen
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qltkBUS.update(taikhoan, matkhau, manv, maquyen);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        TaiKhoan tk = new TaiKhoan(taikhoan, matkhau, manv, maquyen);
                        qltkBUS.add(tk);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel Kh??ch h??ng
    public void docFileExcelKhachhang() {
        fd.setTitle("Nh???p d??? li???u kh??ch h??ng t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("???n") ? 1 : 0);

                    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
                    KhachHang khOLD = qlkhBUS.getKhachHang(ma);

                    if (khOLD != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "?????a ch???", "SDT", "Tr???ng th??i"});
                            mtb.addRow(new String[]{
                                "C??:", khOLD.getMaKH(),
                                khOLD.getTenKH(),
                                khOLD.getDiaChi(),
                                khOLD.getSDT(),
                                String.valueOf(khOLD.getTrangThai())
                            });
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, diachi, sdt, String.valueOf(trangthai)
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlkhBUS.update(ma, ten, diachi, sdt, trangthai);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        KhachHang kh = new KhachHang(ma, ten, diachi, sdt, trangthai);
                        qlkhBUS.add(kh);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel Nh??n vi??n
    public void docFileExcelNhanVien() {
        fd.setTitle("Nh???p d??? li???u nh??n vi??n t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    LocalDate ngaysinh = LocalDate.parse(cellIterator.next().getStringCellValue());
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("???n") ? 1 : 0);

                    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
                    NhanVien nvOld = qlnvBUS.getNhanVien(ma);

                    if (nvOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "Ng??y sinh", "?????a ch???", "SDT", "Tr???ng th??i"});
                            mtb.addRow(new String[]{
                                "C??:", nvOld.getMaNV(),
                                nvOld.getTenNV(),
                                String.valueOf(nvOld.getNgaySinh()),
                                nvOld.getDiaChi(),
                                nvOld.getSDT(),
                                String.valueOf(nvOld.getTrangThai())
                            });
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, String.valueOf(ngaysinh), diachi, sdt, String.valueOf(trangthai)
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlnvBUS.update(ma, ten, ngaysinh, diachi, sdt, trangthai);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        NhanVien nv = new NhanVien(ma, ten, ngaysinh, diachi, sdt, trangthai);
                        qlnvBUS.add(nv);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel Khuy???n m??i
    public void docFileExcelKhuyenMai() {
        fd.setTitle("Nh???p d??? li???u khuy???n m??i t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    float dieukien = (float) cellIterator.next().getNumericCellValue();
                    float phantram = (float) cellIterator.next().getNumericCellValue();
                    LocalDate ngaybatdau = LocalDate.parse(cellIterator.next().getStringCellValue());
                    LocalDate ngayketthuc = LocalDate.parse(cellIterator.next().getStringCellValue());

                    QuanLyKhuyenMaiBUS qlkmBUS = new QuanLyKhuyenMaiBUS();
                    KhuyenMai kmOld = qlkmBUS.getKhuyenMai(ma);

                    if (kmOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??nKM", "??i???u ki???n", "Gi???m gi??", "Ng??y b???t ?????u", "Ng??y k???t th??c"});
                            mtb.addRow(new String[]{
                                "C??:", kmOld.getMaKM(),
                                kmOld.getTenKM(),
                                String.valueOf(kmOld.getDieuKienKM()),
                                String.valueOf(kmOld.getPhanTramKM()),
                                String.valueOf(kmOld.getNgayBD()),
                                String.valueOf(kmOld.getNgayKT()),});
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten,
                                String.valueOf(dieukien),
                                String.valueOf(phantram),
                                String.valueOf(ngaybatdau),
                                String.valueOf(ngayketthuc)
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlkmBUS.update(ma, ten, dieukien, phantram, ngaybatdau, ngayketthuc);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        KhuyenMai km = new KhuyenMai(ma, ten, dieukien, phantram, ngaybatdau, ngayketthuc);
                        qlkmBUS.add(km);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel S???n ph???m
    public void docFileExcelSanPham() {
        fd.setTitle("Nh???p d??? li???u s???n ph???m t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String masp = cellIterator.next().getStringCellValue();
                    String maloai = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String tensp = cellIterator.next().getStringCellValue();
                    float dongia = (float) cellIterator.next().getNumericCellValue();
                    int soluong = (int) cellIterator.next().getNumericCellValue();
                    String hinhanh = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("???n") ? 1 : 0);
                    String matg = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String manxb = cellIterator.next().getStringCellValue().split(" - ")[0];

                    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
                    SanPham spOld = qlspBUS.getSanPham(masp);
                    if (spOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M?? SP", "M?? LSP", "T??n SP", "????n gi??", "S??? l?????ng", "H??nh ???nh", "Tr???ng th??i", "M?? TG", "M?? NXB"});
                            mtb.addRow(new String[]{
                                "C??:", spOld.getMaSP(),
                                spOld.getMaLSP(),
                                spOld.getTenSP(),
                                String.valueOf(spOld.getDonGia()),
                                String.valueOf(spOld.getSoLuong()),
                                spOld.getFileNameHinhAnh(),
                                String.valueOf(spOld.getTrangThai()),
                                spOld.getMaTG(),
                                spOld.getMaNXB()
                            });
                            mtb.addRow(new String[]{
                                "M???i:", masp, maloai, tensp, String.valueOf(dongia), String.valueOf(soluong), hinhanh, String.valueOf(trangthai), matg, manxb
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qlspBUS.update(masp, maloai, tensp, dongia, soluong, hinhanh, trangthai, matg, manxb);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        SanPham sp = new SanPham(masp, maloai, tensp, dongia, soluong, hinhanh, trangthai, matg, manxb);
                        qlspBUS.add(sp);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }

    //?????c file excel Lo???i s???n ph???m
    public void docFileExcelLoaiSanPham() {
        fd.setTitle("Nh???p d??? li???u lo???i s???n ph???m t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String mota = cellIterator.next().getStringCellValue();

                    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();
                    LoaiSanPham lspOld = qllspBUS.getLoaiSanPham(ma);

                    if (lspOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "M?? t???"});
                            mtb.addRow(new String[]{
                                "C??:", lspOld.getMaLSP(),
                                lspOld.getTenLSP(),
                                lspOld.getMoTa(),});
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, mota
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qllspBUS.update(ma, ten, mota);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        LoaiSanPham lsp = new LoaiSanPham(ma, ten, mota);
                        qllspBUS.add(lsp);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }
    
    // ?????c file excel T??c gi???
    public void docFileExcelTacGia() {
        fd.setTitle("Nh???p d??? li???u t??c gi??? t??? excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String mota = cellIterator.next().getStringCellValue();

                    QuanLyTacGiaBUS qltgBUS = new QuanLyTacGiaBUS();
                    TacGia tgOld = qltgBUS.getTacGia(ma);

                    if (tgOld != null) {
                        if (!hanhDongKhiTrung.contains("t???t c???")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "M??", "T??n", "M?? t???"});
                            mtb.addRow(new String[]{
                                "C??:", tgOld.getMaTG(),
                                tgOld.getTenTG(),
                                tgOld.getMoTa(),});
                            mtb.addRow(new String[]{
                                "M???i:", ma, ten, mota
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi ????")) {
                            qltgBUS.update(ma, ten, mota);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        TacGia tg = new TacGia(ma, ten, mota);
                        qltgBUS.add(tg);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "?????c th??nh c??ng, "
                    + "Th??m " + countThem
                    + "; Ghi ???? " + countGhiDe
                    + "; B??? qua " + countBoQua
                    + ". Vui l??ng l??m m???i ????? th???y k???t qu???");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "L???i khi nh???p d??? li???u t??? file: " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "L???i khi ????ng inputstream: " + ex.getMessage());
            }
        }
    }
}
