package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyNXB.NhaXuatBan;
import giaodienchuan.model.BackEnd.QuanLyNXB.QuanLyNhaXuatBanBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaNhaXuatBanForm extends JFrame {

    String type;
    QuanLyNhaXuatBanBUS qlnxbBUS = new QuanLyNhaXuatBanBUS();
    NhaXuatBan nxbSua;

    JTextField txManxb = new JTextField(15);
    JTextField txTennxb = new JTextField(15);
    JTextArea txDiachi = new JTextArea(3, 15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaNhaXuatBanForm(String _type, String _manxb) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txManxb.setBorder(BorderFactory.createTitledBorder("Mã nhà xuất bản"));
        txTennxb.setBorder(BorderFactory.createTitledBorder("Tên nhà xuất bản"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));

        JPanel plInput = new JPanel();
        plInput.add(txManxb);
        plInput.add(txTennxb);
        plInput.add(txDiachi);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm nhà xuất bản");
            txManxb.setText(qlnxbBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa nhà xuất bản");
            for (NhaXuatBan nxb : qlnxbBUS.getDsnxb()) {
                if (nxb.getMaNXB().equals(_manxb)) {
                    this.nxbSua = nxb;
                }
            }
            if (this.nxbSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy nhà xuất bản");
                this.dispose();
            }

            txManxb.setText(this.nxbSua.getMaNXB());
            txTennxb.setText(this.nxbSua.getTenNXB());
            txDiachi.setText(this.nxbSua.getDiaChi());

            txManxb.setEditable(false);

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
            String manxb = txManxb.getText();
            String tennxb = txTennxb.getText();
            String diachi = txDiachi.getText();

            if (qlnxbBUS.add(manxb, tennxb, diachi)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tennxb + " thành công!");
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String manxb = txManxb.getText();
            String tennxb = txTennxb.getText();
            String diachi = txDiachi.getText();

            if (qlnxbBUS.update(manxb, tennxb, diachi)) {
                JOptionPane.showMessageDialog(this, "Sửa " + manxb + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String manxb = txManxb.getText();
        String tennxb = txTennxb.getText();
        String diachi = txDiachi.getText();

        if (manxb.trim().equals("")) {
            return showErrorTx(txManxb, "Mã nhà xuất bản không được để trống");

        } else if (tennxb.trim().equals("")) {
            return showErrorTx(txTennxb, "Tên nhà xuất bản không được để trống");

        } else if (diachi.trim().equals("")) {
            return showErrorTx(txDiachi, "Địa chỉ không được để trống");
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
