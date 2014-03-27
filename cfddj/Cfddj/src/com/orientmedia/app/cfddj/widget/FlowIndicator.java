

package com.orientmedia.app.cfddj.widget;

/**
 * An interface which defines the contract between a ViewPager and a
 * FlowIndicator.<br/>
 * A FlowIndicator is responsible to show an visual indicator on the total views
 * number and the current visible view.<br/>
 */
public interface FlowIndicator {

    /**
     * The scroll position has been changed. A FlowIndicator may implement this
     * method to reflect the current position
     * 
     * @param left
     * @param width
     */
    public void onScrolled(int left, int width);
}
