package ir.mahdi.postservice.service;

import ir.mahdi.postservice.base.service.BaseService;
import ir.mahdi.postservice.domain.Product;

import java.util.List;

public interface ProductService extends BaseService<Product, Long> {
    List<Long> pack(String packOfProduct, int maxWeight);

    List<Product> store(String packOfProduct);

    float avgPrice(float minWeight, float maxWeight);
}
