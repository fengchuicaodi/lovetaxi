package com.by.taxi.lovetaxi.model;

import android.util.Log;

import com.by.taxi.lovetaxi.javabean.MyUser;
import com.by.taxi.lovetaxi.javabean.ordercar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * MerchandiseListBaseModel 数据Model
 * 因为每个view都会有一个presenter对象绑定，所以这里的Model设定为static域
 * 统一管理静态的订单数据
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/04 13:11 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MerchandiseListBaseModel extends SimpleModel {
    //每页的数据量
    private static int size = 1;
    private static int count = 0;
    //全部订单
    public static List<ordercar> listAll = new ArrayList<>();
    //待完成订单
    private static List<ordercar> listDisPatch = new ArrayList<>();
    //已完成订单
    private static List<ordercar> listReceive = new ArrayList<>();
    //已完成订单
    private static List<ordercar> listFinish = new ArrayList<>();
    //订单Id
    private static Set<Integer> SetId=new HashSet<>();
    private static Boolean flag =false;
    /**
     * 根据标签类别获取订单List
     *
     * @return
     */
    public List<ordercar> getList(int type) {
        switch (type) {
            case 0 :
                return listAll;
            case 1 :
                return listDisPatch;
            case 2 :
                return listReceive;
            case 3 :
                return listFinish;
        }
        return Collections.emptyList();
    }
    /**
     * 获取所有订单ID
     */
    public static void addIdSet(ordercar bean){
        SetId.add(bean.getOrder_id());
        Log.e("sfss",SetId.toString() );
    }

    /**
     *清空列表
     */
    public static void cleanlist() {
        listReceive.clear();
        listFinish.clear();
        listAll.clear();
        listDisPatch.clear();
    }

    /**
     *  去除订单列表重复元素
     */
    public static Boolean RepeatMove(ordercar bean){
        Iterator<Integer> it = SetId.iterator();
        while (it.hasNext()) {
            Integer p =it.next();
           if(bean.getOrder_id().equals(p)){
               Log.e("set",SetId.toString() );
               Log.e("beanid",bean.getOrder_id().toString() );
               return true;
           }
        }
        return false;
    }

    /**
     * 根据标签类别添加订单List元素
     */
    public static void addList(ordercar bean) {
            Log.e("quchong","1111111" );
            switch (Integer.parseInt(bean.getOrderState())) {
                case 1:
                    listDisPatch.add(bean);
                    break;
                case 2:
                    listReceive.add(bean);
                    break;
                case 3:
                    listFinish.add(bean);
                    break;
            }
            listAll.add(bean);
    }

    /**
     * 刷新时添加数据
     */
    public static void refreshList() {
        cleanlist();
        BmobQuery<ordercar> query = new BmobQuery<ordercar>();
        //查询该用户订单
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("passengername", user);
        query.order("-updatedAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.findObjects(new FindListener<ordercar>() {
            @Override
            public void done(List<ordercar> object, BmobException e) {
                if (e == null) {
                    for (com.by.taxi.lovetaxi.javabean.ordercar ordercar : object) {
                        com.by.taxi.lovetaxi.javabean.ordercar bean = new ordercar();
                        bean.setOrderState(ordercar.getOrderState());
                        bean.setEnd_location(ordercar.getEnd_location());
                        bean.setPay(ordercar.getPay());
                        bean.setOrder_id(ordercar.getOrder_id());
                        bean.setOrder_time(ordercar.getOrder_time());
                        addList(bean);
                        addIdSet(bean);
                    }
                } else {
                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
            }
            }
        });
    }
}


