package com.xzhou.book.find;

import com.xzhou.book.models.Entities;
import com.xzhou.book.common.BaseContract;

import java.util.List;

public interface FindContract {
    interface Presenter extends BaseContract.BasePresenter {

    }

    interface View extends BaseContract.BaseView<Presenter> {
        void onInitData(List<Entities.ImageText> list);
    }
}