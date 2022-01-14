package ir.mahdi.postservice.service.impl;

import ir.mahdi.postservice.base.service.impl.BaseServiceImpl;
import ir.mahdi.postservice.domain.Product;
import ir.mahdi.postservice.repository.ProductRepository;
import ir.mahdi.postservice.service.ProductService;
import ir.mahdi.postservice.util.ProductUtil;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long, ProductRepository> implements ProductService {

    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }

    /**
     * get optimized list of products to send pack of products with max value and min weight
     *
     * @param packOfProduct String input of user for packing products eg: "(ID,WEIGHT,$VALUE) (ID,WEIGHT,$VALUE)..."
     * @param maxWeight     max weight of box
     * @return optimized list of products to send
     */
    @Override
    public List<Long> pack(String packOfProduct, int maxWeight) {
        return ProductUtil.pack(packOfProduct, maxWeight);
    }

    /**
     * save list of products in database by string input or update already exists products by id
     * @param packOfProduct String input of user for packing products eg: "(<ID>,<WEIGHT>,$<VALUE>) (<ID>,<WEIGHT>,$<VALUE>)..."
     * @return              List of products that saves on database
     */
    @Override
    public List<Product> store(String packOfProduct) {
        List<Product> products = ProductUtil.getProductFromPackString(packOfProduct);
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            result.add(save(product));
        }
        return result;
    }

    /**
     * get average price in weight range[minWeight, maxWeight]
     * @param minWeight minimum of weight range
     * @param maxWeight maximum of weight range
     * @return          if range has items average price in weight range otherwise -1
     */
    @Override
    public float avgPrice(float minWeight, float maxWeight) {
        try {
            return repository.getAverageByMinAndMaxWeight(minWeight, maxWeight);
        }catch (AopInvocationException e){
            return -1;
        }
    }


}
