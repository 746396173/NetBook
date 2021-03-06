package com.xzhou.book.main;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xzhou.book.common.BaseContract;
import com.xzhou.book.models.Entities;

import java.util.List;

public interface BookDetailContract {
    interface Presenter extends BaseContract.Presenter {
        boolean download();
    }

    interface View extends BaseContract.View<Presenter> {

        void onInitBookDetail(Entities.BookDetail detail);

        void onInitReviews(List<MultiItemEntity> list);

        void onInitRecommend(List<MultiItemEntity> list);

        void onStartDownload();

        void onProgress(int progress, int max);

        void onEndDownload(int failedCount, int error);
    }
}
