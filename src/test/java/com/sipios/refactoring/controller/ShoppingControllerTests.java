package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;



    @Test
    void should_not_throw() { // NC: good name for a test
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    // NC: add tests
    @Test
    void test1() {
        Body standard_customer = new Body(new Item[0], "STANDARD_CUSTOMER");
        Assertions.assertEquals("0.0", controller.getPrice(standard_customer));
    }
}
