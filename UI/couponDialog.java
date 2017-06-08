package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by admin on 2017-05-19.
 */
public class couponDialog extends JDialog implements MouseListener {
    JLabel jLabel;

    @Override
    public void mouseClicked(MouseEvent e) {
        dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public couponDialog(String userid) {

        //getContentPane().add(jLabel);
        jLabel = new JLabel(userid + "고객님" + "\r\n" + "쿠폰이 발송되었습니다");
        JButton jButton = new JButton("확인");
        jButton.addMouseListener(this);
        GridLayout gridLayout = new GridLayout(2, 1);

        JPanel mainPanel = new JPanel();
        JPanel jPanelTop = new JPanel();
        JPanel jPanelBottom = new JPanel();
        mainPanel.setLayout(gridLayout);

        jPanelTop.add(jLabel);
        jPanelBottom.add(jButton);

        mainPanel.add(jPanelTop);
        mainPanel.add(jPanelBottom);

        this.add(mainPanel);
        this.setSize(300, 200);
        this.setVisible(true);


//        this.add(jLabel);
//        this.setSize(300, 200);
//        this.setModal(true);
//        this.add(jButton);
//        this.setVisible(true);
    }

}


