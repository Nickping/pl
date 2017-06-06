package Controller;

import Model.CustomerInfo;
import MyExceptions.CannotFoundInfoException;
import MyExceptions.IncorrectDayException;
import MyExceptions.NotEnoughInfoException;
import MyExceptions.Over10NameException;
import UI.CancleDialog;
import UI.couponDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by admin on 2017-05-18.
 */
public class Order extends JPanel {

    JTextField jTextFieldDay;
    JTextField jTextFieldCustomer;
    JSpinner jSpinnerMenu;
    JButton ok;
    JButton cancel;
    String menus[] = {"김밥", "오뎅", "떡볶이", "만두", "순대"};

    public Order() {
        initLayout();
    }
    public void initLayout() {
        jTextFieldCustomer = new JFormattedTextField();
        jTextFieldDay = new JFormattedTextField();
        jSpinnerMenu = new JSpinner();
        SpinnerListModel listModel = new SpinnerListModel(menus);
        jSpinnerMenu.setModel(listModel);

        ok = new JButton();
        ok.setText("주문");
        cancel = new JButton();
        cancel.setText("주문취소");
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputOrder();
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                cancelOrder();
            }
        });

        setLayout(new GridLayout(4, 2));
        setPreferredSize(new Dimension(600, 400));
        add(new JLabel("날짜"));
        add(jTextFieldDay);

        add(new JLabel("고객번호"));
        add(jTextFieldCustomer);

        add(new JLabel("메뉴"));

        add(jSpinnerMenu);
        add(ok);
        add(cancel);
        // add(jSplitPane);


    }

    public boolean checkException(String orderday, String userid)
    {
        boolean errorCheck=true;
        try
        {
            //날짜 입력시 숫자와 - 이외의 문자 있을경우

            for(int i=0;i<orderday.length();i++)
            {
                if(orderday.charAt(i)<'0'||orderday.charAt(i)>'9')
                {
                    if(orderday.charAt(i)=='/')
                        continue;

                    errorCheck=false;
                    throw new IncorrectDayException("잘못된 날짜 입력");

                }
            }
        }
        catch (IncorrectDayException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        try{
            //입력된 내용 없이 주문할 경우 ( orderday ==null, userid ==null_
            if(orderday.equals(""))
            {
                errorCheck=false;
                throw new NotEnoughInfoException("입력 내용 부족");
            }

        }
        catch (NotEnoughInfoException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try
        {
            if(userid.length()>10) {
                errorCheck=false;
                throw new Over10NameException("고객 이름은 10자를 넘을 수 없습니다");

            }
        }
        catch (Over10NameException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return errorCheck;
    }

    public synchronized void inputOrder() {
        //스레드로 구현

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                boolean errorCheck=true;
                GlobalApplication app = GlobalApplication.getInstance();
                String orderDay = jTextFieldDay.getText();
                String userid = jTextFieldCustomer.getText();
                String menu = (String) jSpinnerMenu.getValue();

                errorCheck=checkException(orderDay,userid);
                //예외 처리

                if(errorCheck)
                {
                    CustomerInfo customerInfo;


                    for (i = 0; i < app.getCustomerInfos().size(); i++) {

                        customerInfo = app.getCustomerInfos().get(i);
                        if (customerInfo.getCustomerid().compareTo(userid) == 0) {//해당 고객 발견
                            if (customerInfo.getOrderCount() == 2) {//3번째일때

                                int count = app.getCustomerInfos().get(i).getOrderCount();
                                app.getCustomerInfos().get(i).setOrderCount(0);

                                updateClientDB(app.getCustomerInfos().get(i));
                                //updateOrderDB(orderDay, userid, menu);

                                System.out.println("주문 완료");
                                System.out.println("쿠폰 발송");

                                couponDialog couponDialog = new couponDialog(userid);


                            } else {
                                int count = app.getCustomerInfos().get(i).getOrderCount();
                                app.getCustomerInfos().get(i).setOrderCount(++count);

                                updateClientDB(app.getCustomerInfos().get(i));
                                //updateOrderDB(orderDay, userid, menu);

                                System.out.println("주문 완료");
                            }
                            break;
                        }
                    }

                    if (i == app.getCustomerInfos().size()) {
                        //검색된 회원이 없는경우
                        try
                        {
                            if(userid.equals(""))
                                System.out.println("Guest "+orderDay+" "+menu+"\r\n");
                            else
                                throw new CannotFoundInfoException("고객을 찾을 수 없습니다");
                        }
                        catch (CannotFoundInfoException e)
                        {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }



            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }


    public synchronized void cancelOrder() {
        //Thread로 구현

        GlobalApplication app= GlobalApplication.getInstance();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                String userid=jTextFieldCustomer.getText();
                int customerCount;
                int i=0;
                customerCount= app.getCustomerInfos().size();
                CustomerInfo customerInfo;
                for(i=0;i<customerCount;i++)
                {
                    customerInfo=app.getCustomerInfos().get(i);
                    if(customerInfo.getCustomerid().equals(userid))
                    {
                        int current=customerInfo.getOrderCount();
                        if(current<=0)
                            break;
                        else {
                            app.getCustomerInfos().get(i).setOrderCount(--current);
                            CancleDialog cancleDialog= new CancleDialog(customerInfo.getCustomerid());
                            break;
                        }
                    }
                }
                if(i==customerCount)
                {
                    try
                    {
                        throw new CannotFoundInfoException("주문 내역을 확인할 수 없습니다");
                    }
                    catch (CannotFoundInfoException e)
                    {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }

            }

        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public synchronized  void updateOrderDb()
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try
                {
                    File file = new File("sale.txt");

                }
                catch (Exception e)
                {}
            }
        };
    }

    public synchronized void updateClientDB(CustomerInfo customerInfo) {
        System.out.println("update Client DB");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    File file = new File("custom.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    StringBuffer stringBuffer = new StringBuffer();

                    while ((line = reader.readLine()) != null) {


                        String[] arr = line.split(":");
                            if (arr[0].compareTo(customerInfo.getCustomerid()) != 0) {
                                stringBuffer.append(line);
                                stringBuffer.append("\r\n");
                                continue;
                            } else {
                                arr[5] = Integer.toString(customerInfo.getOrderCount());
                                String newline = new String();
                                newline = arr[0] + ":" + arr[1] + ":" + arr[2] + ":" + arr[3] + ":" + arr[4] + ":" + arr[5];
                                stringBuffer.append(newline);
                                stringBuffer.append("\r\n");
                            }

                    }

                    String result = stringBuffer.toString();


                    System.out.println(result);
                    FileWriter fileWriter = new FileWriter(new File("custom.txt"));
                    fileWriter.write(result);
                    fileWriter.close();
                    reader.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        Thread thread = new Thread(runnable);
        thread.start();



    }


}


