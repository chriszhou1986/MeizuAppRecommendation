package me.imid.meizuapps;

import java.util.ArrayList;

import me.imid.meizuapps.data.AppInfo;
import me.imid.meizuapps.data.AsyncImageLoader;
import me.imid.meizuapps.data.AsyncImageLoader.ImageLoaderCallback;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeizuappsAdapter extends BaseAdapter {
	private ArrayList<AppInfo> appInfos;
	private LayoutInflater mLayoutInflater;
	private AsyncImageLoader asyncImageLoader;

	public MeizuappsAdapter(Context context, ArrayList<AppInfo> appInfos) {
		// TODO Auto-generated constructor stub
		this.appInfos = appInfos;
		mLayoutInflater = LayoutInflater.from(context);
		asyncImageLoader = new AsyncImageLoader(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return appInfos.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appInfos.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.meizuapps_listitem,
					null);
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView
					.findViewById(R.id.meizuapps_icon);
			holder.appName = (TextView) convertView
					.findViewById(R.id.meizuapps_name);
			holder.appVersion = (TextView) convertView
					.findViewById(R.id.meizuapps_version);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AppInfo appInfo = appInfos.get(position);

		// 载入appIcon
		String iconUrl = appInfo.getIconUrl();
		Bitmap cachedImage = asyncImageLoader.loadImage(iconUrl,
				new ImageLoaderCallback() {

					public void onLoaded(Bitmap bitmap) {
						holder.appIcon.setImageBitmap(bitmap);
					}
				});

		if (cachedImage == null) {
			holder.appIcon.setImageResource(R.drawable.ic_launcher);
		} else {
			holder.appIcon.setImageBitmap(cachedImage);
		}

		// 设置app文字信息
		holder.appName.setText(appInfo.getName());
		holder.appVersion.setText(appInfo.getVersion());
		return convertView;
	}

	private class ViewHolder {
		ImageView appIcon;
		TextView appName;
		TextView appVersion;
	}

}
