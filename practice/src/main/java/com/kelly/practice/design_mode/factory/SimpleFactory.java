package com.kelly.practice.design_mode.factory;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 简单工厂设计模式
 * 缺点：不符合开闭原则，增加一种类型时需要修改工厂类
 */
class SimpleFactory {
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

    //3.定义简单工厂方法类，使用一个静态工厂方法来根据不同的品牌条件来产生不同品牌的鞋
    static class SimpleFactoryImpl {
        public static Shoes makeShoes(String brand){
            Shoes shoes = null;
            switch (brand){
                case "nike":
                    shoes = new NikeShoes();
                    break;
                case "adidas":
                    shoes = new AdidasShoes();
                    break;
                default:
                    System.out.println("错误的品牌");
            }
            return shoes;
        }
    }

    //使用简单工厂模式
    public static class SimpleFactoryExample{
        public static void main(String[] args) {
            //使用工厂模式创建耐克品牌的鞋子
            Shoes nikeShoes = SimpleFactoryImpl.makeShoes("nike");
            nikeShoes.showLogo();

            //使用工厂模式创建阿迪品牌的鞋子
            Shoes adidasShoes = SimpleFactoryImpl.makeShoes("adidas");
            adidasShoes.showLogo();
        }
    }
}
