package com.by.taxi.lovetaxi.javabean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by 风吹草地 on 2018/4/19.
 */

public class ordercar extends BmobObject {
    private String start_location;
    private String end_location;
    private Integer status;
    private Integer order_id;
    private  MyUser passengername;
    private Date ordertime;
    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public MyUser getPassengername() {
        return passengername;
    }

    public void setPassengername(MyUser passengername) {
        this.passengername = passengername;
    }


    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }


}
