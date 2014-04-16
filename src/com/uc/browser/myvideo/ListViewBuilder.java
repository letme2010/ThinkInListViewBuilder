/**
 ***************************************************************************** 
 * Copyright (C) 2005-2014 UCWEB Corporation. All rights reserved File :
 * 2014-3-7
 * 
 * Description : 让ListView使用起来更简单的Builder.
 *                  
 * 
 * Creation : 2014-3-7 Author : chenzp@ucweb.com 
 * History : Creation, 2014-3-7, chenzp, Create the file
 ***************************************************************************** 
 */

package com.uc.browser.myvideo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uc.framework.resources.Theme;
import com.uc.framework.resources.ThemeManager;
import com.uc.util.Utilities;

public class ListViewBuilder {

    public static interface IDataSource {
        public List<Object> getDataList();
    }

    public static ListViewBuilder newInstance(IDataSource aDataSource,
            ItemViewConfig<?, ?>... aItemViewBuilderList) {
        return new ListViewBuilder(aDataSource, aItemViewBuilderList);
    }

    /**
     * Call newInstance() to new instance.
     */
    private ListViewBuilder() {

    }

    private ListViewBuilder(IDataSource aDataSource, ItemViewConfig<?, ?>... aItemViewBuilderList) {
        this.mDataSource = aDataSource;

        for (ItemViewConfig<?, ?> itemBuilder : aItemViewBuilderList) {
            this.mItemViewBuilderList.add(itemBuilder);
        }

    }

    private class HeaderWrap {
        private View mView;

        private Object mData;

        private boolean mIsSelectable;

        public View getView() {
            return mView;
        }

        public void setView(View aView) {
            mView = aView;
        }

        public Object getData() {
            return mData;
        }

        public void setData(Object aData) {
            mData = aData;
        }

        public boolean isIsSelectable() {
            return mIsSelectable;
        }

        public void setIsSelectable(boolean aIsSelectable) {
            mIsSelectable = aIsSelectable;
        }

        public HeaderWrap(View aView, Object aData, boolean aIsSelectable) {
            super();
            mView = aView;
            mData = aData;
            mIsSelectable = aIsSelectable;
        }

    }

    private List<ItemViewConfig<?, ?>> mItemViewBuilderList = new ArrayList<ListViewBuilder.ItemViewConfig<?, ?>>();

    private int mBackgroundColor = -1;

    private boolean mVerticalFadingEdgeEnabled = true;

    private boolean mUseUcDefaultEdgeEffect = false;

    private boolean mLongClickable = false;

    private int mDividerHeight = -1;

    private Drawable mDivider;

    private Drawable mScrollbarVerticalThumb;

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongclickListener;

    private int mCacheColorHint = -1;

    private List<HeaderWrap> mHeaderViewList = new ArrayList<HeaderWrap>();

    private OnScrollListener mOnScrollListener;

    private View mEmptyView;

    private IDataSource mDataSource;

    private ListAdapter mListAdapter;

    public ListView build(Context aContext) {
        ListView ret = new ListView(aContext) {
            @Override
            protected float getTopFadingEdgeStrength() {
                return 0;
            }
        };

        if (null == this.mItemViewBuilderList) {
            throw new RuntimeException();
        }

        if (null == aContext) {
            throw new RuntimeException();
        }

        if (-1 < this.mBackgroundColor) {
            ret.setBackgroundColor(this.mBackgroundColor);
        }

        if (-1 < this.mCacheColorHint) {
            ret.setCacheColorHint(this.mCacheColorHint);
        }

        ret.setVerticalFadingEdgeEnabled(this.mVerticalFadingEdgeEnabled);

        Theme theme = ThemeManager.getInstance().getCurrentTheme();

        if (this.mUseUcDefaultEdgeEffect) {
            Utilities.setEdgeEffectDrawable(ret, theme.getDrawable("overscroll_edge.png"),
                    theme.getDrawable("overscroll_glow.png"));
        }

        if (null != this.mScrollbarVerticalThumb) {
            Utilities.setScrollbarVerticalThumbDrawable(ret, this.mScrollbarVerticalThumb);
        }

        ret.setLongClickable(this.mLongClickable);

        if (null != this.mDivider) {
            ret.setDivider(this.mDivider);
        }

        if (0 <= this.mDividerHeight) {
            ret.setDividerHeight(this.mDividerHeight);
        }

        if (null != this.mOnItemClickListener) {
            ret.setOnItemClickListener(this.mOnItemClickListener);
        }

        if (null != this.mOnItemLongclickListener) {
            ret.setOnItemLongClickListener(this.mOnItemLongclickListener);
        }

        if (null != mEmptyView) {
            ret.setEmptyView(mEmptyView);
        }

        if (null != this.mOnScrollListener) {
            ret.setOnScrollListener(this.mOnScrollListener);
        }

        for (HeaderWrap wrap : this.mHeaderViewList) {
            ret.addHeaderView(wrap.getView(), wrap.getData(), wrap.isIsSelectable());
        }

        ret.setAdapter(this.getListAdapter());

        return ret;
    }

    protected ListAdapter getListAdapter() {
        if (null == this.mListAdapter) {

            this.mListAdapter = new BaseAdapter() {

                @Override
                public View getView(int aPosition, View aConvertView, ViewGroup aParent) {

                    Object data = mDataSource.getDataList().get(aPosition);
                    ItemViewConfig<?, ?> tagBuilder = findItemViewConfig(data.getClass());

                    if (null == tagBuilder) {
                        throw new RuntimeException(
                                "Can not find target ItemViewBuilder. Please check dataSource return data and newInstance() arguments ItemViewConfig");
                    }

                    View itemView = null;

                    if (null == aConvertView) {
                        itemView = tagBuilder.createView();
                    } else {
                        itemView = aConvertView;
                    }

                    tagBuilder
                            .update(aPosition, mDataSource.getDataList().get(aPosition), itemView);

                    return (View)itemView;
                }

                private ItemViewConfig<?, ?> findItemViewConfig(Class<? extends Object> aClass) {

                    for (ItemViewConfig<?, ?> itemBuilder : mItemViewBuilderList) {
                        if (aClass.equals(itemBuilder.getDataClass())) {
                            return itemBuilder;
                        }
                    }
                    return null;
                }

                @Override
                public long getItemId(int aPosition) {
                    return 0;
                }

                @Override
                public Object getItem(int aPosition) {
                    return mDataSource.getDataList().get(aPosition);
                }

                @Override
                public int getCount() {
                    try {
                        return mDataSource.getDataList().size();
                    } catch (Exception e) {
                        return 0;
                    }
                }
            };
        }

        return this.mListAdapter;
    }

    public static abstract class ItemViewConfig<ItemDataClass, ItemViewClass extends View> {

        public abstract void updateItemView(final int aPosition, final ItemDataClass aData,
                final ItemViewClass aItemView);

        @SuppressWarnings("unchecked")
        public void update(final int aPosition, final Object aData, final View aItemView) {
            this.updateItemView(aPosition, (ItemDataClass)aData, (ItemViewClass)aItemView);
        }

        public abstract Class<ItemDataClass> getDataClass();

        public abstract Class<ItemViewClass> getViewClass();

        public abstract ItemViewClass createView();
    }

    public ListViewBuilder setBackgroundColor(int aBackgroundColor) {
        this.mBackgroundColor = aBackgroundColor;
        return this;
    }

    public ListViewBuilder setVerticalFadingEdgeEnabled(boolean aB) {
        this.mVerticalFadingEdgeEnabled = aB;
        return this;
    }

    public ListViewBuilder useUcDefaultScrollbarVerticalThumb() {
        this.mScrollbarVerticalThumb = ThemeManager.getInstance().getCurrentTheme()
                .getDrawable("scrollbar_thumb.9.png");
        return this;
    }

    public ListViewBuilder useUcDefaultEdgeEffect(boolean aB) {
        this.mUseUcDefaultEdgeEffect = aB;
        return this;
    }

    public ListViewBuilder setLongClickable(boolean aB) {
        this.mLongClickable = aB;
        return this;
    }

    public ListViewBuilder setDividerHeight(int aHeigh) {
        this.mDividerHeight = aHeigh;
        return this;
    }

    public ListViewBuilder setDivider(Drawable aDivider) {
        this.mDivider = aDivider;
        return this;
    }

    public ListViewBuilder setScrollbarVerticalThumb(Drawable aDrawable) {
        this.mScrollbarVerticalThumb = aDrawable;
        return this;
    }

    public ListViewBuilder setOnItemClickListener(OnItemClickListener aListener) {
        this.mOnItemClickListener = aListener;
        return this;
    }

    public ListViewBuilder setOnItemLongclickListener(OnItemLongClickListener aListener) {
        this.mOnItemLongclickListener = aListener;
        return this;
    }

    public ListViewBuilder setCacheColorHint(int aColor) {
        this.mCacheColorHint = aColor;
        return this;
    }

    public ListViewBuilder addHeaderView(View aView, Object aData, boolean aIsSelectable) {
        this.mHeaderViewList.add(new HeaderWrap(aView, aData, aIsSelectable));
        return this;
    }

    public ListViewBuilder setOnScrollListener(OnScrollListener aListener) {
        this.mOnScrollListener = aListener;
        return this;
    }

    public ListViewBuilder setEmptyView(View view) {
        this.mEmptyView = view;
        return this;
    }

}
