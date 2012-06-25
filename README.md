###项目简介
开发者都乐于在自己的应用中使用互推的形式，推荐自己的其他作品。在不同的app中维护同一个列表也是很麻烦的。
为此我开发了这个听上去牛逼哄哄的“Flyme OS Apps互推系统”
使用的技术很简单，就是爬虫加上一点Android的基本技能。

###使用效果
该列表包含了指定开发者的所有App，点击某一行会进入到mstore相应的页面中
![](http://i.imgur.com/57gTk.jpg)

###导入方式
1. **作为library导入到所需工程中**
<a href="http://imgur.com/Yl2jm.png"><img title="Hosted by imgur.com" src="http://i.imgur.com/Yl2jm.png" alt="" /></a>
&nbsp;
2. **注册Activity，在manifest中添加如下代码**
```xml
<activity
     android:name="me.imid.meizuapps.MeizuAppsActivity"
     android:label="@string/label_otherapps" android:theme="@android:style/Theme.Light.NoTitleBar">
</activity>
```

3. **添加权限**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

4. **配置url**
修改Config.java 中的developerUrl 为目标开发者链接，这个链接可以在开发者控制台中找到。
比如我的就是:[http://app.meizu.com/imid](http://app.meizu.com/imid)


5. **使用以下代码跳转到app互推目录**
```java
startActivity(new Intent(getApplicationContext(),MeizuAppsActivity.class));
```
