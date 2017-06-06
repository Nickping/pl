import Controller.Customer;
import Controller.GlobalApplication;
import Controller.Order;
import Controller.Sales;
import Model.CustomerInfo;
import Model.OrderInfo;

import java.io.*;
import javax.swing.*;

/**
 * Created by admin on 2017-05-10.
 */


public class main {


    public static void main(String args[]) {


        try {//파일이 없을경우 custom파일 생성
            File file = new File("custom.txt");
            if (!file.exists()) {
                FileOutputStream fileOutputStream = new FileOutputStream("custom.txt");
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadData();

        JFrame jFrame = new JFrame();
        jFrame.setSize(700, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane jTabbedPane = new JTabbedPane();
        jFrame.add(jTabbedPane);


        JPanel customerPanel = new JPanel();
        JPanel orderPanel = new JPanel();
        JPanel salesPanel = new JPanel();
        Customer customer = new Customer();
        Order order = new Order();

        Sales sales = new Sales();

        customerPanel.add(customer);
        orderPanel.add(order);
        salesPanel.add(sales);
        //  order.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//       orderPanel.setSize(600,400);

        jTabbedPane.addTab("주문관리", orderPanel);
        jTabbedPane.addTab("고객관리", customerPanel);
        jTabbedPane.addTab("매출관리", salesPanel);
        jFrame.setVisible(true);


    }

    public static void loadData() {
        //app start 시 txt파일에서 데이터 로드
        GlobalApplication app = GlobalApplication.getInstance();

        try {
            File file = new File("custom.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '\uFEFF') {//UTF-8 BOM 문제 해결
                    line = line.substring(1);

                    if (line.equals(""))
                        break;
                }
                System.out.println(line);
                String[] arr = line.split(":");
                CustomerInfo customerInfo = new CustomerInfo();
                OrderInfo orderInfo = new OrderInfo();

                customerInfo.setCustomerid(arr[0]);
                customerInfo.setName(arr[1]);
                customerInfo.setJoinedday(arr[2]);
                customerInfo.setPhonenum(arr[3]);
                customerInfo.setBirthday(arr[4]);
                customerInfo.setOrderCount(Integer.valueOf(arr[5]));
                app.getCustomerInfos().add(customerInfo);


            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

