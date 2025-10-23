package practice.hotel_system.bl.discount_strategy;

import practice.hotel_system.entity.Bookings;

import java.math.BigDecimal;

public interface DiscountStrategy {

    //calc the discount amount for a booking
    BigDecimal calculateDiscount(Bookings booking, BigDecimal originalPrice);

    //get discount name (related to the strategy)
    String getStrategyName();

    //check if the discount is applicable
    boolean isApplicable(Bookings booking);
}
