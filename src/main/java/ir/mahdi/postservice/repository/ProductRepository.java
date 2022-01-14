package ir.mahdi.postservice.repository;

import ir.mahdi.postservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select avg(p.price) from Product p where p.weight >= ?1 and p.weight <= ?2")
    float getAverageByMinAndMaxWeight(float minWeight, float maxWeight);
}
