package Model;

/**
 * Created by admin on 2017-05-18.
 */
public class CustomerInfo {
    String customerid;
    String joinedday;
    String name;
    String phonenum;
    String birthday;
    int orderCount;

    public CustomerInfo() {
    }

    public CustomerInfo(String customerid, String joinedday, String name, String phonenum, String birthday, int orderCount) {
        this.customerid = customerid;
        this.joinedday = joinedday;
        this.name = name;
        this.phonenum = phonenum;
        this.birthday = birthday;
        this.orderCount = orderCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getJoinedday() {
        return joinedday;
    }

    public void setJoinedday(String joinedday) {
        this.joinedday = joinedday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
