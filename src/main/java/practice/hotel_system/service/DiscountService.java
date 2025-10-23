package practice.hotel_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.hotel_system.bl.discount_strategy.DiscountFactory;
import practice.hotel_system.bl.discount_strategy.DiscountStrategy;
import practice.hotel_system.bl.discount_strategy.PriceCalcResult;
import practice.hotel_system.entity.Bookings;

import java.math.BigDecimal;
import java.util.List;


//handles discount calculation
@Service
public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);
    private final DiscountFactory discountFactory;

    @Autowired
    public DiscountService(DiscountFactory discountFactory) {
        this.discountFactory = discountFactory;
    }

    public PriceCalcResult calcFinalPrice(Bookings booking, BigDecimal originalPrice) {
        //factory to get the right discount strategy
        DiscountStrategy concreteStrategy = discountFactory
                .getBestDiscountStrategy(booking, originalPrice);
        //calc discount amount
        BigDecimal discountAmount = concreteStrategy.calculateDiscount(booking, originalPrice);
        //calc final price
        BigDecimal finalPrice = originalPrice.subtract(discountAmount);

        logger.info("Applied discount strategy: {} | Original: {} | Discount: {} | Final: {}",
                concreteStrategy.getStrategyName(), originalPrice, discountAmount, finalPrice);

        //return dto obj with all details
        return new PriceCalcResult(
                originalPrice,
                discountAmount,
                finalPrice,
                concreteStrategy.getStrategyName()
        );
    }

    public List<DiscountStrategy> getAllDiscountStrategies() {
        return discountFactory.getAllDiscountStrategies();
    }

    //just a discount amount (without calc)
    public BigDecimal getDiscountAmount(Bookings booking, BigDecimal originalPrice) {
        DiscountStrategy strategy = discountFactory.getBestDiscountStrategy(booking, originalPrice);
        return strategy.calculateDiscount(booking, originalPrice);
    }
}
