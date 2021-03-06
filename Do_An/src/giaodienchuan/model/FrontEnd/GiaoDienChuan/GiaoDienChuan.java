package giaodienchuan.model.FrontEnd.GiaoDienChuan;

import giaodienchuan.model.BackEnd.ReadWriteFile.WorkWithFile;
import giaodienchuan.model.FrontEnd.FormQuanLy.BanHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.BeginForm;
//import giaodienchuan.model.FrontEnd.FormQuanLy.EmptyPage;
import giaodienchuan.model.FrontEnd.FormQuanLy.NhapHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhaCungCapForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhaXuatBanForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyHoaDonForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLySanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyKhachHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyKhuyenMaiForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyLoaiSanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyTacGiaForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhanVienForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyPhieuNhapForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyQuyenForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyTaiKhoanForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.ThongKe.ThongKeForm;
import giaodienchuan.model.FrontEnd.NavBar.NavBarButton;
import giaodienchuan.model.FrontEnd.NavBar.NavBarContainer;
import giaodienchuan.model.FrontEnd.NavBar.NavBarSeperator;
import giaodienchuan.model.FrontEnd.NavBar.NavBarTitle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GiaoDienChuan extends JFrame implements MouseListener {
    
    final int WIDTH = 1370, HEIGHT = 800;/*OK*/
    int px, py;
    NavBarContainer menu, header;
    NavBarButton currentTab;
    NavBarTitle headerTitle;

    JPanel plContent = new JPanel();
//    EmptyPage emptypage = new EmptyPage();
    BanHangForm banhang;
    NhapHangForm nhaphang;
    QuanLySanPhamForm qlsp;
    QuanLyLoaiSanPhamForm qllsp;
    QuanLyTacGiaForm qltg;
    QuanLyTaiKhoanForm qltk;
    QuanLyNhanVienForm qlnv;
    QuanLyKhachHangForm qlkh;
    QuanLyQuyenForm qlq;
    QuanLyHoaDonForm qlhd;
    QuanLyPhieuNhapForm qlpn;
    QuanLyKhuyenMaiForm qlkm;
    QuanLyNhaCungCapForm qlncc;
    QuanLyNhaXuatBanForm qlnxb;
    ThongKeForm thongke;

    public GiaoDienChuan() {

        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setTitle("Qu???n L?? S??ch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8-book-30.png"));
        setIconImage(logo.getImage());

        // ======================== Menu =======================
        String[] navItemInfo = {
            "seperate", "1", "", "",
            "B??n h??ng", "icons8-shop-30.png", "qlBanHang", "qlBanHang",
            "Nh???p h??ng", "icons8_downloads_30px.png", "qlNhapHang", "qlNhapHang",
            "seperate", "2", "", "",
            "S???n ph???m", "icons8-book-30.png", "xemSanPham", "qlSanPham",
            "Th??? lo???i", "icons8_dossier_folder_30px.png", "xemLoaiSanPham", "qlLoaiSanPham",
            "T??c gi???", "icons8-quill-with-ink-30.png", "xemTacGia", "qlTacGia",
            "Nh?? xu???t b???n", "icons8_company_30px.png", "xemNhaXuatBan", "qlNhaXuatBan",
            "H??a ????n", "icons8_agreement_30px.png", "xemHoaDon", "qlHoaDon",
            "Phi???u nh???p", "icons8-invoice-30.png", "xemPheuNhap", "qlPhieuNhap",
            "Khuy???n m??i", "icons8-discount-30.png", "xemKhuyenMai", "qlKhuyenMai",
            "seperate", "2", "", "",
            "Nh??n vi??n", "icons8-customer-agent-30.png", "xemNhanVien", "qlNhanVien",
            "Kh??ch h??ng", "icons8-account-30.png", "xemKhachHang", "qlKhachHang",
            "Nh?? cung c???p", "icons8_company_30px.png", "xemNCC", "qlNCC",
            "seperate", "2", "", "",
            "T??i kho???n", "icons8_key_30px.png", "xemTaiKhoan", "qlTaiKhoan",
            "Quy???n", "icons8_police_badge_30px.png", "xemQuyen", "qlQuyen",
            "seperate", "1", "", "",
            "Th???ng k??", "icons8-chart-30.png", "", ""
        };
        //Kich thuoc thanh menu
        int menuW = 230;
        int menuH = 0;
        menu = new NavBarContainer(new Rectangle(0, 0, menuW, HEIGHT));
        // menu.addItem(new NavBarTitle(new Rectangle(0, 0, 0, 55), "CH???C N??NG"));
        for (int i = 0; i < navItemInfo.length; i += 4) {
            if (navItemInfo[i].equals("seperate")) {
                NavBarSeperator s = new NavBarSeperator(new Rectangle(0, 0, 0, Integer.parseInt(navItemInfo[i + 1])));
                menu.addItem(s);

            } else {

                String chitietquyen = LoginForm.quyenLogin.getChiTietQuyen();
                if (chitietquyen.contains(navItemInfo[i + 2]) || chitietquyen.contains(navItemInfo[i + 3])) {
                    NavBarButton nb = new NavBarButton(new Rectangle(0, 0, 0, 60), navItemInfo[i], navItemInfo[i + 1]);
                    nb.addMouseListener(this);
                    menu.addItem(nb);
                    menuH += 65;
                }
            }
        }

        
        JScrollPane scrollMenu = new JScrollPane(menu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menu.setAutoscrolls(true);
        menu.setPreferredSize(new Dimension(menuW , menuH + 20));
        scrollMenu.setPreferredSize(new Dimension(menuW, HEIGHT));
        scrollMenu.setBorder(BorderFactory.createEmptyBorder());
        scrollMenu.getHorizontalScrollBar().setUnitIncrement(5);

        // ================ Header ===================
        int headerBg = 100;
        int headerH = 45;
        header = new NavBarContainer(new Rectangle(0, 0, WIDTH, headerH));
        header.setBackground(new Color(headerBg, headerBg, headerBg));

        headerTitle = new NavBarTitle(new Rectangle((WIDTH - 400) / 2, 0, 400, headerH), "QU???N L?? C???A H??NG S??CH");
        headerTitle.setColorDefault(new Color(255, 255, 255));
        headerTitle.setBgDefault(new Color(headerBg, headerBg, headerBg));
        headerTitle.setFontSize(23);
        header.addItem(headerTitle, false);

        // Close Button
        int btnWidth = 50;
        int iconSize = 30;
        NavBarButton btnClose = new NavBarButton(new Rectangle(WIDTH - btnWidth, 0, btnWidth, headerH), "", "icons8_shutdown_30px_1.png");
        btnClose.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnClose.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnClose.setBgHover(new Color(190, 45, 45));
        btnClose.setToolTipText("Tho??t");
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                int reply = JOptionPane.showConfirmDialog(getRootPane(),
                        "B???n c?? ch???c mu???n tho??t ch????ng tr??nh Qu???n L???", "Ch?? ??",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        header.addItem(btnClose, false);

        // Minimize Button
        NavBarButton btnMinimize = new NavBarButton(new Rectangle(WIDTH - btnWidth * 2, 0, btnWidth, headerH), "", "icons8_angle_down_30px.png");
        btnMinimize.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnMinimize.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnMinimize.setBgHover(new Color(49, 49, 49));
        btnMinimize.setToolTipText("Thu nh???");
        btnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                setState(ICONIFIED);
            }
        });
        header.addItem(btnMinimize, false);

        // logout button
        if (LoginForm.taiKhoanLogin != null) {

            String tenNhanVien = LoginForm.nhanVienLogin.getTenNV();

            NavBarButton btnLogout = new NavBarButton(new Rectangle(0, 0, 230 - btnWidth, headerH), tenNhanVien, "icons8_exit_30px.png");
            btnLogout.setBgDefault(new Color(headerBg, headerBg, headerBg));
            btnLogout.setBgHover(new Color(49, 49, 49));
            btnLogout.relocate2();
            btnLogout.setToolTipText("????ng xu???t (" + tenNhanVien + " - " + LoginForm.nhanVienLogin.getMaNV() + ")");
            btnLogout.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    logout();
                }
            });
            header.addItem(btnLogout, false);

            // btn Xem t??i kho???n ????ng nh???p
            NavBarButton btnSettingUser = new NavBarButton(new Rectangle(230 - btnWidth, 0, btnWidth, headerH), "", "icons8_settings_30px_1.png");
            btnSettingUser.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
            btnSettingUser.setBgDefault(new Color(headerBg, headerBg, headerBg));
            btnSettingUser.setBgHover(new Color(49, 49, 49));
            btnSettingUser.setToolTipText("T??i kho???n");
            btnSettingUser.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    new DoiMatKhauForm(LoginForm.taiKhoanLogin.getUsername()).setVisible(true);
                }
            });
            header.addItem(btnSettingUser, false);
        }

        // ========= Draggable ===========
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                px = me.getX();
                py = me.getY();
            }
        });

        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - px, getLocation().y + me.getY() - py);
            }
        });

        plContent.setLayout(new BorderLayout());
        plContent.add(new BeginForm("Ch??o " + LoginForm.nhanVienLogin.getTenNV()), BorderLayout.CENTER);
        plContent.setBackground(new Color(255,255,255));

        addMouseListener(this);
        
        add(header, BorderLayout.NORTH);
        add(scrollMenu, BorderLayout.WEST);
        add(plContent, BorderLayout.CENTER);
    }

    private void logout() {
        int reply = JOptionPane.showConfirmDialog(getRootPane(),
                "B???n c?? ch???c mu???n ????ng xu???t kh???i " + LoginForm.nhanVienLogin.getTenNV() + "?", "Ch?? ??",
                JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            new WorkWithFile(LoginForm.saveFileName).write(""); // x??a d??? li???u ????ng nh???p
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }

    public void doAction(String nameAction) {
        plContent.removeAll();
        switch (nameAction) {
            case "B??n h??ng":
                if (banhang == null) {
                    banhang = new BanHangForm(WIDTH - 250, HEIGHT - 55);
                }
                plContent.add(banhang, BorderLayout.CENTER);
                break;
                
            case "Nh???p h??ng":
                if (nhaphang == null) {
                    nhaphang = new NhapHangForm(WIDTH - 250, HEIGHT - 55);
                }
                plContent.add(nhaphang, BorderLayout.CENTER);
                break;

            case "S???n ph???m":
                if (qlsp == null) {
                    qlsp = new QuanLySanPhamForm();
                }
                plContent.add(qlsp, BorderLayout.CENTER);
                break;

            case "Th??? lo???i":
                if (qllsp == null) {
                    qllsp = new QuanLyLoaiSanPhamForm();
                }
                plContent.add(qllsp, BorderLayout.CENTER);
                break;

            case "T??c gi???":
                if (qltg == null) {
                    qltg = new QuanLyTacGiaForm();
                }
                plContent.add(qltg, BorderLayout.CENTER);
                break;
            
            case "Nh?? xu???t b???n":
                if (qlnxb == null){
                    qlnxb = new QuanLyNhaXuatBanForm();
                }
                plContent.add(qlnxb, BorderLayout.CENTER);
                break;
            
            case "H??a ????n":
                if (qlhd == null) {
                    qlhd = new QuanLyHoaDonForm();
                }
                plContent.add(qlhd, BorderLayout.CENTER);
                break;

            case "Khuy???n m??i":
                if (qlkm == null) {
                    qlkm = new QuanLyKhuyenMaiForm();
                }
                plContent.add(qlkm, BorderLayout.CENTER);
                break;

            case "Phi???u nh???p":
                if (qlpn == null) {
                    qlpn = new QuanLyPhieuNhapForm();
                }
                plContent.add(qlpn, BorderLayout.CENTER);
                break;

            case "Quy???n":
                if (qlq == null) {
                    qlq = new QuanLyQuyenForm();
                }
                plContent.add(qlq, BorderLayout.CENTER);
                break;

            case "T??i kho???n":
                if (qltk == null) {
                    qltk = new QuanLyTaiKhoanForm();
                }
                plContent.add(qltk, BorderLayout.CENTER);
                break;

            case "Nh??n vi??n":
                if (qlnv == null) {
                    qlnv = new QuanLyNhanVienForm();
                }
                plContent.add(qlnv, BorderLayout.CENTER);
                break;

            case "Kh??ch h??ng":
                if (qlkh == null) {
                    qlkh = new QuanLyKhachHangForm();
                }
                plContent.add(qlkh, BorderLayout.CENTER);
                break;

            case "Nh?? cung c???p":

                if (qlncc == null) {
                    qlncc = new QuanLyNhaCungCapForm();
                }
                plContent.add(qlncc, BorderLayout.CENTER);
                break;

            case "Th???ng k??":
                if (thongke == null) {
                    thongke = new ThongKeForm();
                }
                plContent.add(thongke, BorderLayout.CENTER);
                break;

            
        }
        headerTitle.setLabel(nameAction.toUpperCase());
        // https://stackoverflow.com/questions/12989388/switching-panels-with-menubar
        revalidate();//refresh ui and layout
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getSource() instanceof NavBarButton) {

            NavBarButton btn = (NavBarButton) me.getSource();
            if (currentTab != null) {
                currentTab.setActive(false);
            }

            btn.setActive(true);
            currentTab = btn;
            doAction(btn.text);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
