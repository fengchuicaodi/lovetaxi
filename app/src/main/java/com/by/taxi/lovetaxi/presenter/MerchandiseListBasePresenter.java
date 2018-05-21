package com.by.taxi.lovetaxi.presenter;

import android.os.Handler;

import com.by.taxi.lovetaxi.base.BasePresenter;
import com.by.taxi.lovetaxi.fragment.MerchandiseListBaseFragment;
import com.by.taxi.lovetaxi.model.MerchandiseListBaseModel;

import java.util.List;

/**
 * MerchandiseListBasePresenter
 * <p>
 * author: 顾博君 <br>
 * time:   2016/11/8 13:08 <br>
 * e-mail: gubojun@csii.com.cn <br>
 * </p>
 */

public class MerchandiseListBasePresenter extends BasePresenter<MerchandiseListBaseFragment, MerchandiseListBaseModel> {
    private boolean first = true;
    //进页面时获取初始化的数据
    @Override
    public void loadData() {
        refresh();
    }

    /**
     * 刷新数据
     */
    public void refresh() {

        mModel.refreshList();
        loadSynData(mModel.getList(mView.getType()));
    }

    //上拉加载更多
    public void loadMore() {
        mModel.refreshList();
        loadSynData(mModel.getList(mView.getType()));
    }

    public void loadSynData(final List list) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (list.size() != 0) {
                    mView.returnList(list);
                } else {
                    new Handler().postDelayed(this, 100);
                }
            }
        }, 100);
    }

}
