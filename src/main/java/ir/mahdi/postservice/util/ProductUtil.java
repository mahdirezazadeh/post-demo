package ir.mahdi.postservice.util;

import ir.mahdi.postservice.base.BaseEntity;
import ir.mahdi.postservice.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductUtil {

    /**
     * get optimized list of products to send pack of products with max value and min weight
     *
     * @param packOfProduct String input of user for packing products eg: "(ID,WEIGHT,$VALUE) (ID,WEIGHT,$VALUE)..."
     * @param maxWeight     max weight of box
     * @return optimized list of products to send
     */
    public static List<Long> pack(String packOfProduct, int maxWeight) {
        List<Product> products = ProductUtil.getProductFromPackString(packOfProduct);

//        remove products that weights is more than max weight
        Predicate<Product> productFilter = p -> p.getWeight() <= maxWeight;
        products = products.stream().filter(productFilter)
                .collect(Collectors.toList());

        if (products.isEmpty())
            return new ArrayList<>();

//        get prices and weights from products
        List<Float> prices = products.stream().map(Product::getPrice).collect(Collectors.toList());
        List<Float> weights = products.stream().map(Product::getWeight).collect(Collectors.toList());

//          calculate knapsack 2-D array
        float[][] knapsackResult = ProductUtil.knapsack(prices, weights, maxWeight);

//        get items indexes list from knapsack result
        List<Integer> itemsIndex = ProductUtil.getItemsByKnapsackResult(knapsackResult, weights);

//        add items by index
        List<Product> items = new ArrayList<>();
        for (Integer index : itemsIndex) {
            items.add(products.get(index));
        }

        ProductUtil.getLighterBox(products, items);

        return items.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    /**
     * get all products from string input
     *
     * @param packOfProduct string input by user
     * @return List of products
     */
    public static List<Product> getProductFromPackString(String packOfProduct) {

//        remove all spaces, ), and dollar sign
        packOfProduct = packOfProduct.replaceAll("\s+|\\)|\\$", "");

//        split d=input by (
        String[] productsString = packOfProduct.split("\\(");

        List<Product> products = new ArrayList<>();


        for (int index = 1; index < productsString.length; index++) {
//            split values of each product string by comma
            String[] split = productsString[index].split(",");

//            create product from values
            Product product = new Product(
                    Long.parseLong(split[0]),
                    Float.parseFloat(split[2]),
                    Float.parseFloat(split[1]));

//            add product to list
            products.add(product);
        }

        return products;
    }

    /**
     * knapsack problem By(N*Max_weight)
     *
     * @param prices    list of prices for each item
     * @param weights   list of weights for each item
     * @param maxWeight max weight of knapsack
     * @return a 2-D array that resulted by knapsack
     */
    private static float[][] knapsack(List<Float> prices, List<Float> weights, int maxWeight) {
        int productNumbers = prices.size();
        float[][] w = new float[productNumbers + 1][maxWeight + 1];

        for (int row = 0; row <= productNumbers; row++) {
            for (int weight = 0; weight <= maxWeight; weight++) {
                if (row == 0 || weight == 0) {
                    w[row][weight] = 0;
                } else if (weights.get(row - 1) <= weight) {
//                    Previous weight of the box before adding the item
                    int previousWeight = (int) (weight - Math.floor(weights.get(row - 1)));
//                    new price of box if we include current item(item by index "row-1")
                    float newPrice = prices.get(row - 1) + w[row - 1][previousWeight];
//                    calculate max of this weight for price whether by including new item or without new item
                    w[row][weight] = Math.
                            max(newPrice, w[row - 1][weight]);

                } else {
                    w[row][weight] = w[row - 1][weight];
                }
            }
        }
        return w;

    }

    /**
     * get index of knapsack problem result
     *
     * @param knapsackResult 2-D array resulted by knapsack
     * @param weights        weights of items
     * @return indexes of items we want to put in the box
     */
    private static List<Integer> getItemsByKnapsackResult(float[][] knapsackResult, List<Float> weights) {
        List<Integer> items = new ArrayList<>();
        int item = knapsackResult.length - 1;
        int weight = knapsackResult[0].length - 1;

        while (item > 0 && weight > 0) {
            if (knapsackResult[item][weight] == knapsackResult[item - 1][weight]) {
//                current item not included
                item--;
            } else {
//                current item is indexed by item-1 so we reduce item by 1
                item--;

//                item included
                items.add(item);

//                if item included reduce item weight from knapsack weight
                weight -= (int) (Math.floor(weights.get(item)));
            }
        }
        return items;

    }

    /**
     * reduce weight of box
     *
     * @param products all products listed by user
     * @param items    all items currently in the box
     */
    private static void getLighterBox(List<Product> products, List<Product> items) {
        for (int index = 0; index < items.size(); index++) {
            Product item = items.get(index);

//            find from all products with equal price and less weight
            List<Product> filteredProducts = products.stream()
                    .filter(p -> p.getPrice() == item.getPrice() && p.getWeight() < item.getWeight() && !items.contains(p))
                    .collect(Collectors.toList());


            if (!filteredProducts.isEmpty()) {
//                sort by weight
                filteredProducts.sort((p1, p2) -> Float.compare(p1.getWeight(), p2.getWeight()));

//                replace lighter item with heavier one
                items.remove(index);
                items.add(index, filteredProducts.get(0));
            }
        }
    }
}
