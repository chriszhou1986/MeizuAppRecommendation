package me.imid.meizuapps.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	private Context mContext;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private HashMap<String, SoftReference<Bitmap>> imageCache;

	public AsyncImageLoader(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 载入图片
	 * 
	 * @param imageUrl
	 *            图片url
	 * @param imageLoaderCallback
	 *            回调
	 * @return 可用的缓存
	 */
	public Bitmap loadImage(final String imageUrl,
			final ImageLoaderCallback imageLoaderCallback) {
		// 内存中缓存有该图片，直接载入
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> bitmapReference = imageCache.get(imageUrl);
			Bitmap bitmap = bitmapReference.get();
			if (bitmap != null) {
				return bitmap;
			}
		} else {
			// 内存中没有缓存，查找本地文件
			String imageName = imageUrl
					.substring(imageUrl.lastIndexOf("/") + 1);
			File imageFile = null;
			File cacheDir = mContext.getExternalCacheDir();
			File[] cachedFiles = cacheDir.listFiles();
			for (File file : cachedFiles) {
				if (imageName.equals(file.getName())) {
					imageFile = file;
					break;
				}
			}
			if (imageFile != null) {
				return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			}
		}

		// 内存和本地缓存中都没有该文件，从网络下载并缓存
		LoadHandler handler = new LoadHandler(imageLoaderCallback);

		DownloadImageRunnable runnable = new DownloadImageRunnable(imageUrl,
				handler);

		executorService.execute(runnable);

		return null;
	}

	/**
	 * 用于从网络上获取图片
	 * 
	 * @author issac
	 * 
	 */
	private class DownloadImageRunnable implements Runnable {
		String imageUrl;
		Handler handler;

		public DownloadImageRunnable(String imageUrl, Handler handler) {
			// TODO Auto-generated constructor stub
			this.imageUrl = imageUrl;
			this.handler = handler;
		}

		public void run() {
			try {
				InputStream inputStream = (new URL(imageUrl).openStream());
				Bitmap tempBitmap = BitmapFactory.decodeStream(inputStream);

				Message msg = new Message();
				msg.obj = tempBitmap;
				handler.sendMessage(msg);

				imageCache.put(imageUrl, new SoftReference<Bitmap>(tempBitmap));

				File file = mContext.getExternalCacheDir();
				String bitmapName = imageUrl.substring(imageUrl
						.lastIndexOf("/") + 1);
				File bitmapfile = new File(file.getAbsoluteFile() + bitmapName);

				bitmapfile.createNewFile();

				FileOutputStream outputStream = new FileOutputStream(bitmapfile);
				tempBitmap.compress(Bitmap.CompressFormat.PNG, 100,
						outputStream);

				outputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private class LoadHandler extends Handler {
		private ImageLoaderCallback imageLoaderCallback;

		public LoadHandler(ImageLoaderCallback callback) {
			// TODO Auto-generated constructor stub
			imageLoaderCallback = callback;
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bitmap bitmap = (Bitmap) msg.obj;
			imageLoaderCallback.onLoaded(bitmap);
		}
	}

	public interface ImageLoaderCallback {
		public void onLoaded(Bitmap bitmap);
	}
}
