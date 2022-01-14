package ir.mahdi.postservice.controller;

import ir.mahdi.postservice.domain.Product;
import ir.mahdi.postservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/pack")
    public List<Long> pack(int maxWeight, String packOfProduct) {
        return productService.pack(packOfProduct, maxWeight);
    }

    @PostMapping("/save-in-db")
    public List<Product> saveInDB(String packOfProduct) {
        return productService.store(packOfProduct);
    }

    @GetMapping("/avg-price/weight/{minWeight}-{maxWeight}")
    public float avgPrice(@PathVariable float minWeight,@PathVariable float maxWeight) {
        return productService.avgPrice(minWeight, maxWeight);
    }


}
