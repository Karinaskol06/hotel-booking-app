package practice.hotel_system.bl.discount_strategy;

import java.math.BigDecimal;

//bundles all pricing info together
public class PriceCalcResult {

    private BigDecimal originalPrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private String strategyName;

    public PriceCalcResult(BigDecimal originalPrice, BigDecimal discountAmount,
                           BigDecimal finalPrice, String strategyName) {
        this.originalPrice = originalPrice;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.strategyName = strategyName;
    }

    public boolean hasDiscount() {
        return discountAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal getDiscountPercentage() {
        if (originalPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return discountAmount.divide(originalPrice, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return String.format("PriceCalculation{original=%.2f, discount=%.2f (%.1f%%), final=%.2f, type='%s'}",
                originalPrice, discountAmount, getDiscountPercentage(), finalPrice, strategyName);
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
