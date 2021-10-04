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
            return priceToPay.toString();
        }

        InternalCustomer internalCustomer = InternalCustomer.fromCustomer(customer);

        // Compute discount for customer
        if (internalCustomer.getDiscount() == 0) {
            logger.warn("Customer type is unknown");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer type is unknown");
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
        try {
            priceToPay = Arrays.stream(customer.getItems()).map((elem) -> elem.getPrice(internalCustomer.getDiscount(), isDiscountPeriod)).reduce(0.0, Double::sum);
            logger.info("Calculated price to pay is {}", priceToPay);
        } catch (Exception e) {
            logger.warn("Item type unknown");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item type is unknown");
        }

        // Check if price is below business acceptance
        if (internalCustomer.overMaxPrice(priceToPay)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Price (%s) is too high for %s", priceToPay, internalCustomer.logName));
        }
        return priceToPay.toString();
    }
}

