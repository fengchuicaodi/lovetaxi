package com.by.taxi.lovetaxi.javabean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 风吹草地 on 2018/4/8.
 */

public class MyUser extends BmobUser {
    ;
    private Integer type;
    private BmobFile pic;
    private String sex;
    private String idcard;
    private String taxi_id;

    public String getTaxi_id() {
        return taxi_id;
    }

    public void setTaxi_id(String taxi_id) {
        this.taxi_id = taxi_id;
    }


    public String getIdcard() {
        return idcard;
    }


    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

}