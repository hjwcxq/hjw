
package com.orientmedia.app.cfddj.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class IndicatorViewPager extends ViewPager {
    private FlowIndicator mIndicator;

    public IndicatorViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * Set the FlowIndicator
     * 
     * @param flowIndicator
     */
    public void setFlowIndicator(FlowIndicator flowIndicator) {
        mIndicator = flowIndicator;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIndicator != null) {
            /*
             * The actual horizontal scroll origin does typically not match the
             * perceived one. Therefore, we need to calculate the perceived
             * horizontal scroll origin here, since we use a view buffer.
             */
            mIndicator.onScrolled(l, this.getWidth());
        }
    }
}
