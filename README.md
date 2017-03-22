#   敏捷开发基本框架
![](HandyBase.png)

## 最新版本
    compile 'com.github.liujie045:HandyBase:1.2.1'

## 项目引用
***Step 1.添加maven地址到Project的build.gradle配置文件中***

```javascript
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath 'com.android.tools.build:gradle:2.3.0'
            classpath 'me.tatarka:gradle-retrolambda:3.6.0'
            classpath 'com.jakewharton:butterknife-gradle-plugin:8.5.1'
        }
    }
    
    allprojects {
        repositories {
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }
```
***Step 2.添加compile引用到Module的build.gradle配置文件中***

```javascript
    apply plugin: 'com.android.application'（or：apply plugin: 'com.android.library'）
    apply plugin: 'me.tatarka.retrolambda'
    apply plugin: 'com.jakewharton.butterknife'

    android {
        ...
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
    
    dependencies {
        compile 'com.github.liujie045:HandyBase:1.2.1'
    }
```
***Step 3.在项目的Application或BaseActivity中添加***

    Utils.init(context);

***Step 4.若要使用LitePal数据库功能，需要在assets文件中创建litepal.xml配置文件，文件内容如下***

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
***Step 5.若要在Library中使用Butterknife，正确配置后应使用R2标注资源***

```javascript
    class ExampleActivity extends Activity {
      @BindView(R2.id.user) EditText username;
      @BindView(R2.id.pass) EditText password;
    ...
    }
```

##  更新日志
***2017年3月22日 v1.2.1***

* 更新utils中部分变量的访问权限为public

***2017年3月22日 v1.2***

* 更新build.gradle配置
* 新接入[Litepal](https://github.com/LitePalFramework/LitePal#latest-downloads/):1.5.1
* 新接入[Butterknife](https://github.com/JakeWharton/butterknife):8.5.1
* 新接入[QuickAdapter](https://github.com/ThePacific/adapter):1.0.6

***2017年3月21日 v1.1***

* 更新BaseAppApi注释
* 新接入[Glide](https://github.com/bumptech/glide):3.7.0
* 新接入[EventBus](https://github.com/greenrobot/EventBus):3.0.0
* 新接入[Retrofit](https://github.com/square/retrofit):2.2.0

***2017年3月20日 v1.0***

* 初始化敏捷开发框架项目
* 新增base包，含有Activity、Fragment、Application的基类
* 新增simplemvp和strongmvp包，含有Mvp模式的基类
* 新增mycolor、myvalue颜色及尺寸的资源值
* 新增drawable-hdpi中图标资源（选中、未选中）
* 新增utils包，引用自[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 的工具类开源项目
* 新接入[Rxjava](https://github.com/ReactiveX/RxJava):2.0.7
* 新接入[Rxandroid](https://github.com/ReactiveX/RxAndroid):2.0.1
* 新接入[Gradle-RetroLambda](https://github.com/evant/gradle-retrolambda):3.6.0