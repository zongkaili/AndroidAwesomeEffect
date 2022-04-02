package com.kelly.effect.design_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。
 * 这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 一个 Builder 类会一步一步构造最终的对象。该 Builder 类是独立于其他对象的。
 *
 * @author zyb
 */
public class BuilderPattern {
    public interface Item {
        String name();

        Packing packing();

        float price();
    }

    public interface Packing {
        String pack();
    }

    public static class Wrapper implements Packing {
        @Override
        public String pack() {
            return "Wrapper";
        }
    }

    public static class Bottle implements Packing {
        @Override
        public String pack() {
            return "Bottle";
        }
    }

    public static abstract class Burger implements Item {
        @Override
        public Packing packing() {
            // 默认打包方式：袋装
            return new Wrapper();
        }

        @Override
        public abstract float price();
    }

    public static abstract class ColdDrink implements Item {
        @Override
        public Packing packing() {
            // 默认打包方式：瓶装
            return new Bottle();
        }

        @Override
        public abstract float price();
    }

    public static class VegBurger extends Burger {
        @Override
        public float price() {
            return 25.0f;
        }

        @Override
        public String name() {
            return "Veg Burger";
        }
    }

    public static class ChickenBurger extends Burger {
        @Override
        public float price() {
            return 50.5f;
        }

        @Override
        public String name() {
            return "Chicken Burger";
        }
    }

    public static class Coke extends ColdDrink {
        @Override
        public float price() {
            return 30.0f;
        }

        @Override
        public String name() {
            return "Coke";
        }
    }

    public static class Pepsi extends ColdDrink {
        @Override
        public float price() {
            return 35.0f;
        }

        @Override
        public String name() {
            return "Pepsi";
        }
    }

    public static class Meal {
        private final List<Item> items = new ArrayList<>();

        public void addItem(Item item) {
            items.add(item);
        }

        public float getCost() {
            float cost = 0.0f;
            for (Item item : items) {
                cost += item.price();
            }
            return cost;
        }

        public void showItems() {
            for (Item item : items) {
                System.out.print("Item : " + item.name());
                System.out.print(", Packing : " + item.packing().pack());
                System.out.println(", Price : " + item.price());
            }
        }
    }

    /**
     * 实际的 Builder 类，负责创建 Meal
     */
    public static class MealBuilder {
        public Meal prepareVegMeal() {
            Meal meal = new Meal();
            meal.addItem(new VegBurger());
            meal.addItem(new Coke());
            return meal;
        }

        public Meal prepareNonVegMeal() {
            Meal meal = new Meal();
            meal.addItem(new ChickenBurger());
            meal.addItem(new Pepsi());
            return meal;
        }
    }

    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();

        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost: " + vegMeal.getCost());

        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost: " + nonVegMeal.getCost());
    }
    // 输出如下：
    /*
    Veg Meal
    Item : Veg Burger, Packing : Wrapper, Price : 25.0
    Item : Coke, Packing : Bottle, Price : 30.0
    Total Cost: 55.0

    Non-Veg Meal
    Item : Chicken Burger, Packing : Wrapper, Price : 50.5
    Item : Pepsi, Packing : Bottle, Price : 35.0
    Total Cost: 85.5
     */

}
