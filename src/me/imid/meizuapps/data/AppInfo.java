package me.imid.meizuapps.data;

public class AppInfo {
	private String name;
	private String version;
	private String iconUrl;
	private String appUrl;

	public AppInfo(String name, String version, String iconUrl, String appUrl) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.version = version;
		this.iconUrl = iconUrl;
		this.appUrl = appUrl;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\nname:" + name + "\nversion:" + version + "\nimageUrl:"
				+ iconUrl + "\nappUrl:" + appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public String getVersion() {
		return version;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getName() {
		return name;
	}
}
