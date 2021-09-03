package giaodienchuan.model.FrontEnd.NavBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

public class NavBarContainer extends JPanel {

    private int bottomPos = 0;
    private int lengthPos = 0;
    private ArrayList<NavBarItem> navItems = new ArrayList<>();

    public NavBarContainer(Rectangle rec) {
        setLayout(null);
        setBounds(rec);
        setPreferredSize(new Dimension(rec.width, rec.height));
        setBackground(new Color(255, 255, 255));
    }

    public void addItem(NavBarItem item, Boolean fullWidth) {
        if (fullWidth) {
            item.setBounds(0, bottomPos, getWidth(), item.getHeight());
            if(item instanceof NavBarButton) {
                NavBarButton btnitem = (NavBarButton)item;
                btnitem.relocate();
            }
        } else {
            item.setLocation(item.getBounds().x, item.getBounds().y);
        }

        bottomPos += item.getHeight();
        navItems.add(item); // add to arraylist
        add(item); // add to jpanel
    }
    
    public void addItem1(NavBarItem item, Boolean fullHeight) {
        if (fullHeight) {
            item.setBounds(lengthPos, 0, item.getWidth(), getHeight());
            if(item instanceof NavBarButton) {
                NavBarButton btnitem = (NavBarButton)item;
                btnitem.relocate();
            }
        } else {
            item.setLocation(item.getBounds().x, item.getBounds().y);
        }

        lengthPos+= item.getWidth();
        navItems.add(item); // add to arraylist
        add(item); // add to jpanel
    }

    public void addItem(NavBarItem i) {
        addItem(i, true);
    }
    
    public void addItem1(NavBarItem i)
    {
        addItem1(i, true);
    }
    
    public NavBarItem getItem(int i) {
        return navItems.get(i);
    }
    
    public int getLength() {
        return navItems.size();
    }
}
