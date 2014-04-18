
package com.example.thinkinlistviewbuilder;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeAnimation extends Animation {

    private int mFromW;

    private int mToW;

    private Integer mWidthDelta;

    private View mTagView;

    public ResizeAnimation(int aFromW, int aToW, View aTagView) {
        this.mFromW = aFromW;
        this.mToW = aToW;
        this.mTagView = aTagView;
    }

    // delta_w
    private int getWidthDelta() {
        if (null == this.mWidthDelta) {
            this.mWidthDelta = Integer.valueOf(this.mToW - this.mFromW);
        }
        return this.mWidthDelta;
    }

    @Override
    protected void applyTransformation(float aInterpolatedTime, Transformation aT) {
        aT.setTransformationType(Transformation.TYPE_IDENTITY);
        super.applyTransformation(aInterpolatedTime, aT);

        float deltaW = this.getWidthDelta();
        float currentDeltaW = aInterpolatedTime * deltaW;

        float currentW = this.mFromW + currentDeltaW;

        ViewGroup.LayoutParams lp = mTagView.getLayoutParams();
        lp.width = (int)currentW;
        mTagView.requestLayout();
    }

}
