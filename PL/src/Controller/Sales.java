package Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 2017-06-06.
 */
public class Sales  extends JPanel{

    JTextField mFrom;
    JTextField mEnd;
    JTextField mResult;
    JButton mSearch;
    JButton mCancel;

    GlobalApplication app;
    public Sales ()
    {
        initLayout();
        app=GlobalApplication.getInstance();
    }
    public void initLayout()
    {
        mFrom = new JTextField(20);
        mEnd = new JTextField(20);
        mResult = new JTextField();
        mSearch = new JButton();
        mCancel = new JButton();

        mFrom.setText("yyyy/mm/dd");
        mEnd.setText("yyyy/mm/dd");
        mSearch.setText("조회");
        mCancel.setText("취소");

    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    //JPanel jPanelTop = new JPanel(new GridLayout(1,5));
    JPanel jPanelTop=new JPanel();
        JPanel jPanelMid = new JPanel(new GridLayout(1,1));
    JPanel jPanelBottom = new JPanel(new FlowLayout());

    jPanelTop.add(new JLabel("날짜"));
    jPanelTop.add(mFrom);
    jPanelTop.add(new JLabel("~"));
    jPanelTop.add(mEnd);
    jPanelTop.add(new JLabel("까지"));

    jPanelMid.add(mResult);
    jPanelBottom.add(mSearch);
    jPanelBottom.add(mCancel);

    add(jPanelTop);
    add(jPanelMid);
    add(jPanelBottom);

    setPreferredSize(new Dimension(600,400));

    }
}
