package com.by.taxi.lovetaxi.base;


import com.by.taxi.lovetaxi.base.baseMVP.IBaseModel;

/**
 * BaseModel  数据处理模块，可以放置一些网络框架的数据请求
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:26 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class BaseModel implements IBaseModel {
    public void cancleTasks(){
        // TODO 终止线程池ThreadPool.shutDown()，AsyncTask.cancle()，或者调用框架的取消任务api
    }
}
