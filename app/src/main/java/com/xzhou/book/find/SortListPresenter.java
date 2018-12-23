package com.xzhou.book.find;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xzhou.book.MyApp;
import com.xzhou.book.R;
import com.xzhou.book.datasource.ZhuiShuSQApi;
import com.xzhou.book.models.Entities;
import com.xzhou.book.common.BasePresenter;
import com.xzhou.book.utils.AppUtils;
import com.xzhou.book.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class SortListPresenter extends BasePresenter<SortListContract.View> implements SortListContract.Presenter {
    public static final int SOURCE_RANK = 1;
    public static final int SOURCE_CATEGORY = 2;

    private List<MultiItemEntity> mList;
    private int mSource;

    public SortListPresenter(SortListContract.View view, int source) {
        super(view);
        mSource = source;
    }

    @Override
    public boolean start() {
        if (mList == null) {
            ZhuiShuSQApi.getPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (mSource == SOURCE_RANK) {
                        Entities.RankingList rankingList = ZhuiShuSQApi.get().getRanking();
                        if (rankingList != null) {
                            mList = new ArrayList<>();
                            parseRankingList(rankingList.male, AppUtils.getString(R.string.male));
                            parseRankingList(rankingList.female, AppUtils.getString(R.string.female));
//                            parseRankingList(rankingList.picture, AppUtils.getString(R.string.picture));
//                            parseRankingList(rankingList.epub, AppUtils.getString(R.string.epub));
                        }
                    } else {
                        Entities.CategoryList categoryList = ZhuiShuSQApi.get().getCategoryList();
                        if (categoryList != null) {
                            mList = new ArrayList<>();
                            parseCategoryList(categoryList.male, AppUtils.getString(R.string.male), "male");
                            parseCategoryList(categoryList.female, AppUtils.getString(R.string.female), "female");
//                            parseCategoryList(categoryList.picture, AppUtils.getString(R.string.picture), "");
//                            parseCategoryList(categoryList.press, AppUtils.getString(R.string.epub), "");
                        }
                    }
                    initData();
                }
            });
            return true;
        }
        return false;
    }

    private void parseCategoryList(List<Entities.CategoryList.Category> list, String title, String gender) {
        if (list == null || list.size() < 1) {
            Log.e("CategoryList.Category is null or empty");
            return;
        }

        mList.add(new Entities.CategoryLv0(title));
        for (Entities.CategoryList.Category category : list) {
            mList.add(new Entities.CategoryLv1(category, gender));
        }
    }

    private void parseRankingList(List<Entities.RankLv1> list, String name) {
        if (list == null || list.size() < 1) {
            Log.e("RankingList.Ranking is null or empty");
            return;
        }

        Entities.RankLv0 lv0 = new Entities.RankLv0();
        lv0.name = name;
        mList.add(lv0);
        List<Entities.RankLv2> rankLv2s = new ArrayList<>();
        for (Entities.RankLv1 ranking : list) {
            if (ranking.collapse) {
                rankLv2s.add(new Entities.RankLv2(ranking));
            } else {
                mList.add(ranking);
            }
        }
        if (rankLv2s.size() > 0) {
            Entities.RankLv1 lv1 = new Entities.RankLv1(AppUtils.getString(R.string.rank_other));
            lv1.setSubItems(rankLv2s);
            mList.add(lv1);
        }
    }

    private void initData() {
        MyApp.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mView != null) {
                    mView.initData(mList);
                }
            }
        });
    }
}