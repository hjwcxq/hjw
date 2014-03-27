
package com.orientmedia.app.cfddj.widget;


import com.orientmedia.app.cfddj.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

/**
 * A FlowIndicator which draws circles (one for each view). <br/>
 * Availables attributes are:<br/>
 * <ul>
 * activeColor: Define the color used to draw the active circle (default to
 * white)
 * </ul>
 * <ul>
 * inactiveColor: Define the color used to draw the inactive circles (default to
 * 0x44FFFFFF)
 * </ul>
 * <ul>
 * inactiveType: Define how to draw the inactive circles, either stroke or fill
 * (default to stroke)
 * </ul>
 * <ul>
 * activeType: Define how to draw the active circle, either stroke or fill
 * (default to fill)
 * </ul>
 * <ul>
 * fadeOut: Define the time (in ms) until the indicator will fade out (default
 * to 0 = never fade out)
 * </ul>
 * <ul>
 * radius: Define the circle radius (default to 4.0)
 * </ul>
 * <ul>
 * count: Define the circle count
 * </ul>
 */
public class CircleFlowIndicator extends View implements FlowIndicator, AnimationListener {
    private final int STYLE_STROKE = 0;

    private final int STYLE_FILL = 1;

    private final int COUNT_DEFAULT = 1;

    private final float RADIUS_DEFAULT = 4.0f;

    /**
     * 显示个数
     */
    private int mCount = COUNT_DEFAULT;

    private float mRadius = RADIUS_DEFAULT;

//    private int mFadeOutTime = 0;

    private final Paint mPaintInactive = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mPaintActive = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mCurrentScroll = 0;

    private float[] mCircleInactiveXCoordinate = null;

    private float mCircleActiveXCoordinateOff = 0.0f;

    private float mCircleYCoordinate = 0.0f;

    private int mCorrespondingWidth = 0;

    private float mCircleSeparation = 0;

//    private FadeTimer mFadeTimer;

    public AnimationListener mAnimationListener = this;

    private Animation mAnimation;

    /**
     * Default constructor
     * 
     * @param context
     */
    public CircleFlowIndicator(Context context) {
        super(context);

        init();

        initColors(0xFFFFFFFF, 0xFFFFFFFF, STYLE_FILL, STYLE_STROKE);
    }

    /**
     * The contructor used with an inflater
     * 
     * @param context
     * @param attrs
     */
    public CircleFlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Retrieve styles attributs
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleFlowIndicator);

        // Gets the inactive circle type, defaulting to "fill"
        int activeType = a.getInt(R.styleable.CircleFlowIndicator_activeType, STYLE_FILL);

        int activeDefaultColor = 0xFFFFFFFF;

        // Get a custom inactive color if there is one
        int activeColor = a.getColor(R.styleable.CircleFlowIndicator_activeColor,
                activeDefaultColor);

        // Gets the inactive circle type, defaulting to "stroke"
        int inactiveType = a.getInt(R.styleable.CircleFlowIndicator_inactiveType, STYLE_FILL);

        int inactiveDefaultColor = 0x44FFFFFF;
        // Get a custom inactive color if there is one
        int inactiveColor = a.getColor(R.styleable.CircleFlowIndicator_inactiveColor,
                inactiveDefaultColor);

        // Retrieve the radius
        mRadius = a.getDimension(R.styleable.CircleFlowIndicator_radius, RADIUS_DEFAULT);

        // Retrieve the fade out time
//        mFadeOutTime = a.getInt(R.styleable.CircleFlowIndicator_fadeOut, 0);

        mCount = a.getInt(R.styleable.CircleFlowIndicator_count, COUNT_DEFAULT);

        init();

        initColors(activeColor, inactiveColor, activeType, inactiveType);
    }

    private void init() {
        mCircleSeparation = 2 * mRadius + mRadius;
        mCircleYCoordinate = getPaddingTop() + mRadius;

        initCircleInactiveXCoordinate();

        mCircleActiveXCoordinateOff = getPaddingLeft() + mRadius;
    }

    private void initCircleInactiveXCoordinate(){
        mCircleInactiveXCoordinate = new float[getCount()];
        for (int i = 0; i < getCount(); i++) {
            mCircleInactiveXCoordinate[i] = getPaddingLeft() + mRadius + (i * mCircleSeparation);
        }
    } 
    
    private void initColors(int activeColor, int inactiveColor, int activeType, int inactiveType) {
        // Select the paint type given the type attr
        switch (inactiveType) {
            case STYLE_FILL:
                mPaintInactive.setStyle(Style.FILL);
                break;
            default:
                mPaintInactive.setStyle(Style.STROKE);
        }
        mPaintInactive.setColor(inactiveColor);

        // Select the paint type given the type attr
        switch (activeType) {
            case STYLE_STROKE:
                mPaintActive.setStyle(Style.STROKE);
                break;
            default:
                mPaintActive.setStyle(Style.FILL);
        }
        mPaintActive.setColor(activeColor);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw stroked circles
        for (int i = 0; i < getCount(); i++) {
            canvas.drawCircle(mCircleInactiveXCoordinate[i], mCircleYCoordinate, mRadius,
                    mPaintInactive);
        }
        float cx = 0;
        if (mCorrespondingWidth != 0) {
            // Draw the filled circle according to the current scroll
            cx = mCurrentScroll * mCircleSeparation / mCorrespondingWidth;
        }
        // The flow width has been upadated yet. Draw the default position
        canvas.drawCircle(mCircleActiveXCoordinateOff + cx, mCircleYCoordinate, mRadius,
                mPaintActive);
    }

    /*
     * (non-Javadoc)
     * @see org.taptwo.android.widget.FlowIndicator#onScrolled(int, int, int,
     * int)
     */
    @Override
    public void onScrolled(int left, int width) {
        if (this.getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
        }
//        resetTimer();
        mCurrentScroll = left;
        mCorrespondingWidth = width;
        invalidate();
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     * 
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Calculate the width according the views count
        else {
            result = (int) (getPaddingLeft() + getPaddingRight() + (getCount() * 2 * mRadius)
                    + (getCount() - 1) * mRadius + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     * 
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Measure the height
        else {
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Sets the fill color
     * 
     * @param color ARGB value for the text
     */
    public void setFillColor(int color) {
        mPaintActive.setColor(color);
        invalidate();
    }

    /**
     * Sets the stroke color
     * 
     * @param color ARGB value for the text
     */
    public void setStrokeColor(int color) {
        mPaintInactive.setColor(color);
        invalidate();
    }

    /**
     * Resets the fade out timer to 0. Creating a new one if needed
     */
    /*private void resetTimer() {
        // Only set the timer if we have a timeout of at least 1 millisecond
        if (mFadeOutTime > 0) {
            // Check if we need to create a new timer
            if (mFadeTimer == null || mFadeTimer.mRun == false) {
                // Create and start a new timer
                mFadeTimer = new FadeTimer();
                mFadeTimer.execute();
            } else {
                // Reset the current tiemr to 0
                mFadeTimer.resetTimer();
            }
        }
    }*/

    /**
     * Counts from 0 to the fade out time and animates the view away when
     * reached
     */
    /*private class FadeTimer extends AsyncTask<Void, Void, Void> {
        // The current count
        private long mTimer = 0;

        // If we are inside the timing loop
        private boolean mRun = true;

        public void resetTimer() {
            mTimer = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            while (mRun) {
                // Check if we've reached the fade out time
                if (mTimer > 0 && mTimer <= System.currentTimeMillis() - mFadeOutTime) {
                    // Stop running
                    mRun = false;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
            mAnimation.setAnimationListener(mAnimationListener);
            startAnimation(mAnimation);
        }
    }*/

    @Override
    public void onAnimationEnd(Animation animation) {
        setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    /**
     * 设置显示的数量
     * 
     * @param count
     */
    public void setCount(int count) {
        this.mCount = count;
        initCircleInactiveXCoordinate();
        this.invalidate();
        this.requestLayout();
    }

    /**
     * 获取当前显示数量
     * 
     * @return
     */
    public int getCount() {
        return mCount;
    }
}
