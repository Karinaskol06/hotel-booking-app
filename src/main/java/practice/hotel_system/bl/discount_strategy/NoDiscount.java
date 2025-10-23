package practice.hotel_system.bl.discount_strategy;

import org.springframework.stereotype.Component;
import practice.hotel_system.entity.Bookings;

import java.math.BigDecimal;

@Component
public class NoDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(Bookings booking, BigDecimal originalPrice) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getStrategyName() {
        return "No Discount";
    }

    @Override
    public boolean isApplicable(Bookings booking) {
        return true; //is always applicable as a fallback
    }
}
