//package tv.aniu.app.dzlc.service;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import mobi.dreambox.dzlc.restclient.exception.MTomatoClientException;
//
//
///**
// * Class that uses reflection to asynchronously wrap Client methods
// */
//class AsyncReflector {
//
//	/**
//	 * List of primitives to convert from autoboxed method calls
//	 */
//	public final static Map<Class<?>, Class<?>> PRIMITIVE_MAP = new HashMap<Class<?>, Class<?>>();
//	static {
//		PRIMITIVE_MAP.put(Boolean.class, boolean.class);
//		PRIMITIVE_MAP.put(Byte.class, byte.class);
//		PRIMITIVE_MAP.put(Short.class, short.class);
//		PRIMITIVE_MAP.put(Character.class, char.class);
//		PRIMITIVE_MAP.put(Integer.class, int.class);
//		PRIMITIVE_MAP.put(Long.class, long.class);
//		PRIMITIVE_MAP.put(Float.class, float.class);
//		PRIMITIVE_MAP.put(Double.class, double.class);
//	}
//
//	/**
//	 * Singled threaded Executor for async work
//	 */
//	private static ExecutorService sThreadExecutor = Executors
//			.newSingleThreadExecutor();
//
//	private static boolean result = false;
//	
//	/**
//	 * Reflection to run Asynchronous methods
//	 */
//	static <T> void execute(final Object receiver,
//			final OnClientCallback<T> callback, final String function,
//			final Object... args) {
//		
//		result = false;
//		
//		final Handler handler = new Handler(Looper.getMainLooper());
//		sThreadExecutor.execute(new Runnable() {
//			public void run() {
//				try {
//					Class[] classes = new Class[args.length];
//					for (int i = 0; i < args.length; i++) {
//						// Convert Autoboxed primitives to actual primitives
//						// (ex: Integer.class to int.class)
//						// 另外注意的一点如果是boolean型的参数，得到方法的时候需要用boolean.class或者Boolean.TYPE，不能用Boolean.class。
//
//						if (PRIMITIVE_MAP.containsKey(args[i].getClass())) {
//							classes[i] = PRIMITIVE_MAP.get(args[i].getClass());
//						} else {
//							classes[i] = args[i].getClass();
//						}
//					}
//
//					Method method = null;
//					if (receiver instanceof Class) {
//						// Can receive a class if using for static methods
//						method = ((Class) receiver)
//								.getMethod(function, classes);
//					} else {
//						// used for instance methods
//						method = receiver.getClass().getMethod(function,
//								classes);
//					}
//
//					final T answer = (T) method.invoke(receiver, args);
//
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							if (callback != null)
//								callback.onSuccess(answer);
//						}
//					});
//					
//					result = true;
//
//				} catch (final Exception ex) {
//					result = false;
//					if (ex != null) {
//						if(ex instanceof InvocationTargetException){
//							InvocationTargetException targetEx = (InvocationTargetException) ex;
//							final Throwable t = targetEx.getTargetException();
//							if (t instanceof MTomatoClientException) {
//								final MTomatoClientException exce = new MTomatoClientException(
//										((MTomatoClientException) t).getErrCode(),
//										((MTomatoClientException) t).getErrMsg());
//								handler.post(new Runnable() {
//									@Override
//									public void run() {
//										if (callback != null)
//											callback.onException(exce);
//									}
//								});
//							}
//						}else{
//							handler.post(new Runnable() {
//								@Override
//								public void run() {
//									if (callback != null)
//										callback.onException(ex);
//								}
//							});
//						}
//					}else{
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								if (callback != null)
//									callback.onException(null);
//							}
//						});
//					}
//				}finally{
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
//							if (callback != null)
//								callback.onPostExecute(Boolean.valueOf(result));
//						}
//					});
//				}
//			}
//		});
//	}
//}
