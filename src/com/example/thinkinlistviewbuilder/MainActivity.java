
package com.example.thinkinlistviewbuilder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uc.browser.myvideo.ListViewBuilder;
import com.uc.browser.myvideo.ListViewBuilder.IDataSource;
import com.uc.browser.myvideo.ListViewBuilder.ItemViewConfig;

public class MainActivity extends Activity implements IDataSource {

    private List<Object> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contain = new FrameLayout(this);
        contain.setBackgroundColor(Color.argb(100, 0, 0, 250));
        contain.addView(this.getListView(), this.createListViewLP());

        this.setContentView(contain);
    }

    private static class ViewA extends FrameLayout {
        private TextView mContentView;

        public ViewA(Context aContext) {
            super(aContext);

            this.addView(this.getContentView(), this.createContentViewLP());
        }

        private View getContentView() {
            if (null == this.mContentView) {
                this.mContentView = new TextView(this.getContext());
                this.mContentView.setText("Group");
            }
            return this.mContentView;
        }

        private ViewGroup.LayoutParams createContentViewLP() {
            return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
    }

    private static class ViewB extends FrameLayout {
        private TextView mContentView;

        public ViewB(Context aContext) {
            super(aContext);

            this.addView(this.getContentView(), this.createContentViewLP());
        }

        private View getContentView() {
            if (null == this.mContentView) {
                this.mContentView = new TextView(this.getContext());
                this.mContentView.setText("Child");
            }
            return this.mContentView;
        }

        private ViewGroup.LayoutParams createContentViewLP() {
            return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
    }

    private static class ViewC extends View {
        public ViewC(Context aContext) {
            super(aContext);
        }
    }

    private static class DataA {

    }

    private static class DataB {

    }

    private static class DataC {

    }

    private View getListView() {
        return ListViewBuilder.newInstance(this, new ItemViewConfig<DataA, ViewA>() {

            @Override
            public void updateItemView(int aPosition, DataA aData, ViewA aItemView) {
                // TODO Auto-generated method stub

            }

            @Override
            public Class<DataA> getDataClass() {
                return DataA.class;
            }

            @Override
            public Class<ViewA> getViewClass() {
                return ViewA.class;
            }

            @Override
            public ViewA createView() {
                return new ViewA(MainActivity.this);
            }

        }, new ItemViewConfig<DataB, ViewB>() {

            @Override
            public void updateItemView(int aPosition, DataB aData, ViewB aItemView) {
                // TODO Auto-generated method stub

            }

            @Override
            public Class<DataB> getDataClass() {
                return DataB.class;
            }

            @Override
            public Class<ViewB> getViewClass() {
                return ViewB.class;
            }

            @Override
            public ViewB createView() {
                return new ViewB(MainActivity.this);
            }

        }).build(this);
    }

    private LayoutParams createListViewLP() {
        return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public List<Object> getDataList() {
        if (null == this.mDataList) {
            this.mDataList = new ArrayList<Object>();

            this.mDataList.add(new DataA());
            this.mDataList.add(new DataB());
            this.mDataList.add(new DataB());
            this.mDataList.add(new DataA());
            this.mDataList.add(new DataB());
            this.mDataList.add(new DataB());

        }
        return this.mDataList;
    }

}
