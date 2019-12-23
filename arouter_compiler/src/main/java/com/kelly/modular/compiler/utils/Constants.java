package com.kelly.modular.compiler.utils;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class Constants {
    //注解处理器支持的注解类型:ARouter
    public static final String AROUTER_ANNOTATION_TYPES = "com.kelly.modular.annotation.ARouter";
    //注解处理器支持的注解类型:Parameter
    public static final String PARAMETER_ANNOTATION_TYPES = "com.kelly.modular.annotation.Parameter";
    //子模块的模块名
    public static final String MODULE_NAME = "moduleName";
    //存放apt生成的类文件的目录
    public static final String APT_PACKAGE = "packageNameForAPT";

    //String全类名
    public static final String STRING = "java.lang.String";
    //Activity全类名
    public static final String ACTIVITY = "android.app.Activity";
    //包名前缀封装
    public static final String BASE_PACKAGE = "com.kelly.modular.api";
    //路由组Group加载接口
    public static final String AROUTER_GROUP = BASE_PACKAGE + ".core.ARouterLoadGroup";
    //路由组Group对应的详细Path加载接口
    public static final String AROUTER_PATH = BASE_PACKAGE + ".core.ARouterLoadPath";
    //获取参数，加载接口
    public static final String PARAMETER_LOAD = BASE_PACKAGE + ".core.ParameterLoad";

    //路由组Group对应的详细Path, 方法名
    public static final String PATH_METHOD_NAME = "loadPath";
    //路由组Group对应的详细Path, 参数名
    public static final String PATH_PARAMETER_NAME = "pathMap";
    //路由组Group对应的详细Path, 文件名前缀
    public static final String PATH_FILE_NAME = "ARouter$$Path$$";
    //路由组Group, 方法名
    public static final String GROUP_METHOD_NAME = "loadGroup";
    //路由组Group, 参数名
    public static final String GROUP_PARAMETER_NAME = "groupMap";
    //路由组Group, 文件名前缀
    public static final String GROUP_FILE_NAME = "ARouter$$Group$$";

    //获取参数，方法名
    public static final String PARAMETER_NAME = "target";
    //获取参数，参数名
    public static final String PARAMETER_METHOD_NAME = "loadParameter";
    //APT生成的获取参数类文件名
    public static final String PARAMETER_FILE_NAME = "$$Parameter";
}
