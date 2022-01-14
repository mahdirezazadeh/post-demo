package ir.mahdi.postservice.domain;

import ir.mahdi.postservice.base.BaseEntity;
import ir.mahdi.postservice.exception.ArgumentLimitException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity<Long> {
    private static final String PRICE = "price";
    private static final String WEIGHT = "weight";
    private static final float PRICE_LIMIT = 100;
    private static final float WEIGHT_LIMIT = 100;

    @Column(name = PRICE)
    private float price;

    @Column(name = WEIGHT)
    private float weight;


    public void setPrice(float price) {
        if (price > PRICE_LIMIT)
            throw new ArgumentLimitException(String.format("Price of Product can not be more than $%.2f", PRICE_LIMIT));
        this.price = price;
    }

    public void setWeight(float weight) {
        if (weight > WEIGHT_LIMIT)
            throw new ArgumentLimitException(String.format("Weight of Product can not be more than %.2f", WEIGHT_LIMIT));
        this.weight = weight;
    }

    public Product(long id, float price, float weight) {
        setPrice(price);
        setWeight(weight);
        setId(id);
    }
}
