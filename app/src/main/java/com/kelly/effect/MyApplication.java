package com.kelly.effect;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kelly.effect.neteasy.musiclist.ui.UIUtils;
import com.kelly.effect.plugin.hook.HookProxyActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.getInstance(this);

        /* ************一下代码适用于Android 25，26及以上平台api有所更改**************** */

        //通过hook源码的方式，能成功启动"一个没有在AndroidManifest.xml中注册的Activity"
        try {
            //1.先hook ams中的activity检查过程，
            //把需要启动的activity换成已在配置文件中注册的代理activity
            hookAms();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("hook", "hookAms失败 >>> " + e.getMessage());
        }

        try {
            //2.然后hook activity的加载启动过程
            //在真正加载activity之前，把之前注入的代理activity换成需要启动的activity
            hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("hook", "hookLaunchActivity失败 >>> " + e.getMessage());
        }

        //将插件的dexElements和宿主的dexElements合并，便于宿主能加载到插件定义的类
        try {
            pluginToAppAction();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("hook", "pluginToAppAction失败 >>> " + e.getMessage());
        }

        //LoadedApk式插件化：切入点是ActivityThread$H中的handleMessage()-->LAUNCH_ACTIVIT
        try {
            customLoadedApkAction();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("hook", "pluginToAppAction失败 >>> " + e.getMessage());
        }
    }

    /**
     * 已过时，适用于Android API 25 Platform
     *
     * @throws Exception
     */
    private void hookAms() throws Exception {
        Class iActivityManagerClass = Class.forName("android.app.IActivityManager");

        Class activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Object iActivityManager = activityManagerNativeClass.getMethod("getDefault()").invoke(null);

        Object iActivityManagerProxy = Proxy.newProxyInstance(
                MyApplication.class.getClassLoader(),
                new Class[]{iActivityManagerClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //IActivityManager中所有方法都会在回调到这里来
                        //startActivity()中第3个参数是包含目标activity的intent，见源码android.app.IActivityManager
                        if ("startActivity".equals(method.getName())) {
                            //将需要启动的activity换成已注册的代理activity，让其能通过AMS的检查
                            Intent intent = new Intent(MyApplication.this, HookProxyActivity.class);
                            ////将包含目标activity的intent保存一份，便于后续在真正加载activity时取出再替换回来
                            intent.putExtra("actionIntent", (Intent) args[2]);
                            args[2] = intent;
                        }
                        Log.d("hook", "拦截到了IActivityManager里面的方法 ： " + method.getName());
                        return method.invoke(iActivityManager, args);
                    }
                }
        );

        Class singletonClass = Class.forName("android.util.Singleton");
        Field instanceField = singletonClass.getDeclaredField("mInstance");
        instanceField.setAccessible(true);

//        Class activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Object gDefault = activityManagerNativeClass.getDeclaredField("gDefault").get(null);


        instanceField.set(gDefault, iActivityManagerProxy);
    }

    private void hookLaunchActivity() throws Exception {
        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);

        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field hField = activityThreadClass.getDeclaredField("mH");
        hField.setAccessible(true);
        Handler h = (Handler) hField.get(activityThread);

        callbackField.set(h, new MyCallback(h));
    }

    //copy from ActivityThread$H
    public static final int LAUNCH_ACTIVITY = 100;

    class MyCallback implements Handler.Callback {
        private Handler mH;

        MyCallback(Handler h) {
            mH = h;
        }

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == LAUNCH_ACTIVITY) {
                Object obj = msg.obj;
                try {
                    Field intentField = obj.getClass().getDeclaredField("intent");
                    intentField.setAccessible(true);

                    //取出事先保存的包含目标activity的intent，然后替换回来，这样真正加载的activity就还是我们需要启动的那个目标activity了
                    Intent intent = (Intent) intentField.get(obj);
                    Intent actionIntent = intent.getParcelableExtra("activityIntent");
                    if (actionIntent != null) {
                        intentField.set(obj, actionIntent);
                    }

                    /**
                     * 对宿主和插件进行区分
                     */
                    Field activityInfoField = obj.getClass().getDeclaredField("activityInfo");
                    activityInfoField.setAccessible(true);
                    ActivityInfo activityInfo = (ActivityInfo) activityInfoField.get(obj);
                    if (actionIntent.getPackage() != null) {
                        activityInfo.applicationInfo.packageName = actionIntent.getPackage();
                    } else {
                        activityInfo.applicationInfo.packageName = actionIntent.getComponent().getPackageName();
                        //根据源码LoadedApk中的initializeJavaContextClassLoader()方法得知：
                        //可通过hook getPackageInfo()方法，让其返回值不为null，就不会抛出异常:
                        //IllegalStateException("Unable to get package info for + mPackageName + "; is package not installed?")
                        hookGetPackageInfo();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mH.handleMessage(msg);
            return true;
        }
    }

    /**
     * 将插件的dexElements和宿主的dexElements合并
     * 产生此方式的背景：插桩式插件化的插件必须使用宿主的上下文环境，不够灵活
     * 此方式将插件和宿主的dexElements合并成新的dexElements对象，并用新的对象替换旧的，这样宿主就可以加载到插件的类了
     */
    private void pluginToAppAction() throws Exception {
        //第一步：得到宿主的dexElements对象
        Class<?> baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        PathClassLoader pathClassLoader = (PathClassLoader) this.getClassLoader();//本质是一个PathClassLoader
        Object pathListObj = pathListField.get(pathClassLoader);//获取PathClassLoader中的pathList对象
        Field dexElementsField = pathListObj.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        Object dexElementsObj = dexElementsField.get(pathListObj);//拿到了宿主的dexElements对象了

        //第二步：得到插件的dexElements对象
        Class<?> pluginBaseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pluginPathListField = pluginBaseDexClassLoaderClass.getDeclaredField("pathList");
        pluginPathListField.setAccessible(true);

        //找到插件apk，创建DexClassLoader对象
        File pluginFile = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!pluginFile.exists()) {
            throw new FileNotFoundException("没有找到插件文件!");
        }
        String pluginFilePath = pluginFile.getAbsolutePath();
        File pluginDir = this.getDir("pluginDir", Context.MODE_PRIVATE);//data/data/包名/pluginDir/
        DexClassLoader pluginDexClassLoader = new DexClassLoader(pluginFilePath, pluginDir.getAbsolutePath(),
                null, getClassLoader());

        Object pluginPathListObj = pluginPathListField.get(pluginDexClassLoader);//获取DexClassLoader中的pathList对象
        Field pluginDexElementsField = pluginPathListObj.getClass().getDeclaredField("dexElements");
        pluginDexElementsField.setAccessible(true);
        Object pluginDexElementsObj = pluginDexElementsField.get(pathListObj);//拿到了插件的dexElements对象了

        //第三步：创建新的dexElements对象
        int mainDexLength = Array.getLength(dexElementsObj);
        int pluginDexLength = Array.getLength(pluginDexElementsObj);
        int dexLengthSum = mainDexLength + pluginDexLength;
        Object newDexElementsObj = Array.newInstance(dexElementsObj.getClass().getComponentType(), dexLengthSum);

        //第四步：将插件和宿主dexElements中的元素重新装进新的dexElements中
        for (int i = 0; i < dexLengthSum; i++) {
            if (i < mainDexLength) {
                Array.set(newDexElementsObj, i, Array.get(dexElementsObj, i));
            } else {
                Array.set(newDexElementsObj, i, Array.get(pluginDexElementsObj, i - mainDexLength));
            }
        }

        //第五步：将新的dexElements对象设置到宿主中，即替换宿主原来的dexElements
        dexElementsField.set(dexElementsObj, newDexElementsObj);

        //定义如何去加载插件中的布局
        doLoadPluginLayout();
    }

    private Resources mResources;
    private AssetManager mAssetManager;

    /**
     * 针对于插件，重新定义Resources对象
     *
     * @throws Exception
     */
    private void doLoadPluginLayout() throws Exception {
        mAssetManager = AssetManager.class.newInstance();

        //通过AssetManager中的addAssetPath()将插件包的路径给AssetManager，它才能去加载插件包中的资源
        File pluginFile = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!pluginFile.exists()) {
            throw new FileNotFoundException("没有找到插件文件!");
        }
        Method addAssetPathMethod = mAssetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
        addAssetPathMethod.setAccessible(true);
        addAssetPathMethod.invoke(mAssetManager, pluginFile.getAbsolutePath());

        //通过阅读源码得知：如果要通过textView.setText(R.string.xxx)来设置text的话，
        //源码会最终调用AssetManager的getResourceValue()来将string资源id转化成string，
        //该方法中用到mStringBlocks[]来获取text，所以得知mStringBlocks必须要实例化才行，
        //所以需要通过执行AssetManager中的ensureStringBlocks()方法来实例化mStringBlocks
        Method ensureStringBlocksMethod = mAssetManager.getClass().getDeclaredMethod("ensureStringBlocks");
        ensureStringBlocksMethod.setAccessible(true);
        ensureStringBlocksMethod.invoke(mAssetManager);

        Resources resources = getResources();//当前宿主的Resources对象

        //用当前宿主的配置信息去重新创建一个Resources对象，专门用来加载插件
        mResources = new Resources(mAssetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    /**
     * 通过重写以下两个方法，便于在插件中使用
     * 插件的BaseActivity中可重写getResources()和getAssets()，然后如下判断：
     * if (getApplication() != null && getApplication().getResources() != null) {
     *     return getApplication().getResources();
     * }
     * return super.getResources();
     *
     * if (getApplication() != null && getApplication().getAssets() != null) {
     *     return getApplication().getAssets();
     * }
     * return super.getAssets();
     *
     */

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    /**
     * 自己创建一个LoadedApk.ClassLoader 添加到mPackages，此LoadedApk专门用来加载插件里面的class
     */
    private void customLoadedApkAction() throws Exception {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!file.exists()) {
            throw new FileNotFoundException("插件包不存在..." + file.getAbsolutePath());
        }
        String pluginPath = file.getAbsolutePath();

        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        //执行ActivityThread类中的currentActivityThread()得到ActivityThread对象
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        //拿到其中的mPackages对象
        Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
        mPackagesField.setAccessible(true);
        Object mPackagesObj = mPackagesField.get(activityThread);

        Map mPackages = (Map) mPackagesObj;

        //执行方法：public final LoadedApk getPackageInfoNoCheck(ApplicationInfo ai, CompatibilityInfo compatInfo)
        //得到LoadedApk对象
        Class compatibilityInfoClass = Class.forName("android.content.res.CompatibilityInfo");
        Method getPackageInfoNoCheckMethod = activityThreadClass.getMethod("getPackageInfoNoCheck", ApplicationInfo.class, compatibilityInfoClass);
        ApplicationInfo applicationInfo = getApplicationInfoAction();
        Field defaultCompatibilityInfoField = compatibilityInfoClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultCompatibilityInfoField.setAccessible(true);
        Object defaultCompatibilityInfoObj = defaultCompatibilityInfoField.get(null);
        Object loadedApkObj = getPackageInfoNoCheckMethod.invoke(activityThread, applicationInfo, defaultCompatibilityInfoObj);

        //常见新的classLoader并替换loadedApk中的classLoader
        File fileDir = getDir("pluginPathDir", Context.MODE_PRIVATE);
        ClassLoader classLoader = new PluginClassLoader(pluginPath, fileDir.getAbsolutePath(), null, getClassLoader());
        Field classLoaderField = loadedApkObj.getClass().getDeclaredField("mClassLoader");
        classLoaderField.setAccessible(true);
        classLoaderField.set(loadedApkObj, classLoader);//替换LoadApk里面的ClassLoader对象

        //添加自定义的loadedApk，加入到mPackages中
        WeakReference weakReference = new WeakReference(loadedApkObj);
        mPackages.put(applicationInfo.packageName, weakReference);
    }

    private ApplicationInfo getApplicationInfoAction() throws Exception {
        Class packageParserClass = Class.forName("android.content.pm.PackageParser");

        Object packageParser = packageParserClass.newInstance();

        Class $PackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class packageUserStateClass = Class.forName("android.content.pm.PackageUserState");

        Method applicationInfoMethod = packageParserClass.getMethod("generateApplicationInfo", $PackageClass, int.class, packageUserStateClass);

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        String pluginPath = file.getAbsolutePath();

        Method packageMethod = packageParserClass.getMethod("parsePackage", File.class, int.class);
        Object packageObj = packageMethod.invoke(packageParser, file, PackageManager.GET_ACTIVITIES);

        ApplicationInfo applicationInfo = (ApplicationInfo) applicationInfoMethod.invoke(packageParser, packageObj, 0, packageUserStateClass.newInstance());
        applicationInfo.publicSourceDir = pluginPath;
        applicationInfo.sourceDir = pluginPath;

        return applicationInfo;
    }

    class PluginClassLoader extends DexClassLoader {

        public PluginClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
            super(dexPath, optimizedDirectory, librarySearchPath, parent);
        }
    }


    private void hookGetPackageInfo() throws Exception {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Field sCurrentActivityThread = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        sCurrentActivityThread.setAccessible(true);

        Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object packageManager = sPackageManagerField.get(null);//sPackageManager是一个静态属性，所以此处参数传null即可

        Class iPackageManagerClass = Class.forName("android.content.pm.IPackageManager");

        Object iPackageManagerProxy = Proxy.newProxyInstance(getClassLoader(),
                new Class[]{iPackageManagerClass},//要监听的接口
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("getPackageInfo".equals(method.getName())) {
                            //hook住getPackageInfo()，返回一个实例对象，让其不为null,就不会抛出异常
                            //可见源码：LoadedApk类的initializeJavaContextClassLoader()
                            return new PackageInfo();
                        }
                        return method.invoke(packageManager, args);
                    }
                });

        sPackageManagerField.set(null, iPackageManagerProxy);
    }

}
