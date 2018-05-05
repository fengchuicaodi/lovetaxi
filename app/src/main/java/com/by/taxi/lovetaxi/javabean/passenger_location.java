package com.by.taxi.lovetaxi.javabean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by 风吹草地 on 2018/4/19.
 */

public class passenger_location extends BmobObject {
    private Integer pl_rid;
    private  Integer pl_status;
    private BmobGeoPoint pl_location;
    private MyUser passengername;

    public MyUser getPassengername() {
        return passengername;
    }

    public void setPassengername(MyUser passengername) {
        this.passengername = passengername;
    }


    public Integer getPl_rid() {
        return pl_rid;
    }

    public void setPl_rid(Integer pl_rid) {
        this.pl_rid = pl_rid;
    }


    public Integer getPl_status() {
        return pl_status;
    }

    public void setPl_status(Integer pl_status) {
        this.pl_status = pl_status;
    }


    public BmobGeoPoint getPl_location() {
        return pl_location;
    }

    public void setPl_location(BmobGeoPoint pl_location) {
        this.pl_location = pl_location;
    }



}
