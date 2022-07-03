package com.kelly.practice.design_mode.factory;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 工厂模式
 * 符合开闭原则
 */
class NormalFactory {
    //1.定义鞋这种产品的抽象基类
    static abstract class Shoes{
        public abstract void showLogo();
    }

    //2.定义具体品牌的鞋类，继承鞋抽象基类
    static class NikeShoes extends Shoes{

        @Override
        public void showLogo() {
            System.out.println("我是耐克鞋子");
        }
    }

    //2.定义具体品牌的鞋类，继承鞋抽象基类
    static class AdidasShoes extends Shoes{

        @Override
        public void showLogo() {
            System.out.println("我是阿迪鞋子");
        }
    }

    //3.定义工厂类接口
    interface ShoesFactory {
        public abstract Shoes makeShoes();
    }

    //4.定义生产具体产品工厂类，如生成耐克鞋的工厂
    static class NikeShoesFactory implements ShoesFactory{
        @Override
        public Shoes makeShoes() {
            return new NikeShoes();
        }
    }

    //4.定义实现具体产品工厂类，如生成阿迪鞋的工厂
    static class AdidasShoesFactory implements ShoesFactory{
        @Override
        public Shoes makeShoes() {
            return new AdidasShoes();
        }
    }

    //工厂方法模式
    public static class FactoryMethodExample {
        public static void main(String[] args) {
            NikeShoesFactory nikeShoesFactory = new NikeShoesFactory();
            nikeShoesFactory.makeShoes().showLogo();

            AdidasShoesFactory adidasShoesFactory = new AdidasShoesFactory();
            adidasShoesFactory.makeShoes().showLogo();
        }
    }
}
