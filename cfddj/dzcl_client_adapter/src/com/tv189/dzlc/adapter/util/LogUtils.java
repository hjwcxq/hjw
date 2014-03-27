package com.tv189.dzlc.adapter.util;

import android.util.Log;

public class LogUtils {
	/**
	 * 鏃ュ織绾у�?
	 */
	private static final int LOG_LEVEL = Log.VERBOSE;

	/**
	 * 寮傚父鏍堜綅绉�
	 */
	private static final int EXCEPTION_STACK_INDEX = 2;

	/**
	 * verbose绾у埆鐨勬棩蹇�?	 * 
	 * @param msg
	 *            鎵撳嵃鍐呭
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	public static void verbose(String msg) {
		if (Log.VERBOSE >= LOG_LEVEL) {
			Log.v(getTag(), msg);
		}
	}

	/**
	 * debug绾у埆鐨勬棩蹇�?	 * 
	 * @param msg
	 *            鎵撳嵃鍐呭
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	public static void debug(String msg) {
		if (Log.DEBUG >= LOG_LEVEL) {
			Log.d(getTag(), msg);
		}
	}

	/**
	 * info绾у埆鐨勬棩蹇�?	 * 
	 * @param msg
	 *            鎵撳嵃鍐呭
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	public static void info(String msg) {
		if (Log.INFO >= LOG_LEVEL) {
			Log.i(getTag(), msg);
		}
	}

	/**
	 * warn绾у埆鐨勬棩蹇�?	 * 
	 * @param msg
	 *            鎵撳嵃鍐呭
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	public static void warn(String msg) {
		if (Log.WARN >= LOG_LEVEL) {
			Log.w(getTag(), msg);
		}
	}

	/**
	 * error绾у埆鐨勬棩蹇�?	 * 
	 * @param msg
	 *            鎵撳嵃鍐呭
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	public static void error(String msg) {
		if (Log.ERROR >= LOG_LEVEL) {
			Log.e(getTag(), msg);
		}
	}

	/**
	 * 鑾峰彇鏃ュ織鐨勬爣绛�鏍煎紡锛氱被鍚峗鏂规硶鍚峗琛屽�?锛堥渶瑕佹潈闄愶細android.permission.GET_TASKS锛�
	 * 
	 * @return tag
	 * @see [绫汇�绫�鏂规硶銆佺�?鎴愬憳]
	 */
	private static String getTag() throws StackOverflowError {
		StackTraceElement element = new LogException().getStackTrace()[EXCEPTION_STACK_INDEX];

		String className = element.getClassName();

		int index = className.lastIndexOf(".");
		if (index > 0) {
			className = className.substring(index + 1);
		}

		return className + "_" + element.getMethodName() + "_"
				+ element.getLineNumber();
	}

	/**
	 * 鍙栨棩蹇楁爣绛剧敤鐨勭殑寮傚父绫伙紝鍙槸鐢ㄤ簬鍙栧緱鏃ュ織鏍囩�?
	 * 
	 */
	private static class LogException extends Exception {
		/**
		 * 娉ㄩ噴鍐呭
		 */
		private static final long serialVersionUID = 1L;
	}
}
