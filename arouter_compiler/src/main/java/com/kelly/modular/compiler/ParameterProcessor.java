package com.kelly.modular.compiler;

import com.google.auto.service.AutoService;
import com.kelly.modular.annotation.Parameter;
import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.compiler.factory.ParameterFactory;
import com.kelly.modular.compiler.utils.Constants;
import com.kelly.modular.compiler.utils.EmptyUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(Constants.PARAMETER_ANNOTATION_TYPES)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParameterProcessor extends AbstractProcessor {
    private Elements elementsUtil;
    private Types typesUtils;
    private Messager messager;
    private Filer filer;

    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsUtil = processingEnv.getElementUtils();
        typesUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (EmptyUtils.isEmpty(annotations)) return false;
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Parameter.class);
        if (!EmptyUtils.isEmpty(elements)) {
            //存储元素信息
            valueOfParameter(elements);
            //生成类文件
            try {
                createParameterFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void valueOfParameter(Set<? extends Element> elements) {
        for (Element element : elements) {
            //注解的属性，父节点是类节点
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            if (tempParameterMap.containsKey(typeElement)) {
                tempParameterMap.get(typeElement).add(element);
            } else {
                List<Element> list = new ArrayList<>();
                list.add(element);
                tempParameterMap.put(typeElement, list);
            }
        }
    }

    private void createParameterFile() throws IOException {
        if (EmptyUtils.isEmpty(tempParameterMap)) return;
        //获取Parameter类型
        TypeElement parameterType = elementsUtil.getTypeElement(Constants.PARAMETER_LOAD);
        //参数体配置（Object target）
        ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT, Constants.PARAMETER_NAME).build();
        for (Map.Entry<TypeElement, List<Element>> entry : tempParameterMap.entrySet()) {
            //Map集合中的key值是类名，如：MainActivity
            TypeElement typeElement = entry.getKey();
            //获取类名
            ClassName className = ClassName.get(typeElement);
            //方法体内容构建
            ParameterFactory factoty = new ParameterFactory.Builder(parameterSpec)
                    .setMessager(messager)
                    .setElementUtils(elementsUtil)
                    .setTypeUtils(typesUtils)
                    .setClassName(className)
                    .build();

            //添加方法体内容的第一行： JPMainActivity t = (JPMainActivity) target;
            factoty.addFirstStatement();

            //遍历类里面所有属性： t.name = t.getIntent().getStringExtra("name");
            for (Element fieldElement : entry.getValue()) {
                factoty.buildStatement(fieldElement);
            }

            //最终生成的类文件名（类名$$Parameter）
            String finalClassName = typeElement.getSimpleName() + Constants.PARAMETER_FILE_NAME;
            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件名为：" + finalClassName);

            //生成类文件：MainActivity$$Parameter
            JavaFile.builder(className.packageName(),
                    TypeSpec.classBuilder(finalClassName)
                            .addSuperinterface(ClassName.get(parameterType))
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(factoty.build())
                            .build())
                    .build()
                    .writeTo(filer);
        }
    }

}
