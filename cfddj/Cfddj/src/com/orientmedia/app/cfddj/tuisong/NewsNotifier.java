/*
 * Copyright (C) 2010 Moduad Co., Ltd.
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
package com.orientmedia.app.cfddj.tuisong;

import java.util.Random;

import com.orientmedia.app.cfddj.R;
import com.orientmedia.app.cfddj.ui.NewsDetailsActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * This class is to notify the user of messages with NotificationManager.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NewsNotifier {
	public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
	public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";
	public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
	public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
	public static final String NOTIFICATION_URI = "NOTIFICATION_URI";

	public static final String SETTINGS_NOTIFICATION_ENABLED = "SETTINGS_NOTIFICATION_ENABLED";
	public static final String SETTINGS_SOUND_ENABLED = "SETTINGS_SOUND_ENABLED";
	public static final String SETTINGS_VIBRATE_ENABLED = "SETTINGS_VIBRATE_ENABLED";
	public static final String SETTINGS_TOAST_ENABLED = "SETTINGS_TOAST_ENABLED";

	public static final String NOTIFICATION_ICON = "NOTIFICATION_ICON";

	public static String PUSH_FILENAME = "pushsetting";

	private static final String LOGTAG = NewsNotifier.class.getSimpleName();

	private static final Random random = new Random(System.currentTimeMillis());

	private Context context;

	private SharedPreferences sharedPrefs;

	private NotificationManager notificationManager;

	public NewsNotifier(Context context) {
		this.context = context;
		this.sharedPrefs = context.getSharedPreferences(PUSH_FILENAME,
				Context.MODE_PRIVATE);
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notifyMessage(String title, String id) {
		if (isNotificationEnabled()) {
			// Notification
			Notification notification = new Notification();
			notification.icon = getNotificationIcon();
			notification.defaults = Notification.DEFAULT_LIGHTS;
			if (isNotificationSoundEnabled()) {
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			if (isNotificationVibrateEnabled()) {
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.when = System.currentTimeMillis();
			notification.tickerText = title;

			Intent intent = new Intent(context, NewsDetailsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("id", id);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(context, "股票老左新闻", title,
					contentIntent);
			notificationManager.notify(random.nextInt(), notification);
		} else {
			Log.e(LOGTAG, "Notificaitons disabled.");
		}
	}

	public void notify(String data) {
		Intent intent = new Intent(context, NewsDetailsActivity.class);
		intent.putExtra("data", data);
		context.startActivity(intent);

	}

	private int getNotificationIcon() {
		return sharedPrefs.getInt(NOTIFICATION_ICON, R.drawable.ic_launcher);
	}

	private boolean isNotificationEnabled() {
		return true;
	}

	private boolean isNotificationSoundEnabled() {
		return sharedPrefs.getBoolean(SETTINGS_SOUND_ENABLED, true);
	}

	private boolean isNotificationVibrateEnabled() {
		return sharedPrefs.getBoolean(SETTINGS_VIBRATE_ENABLED, true);
	}

	private boolean isNotificationToastEnabled() {
		return sharedPrefs.getBoolean(SETTINGS_TOAST_ENABLED, false);
	}

}
