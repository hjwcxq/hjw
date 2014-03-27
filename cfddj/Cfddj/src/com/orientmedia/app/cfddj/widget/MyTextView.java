package com.orientmedia.app.cfddj.widget;

import com.tv189.dzlc.adapter.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		Layout layout = getLayout();

		if (layout != null) {

			int height = (int) FloatMath.ceil(getMaxLineHeight(this.getText()
					.toString()))
					+ getCompoundPaddingTop()
					+ getCompoundPaddingBottom();
			int width = getMeasuredWidth();
			setMeasuredDimension(width, height);
		}
	}

	private float getMaxLineHeight(String str) {
		float height = 0.0f;
		float screenW = ((Activity)getContext()).getWindowManager()
				.getDefaultDisplay().getWidth() - Utils.dip2px(getContext(), 40);
		float paddingLeft = ((RelativeLayout) this.getParent()).getPaddingLeft();
		float paddingReft = ((RelativeLayout) this.getParent()).getPaddingRight();
		// 这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
		int line = (int) Math.ceil((this.getPaint().measureText(str) / (screenW
				- paddingLeft - paddingReft)));
		height = (this.getPaint().getFontMetrics().descent - this.getPaint()
				.getFontMetrics().ascent) * line;
		return height;
	}

}
