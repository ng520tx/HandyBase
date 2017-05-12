#   敏捷开发基本框架
![](HandyBase.png)

## 最新版本
    compile 'com.github.liujie045:HandyBase:2.1.1'

## 项目引用
#### Step 1.添加maven地址到Project的build.gradle配置文件中
```javascript
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath 'com.android.tools.build:gradle:2.3.1'
            classpath 'me.tatarka:gradle-retrolambda:3.6.0'
            classpath 'com.jakewharton:butterknife-gradle-plugin:8.5.1'
        }
    }
    
    allprojects {
        repositories {
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
            maven { url "https://raw.githubusercontent.com/Pgyer/mvn_repo_pgyer/master" }
        }
    }
```
#### Step 2.添加compile引用到Module的build.gradle配置文件中
```javascript
    apply plugin: 'com.android.application'（or：apply plugin: 'com.android.library'）
    apply plugin: 'me.tatarka.retrolambda'
    apply plugin: 'com.jakewharton.butterknife'
    android {
        ...
        defaultConfig {
            ...
            javaCompileOptions {
                annotationProcessorOptions {
                    arguments = [ eventBusIndex : 'com.example.myapp.MyEventBusIndex' ]
                }
            }
        }
            
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
    
    dependencies {
        ...
        compile 'com.github.liujie045:HandyBase:最新版本'
        annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'
        annotationProcessor 'org.greenrobot:eventbus-annotation-processor:3.0.1'
    }
```

#### Step 3.工具类已在BaseApplication中初始化
```javascript
    if (isInitUtils) {
        Utils.init(getApplicationContext());
        CrashUtils.getInstance().init(); //初始化崩溃捕获工具
    
        LogUtils.Builder builder = new LogUtils.Builder()
            .setLogSwitch(AppUtils.isAppDebug())// 设置log总开关，默认开
            .setGlobalTag(null)// 设置log全局标签，默认为空。当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
            .setLog2FileSwitch(AppUtils.isAppDebug())// 打印log时是否存到文件的开关，默认关
            .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
            .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
            .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
        LogUtils.d(builder.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
```

```javascript
如果不需要初始化工具类，或手动初始化。则可以在BaseApplication的子类中添加代码：
    public class MyBaseApplication extends BaseApplication {
        {
            isInitHandyBaseUtils = false;
        }
        ...
    }
```

#### Step 4.已在BaseActivity中内置Android6.0权限扫描功能，框架已默认添加了四种权限
```javascript
已默认追加的权限：
    <uses-permission android:name="android.permission.INTERNET"/> <!-- 网络通信-->
    <uses-permission android:name="android.permission.RECORD_AUDIO"/> <!-- 允许程序录制音频 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>  <!-- 获取设备信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/> <!-- 获取WIFI状态-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/> <!-- 获取网络状态 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> <!-- 读写sdcard，storage等等 -->
```

```javascript
在BaseApplication的子类中更多需扫描的权限（追加的权限必须在AndroidManifest中配置使用）：
    public class MyBaseApplication extends BaseApplication {
        {
        ...
        PermissionsUtils.getInstance().addPermissions( new ArrayList<String>() {{
            add(Manifest.permission.CAMERA);
        }});
    }
```

```javascript
配置好权限后在BaseActivity中onStart方法中会默认进行扫描操作。
如果扫描权限发现已全部允许，则调用onPermissionSuccessHDB()接口方法。
如果扫描权限发现有未启用的权限，则调用onPermissionRejectionHDB()接口方法。在此方法中可以弹出对话框提示用户手动开启权限，从设置界面返回到应用时需再次扫描权限
参考操作：
    @Override
    public void onPermissionRejectionHDB() {
        super.onPermissionRejectionHDB();
        SweetDialogUT.showNormalDialog((BaseActivity) activity, "发现未启用权限", "为保障应用正常使用，请开启应用权限", "开启", "退出", new SweetAlertDialog.OnSweetClickListener()
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            ToastUtils.getInstance().showShortToast("请在手机设置权限管理中启用开启此应用系统权限");
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 45);
            sweetAlertDialog.dismiss();
        }
    }, new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();
            ActivityStackUtils.getInstance().AppExit(context);
        }
    }).setCancelable(false);
    
若从设置界面返回，重新扫描权限（请将此方法放与onActivityPermissionRejection()同级）
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45) {
            PermissionsUtils.getInstance().checkDeniedPermissions(activity, true);
        }
    }
```

```javascript
* 若要动态扫描权限，可以使用：public boolean checkDeniedPermissions(Activity activity, List<String> permissions, boolean isRequest)方法。
* 参数permissions为要扫描的权限，扫描后的处理同上。
```

#### Step 5.若要使用LitePal数据库功能，需要在assets文件中创建litepal.xml配置文件，文件内容如下
```javascript
    <?xml version="1.0" encoding="utf-8"?>
    <litepal>
        <!--<dbname>是数据库的名字-->
        <dbname value="collectres"/>
        <!--<version>是数据库的版本号-->
        <version value="1"/>
        <!--<list>是数据库的映射模型（数据库表）-->
        <list>
            <!--<mapping>是数据库的映射模型的地址（数据库表结构）-->
            <!--只有private修饰的字段才会被映射到数据库表中，即如果有某一个字段不想映射的话，就设置为public、protected或者default修饰符就可以了。-->
            <mapping class="com.boco.gx.areacollect.model.collect.Community"/>
        </list>
    
        <!--keep ：按类和字段名大小写作为表名和列名-->
        <!--upper ：将所有的类和字段名称以大写的方式作为表明和列名。-->
        <!--lower ：将所有的类和字段名称以小写的方式作为表明和列名。-->
        <cases value="keep"/>
    
        <!--不设置则默认internal-->
        <!--internal：设置internal将把数据库存在应用内部文件夹，非本应用和root权限无法查看-->
        <!--external：如果设置external，数据库文件将储存在/storage/sdcard1/Android/data/应用包名/files/databases-->
        <!--如果是不想被别人查看的数据，最好不要设置external。在设置external的时候别忘了加权限<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>-->
        <storage value="internal"/>
    </litepal>
```
#### Step 6.若要在Library中使用Butterknife，正确配置后应使用R2标注资源
```javascript
    class ExampleActivity extends Activity {
      @BindView(R2.id.user) EditText username;
      @BindView(R2.id.pass) EditText password;
    ...
    }
```
#### Step 7.若要使用蒲公英内测功能，需要在module中配置
```javascript
在BaseApplication中已添加注册方法，若需要使用此功能，可以在BaseApplication的子类中添加代码：
    public class MyBaseApplication extends BaseApplication {
        {
            ...
            isInitPgyCrashManager = true;
        }
        ...
    }
```

```javascript
在AndroidManifest配置文件中添加：
<application
    ...>
    ...
    <meta-data
        android:name="PGYER_APPID"
        android:value="此处填写蒲公英平台上传应用后获得的AppId" >
    </meta-data>
    <provider
        android:name="com.handy.base.provider.pgyProvider"
        android:authorities="此处输入授权名，可以随意输入但要保证唯一性，可能会和手机其他App产生冲突"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/pgycrash_paths"/>
    </provider>
</application>
```

```javascript
在res下的xml文件夹（不存在则创建）中创建pgycrash_paths.xml文件，写入内容：
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="files_root"
        path="Android/data/项目包名/pgycrash"/>
    <external-path
        name="external_storage_root"
        path="."/>
</paths>
```

##  更新日志
***2017年5月12日 v2.1.1***

* Utils工具类框架增加部分方法

***2017年5月12日 v2.1.0***

* 更新Utils工具类框架为2017年5月12日版本
* 设置Utils全局Context引用方式

***2017年4月27日 v2.0.8***

* 优化了ImageUtils中绘制水印右边距
* 增加了以照片高度比例配置水印字体大小的绘制方法

***2017年4月27日 v2.0.7***

* 优化了BaseActivity，新增了setContentViewHDB方法

***2017年4月27日 v2.0.6***

* 优化了ActivityStackUtils
* NetworkUtils中新增网络连接判断方法

***2017年4月24日 v2.0.5***

* 更新了QuickAdapter的版本为1.0.7

***2017年4月24日 v2.0.4***

* 更新了ShareUtils构造函数
* LogUtils日志打印中增加了加密能力

***2017年4月20日 v2.0.3***

* 修复了BaseFragmet的启动bug

***2017年4月19日 v2.0.1***

* 更新了项目框架
* 更新了Utils工具类框架
* 更新了异常处理工具类的捕获异常时的处理
* 使用retrolambda替换了默认的Lambda表达式功能

***2017年3月29日 v1.1.2***

* 更新BaseAppApi
* 更新Library的build.gradle配置文件
* 更新了PermissionsUtils中默认的扫描权限
* 在ImageUtils中新增了按质量压缩图片的方法
* 新接入[蒲公英内测](https://www.pgyer.com/)2.5.6

***2017年3月24日 v1.1.1***

* 新增SQLiteUtils数据库工具类
* 更新Library的build.gradle配置文件

***2017年3月24日 v1.1.0***

* 更新了使用说明
* 优化了工具类代码，使用单例模式。
* LogUtils中对文件日志增加了AES内容加密
* 移除了SPUtils工具类，替换为ShareUtils
* 配置框架默认权限，及6.0系统中对已配置权限的扫描功能
* 在BaseActivity和BaseApplication中增加了对HandyBaseUtils的注册

***2017年3月22日 v1.0.2***

* 更新build.gradle配置
* 更新utils中部分变量的访问权限为public
* 新接入[Litepal](https://github.com/LitePalFramework/LitePal#latest-downloads/):1.5.1
* 新接入[Butterknife](https://github.com/JakeWharton/butterknife):8.5.1
* 新接入[QuickAdapter](https://github.com/ThePacific/adapter):1.0.6

***2017年3月21日 v1.0.1***

* 更新BaseAppApi注释
* 新接入[Glide](https://github.com/bumptech/glide):3.7.0
* 新接入[Retrofit](https://github.com/square/retrofit):2.2.0
* 新接入[EventBus](https://github.com/greenrobot/EventBus):3.0.0   [参考1](http://www.jianshu.com/p/1eaca34e5314)

***2017年3月20日 v1.0.0***

* 初始化敏捷开发框架项目
* 新增mycolor、myvalue颜色及尺寸的资源值
* 新增drawable-hdpi中图标资源（选中、未选中）
* 新增simplemvp和strongmvp包，含有Mvp模式的基类
* 新增base包，含有Activity、Fragment、Application的基类
* 新增utils包，引用自[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 的工具类开源项目
* 新接入[Rxjava](https://github.com/ReactiveX/RxJava):2.0.7
* 新接入[Rxandroid](https://github.com/ReactiveX/RxAndroid):2.0.1