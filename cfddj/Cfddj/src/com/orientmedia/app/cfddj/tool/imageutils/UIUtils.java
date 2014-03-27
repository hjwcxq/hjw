/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orientmedia.app.cfddj.tool.imageutils;

import java.lang.reflect.Field;

import com.orientmedia.app.cfddj.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 
 * @author crossle
 */
public class UIUtils {

	private static final String IMAGE_FETCHER = "imageFetcher";

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasGingerbreadMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return hasHoneycomb() && isTablet(context);
	}

	public static boolean isGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null || !activeNetwork.isConnected()) {
			return false;
		}
		return true;
	}

	public static ImageFetcher getImageFetcher(final FragmentActivity activity) {
		// The ImageFetcher takes care of loading remote images into our
		// ImageView
		ImageFetcher fetcher = new ImageFetcher(activity);
		fetcher.setImageCache(ImageCache.findOrCreateCache(activity,
				IMAGE_FETCHER));
		return fetcher;
	}

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	public static void changeToTheme(Activity activity) {
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	public static TypedValue getAttrValue(Activity activity, int attrId) {
		TypedValue typedValue = new TypedValue();
		activity.getTheme().resolveAttribute(attrId, typedValue, true);
		return typedValue;
	}

	/**
	 * Share note to apps that support {@link Intent#ACTION_SEND} action and
	 * {@text/plain} type
	 */
	public static void sendTo(Context context, String info) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, info);
		intent.setType("text/plain");
		context.startActivity(intent);
	}

	@SuppressLint("NewApi")
	public static void customActionBatHome(ActionBar bar, Context cont) {
		try {
			Class<?> mActionBarImpl = Class
					.forName("com.android.internal.app.ActionBarImpl");
			Class<?> mActionBarView = Class
					.forName("com.android.internal.widget.ActionBarView");

			Field fActionView = mActionBarImpl.getDeclaredField("mActionView");
			fActionView.setAccessible(true);
			Object objActionView = fActionView.get(bar);

			Field homeLayout = mActionBarView.getDeclaredField("mHomeLayout");
			homeLayout.setAccessible(true);
			Object objHomeView = homeLayout.get(objActionView);

			Field titleLayout = mActionBarView.getDeclaredField("mTitleLayout");
			titleLayout.setAccessible(true);
			Object objTitleView = titleLayout.get(objActionView);

			FrameLayout homeView = (FrameLayout) objHomeView;
			ImageView imagIcon = (ImageView) homeView.getChildAt(1);
			imagIcon.setBackgroundResource(R.drawable.ic_launcher);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
