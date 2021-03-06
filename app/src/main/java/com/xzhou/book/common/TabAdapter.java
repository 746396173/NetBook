package com.xzhou.book.common;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xzhou.book.R;
import com.xzhou.book.community.PostsDetailActivity;
import com.xzhou.book.find.BookListDetailActivity;
import com.xzhou.book.main.BookDetailActivity;
import com.xzhou.book.models.Entities;
import com.xzhou.book.utils.AppUtils;
import com.xzhou.book.utils.Constant;
import com.xzhou.book.utils.Log;
import com.xzhou.book.widget.RatingBar;

import java.util.List;

public class TabAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, CommonViewHolder> {
    private String TAG;
    private int mTabId;

    TabAdapter(int tabId) {
        super(null);
        mTabId = tabId;
        TAG = "TabAdapter" + "_" + mTabId;
        addItemType(Constant.ITEM_TYPE_NET_BOOK, R.layout.item_view_net_book);
        addItemType(Constant.ITEM_TYPE_BOOK_BY_SEARCH, R.layout.item_view_net_book);
        addItemType(Constant.ITEM_TYPE_BOOK_BY_AUTHOR, R.layout.item_view_search_result);
        addItemType(Constant.ITEM_TYPE_BOOK_BY_TAG, R.layout.item_view_tag_book);
        addItemType(Constant.ITEM_TYPE_NET_BOOK_LIST, R.layout.item_view_net_book);
        addItemType(Constant.ITEM_TYPE_UGC_BOOK_LIST, R.layout.item_view_net_book);
        addItemType(Constant.ITEM_TYPE_REVIEWS, R.layout.item_view_review);
        addItemType(Constant.ITEM_TYPE_DISCUSSION, R.layout.item_view_posts_discussion);
    }

    @Override
    protected void convert(CommonViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
        case Constant.ITEM_TYPE_BOOK_BY_SEARCH: {
            final Entities.SearchBook netBook = (Entities.SearchBook) item;
            if (AppUtils.isEmpty(netBook.cat)) {
                netBook.cat = "";
            }
            holder.setRoundImageUrl(R.id.book_image, netBook.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, netBook.title)
                    .setText(R.id.book_author, AppUtils.getString(R.string.net_book_author, netBook.author, netBook.cat))
                    .setText(R.id.book_describe, netBook.shortIntro)
                    .setText(R.id.book_save_count, AppUtils.getString(R.string.net_book_save_count,
                            netBook.latelyFollower, String.valueOf(netBook.retentionRatio)));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.startActivity(getRecyclerView().getContext(), netBook._id);
                }
            });
        }
        case Constant.ITEM_TYPE_NET_BOOK: {
            final Entities.NetBook netBook = (Entities.NetBook) item;
            if (AppUtils.isEmpty(netBook.cat)) {
                netBook.cat = "";
                if (netBook instanceof Entities.BooksByCats.CatBook) {
                    netBook.cat = ((Entities.BooksByCats.CatBook) netBook).majorCate;
                }
            }
            holder.setRoundImageUrl(R.id.book_image, netBook.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, netBook.title)
                    .setText(R.id.book_author, AppUtils.getString(R.string.net_book_author, netBook.author, netBook.cat))
                    .setText(R.id.book_describe, netBook.shortIntro)
                    .setText(R.id.book_save_count, AppUtils.getString(R.string.net_book_save_count,
                            netBook.latelyFollower, String.valueOf(netBook.retentionRatio)));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.startActivity(getRecyclerView().getContext(), netBook._id);
                }
            });
        }
        break;
        case Constant.ITEM_TYPE_BOOK_BY_AUTHOR: {
            final Entities.BooksByTag.TagBook tagBook = (Entities.BooksByTag.TagBook) item;
            holder.setRoundImageUrl(R.id.book_image, tagBook.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, tagBook.title)
                    .setText(R.id.book_h2, AppUtils.getString(R.string.search_result_h2, tagBook.latelyFollower,
                            String.valueOf(tagBook.retentionRatio), tagBook.author));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.startActivity(getRecyclerView().getContext(), tagBook._id);
                }
            });
            break;
        }
        case Constant.ITEM_TYPE_BOOK_BY_TAG: {
            final Entities.BooksByTag.TagBook tagBook = (Entities.BooksByTag.TagBook) item;
            holder.setRoundImageUrl(R.id.book_image, tagBook.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, tagBook.title)
                    .setText(R.id.book_h2, AppUtils.isEmpty(tagBook.shortIntro) ? AppUtils.getString(R.string.detail_no_intro) : tagBook.shortIntro)
                    .setText(R.id.book_h3, formatTags(tagBook.tags));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.startActivity(getRecyclerView().getContext(), tagBook._id);
                }
            });
            break;
        }
        case Constant.ITEM_TYPE_NET_BOOK_LIST: {
            final Entities.BookListBean bookListBean = (Entities.BookListBean) item;
            holder.setRoundImageUrl(R.id.book_image, bookListBean.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, bookListBean.title)
                    .setText(R.id.book_author, bookListBean.author)
                    .setTextColor(R.id.book_author, AppUtils.getColor(R.color.common_h2))
                    .setText(R.id.book_describe, bookListBean.desc)
                    .setText(R.id.book_save_count, AppUtils.getString(R.string.book_list_count, bookListBean.bookCount, bookListBean.collectorCount));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookListDetailActivity.startActivity(getRecyclerView().getContext(), bookListBean.id());
                }
            });
            break;
        }
        case Constant.ITEM_TYPE_UGC_BOOK_LIST: {
            final Entities.SearchBookList.UgcBookList ugcBookList = (Entities.SearchBookList.UgcBookList) item;
            holder.setRoundImageUrl(R.id.book_image, ugcBookList.cover(), R.mipmap.ic_cover_default)
                    .setText(R.id.book_title, ugcBookList.title)
                    .setText(R.id.book_author, ugcBookList.author.nickname)
                    .setTextColor(R.id.book_author, AppUtils.getColor(R.color.common_h2))
                    .setText(R.id.book_describe, ugcBookList.desc)
                    .setText(R.id.book_save_count, AppUtils.getString(R.string.book_list_count, ugcBookList.bookCount, ugcBookList.collectorCount));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookListDetailActivity.startActivity(getRecyclerView().getContext(), ugcBookList._id);
                }
            });
            break;
        }
        case Constant.ITEM_TYPE_REVIEWS: {
            final Entities.Reviews reviews = (Entities.Reviews) item;
            holder.setCircleImageUrl(R.id.review_img, reviews.avatar(), R.mipmap.avatar_default)
                    .setText(R.id.review_author, AppUtils.getString(R.string.book_detail_review_author,
                            reviews.nickname(), reviews.lv()))
                    .setText(R.id.review_time, AppUtils.getDescriptionTimeFromDateString(reviews.created))
                    .setText(R.id.review_title, reviews.title)
                    .setText(R.id.review_content, reviews.content)
                    .setText(R.id.review_useful_yes, String.valueOf(reviews.yes()));
            RatingBar ratingBar = holder.getView(R.id.review_rating_bar);
            ratingBar.setStarCount(reviews.rating);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PostsDetailActivity.startActivity(mContext, reviews._id, PostsDetailActivity.TYPE_REVIEW);
                }
            });
            break;
        }
        case Constant.ITEM_TYPE_DISCUSSION: {
            final Entities.Posts posts = (Entities.Posts) item;
            holder.setCircleImageUrl(R.id.discussion_img, posts.avatar(), R.mipmap.avatar_default)
                    .setText(R.id.discussion_author, AppUtils.getString(R.string.book_detail_review_author,
                            posts.nickname(), posts.authorLv()))
                    .setText(R.id.discussion_time, AppUtils.getDescriptionTimeFromDateString(posts.created))
                    .setText(R.id.discussion_title, posts.title)
                    .setText(R.id.discussion_comment_count, String.valueOf(posts.commentCount))
                    .setText(R.id.discussion_vote_count, String.valueOf(posts.voteCount))
                    .setText(R.id.discussion_comment_like_count, String.valueOf(posts.likeCount))
                    .setGone(R.id.author_type_view, posts.isOfficial() || posts.isDoyen())
                    .setGone(R.id.discussion_comment_count, !posts.isVote())
                    .setGone(R.id.discussion_vote_count, posts.isVote())
                    .setGone(R.id.discussion_time, !posts.isHot() && !posts.isDistillate())
                    .setGone(R.id.view_hot, posts.isHot())
                    .setGone(R.id.view_distillate, posts.isDistillate());
            int resId = 0;
            if (posts.isOfficial()) {
                resId = R.mipmap.user_avatar_verify_official;
            } else if (posts.isDoyen()) {
                resId = R.mipmap.user_avatar_verify_doyen;
            }
            if (resId != 0) {
                holder.setImageResource(R.id.author_type_view, resId);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int type;
                    if (posts.isReview()) {
                        type = PostsDetailActivity.TYPE_REVIEW;
                    } else {
                        type = PostsDetailActivity.TYPE_DISCUSS;
                    }
                    PostsDetailActivity.startActivity(mContext, posts._id, type);
                }
            });
        }
        }
    }

    private static String formatTags(List<String> tags) {
        StringBuilder sb = new StringBuilder();
        int size = tags.size();
        for (int i = 0; i < size; i++) {
            String tag = tags.get(i);
            if (i == 0) {
                sb.append(tag);
            } else {
                sb.append(" | ").append(tag);
            }
        }
        return sb.toString();
    }
}
