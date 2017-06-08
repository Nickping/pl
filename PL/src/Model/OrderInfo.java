package Model;

/**
 * Created by admin on 2017-05-19.
 */
public class OrderInfo {
    private String orderDay;
    private String userid;
    private String ordermenu;
    private  String coupon;
    public OrderInfo(String orderDay, String userid, String ordermenu) {
        this.orderDay = orderDay;
        this.userid = userid;
        this.ordermenu = ordermenu;
    }

    public OrderInfo(String orderDay, String userid, String ordermenu, String coupon) {
        this.orderDay = orderDay;
        this.userid = userid;
        this.ordermenu = ordermenu;
        this.coupon = coupon;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public OrderInfo() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrdermenu() {
        return ordermenu;
    }

    public void setOrdermenu(String ordermenu) {
        this.ordermenu = ordermenu;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }
}
