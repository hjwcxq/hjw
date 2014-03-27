package com.orientmedia.app.cfddj.tool;


import android.widget.EditText;
import android.widget.TextView;

public class StringUtil {

	public static String getStr(String str) {
		return isEmpty(str) ? "" : str;
	}

	public static String getEditTextContent(EditText edt) {
		return edt.getText().toString().trim();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	public static String getTextViewContent(TextView tv) {
		return tv.getText().toString().trim();
	}

	// 将返回的字符串转换成int类型
	public static int strToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			// Toast.makeText(context, "类型转换异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return 9999;
	}

	// 将返回的字符串转换成float类型
	public static float strToFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			// Toast.makeText(context, "类型转换异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return 9999;
	}
}
