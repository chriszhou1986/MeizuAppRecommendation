package me.imid.meizuapps.util;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.meizuapps.data.AppInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {
	/**
	 * 解析 developerUrl开发者主页的信息
	 * @param developerUrl
	 * @return appinfo 列表
	 * @throws IOException 
	 */
	public static ArrayList<AppInfo> getAppInfos(String developerUrl) throws IOException {
		Document doc = null;
		doc = Jsoup.connect(developerUrl).get();

		ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
		Elements devperson_apps = doc.select("ul.devperson_app").select("li");
		for (Element element : devperson_apps) {
			// 获得appName 以及appUrl
			Element name = element.select("span.devapp_name").select("a")
					.first();
			String appName = name.text();
			String appUrl = "mstore:".concat(name.absUrl("href"));

			// 获得iconUrl
			Element img = element.select("img").first();
			String iconUrl = img.attr("abs:src");

			// 获取version
			String version = element.select("span").last().text();

			AppInfo appInfo = new AppInfo(appName, version, iconUrl, appUrl);
			appInfos.add(appInfo);
		}
		
		return appInfos;
	}
}
