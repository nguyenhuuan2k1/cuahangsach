package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyTacGia.QuanLyTacGiaBUS;
import giaodienchuan.model.BackEnd.WorkWithExcel.DocExcel;
import giaodienchuan.model.BackEnd.WorkWithExcel.XuatExcel;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiTacGia;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaTacGiaForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import giaodienchuan.model.FrontEnd.MyButton.ExportExcelButton;
import giaodienchuan.model.FrontEnd.MyButton.ImportExcelButton;
import giaodienchuan.model.FrontEnd.MyButton.SuaButton;
import giaodienchuan.model.FrontEnd.MyButton.ThemButton;
import giaodienchuan.model.FrontEnd.MyButton.XoaButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyTacGiaForm extends JPanel {

    HienThiTacGia formHienThi = new HienThiTacGia();

    ThemButton btnThem = new ThemButton();
    SuaButton btnSua = new SuaButton();
    XoaButton btnXoa = new XoaButton();
    
    ExportExcelButton btnXuatExcel = new ExportExcelButton();
    ImportExcelButton btnNhapExcel = new ImportExcelButton();

    JComboBox<String> cbTypeSearch;

    // index
    final int MATG_I = 1, TENTG_I = 2, MOTA_I = 3;

    public QuanLyTacGiaForm() {
        setLayout(new BorderLayout());

        // buttons        
        if (!LoginForm.quyenLogin.getChiTietQuyen().contains("qlTacGia")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
            btnNhapExcel.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnXuatExcel);
        plBtn.add(btnNhapExcel);

        //=========== add all to this jpanel ===========
        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
        });
        btnXuatExcel.addActionListener((ActionEvent ae) -> {
            new XuatExcel().xuatFileExcelLoaiSanPham();
        });
        btnNhapExcel.addActionListener((ActionEvent ae) -> {
            new DocExcel().docFileExcelLoaiSanPham();
        });
    }

    private void btnSuaMouseClicked() {
        String matg = formHienThi.getSelectedRow(1);
        if (matg != null) {
            ThemSuaTacGiaForm suatg = new ThemSuaTacGiaForm("Sửa", matg);

            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suatg.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn tác giả nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String matg = formHienThi.getSelectedRow(1);
        if (matg != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tác giả " + matg + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyTacGiaBUS().delete(matg);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn tác giả nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaTacGiaForm themtg = new ThemSuaTacGiaForm("Thêm", "");

        themtg.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
