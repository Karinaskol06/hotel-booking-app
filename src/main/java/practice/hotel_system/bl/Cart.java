package practice.hotel_system.bl;

import lombok.Getter;
import lombok.Setter;
import practice.hotel_system.entity.Apartments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.temporal.ChronoUnit;

@Getter
@Setter

public class Cart {
    List<ItemCart> cart;
    //price of the whole purchase
    public double totalValue;
    public double sumElements;

    public Cart() {
        cart = new ArrayList<>();
        totalValue = 0;
        sumElements = 0;
    }

    //addApartmentToCart
    public void addNewItemToCart(Apartments apartment, LocalDate checkin, LocalDate checkout) {
        // Check if item already exists in cart
        for (ItemCart item : cart) {
            if (item.getApartment().getId().equals(apartment.getId())) {
                // Update existing item instead of adding a new one
                item.setCheckin(checkin);
                item.setCheckout(checkout);
                return;
            }
        }
        // Add new item if it doesn't exist
        cart.add(new ItemCart(apartment, checkin, checkout));
    }

    //deleteBookingsFromCart
    public synchronized void deleteItemFromCart(Apartments apartment) {
        for (ItemCart itemCart : cart) {
            if (Objects.equals(itemCart.getApartment().getId(), apartment.getId())) {
                cart.remove(itemCart);
                break;
            }
        }
    }

    //deleteAllFromCart
    public synchronized void deleteAllFromCart() {
        cart.clear();
        totalValue = 0;
        sumElements = 0;
    }

    //getTotalValue
    public BigDecimal getTotalValue() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemCart i : cart) {
            long days = ChronoUnit.DAYS.between(i.getCheckin(), i.getCheckout());
            BigDecimal pricePerNight = i.getApartment().getPricePerNight();
            BigDecimal totalForItem = pricePerNight.multiply(BigDecimal.valueOf(days));

            totalPrice = totalPrice.add(totalForItem);
        }

        return totalPrice;
    }

    //getSumElements
    public synchronized int getSumElements() {
        return cart.size();
    }
}