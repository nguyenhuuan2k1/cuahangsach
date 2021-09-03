package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyTacGia.TacGia;
import giaodienchuan.model.BackEnd.QuanLyTacGia.QuanLyTacGiaBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaTacGiaForm extends JFrame {

    String type;
    QuanLyTacGiaBUS qltgBUS = new QuanLyTacGiaBUS();
    TacGia tgSua;

    JTextField txMatg = new JTextField(15);
    JTextField txTentg = new JTextField(15);
    JTextArea txMota = new JTextArea(3, 15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaTacGiaForm(String _type, String _matg) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMatg.setBorder(BorderFactory.createTitledBorder("Mã tác giả"));
        txTentg.setBorder(BorderFactory.createTitledBorder("Tên tác giả"));
        txMota.setBorder(BorderFactory.createTitledBorder("Mô tả"));

        JPanel plInput = new JPanel();
        plInput.add(txMatg);
        plInput.add(txTentg);
        plInput.add(txMota);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm tác giả");
            txMatg.setText(qltgBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa tác giả");
            for (TacGia tg : qltgBUS.getDstg()) {
                if (tg.getMaTG().equals(_matg)) {
                    this.tgSua = tg;
                }
            }
            if (this.tgSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy tác giả");
                this.dispose();
            }

            txMatg.setText(this.tgSua.getMaTG());
            txTentg.setText(this.tgSua.getTenTG());
            txMota.setText(this.tgSua.getMoTa());

            txMatg.setEditable(false);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }
        
        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemMouseClicked();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            this.dispose();
        });

        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String matg = txMatg.getText();
            String tentg = txTentg.getText();
            String mota = txMota.getText();

            if (qltgBUS.add(matg, tentg, mota)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tentg + " thành công!");
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String matg = txMatg.getText();
            String tentg = txTentg.getText();
            String mota = txMota.getText();

            if (qltgBUS.update(matg, tentg, mota)) {
                JOptionPane.showMessageDialog(this, "Sửa " + matg + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String matg = txMatg.getText();
        String tentg = txTentg.getText();
        String mota = txMota.getText();

        if (matg.trim().equals("")) {
            return showErrorTx(txMatg, "Mã tác giả không được để trống");

        } else if (tentg.trim().equals("")) {
            return showErrorTx(txTentg, "Tên tác giả không được để trống");

        } else if (mota.trim().equals("")) {
            return showErrorTx(txMota, "Mô tả không được để trống");
        }

        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }

    private Boolean showErrorTx(JTextArea tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
