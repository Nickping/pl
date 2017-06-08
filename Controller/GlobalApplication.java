package Controller;

import Model.CustomerInfo;
import Model.OrderInfo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2017-05-18.
 */


public class GlobalApplication {

    //싱글턴 패턴 객체
    private static GlobalApplication app = null;
    private  ArrayList<CustomerInfo> customerInfos;
    private ArrayList<OrderInfo> orderInfos;
    public ArrayList<OrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(ArrayList<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }

    private GlobalApplication() {
        customerInfos = new ArrayList<>();
        orderInfos= new ArrayList<>();
    }

    public  static GlobalApplication getInstance() {
        if (app == null)
        {
            synchronized (GlobalApplication.class)
            {
                if(app==null)
                {
                    app=new GlobalApplication();
                }
            }
        }

        return app;
    }

    public ArrayList<CustomerInfo> getCustomerInfos() {
        return customerInfos;
    }

    public synchronized void setCustomerInfos(ArrayList<CustomerInfo> customerInfos) {
        this.customerInfos = customerInfos;
    }
}
