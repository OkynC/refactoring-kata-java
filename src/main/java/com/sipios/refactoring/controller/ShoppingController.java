package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.sipios.refactoring.models.Customer;
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
    private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    // NC: Change signature
    // NC: Change variables names.....
    public String getPrice(@RequestBody Customer b) {
        double p = 0;
        double d;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer <= NC: verify comments
        // NC: use a switch and therefore maybe an enum
        if (b.getType().equals("STANDARD_CUSTOMER")) {
            d = 1;
        } else if (b.getType().equals("PREMIUM_CUSTOMER")) {
            d = 0.9;
        } else if (b.getType().equals("PLATINUM_CUSTOMER")) {
            d = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        // NC: comparison are to be changed and maybe check if cleaner way can be done
        if (
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 5
            ) &&
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 0
            )
        ) {
            if (b.getItems() == null) {
                return "0"; // NC: should return a INT !!!!
            }

            // NC: use a forEach
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                // NC: use a switch and an enum
                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getQuantity() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getQuantity() * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getQuantity() * d;
                }
                // NC: remove (useless?) commented out code
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (b.getItems() == null) {
                return "0"; // NC: should return a INT !!!!
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                // NC: same code as above, but with a "discount" => factorisation to do
                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getQuantity() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getQuantity() * 0.8 * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getQuantity() * 0.9 * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        // NC: is an exception process in order ?
        try {
            if (b.getType().equals("STANDARD_CUSTOMER")) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getType().equals("PREMIUM_CUSTOMER")) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getType().equals("PLATINUM_CUSTOMER")) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        // NC: Maybe add a OK status XD
        return String.valueOf(p);
    }
}

