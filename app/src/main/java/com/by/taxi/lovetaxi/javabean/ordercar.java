package com.by.taxi.lovetaxi.javabean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 风吹草地 on 2018/4/19.
 */

public class ordercar extends BmobObject {
    private String start_location;
    private String end_location;
    private Integer order_id;
    private  MyUser passengername;
    private BmobDate order_time;
    private Integer assess_number;
    private String assess;
    private Double pay;
    private String OrderState;//订单的页面状态
    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }


    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }


    public Integer getAssess_number() {
        return assess_number;
    }

    public void setAssess_number(Integer assess_number) {
        this.assess_number = assess_number;
    }


    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }


    public BmobDate getOrder_time() {
        return order_time;
    }

    public void setOrder_time(BmobDate order_time) {
        this.order_time = order_time;
    }

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




}
