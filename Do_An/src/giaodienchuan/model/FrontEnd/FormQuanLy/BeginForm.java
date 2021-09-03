/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class BeginForm extends JPanel {

    JLabel lbInfo;
    JLabel lbInfo1;

    public BeginForm(String text) {
        setLayout(new BorderLayout());

        lbInfo = new JLabel(text, JLabel.CENTER);
        lbInfo.setFont(new Font("Arial", Font.BOLD, 40));
        lbInfo.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8-ok-96.png")));
        lbInfo1 = new JLabel("Chào mừng đến với phần mềm quản lý cửa hàng sách", JLabel.CENTER);
        lbInfo1.setFont(new Font("Arial", Font.BOLD, 40));
        add(lbInfo, BorderLayout.CENTER);
        add(lbInfo1, BorderLayout.NORTH);
    }

    public void setLabelText(String text) {
        lbInfo.setText(text);
    }
}
