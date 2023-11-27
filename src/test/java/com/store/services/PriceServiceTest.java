package com.store.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class PriceServiceTest {
    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceService = new PriceService();
    }

    @Test
    void testCanComputeTotalPrice() {
        Double basePrice = 10.0;
        Double totalPrice = priceService.computeTotalPrice(basePrice);

        Double expected = basePrice + priceService.computeTaxFromBasePrice(basePrice) + priceService.getDeliveryFee();

        Assertions.assertEquals(totalPrice, expected);
    }
}
