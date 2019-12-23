package com.kelly.modular.annotation.model;

import javax.lang.model.element.Element;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class RouterBean {

    public enum Type {
        ACTIVITY
    }

    private Type type;
    //类节点
    private Element element;
    //被注解的类对象
    private Class<?> clazz;
    //路由的组名
    private String group;
    //路由的地址
    private String path;

    private RouterBean(Builder builder) {
        this.element = builder.element;
        this.group = builder.group;
        this.path = builder.path;
    }

    private RouterBean(Type type, Class<?> clazz, String group, String path) {
         this.type = type;
         this.clazz = clazz;
         this.group = group;
         this.path = path;
    }

    public static RouterBean create(Type type, Class<?> clazz, String group, String path) {
        return new RouterBean(type, clazz, group, path);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Type getType() {
        return type;
    }

    public Element getElement() {
        return element;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }

    public final static class Builder {
        //类节点
        private Element element;
        //路由的组名
        private String group;
        //路由的地址
        private String path;

        public Builder setElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public RouterBean build() {
            return new RouterBean(this);
        }
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "type=" + type +
                ", group='" + group + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
