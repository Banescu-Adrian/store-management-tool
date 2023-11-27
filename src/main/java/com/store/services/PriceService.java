package com.store.services;

import org.springframework.stereotype.Service;

@Service
public class PriceService {
    public Double computeTotalPrice(Double basePrice) {
        return basePrice + computeTaxFromBasePrice(basePrice) + getDeliveryFee();
    }

    public Double computeTaxFromBasePrice(Double basePrice) {
        return basePrice * getTax();
    }

    private Double getTax() {
        return 0.0;
    }

    public Double getDeliveryFee() {
        return 0.0;
    }
}
