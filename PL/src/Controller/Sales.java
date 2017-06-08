package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by admin on 2017-06-06.
 */
public class Sales  extends JPanel{

    JTextField mFrom;
    JTextField mEnd;
    JTextArea mResult;
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
        mResult = new JTextArea();
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

    mSearch.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            String start = mFrom.getText();
            String end = mEnd.getText();
            searchSales(start,end);
        }
    });

    setPreferredSize(new Dimension(600,400));

    }

    public synchronized void searchSales(String start, String end) {

        int dduck=0;
        int gimbap=0;
        int soon=0;
        int oh=0;
        int fri=0;
        int mandoo=0;
        int total;
        int coupon=0;
        try{
            File file = new File("sales.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuffer stringBuffer = new StringBuffer();

            while((line=reader.readLine())!=null)
            {
                String [] arr= line.split(":");
                System.out.println(arr[0]);
                if(arr[0].compareTo(start)>=0
                        &&arr[0].compareTo(end)<=0)
                {
                    if(arr[2].equals("김밥"))
                    {
                        gimbap++;
                    }
                    else if(arr[2].equals("순대"))
                    {
                        soon++;
                    }
                    else if(arr[2].equals("오뎅"))
                    {
                        oh++;
                    }
                    else if(arr[2].equals("튀김"))
                    {
                        fri++;
                    }
                    else if(arr[2].equals("만두"))
                    {
                        mandoo++;
                    }
                    else if(arr[2].equals("떡볶이"))
                        dduck++;

                    if(arr[3].equals("O"))
                        coupon++;
                }
            }



            reader.close();
            String gimStr="김밥\t\t"+gimbap+"\t\t"+gimbap*1500+"\r\n";
            String soonStr="순대\t\t"+soon+"\t\t"+soon*3000+"\r\n";
            String ohStr="오뎅\t\t"+oh+"\t\t"+oh*500+"\r\n";
            String friStr="튀김\t\t"+fri+"\t\t"+oh*1000+"\r\n";
            String couponStr="쿠폰\t\t"+coupon+"\r\n";
            total=gimbap*1500+soon*3000+oh*500;
            String result;
            StringBuffer stringBuffer1=new StringBuffer();
            stringBuffer1.append("메뉴\t\t"+"갯수"+"\t\t메출금액\r\n");
            stringBuffer1.append("=========================================================\r\n");
            stringBuffer1.append(gimStr);
            stringBuffer1.append(soonStr);
            stringBuffer1.append(ohStr);
            stringBuffer1.append(friStr);
            stringBuffer1.append(couponStr);
            stringBuffer1.append("=========================================================\r\n");
            stringBuffer1.append("매출합계\t\t  \t\t"+total);

            mResult.setText(stringBuffer1.toString());
            System.out.print(stringBuffer1.toString());
           }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
