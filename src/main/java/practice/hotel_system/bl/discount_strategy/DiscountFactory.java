package practice.hotel_system.bl.discount_strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.hotel_system.entity.Bookings;

import java.math.BigDecimal;
import java.util.List;

//selects an appropriate discount strategy
@Component
public class DiscountFactory {

    private final List<DiscountStrategy> discountStrategies;
    private final NoDiscount noDiscount;

    @Autowired
    public DiscountFactory(List<DiscountStrategy> discountStrategies, NoDiscount noDiscount) {
        this.discountStrategies = discountStrategies;
        this.noDiscount = noDiscount;
    }

    public DiscountStrategy getBestDiscountStrategy(Bookings booking, BigDecimal originalPrice) {
        DiscountStrategy bestStrategy = noDiscount;
        BigDecimal maxDiscount = BigDecimal.ZERO;

        for (DiscountStrategy strategy : discountStrategies) {
            if (strategy instanceof NoDiscount) {
                continue;
            }

            if (strategy.isApplicable(booking)) {
                BigDecimal discount = strategy.calculateDiscount(booking, originalPrice);
                if (discount.compareTo(maxDiscount) > 0) {
                    maxDiscount = discount;
                    bestStrategy = strategy;
                }
            }
        }

        return bestStrategy;
    }

    public List<DiscountStrategy> getAllDiscountStrategies() {
        return discountStrategies;
    }
}
