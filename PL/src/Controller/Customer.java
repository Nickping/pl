package Controller; /**
 * Created by admin on 2017-05-16.
 */

import Model.CustomerInfo;
import MyExceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Customer extends JPanel {


    GlobalApplication app;
    JTextField customerId;
    JTextField customerName;
    JTextField customerPhone;
    JTextField customerJoinday;
    JTextField customerBirthday;

    JButton assignCustomer;
    JButton searchCustomer;
    JButton deleteCustomer;
    public Customer() {
        initLayout();
        app = GlobalApplication.getInstance();
    }

    public void initLayout() {
        //   setVisible(true);

        customerId = new JFormattedTextField();
        customerName = new JFormattedTextField();
        customerPhone = new JFormattedTextField();
        customerJoinday = new JFormattedTextField();
        customerBirthday = new JFormattedTextField();
        assignCustomer = new JButton();
        searchCustomer = new JButton();
        deleteCustomer = new JButton();

        assignCustomer.setText("고객 등록");
        searchCustomer.setText("고객 검색");
        deleteCustomer.setText("고객 삭제");

        setLayout(new GridLayout(2, 1));
        JPanel jPaneltop = new JPanel(new GridLayout(5, 2));
        JPanel jPanelbottom = new JPanel(new FlowLayout(FlowLayout.CENTER));


        jPaneltop.add(new JLabel("고객 번호"));
        jPaneltop.add(customerId);
        jPaneltop.add(new JLabel("고객명"));
        jPaneltop.add(customerName);
        jPaneltop.add(new JLabel("전화번호"));
        jPaneltop.add(customerPhone);
        jPaneltop.add(new JLabel("가입일"));
        jPaneltop.add(customerJoinday);
        jPaneltop.add(new JLabel("생년월일"));
        jPaneltop.add(customerBirthday);

        jPanelbottom.add(assignCustomer);
        jPanelbottom.add(searchCustomer);
        jPanelbottom.add(deleteCustomer);


        addListener();
        add(jPaneltop);
        add(jPanelbottom);


        setPreferredSize(new Dimension(600, 400));


    }


    public void addListener() {
        assignCustomer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                assignCustomer();
            }
        });
        searchCustomer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchCustomer();
            }
        });
        deleteCustomer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteCustomer();
            }
        });

    }

    public CustomerInfo makeCustomerInfo(String userid, String username, String userphone
            , String userjoingday, String userbirthday) {


        CustomerInfo customerInfo=null;
        boolean correct=true;
        //이름 예외처리
        try
        {//10자 이상 이름일 경우
            if(username.length()>10)
                throw new Over10NameException("고객 이름은 10자를 넘을 수 없습니다");
        }
        catch (Over10NameException e)
        {

            System.out.println(e.getMessage());
            e.printStackTrace();
            correct=false;
        }

        try
        {//전화번호 입력 오류 숫자와 / 이외의 문자일 경우 오류
            for(int i=0;i<userphone.length();i++)
            {
                if(userphone.charAt(i)<'0'||userphone.charAt(i)>'9')
                {
                    if(userphone.charAt(i)=='-')
                        continue;
                    throw new IncorrectPhoneNumberException("전화번호 입력 오류");
                }

            }
        }
        catch (IncorrectPhoneNumberException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            correct=false;
        }
        try
        {
            //가입일 정보 입력 오류 숫자와 / 이외의 문자의 경우 에러
            for(int i=0;i<userjoingday.length();i++)
            {
                if(userjoingday.charAt(i)<'0'||userjoingday.charAt(i)>'9')
                {
                    if(userjoingday.charAt(i)=='/')
                        continue;
                    throw  new IncorrectDayException("잘못된 가입일 입력입니다");
                }
            }
        }
        catch (IncorrectDayException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            correct=false;
        }
        try
        {
            //생일 정보 입력 오류 숫자와 - 이외의 문자의 경우 에러
            for(int i=0;i<userbirthday.length();i++)
            {
                if(userbirthday.charAt(i)<'0'||userbirthday.charAt(i)>'9')
                {
                    if(userbirthday.charAt(i)=='/')
                        continue;
                    throw  new IncorrectDayException("잘못된 생일 입력입니다");
                }
            }
        }
        catch (IncorrectDayException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            correct=false;
        }


        //입력된 내용이 부족할경우
        try
        {
            if(userbirthday.equals("")||userid.equals("")||userjoingday.equals("")||username.equals("")||userphone.equals(""))
                throw new NotEnoughInfoException("입력 정보가 부족합니다");
        }
        catch (NotEnoughInfoException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            correct=false;
        }
        if(correct)//입력 정보가 모두 충족될 경우
            customerInfo=new CustomerInfo(userid,userjoingday,username,userphone,userbirthday,0);
        //고객 정보 수정할 경우(id가 같을 경우)

        return customerInfo;
    }


    public synchronized void assignCustomer() {
        //고객 등록
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean modified=false;
                File file = new File("custom.txt");
                String line = new String();
                String newcustomerStr;
                StringBuffer stringBuffer = new StringBuffer();

                String userid = customerId.getText();
                String username = customerName.getText();
                String userPhone = customerPhone.getText();
                String userJoinday = customerJoinday.getText();
                String userBirthday = customerBirthday.getText();


                CustomerInfo customerInfo= makeCustomerInfo(userid, username, userPhone, userJoinday, userBirthday);


                //CustomerInfo 객체 만들면서 예외처리
                if (customerInfo != null) {

                    CustomerInfo cursor;
                    modified=false;
                    for(int i=0;i<app.getCustomerInfos().size();i++)
                    {
                        cursor=app.getCustomerInfos().get(i);
                        if(userid.equals(cursor.getCustomerid()))
                        {
                            //같은 id 일 경우 수정
                            app.getCustomerInfos().remove(i);
                            app.getCustomerInfos().add(i,customerInfo);
                            modified=true;
                        }
                    }
                    if(modified==false)
                        app.getCustomerInfos().add(customerInfo);//리스트에 추가
                }
                try {
                        for(int i=0;i<app.getCustomerInfos().size();i++) {
                        CustomerInfo cursor = app.getCustomerInfos().get(i);
                        line=cursor.getCustomerid()+":"+cursor.getName()+":"+cursor.getJoinedday()+":"+cursor.getPhonenum()+":"+cursor.getBirthday()+":"+cursor.getOrderCount();
                        stringBuffer.append(line);
                        stringBuffer.append("\r\n");

                    }
//                    if(customerInfo!=null&&modified==true) {
//                        newcustomerStr = userid + ":" + userJoinday + ":" + username + ":" + userPhone + ":" + userBirthday + ":" + customerInfo.getOrderCount();
//                        stringBuffer.append(newcustomerStr);
//                        stringBuffer.append("\r\n");
//                    }
                    System.out.println(stringBuffer.toString());
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(stringBuffer.toString());
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


    }

    public synchronized void searchCustomer() {

        //고객 검색        //스레드로 구현
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                int i = 0;
                GlobalApplication app = GlobalApplication.getInstance();

                CustomerInfo customerInfo = new CustomerInfo();

                for (i = 0; i < app.getCustomerInfos().size(); i++) {
                    customerInfo = app.getCustomerInfos().get(i);
                    if (customerInfo.getCustomerid().compareTo(customerId.getText()) == 0) {
                        customerName.setText(customerInfo.getName());
                        customerJoinday.setText(customerInfo.getJoinedday());
                        customerPhone.setText(customerInfo.getPhonenum());
                        customerBirthday.setText(customerInfo.getBirthday());
                        break;
                    }

                }
                if (i == app.getCustomerInfos().size()) {
                    try {
                        throw new CannotFoundInfoException("고객이 존재하지 않습니다");
                    } catch (CannotFoundInfoException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public synchronized void deleteCustomer() {
        //스레드로 구현

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                int customerCount;
                customerCount = app.getCustomerInfos().size();
                CustomerInfo customerInfo;
                for (i = 0; i < customerCount; i++) {
                    customerInfo = app.getCustomerInfos().get(i);
                    if (customerId.getText().compareTo(customerInfo.getCustomerid()) == 0) {

                        app.getCustomerInfos().remove(i);
                        System.out.println("고객 삭제");


                        try {
                            FileWriter fileWriter = new FileWriter(new File("custom.txt"));
                            CustomerInfo cursor;
                            for (int j = 0; j < app.getCustomerInfos().size(); j++) {
                                cursor = app.getCustomerInfos().get(j);
//                                fileWriter.write("C:");
                                fileWriter.write(cursor.getCustomerid());
                                fileWriter.write(":");
                                fileWriter.write(cursor.getJoinedday());
                                fileWriter.write(":");
                                fileWriter.write(cursor.getName());
                                fileWriter.write(":");
                                fileWriter.write(cursor.getPhonenum());
                                fileWriter.write(":");
                                fileWriter.write(cursor.getBirthday());
                                fileWriter.write(":");
                                fileWriter.write(Integer.toString(cursor.getOrderCount()));
                                fileWriter.write("\r\n");
                            }
                            fileWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;


                    }
                }
                if (i == customerCount) {
                    try
                    {
                        throw new CannotFoundInfoException("존재하지 않는 고객");
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


}
