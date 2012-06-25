package me.imid.meizuapps;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.meizuapps.data.AppInfo;
import me.imid.meizuapps.util.Config;
import me.imid.meizuapps.util.HtmlParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MeizuAppsActivity extends Activity {
	private ListView appListView;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meizuapps_main);
		appListView = (ListView) findViewById(R.id.meizuappsList);

		GetAppInfoTask task = new GetAppInfoTask();
		task.execute(Config.developerUrl);
	}

	/**
	 * 异步加载网页
	 * @author issac
	 *
	 */
	private class GetAppInfoTask extends
			AsyncTask<String, String, ArrayList<AppInfo>> {
		private ArrayList<AppInfo> appInfos;

		@Override
		protected ArrayList<AppInfo> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				appInfos = HtmlParser.getAppInfos(params[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return appInfos;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = new ProgressDialog(
					MeizuAppsActivity.this);
			progressDialog.setMessage("数据载入中，请稍侯..");
			progressDialog.setCancelable(true);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<AppInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result == null) {
				Toast.makeText(getApplicationContext(), "网络故障，请重试",
						Toast.LENGTH_SHORT).show();
				MeizuAppsActivity.this.finish();
				return;
			}

			MeizuappsAdapter adapter = new MeizuappsAdapter(
					getApplicationContext(), result);
			appListView.setAdapter(adapter);
			final ArrayList<AppInfo> appInfos2 = appInfos;
			appListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					startActivity(new Intent("android.intent.action.VIEW", Uri
							.parse(appInfos2.get(position).getAppUrl())));
				}
			});

		}
	}
}