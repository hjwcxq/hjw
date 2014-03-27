package com.orientmedia.app.cfddj.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class AlwaysMarQueeTextView extends TextView {
		public AlwaysMarQueeTextView(Context context) {
			super(context);
		}
		public AlwaysMarQueeTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public AlwaysMarQueeTextView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		
		@Override
		public boolean isFocused() {
			return true;
		}
		
		@Override
		protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
			// TODO Auto-generated method stub
		}
}
