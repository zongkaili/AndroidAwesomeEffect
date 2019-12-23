package com.kelly.modular.compiler;

import com.google.auto.service.AutoService;
import com.kelly.modular.annotation.ARouter;
import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.compiler.utils.Constants;
import com.kelly.modular.compiler.utils.EmptyUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * author: zongkaili
 * data: 2019-12-17
 * desc:
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(Constants.AROUTER_ANNOTATION_TYPES)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions({Constants.MODULE_NAME, Constants.APT_PACKAGE})
public class ARouterProcessor extends AbstractProcessor {
    //操作Element工具类
    private Elements elementsUtil;
    //type(类信息)工具类
    private Types typesUtils;
    //用来输出日志
    private Messager messager;
    //文件生成器
    private Filer filer;
    //子模块的模块名，如：app/order/personal
    private String moduleName;
    //用于存放APT生成的类文件的包路径
    private String packageNameForAPT;
    //临时存放路由组Group对应的详细Path类对象
    //如：key："app", value: "app"组的路由路径"ARouter$$Path$$app.class"
    private Map<String, List<RouterBean>> tempPathMap = new HashMap<>();
    //临时存放路由组Group信息
    //如：key："app", value: "ARouter$$Path$$app.class"
    private Map<String, String> tempGroupMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsUtil = processingEnv.getElementUtils();
        typesUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();

        Map<String, String> options = processingEnv.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            moduleName = options.get(Constants.MODULE_NAME);
            packageNameForAPT = options.get(Constants.APT_PACKAGE);
            messager.printMessage(Diagnostic.Kind.NOTE, "init, moduleName >>> " + moduleName);
            messager.printMessage(Diagnostic.Kind.NOTE, "init, packageNameForAPT >>> " + packageNameForAPT);
        }

        if (EmptyUtils.isEmpty(moduleName) || EmptyUtils.isEmpty(packageNameForAPT)) {
            throw new RuntimeException("moduleName and packageNameForAPT can not be null.");
        }
    }

    /**
     * 开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成java文件
     *
     * @param annotations 使用了注解的节点集合
     * @param roundEnv    运行环境，可以通过该对象找到被特定注解的类节点元素
     * @return true表示已经被处理完成，后续处理器不会再处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (EmptyUtils.isEmpty(annotations)) return false;
        //获取所有被@ARouter注解的类节点
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ARouter.class);

        if (!EmptyUtils.isEmpty(elements)) {
            try {
                parseElements(elements);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void parseElements(Set<? extends Element> elements) throws IOException {
        //获取Activity类型元素
        TypeElement activityType = elementsUtil.getTypeElement(Constants.ACTIVITY);
        TypeMirror activityMirror = activityType.asType();

        for (Element element : elements) {
            //获取每个元素的类信息
            TypeMirror elementMirror = element.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "parseElements, element--->" + elementMirror.toString());

            //获取每个类上的@ARouter注解，对应的path值
            ARouter aRouter = element.getAnnotation(ARouter.class);
            //路由详细信息，封装到实体类
            RouterBean bean = new RouterBean.Builder()
                    .setGroup(aRouter.group())
                    .setPath(aRouter.path())
                    .setElement(element)
                    .build();

            //@ARouter注解只能用在类之上，并且是Activity
            if (typesUtils.isSubtype(elementMirror, activityMirror)) {
                bean.setType(RouterBean.Type.ACTIVITY);
            } else {
                throw new RuntimeException("@ARouter目前仅限用于Activity");
            }
            //临时存储以上信息，便于在后续遍历生成所需代码文件
            valueOfPathMap(bean);
        }
        TypeElement groupLoadType = elementsUtil.getTypeElement(Constants.AROUTER_GROUP);
        TypeElement pathLoadType = elementsUtil.getTypeElement(Constants.AROUTER_PATH);
        //1.生成路由的详细Path类文件，如：ARouter$$Path$$app
        createPathFile(pathLoadType);
        //2.生成路由组Group类文件，如：ARouter$$Group$$app
        createGroupFile(groupLoadType, pathLoadType);
    }

     /*
     createPathFile()生成的最终文件示例：
        public class ARouter$$Path$$order implements ARouterLoadPath {
            @Override
            public Map<String, RouterBean> loadPath() {
                Map<String, RouterBean> pathMap = new HashMap<>();
                pathMap.put("/order/Order_MainActivity", RouterBean.create(
                        RouterBean.Type.ACTIVITY,
                        Order_MainActivity.class,
                        "order",
                        "/order/Order_MainActivity"));
                return pathMap;
            }
         }
     */

    /**
     * 生成路由组Group对应详细Path，如：ARouter$$Path$$app
     *
     * @param pathLoadType ARouterLoadPath接口信息
     */
    private void createPathFile(TypeElement pathLoadType) throws IOException {
        if (EmptyUtils.isEmpty(tempPathMap)) return;

        //方法的返回值Map<String, RouterBean>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouterBean.class));

        //遍历分组，每一个分组创建一个路径类文件，如ARouter$$Path$$app
        for (Map.Entry<String, List<RouterBean>> entry : tempPathMap.entrySet()) {
            //构造方法体：public Map<String, RouterBean> loadPath() {}
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.PATH_METHOD_NAME)
                    .addAnnotation(Override.class)//@Override
                    .addModifiers(Modifier.PUBLIC)//public修饰符
                    .returns(methodReturns);//返回值类型

//            Map<String, RouterBean> pathMap = new HashMap<>();
            methodBuilder.addStatement("$T<$T, $T> $N = new $T<>()",
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(RouterBean.class),
                    Constants.PATH_PARAMETER_NAME,
                    ClassName.get(HashMap.class));
            /**
             *  pathMap.put("/order/Order_MainActivity", RouterBean.create(
             *                 RouterBean.Type.ACTIVITY,
             *                 Order_MainActivity.class,
             *                 "order",
             *                 "/order/Order_MainActivity"));
             */
            List<RouterBean> pathList = entry.getValue();
            for (RouterBean bean : pathList) {
                methodBuilder.addStatement("$N.put($S,$T.create($T.$L,$T.class, $S,$S))",
                        Constants.PATH_PARAMETER_NAME,
                        bean.getPath(),
                        ClassName.get(RouterBean.class),
                        ClassName.get(RouterBean.Type.class),
                        bean.getType(),
                        ClassName.get((TypeElement) bean.getElement()),
                        bean.getGroup(),
                        bean.getPath());
                messager.printMessage(Diagnostic.Kind.NOTE, "createPathFile，添加语句 >>> " + bean.toString());
            }

//            return pathMap;
            methodBuilder.addStatement("return $N", Constants.PATH_PARAMETER_NAME);

            //生成类文件，如：ARouter$$Path$$app
            String finalClassName = Constants.PATH_FILE_NAME + entry.getKey();
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "createPathFile，APT生成路由Path类文件名为："
                            + packageNameForAPT + "." + finalClassName);

            JavaFile.builder(packageNameForAPT,
                    TypeSpec.classBuilder(finalClassName)
                            .addSuperinterface(ClassName.get(pathLoadType))
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(methodBuilder.build())
                            .build())
                    .build()
                    .writeTo(filer);

            //存储组名和组的类文件的映射关系，如key: "app", value: "/app/MainActivity"
            tempGroupMap.put(entry.getKey(), finalClassName);
        }
    }

    /*
    createGroupFile()生成的最终文件示例：
        public class ARouter$$Group$$order implements ARouterLoadGroup {
            @Override
            public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {
                Map<String, Class<? extends ARouterLoadPath>> map = new HashMap<>();
                map.put("order", ARouter$$Path$$order.class);
                return map;
            }
        }
     */

    /**
     * 生成路由组Group文件，如：ARouter$$Group$$app
     *
     * @param groupLoadType ARouterLoadGroup接口信息
     * @param pathLoadType  ARouterLoadPath接口信息
     */
    private void createGroupFile(TypeElement groupLoadType, TypeElement pathLoadType) throws IOException {
        if (EmptyUtils.isEmpty(tempGroupMap) || EmptyUtils.isEmpty(tempPathMap)) return;

        //返回值类型：Map<String, Class<? extends ARouterLoadPath>>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathLoadType)))
        );

        //配置方法体：public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {}
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.GROUP_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(methodReturns);

        //配置第一句代码: Map<String, Class<? extends ARouterLoadPath>> map = new HashMap<>();
        methodBuilder.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathLoadType))),
                Constants.GROUP_PARAMETER_NAME,
                HashMap.class);

        //此句需要循环生成多条：groupMap.put("order", ARouter$$Path$$order.class);
        for (Map.Entry<String, String> entry : tempGroupMap.entrySet()) {
            methodBuilder.addStatement("$N.put($S,$T.class)",
                    Constants.GROUP_PARAMETER_NAME,
                    entry.getKey(),
                    ClassName.get(packageNameForAPT, entry.getValue()));
            messager.printMessage(Diagnostic.Kind.NOTE, "createGroupFile，添加语句 >>> " + entry.getKey() + ", " + entry.getValue());
        }

        //return groupMap;
        methodBuilder.addStatement("return $N", Constants.GROUP_PARAMETER_NAME);

        //生成类文件: 如：ARouter$$Group$$app
        String finalClassName = Constants.GROUP_FILE_NAME + moduleName;
        messager.printMessage(Diagnostic.Kind.NOTE,
                "createGroupFile，APT生成路由Group类文件名为："
                        + packageNameForAPT + "." + finalClassName);
        JavaFile.builder(packageNameForAPT,
                TypeSpec.classBuilder(finalClassName)
                        .addSuperinterface(ClassName.get(groupLoadType))
                        .addMethod(methodBuilder.build())
                        .addModifiers(Modifier.PUBLIC)
                        .build())
                .build()
                .writeTo(filer);
    }

    /**
     * 临时存储路由组group对应的详细Path类对象，便于在后续遍历生成所需代码文件
     *
     * @param bean
     */
    private void valueOfPathMap(RouterBean bean) {
        if (!checkRouterPath(bean)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解参数不合法，示例：/app/MainActivity");
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "valueOfPathMap, >>> " + bean.toString());
        List<RouterBean> routerBeans = tempPathMap.get(bean.getGroup());
        if (EmptyUtils.isEmpty(routerBeans)) {
            routerBeans = new ArrayList<>();
            routerBeans.add(bean);
            tempPathMap.put(bean.getGroup(), routerBeans);
        } else {
            routerBeans.add(bean);
        }
    }

    /**
     * 校验@ARouter注解的值，如果group未填写就从必填项path中截取数据
     *
     * @param bean
     * @return
     */
    private boolean checkRouterPath(RouterBean bean) {
        String group = bean.getGroup();
        String path = bean.getPath();
        //@ARouter注解的path值必须要以"/"开头
        if (EmptyUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解参数path未按规范填写，如：/app/MainActivity");
            return false;
        }
        //校验只有一个"/"的情况, 比如："/MainActivity"
        if (path.lastIndexOf("/") == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解参数path未按规范填写，如：/app/MainActivity");
            return false;
        }
        //从第一个"/"和第二个"/"中间截取出组名
        String finalGroup = path.substring(1, path.indexOf("/", 1));
        //比如：/MainActivity/MainActivity
        if (finalGroup.contains("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解参数path未按规范填写，如：/app/MainActivity");
            return false;
        }
        //@ARouter注解中有group值
        if (!EmptyUtils.isEmpty(group) && !group.equalsIgnoreCase(moduleName)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter中的group值必须和当前子模块名相同");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }
        return true;
    }

}
