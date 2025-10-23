package practice.hotel_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.hotel_system.bl.discount_strategy.DiscountStrategy;
import practice.hotel_system.bl.discount_strategy.PriceCalcResult;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.service.DiscountService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public String showAllDiscountStrategies(Model model) {
        List<DiscountStrategy> strategies = discountService.getAllDiscountStrategies();
        model.addAttribute("strategies", strategies);
        return "discounts/list";
    }

    //extra methods for testing discount calc
    @GetMapping("/calculate")
    public String showDiscountForm(Model model) {
        model.addAttribute("booking", new Bookings());
        model.addAttribute("price", BigDecimal.ZERO);
        return "discounts/form";
    }

    @PostMapping("/calculate")
    public String calculateDiscount(@ModelAttribute Bookings booking,
                                    @RequestParam("price") BigDecimal originalPrice,
                                    Model model) {
        PriceCalcResult result = discountService.calcFinalPrice(booking, originalPrice);
        model.addAttribute("price", originalPrice);
        model.addAttribute("booking", booking);
        model.addAttribute("result", result);
        return "discounts/result";
    }
}
