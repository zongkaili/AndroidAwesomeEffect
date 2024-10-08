package com.kelly.practice.design_mode.factory;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 抽象工厂
 */
class AbstractFactory {
    //1.1 定义鞋的抽象类
    static abstract class Shoes {
        public abstract void showLogo();
    }

    //1.2 定义衣服的抽象类
    static abstract class Clothe {
        public abstract void showLogo();
    }

    //2.1 定义耐克具体品牌的鞋类，继承鞋抽象基类
    static class NikeShoes extends Shoes {
        @Override
        public void showLogo() {
            System.out.println("我是耐克鞋子");
        }
    }

    //2.2 定义阿迪具体品牌的鞋类，继承鞋抽象基类
    static class AdidasShoes extends Shoes {
        @Override
        public void showLogo() {
            System.out.println("我是阿迪鞋子");
        }
    }

    //2.3 定义耐克具体品牌的衣服类，继承衣服抽象基类
    static class NikeClothe extends Clothe {
        @Override
        public void showLogo() {
            System.out.println("我是耐克衣服");
        }
    }

    //2.4 定义阿迪具体品牌的衣服类，继承衣服抽象基类
    static class AdidasClothe extends Clothe {
        @Override
        public void showLogo() {
            System.out.println("我是阿迪衣服");
        }
    }

    //3.定义抽象工厂接口
    interface IAbstractFactory {
        Shoes makeShoes();

        Clothe makeClothe();
    }

    //4.1 定义生产耐克产品的的具体工厂类，这里的工厂类以品牌来定义
    static class NikeFactory implements IAbstractFactory {
        @Override
        public Shoes makeShoes() {
            return new NikeShoes();
        }

        @Override
        public Clothe makeClothe() {
            return new NikeClothe();
        }
    }

    //4.2 定义生产阿迪产品的的具体工厂类，这里的工厂类以品牌来定义
    static class AdidasFactory implements IAbstractFactory {
        @Override
        public Shoes makeShoes() {
            return new AdidasShoes();
        }

        @Override
        public Clothe makeClothe() {
            return new AdidasClothe();
        }
    }

    public static class AbstractFactoryExample {
        public static void main(String[] args) {
            //使用耐克工厂生产耐克的衣服和鞋
            NikeFactory nikeFactory = new NikeFactory();
            nikeFactory.makeShoes().showLogo();
            nikeFactory.makeClothe().showLogo();

            System.out.println("");
            AdidasFactory adidasFactory = new AdidasFactory();
            adidasFactory.makeShoes().showLogo();
            adidasFactory.makeClothe().showLogo();
        }
    }
}
