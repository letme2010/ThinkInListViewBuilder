
package com.example.thinkinlistviewbuilder;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.uc.browser.myvideo.ListViewBuilder;
import com.uc.browser.myvideo.ListViewBuilder.ItemViewConfig;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contain = new FrameLayout(this);
        contain.setBackgroundColor(Color.argb(100, 0, 0, 250));
        contain.addView(this.getListView(), this.createListViewLP());

        this.setContentView(contain);
    }

    private static class ViewA extends View {
        public ViewA(Context aContext) {
            super(aContext);
        }
    }

    private static class ViewB extends View {
        public ViewB(Context aContext) {
            super(aContext);
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
        return ListViewBuilder.newInstance(new ItemViewConfig<DataA, ViewA>() {

            @Override
            protected void updateItemView(int aPosition, DataA aData, ViewA aItemView) {
                // TODO Auto-generated method stub

            }

            @Override
            protected List<DataA> getDataList() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            protected ViewA createView() {
                // TODO Auto-generated method stub
                return null;
            }
        }, new ItemViewConfig<DataB, ViewB>() {

            @Override
            protected void updateItemView(int aPosition, DataB aData, ViewB aItemView) {
                // TODO Auto-generated method stub

            }

            @Override
            protected List<DataB> getDataList() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            protected ViewB createView() {
                // TODO Auto-generated method stub
                return null;
            }
        }, new ItemViewConfig<DataC, ViewC>() {

            @Override
            protected void updateItemView(int aPosition, DataC aData, ViewC aItemView) {
                // TODO Auto-generated method stub

            }

            @Override
            protected List<DataC> getDataList() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            protected ViewC createView() {
                // TODO Auto-generated method stub
                return null;
            }
        }).build(this);
    }

    private LayoutParams createListViewLP() {
        return new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

}
