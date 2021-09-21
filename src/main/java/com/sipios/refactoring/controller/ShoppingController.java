package com.sipios.refactoring.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.sipios.refactoring.models.Customer;
import com.sipios.refactoring.models.InternalCustomer;
import com.sipios.refactoring.models.InternalItem;
import com.sipios.refactoring.models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    // NC: Add a swagger and doc...

    // NC: either use logger or remove it
    private final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    // NC: Change signature => impossible ! (as per the requirements)
    // Should be changed to a GetMapping with request param => list and customer type
    public String getPrice(@RequestBody Customer customer) {
        Double priceToPay = 0.0;

        // If no item => stop soon and answer 0
        if (customer.getItems() == null) {
            logger.info("No item in cart => no price to calculate");
            return priceToPay.toString(); // NC: should return a double
        }

        double customerDiscount = InternalCustomer.valueOf(customer.getType()).getDiscount();

        // Compute discount for customer
        if (customerDiscount == 0) {
            logger.warn("Customer type is unknown");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Compute if period is Winter or Summer discounts periods
        // Winter period => 5th to 15th of January
        // Summer period => 5th to 15th of June
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        boolean isDiscountPeriod = (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5)
            && (cal.get(Calendar.MONTH) == Calendar.JUNE || cal.get(Calendar.MONTH) == Calendar.JANUARY);

        // Compute total amount depending on the types and quantity of product and add discount modifiers
        priceToPay = Arrays.stream(customer.getItems()).map((elem) -> {
            return elem.getPrice(customerDiscount, isDiscountPeriod);
        }).reduce(0.0, Double::sum);

        logger.info("Calculated price to pay is {}", priceToPay);

        // NC => Exception process to remove
        try {
            switch (customer.getType()) {
                case "PREMIUM_CUSTOMER":
                    if (priceToPay > 800) {
                        throw new Exception("Price (" + priceToPay + ") is too high for premium customer");
                    }
                    break;
                case "PLATINUM_CUSTOMER":
                    if (priceToPay > 2000) {
                        throw new Exception("Price (" + priceToPay + ") is too high for platinum customer");
                    }
                    break;
                default:
                    if (priceToPay > 200) {
                        throw new Exception("Price (" + priceToPay + ") is too high for standard customer");
                    }
                    break;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        // NC: Maybe add a OK status XD
        return priceToPay.toString();
    }
}

