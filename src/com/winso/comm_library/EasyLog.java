package com.winso.comm_library;

/**
 * 该项目统一日志打印管理类。 使用统一的日志标签。
 * 
 */
public class EasyLog {

	/** 统一使用的日志标签。 */
	private static String TAG = "com.winso.break_law.log";

	/**
	 * 打印调试类型日志信息。
	 * 
	 * @param message
	 *            要打印的内容。
	 */
	public static void debug(String message) {
		android.util.Log.d(TAG, message);
	}

	/**
	 * 打印错乱类型日志信息。
	 * 
	 * @param message
	 *            要打印的内容。
	 */
	public static void error(String message) {
		android.util.Log.e(TAG, message);
	}

	/**
	 * 打印普通类型日志信息。
	 * 
	 * @param message
	 *            要打印的内容。
	 */
	public static void info(String message) {
		android.util.Log.i(TAG, message);
	}

	/**
	 * 打印长日志。
	 * 
	 * @param message
	 *            要打印的内容。
	 */
	public static void verbose(String message) {
		android.util.Log.v(TAG, message);
	}

	/**
	 * 打印警告类型日志信息。
	 * 
	 * @param message
	 *            要打印的内容。
	 */
	public static void warn(String message) {
		android.util.Log.w(TAG, message);
	}

}
