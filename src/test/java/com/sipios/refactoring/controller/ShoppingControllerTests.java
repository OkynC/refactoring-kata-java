package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.models.Customer;
import com.sipios.refactoring.models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;


    @Test
    void should_not_throw() { // NC: good name for a test
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Customer(new Item[]{}, "STANDARD_CUSTOMER"))
        );
    }

    // NC: add tests
    @Test
    void emptyItemListGetPriceOf0() {
        Customer standardCustomer = new Customer(new Item[0], "STANDARD_CUSTOMER");
        Assertions.assertEquals("0.0", controller.getPrice(standardCustomer));

        Customer standardCustomer2 = new Customer(new Item[]{}, "STANDARD_CUSTOMER");
        Assertions.assertEquals("0.0", controller.getPrice(standardCustomer2));

        Customer premiumCustomer = new Customer(new Item[0], "PREMIUM_CUSTOMER");
        Assertions.assertEquals("0.0", controller.getPrice(premiumCustomer));

        Customer platinumCustomer = new Customer(new Item[0], "PLATINUM_CUSTOMER");
        Assertions.assertEquals("0.0", controller.getPrice(platinumCustomer));
    }

    @Test
    void getPriceForAGivenShoppingList() {
        Item[] bag1 = new Item[]{new Item("TSHIRT", 1), new Item("DRESS", 1), new Item("JACKET", 1)};

        Customer standardCustomer = new Customer(bag1, "STANDARD_CUSTOMER");
        Assertions.assertEquals("180.0", controller.getPrice(standardCustomer));

        Customer premiumCustomer = new Customer(bag1, "PREMIUM_CUSTOMER");
        Assertions.assertEquals("162.0", controller.getPrice(premiumCustomer));

        Customer platinumCustomer = new Customer(bag1, "PLATINUM_CUSTOMER");
        Assertions.assertEquals("90.0", controller.getPrice(platinumCustomer));
    }

    @Test
    void test2(){
        // Set Date to the 10th of June => discount period
        Clock clock = Clock.fixed(Instant.parse("2021-06-10T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime dateTime = LocalDateTime.now(clock);

        Item[] bag1 = new Item[]{new Item("TSHIRT", 1), new Item("DRESS", 1), new Item("JACKET", 1)};

        Customer standardCustomer = new Customer(bag1, "STANDARD_CUSTOMER");
        Assertions.assertEquals("180.0", controller.getPrice(standardCustomer));
    }
}
