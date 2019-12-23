package com.kelly.modular.compiler.factory;

import com.kelly.modular.annotation.Parameter;
import com.kelly.modular.compiler.utils.Constants;
import com.kelly.modular.compiler.utils.EmptyUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.security.PublicKey;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class ParameterFactory {
    private static final String CONTENT = "$T t = ($T)target";
    private MethodSpec.Builder methodBuilder;
    private Messager messager;
    private Types typeUtils;
    private ClassName className;

    private ParameterFactory(Builder builder) {
        this.messager = builder.messager;
        this.className = builder.className;
        typeUtils = builder.typeUtils;

        methodBuilder = MethodSpec.methodBuilder(Constants.PARAMETER_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(builder.parameterSpec);
    }

    /**
     * 添加方法体内容的第一行：MainActivity t = (MainActivity)target;
     */
    public void addFirstStatement() {
        methodBuilder.addStatement(CONTENT, className, className);
    }

    public MethodSpec build() {
        return methodBuilder.build();
    }

    /**
     * 构建方法体内容：
     * 如： t.name = t.getIntent().getStringExtra("name");
     * @param element 被注解的属性元素
     */
    public void buildStatement(Element element) {
        TypeMirror typeMirror = element.asType();
        int type = typeMirror.getKind().ordinal();
        String fieldName = element.getSimpleName().toString();
        String annotationValue = element.getAnnotation(Parameter.class).name();
        annotationValue = EmptyUtils.isEmpty(annotationValue) ? fieldName : annotationValue;
        String finalValue = "t." + fieldName;
        String methodContent = finalValue + " = t.getIntent().";

        if (type == TypeKind.INT.ordinal()) {
            methodContent += "getIntExtra($S, " + finalValue + ")";
        } else if (type == TypeKind.BOOLEAN.ordinal()) {
            methodContent += "getBooleanExtra($S, " + finalValue + ")";
        } else {
            //TypeKind枚举类型不包含String，故采用以下方式来判断
            if (typeMirror.toString().equalsIgnoreCase(Constants.STRING)) {
                methodContent += "getStringExtra($S)";
            }
        }

        if (methodContent.endsWith(")")) {
            methodBuilder.addStatement(methodContent, annotationValue);
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "Intent传递参数类型目前只支持String，int，boolean");
        }
    }

    public static class Builder {
        private Messager messager;
        private Elements elementUtils;
        private Types typeUtils;
        private ClassName className;
        private ParameterSpec parameterSpec;

        public Builder(ParameterSpec parameterSpec) {
            this.parameterSpec = parameterSpec;
        }

        public Builder setMessager(Messager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setElementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder setTypeUtils(Types typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder setClassName(ClassName className) {
            this.className = className;
            return this;
        }

        public ParameterFactory build() {
            if (parameterSpec == null) {
                throw new IllegalArgumentException("parameterSpec为空.");
            }
            if (className == null) {
                throw new IllegalArgumentException("方法内容中的className为空");
            }
            if (messager == null) {
                throw new IllegalArgumentException("messager用来报告错误，警告等提示信息，不能为空.");
            }
            return new ParameterFactory(this);
        }

    }

}
