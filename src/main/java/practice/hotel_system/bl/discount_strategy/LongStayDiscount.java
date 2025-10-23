package practice.hotel_system.bl.discount_strategy;

import org.springframework.stereotype.Component;
import practice.hotel_system.entity.Bookings;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

//concrete strategy that implements discount 20% for stays longer than 5 days
@Component
public class LongStayDiscount implements DiscountStrategy {

    private static final int MIN_DAYS = 5;
    private static final BigDecimal DISCOUNT = new BigDecimal("0.20");

    @Override
    public BigDecimal calculateDiscount(Bookings booking, BigDecimal originalPrice) {
        if (!isApplicable(booking)) {
            return BigDecimal.ZERO;
        }

        return originalPrice.multiply(DISCOUNT)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String getStrategyName() {
        return "Long Stay Discount (20% off for 5+ nights)";
    }

    @Override
    public boolean isApplicable(Bookings booking) {
        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        return daysBetween >= MIN_DAYS;
    }
}
